package Parkeersimulator.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class SimulatorLogic extends AbstractModel implements Runnable{

	private static final String AD_HOC = "1";   // identificatie voor AdHocCars
	private static final String PASS = "2";     // identificatie voor ParkingPassCars
    private static final String RES = "3";      // identificatie voor CarReservations

    private ArrayList<CarReservation> carReservationList = new ArrayList<>(); // lijst met opkomende CarReservations
    private ArrayList<ReservationCar> reservationCarList = new ArrayList<>(); // lijst met opkomende ReservationCars
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private GarageLogic garageLogic;

    private boolean run; // of de simulatie momenteel draait

    private int day = 0;    // huidige dag in de week
    private int hour = 0;   // huidige uur op de dag
    private int minute = 0; // huidige minuut in het uur

    private int tickPause = 10;     // pauze tussen ticks in milliseconden
    private int currentTick = 0;    // huidige tick
    private int maxTicks = 10080;   // maximale hoeveelheid ticks

    private int parkingFee = 1;
    private int totalEarnings = 0;
    private int dayValue = 0;
    private HashMap<Integer, Integer> dayEarnings;

    int weekDayArrivals= 80;        // gemiddelde hoeveelheid AdHocCars die doordeweeks arriveren per uur
    int weekendArrivals = 160;      // gemiddelde hoeveelheid AdHocCars die in het weekend arriveren per uur
    int weekDayPassArrivals= 30;    // gemiddelde hoeveelheid ParkingPassCars die doordeweeks arriveren per uur
    int weekendPassArrivals = 5;    // gemiddelde hoeveelheid ParkingPassCars die in het weekend arriveren per uur
    int weekDayReservations= 2;     // gemiddelde hoeveelheid CarReservations die doordeweeks gemaakt worden per uur
    int weekendReservations = 8;    // gemiddelde hoeveelheid CarReservations die in het weekend gemaakt worden per uur

    private int[] hourlyArrivals = new int[60];     // de kwantiteit van aankomende AdHocCars voor dit uur
    private int[] hourlyPassArrivals = new int[60]; // de kwantiteit van aankomende ParkingPassCars voor dit uur
    private int[] hourlyReservations = new int[60]; // de kwantiteit van toegevoegde CarReservations voor dit uur

    int enterSpeed = 3;     // de hoeveelheid Cars die naar binnen kunned per minuut per ingang
    int paymentSpeed = 7;   // de hoeveelheid Cars die kunnen betalen per minuut
    int exitSpeed = 5;      // de hoeveelheid Cars die naar buiten kunned per minuut per uitgang

    /**
     * Constructor voor objecten van klasse SimulatorLogic.
     */
    public SimulatorLogic() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        dayEarnings = new HashMap<>();
        for (int i=0; i < 7; i++){
            dayEarnings.put(i, 0);
        }
        garageLogic = new GarageLogic(3, 6, 30);
    }

    /**
     * Start of hervat de simulatie.
     */
    public void start(){
        if (!run){
            new Thread(this).start();
            run=true;
        }
    }

    /**
     * Pauseert de simulatie.
     */
    public void pause() {
        run=false;
    }

    /**
     * Overridden van Runnable
     * Laat de simulatie doorspelen zolang deze gestart is.
     */
    @Override
    public void run() {
        while (currentTick <= maxTicks && run){
            tick (1);
        }
    }

    /**
     * Zet de simulatie 1 tick (oftewel, 1 minuut) vooruit.
     * Pauseer de simulatie voor een kort ogenblik.
     * @param times een integer voor hoevaak de simulatie moet ticken.
     */
    public void tick(int times) {
        for (int i = 0; i < times; i++){
            advanceTime();
            handleExit();
            garageLogic.tick();
            updateEarnings();
            updateViews();
            // Pause.
            try {
                Thread.sleep(tickPause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tickReservations();
            handleEntrance();
            currentTick++;
            if (currentTick == maxTicks){
                run=false;
                currentTick = 0;
            }
        }
    }

    /**
     * @return de GarageLogic die de meeste logistieke logica regelt.
     */
    public GarageLogic getGarageLogic() { return garageLogic; }

    /**
     * @return de huidige tick van de simulatie, in minuten vanaf het begin.
     */
    public int getCurrentTick(){ return currentTick; }

    /**
     * @return de huidige minuut van dit uur.
     */
    public int getMinute(){ return minute; }

    /**
     * @return het huidige uur van deze dag.
     */
    public int getHour(){ return hour; }

    /**
     * @return de huidige dag van deze week.
     */
    public int getDay(){ return day; }

    public int getTotalEarnings() { return totalEarnings; }

    public HashMap<Integer, Integer> getDayEarnings() { return dayEarnings; }

    /**
     * Vorder de tijd van de simulatie met 1 minuut
     */
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    /**
     * Voeg een Car toe aan de entranceCarQueue.
     * @param car de toe te voegen Car
     */
    private void addCarToQueue(Car car) {
        entranceCarQueue.addCar(car);
    }

    /**
     * Hanteer alle methodes met invloed op inkomende Cars
     */
    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    }

    /**
     * Hanteer alle methodes met invloed op uitgaande Cars
     */
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Vorder de tijd voor alle CarReservation in carReservationList.
     * Voeg de CarReservation toe aan de parkeergarage als er 15 minuten resterend zijn.
     * Vorder de tijd voor alle ReservationCar in reservationCarList.
     * Voeg de ReservationCar toe aan de ingangswachtrij wanneer zijn interne timer op 0 staat.
     * Verwijder een CarReservation als de bijbehorende ReservationCar te vroeg aankomt.
     */
    private void tickReservations() {
        Iterator<CarReservation> resIt = carReservationList.iterator();
        while (resIt.hasNext()) {
            CarReservation reservation = resIt.next();
            reservation.tick();
            if (reservation.getMinutesToGo() < 15) {
                if (garageLogic.setReservation(reservation)) {
                    resIt.remove();
                }
            }
        }
        Iterator<ReservationCar> carIt = reservationCarList.iterator();
        while (carIt.hasNext()) {
            ReservationCar car = carIt.next();
            car.tick();
            if (car.getMinutesToGo() <= 0) {
                carReservationList.removeIf(reservation -> car.getReservation() == reservation);
                addCarToQueue(car);
                carIt.remove();
            }
        }
    }

    private void handlePayment(Car car){
        int feeTimes = (int) Math.floor(car.getStayMinutes() / 20);
        if (car.getStayMinutes() % 20 > 0){ feeTimes++; }

        int paymentAmount = feeTimes * parkingFee;

        totalEarnings += paymentAmount;
        dayValue += paymentAmount;
    }

    private void updateEarnings(){
        if (hour == 23 && minute == 59) {
            dayEarnings.put(day, dayValue);
            dayValue = 0;
        }
    }

    /**
     * Laat de hoeveelheid Cars per categorie berekenen, en voeg deze toe.
     */
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals, hourlyArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals, hourlyPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayReservations, weekendReservations, hourlyReservations);
        addArrivingCars(numberOfCars, RES);
    }

    /**
     * Laat Cars de parkeergarage betreden, en stuur ze naar de eerste vrije Location.
     * Bij een ReservationCar, probeer deze naar zijn toegekende plek te sturen, en verwijder de Reservation.
     * Als de ReservationCar nog geen toegekende plek heeft, stuur deze naar de eerste vrije Location.
     */
    private void carsEntering(CarQueue queue){
        int i=0;
        boolean isPass = false;
        if (queue == entrancePassQueue) {isPass = true;}
        // Remove car from the front of the queue and assign to a parking space.

    	while (queue.carsInQueue()>0 &&
                garageLogic.getFirstFreeLocation(isPass) != null &&
    			i<enterSpeed) {
            Car car = queue.removeCar();
            if (car instanceof ReservationCar) {
                ReservationCar resCar = (ReservationCar) car;
                Location loc = resCar.getReservedLocation() != null ? resCar.getReservedLocation() : garageLogic.getFirstFreeLocation(isPass);
                garageLogic.setCarAt(loc, car);
                garageLogic.removeReservationAt(loc);
            } else {
                Location freeLocation = garageLogic.getFirstFreeLocation(isPass);
                garageLogic.setCarAt(freeLocation, car);
            }
            i++;
        }
    }

    /**
     * Voeg Cars toe aan de betalingswachtrij, indien deze moeten betalen.
     * Als dit niet het geval is, kunnen de Cars hun plek verlaten.
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = garageLogic.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = garageLogic.getFirstLeavingCar();
        }
    }

    /**
     * Laat Cars betalen, en voeg deze toe aan de uitgangswachtrij.
     */
    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            handlePayment(car);
            carLeavesSpot(car);
            i++;
    	}
    }

    /**
     * Laat Cars de parkeergarage verlaten.
     */
    private void carsLeaving(){
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }

    /**
     * Berekent de hoeveelheid Cars of Reeservations dat aankomt per uur, en returned deze per minuut
     * @param weekDay een integer met de hoeveelheid Cars of Reservations dat doordeweeks gemiddeld aankomt.
     * @param weekend een integer met de hoeveelheid Cars of Reservations dat in het weekend gemiddeld aankomt.
     * @param target een integer array waar de waardes per uur worden geupdate, en gereturned.
     * @return een integer voor de hoeveelheid Cars of Reservations dat deze minuut aankomt.
     */
    private int getNumberOfCars(int weekDay, int weekend, int[] target){
        if (minute == 1) {
            Random random = new Random();

            // Get the average number of cars that arrive per hour.
            int averageNumberOfCarsPerHour = day < 5
                    ? weekDay
                    : weekend;

            // Minder drukte in de nachturen
            if (hour <= 6 || hour >= 19) { averageNumberOfCarsPerHour *= 0.3; }

            // Extra autos voor de schouwburgvoorstellingen
            if (day >= 4 && hour >= 18 && hour <= 19 && target == hourlyArrivals) { averageNumberOfCarsPerHour += 200; }

            // Extra abbonementshouders in de ochtendspits
            if ((day < 4) && (hour >= 7 && hour <= 10) && (target == hourlyPassArrivals)) { averageNumberOfCarsPerHour *= 2; }

            // Calculate the number of cars that arrive this minute.
            double standardDeviation = averageNumberOfCarsPerHour * 0.3;
            double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;

            // Minimum grootte voor de pushvalues voor de array
            int minimumPush = (int)Math.ceil(numberOfCarsPerHour / 60);

            // Voeg 60 waardes aan de array toe
            for (int i=0; i<60; i++) {
                if (numberOfCarsPerHour > 0) {
                    int numberOfCarsThisMinute = random.nextInt(2) + minimumPush;
                    target[i] = numberOfCarsThisMinute;
                    numberOfCarsPerHour -= numberOfCarsThisMinute;
                } else {
                    target[i] = 0;
                }
            }

            // Shuffle de nieuwe array
            shuffleIntegerArray(target);
        }
        return target[minute];
    }

    /**
     * Schud een array met integers door elke waarde om te wisselen met een willekeurige andere plek in de array.
     * @param arr een array met integers.
     */
    private void shuffleIntegerArray(int[] arr) {
        Random random = new Random();

        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    /**
     * Voeg Cars toe aan wachtrijen.
     * @param numberOfCars een integer met de hoeveelheid Cars die moeten worden toegevoegd.
     * @param type een String met het type Car, of Reservation, dat moet worden toegevoegd.
     */
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;
        case RES:
            for (int i = 0; i < numberOfCars; i++) {
                CarReservation reservation = new CarReservation();
                ReservationCar car = new ReservationCar(reservation);
                carReservationList.add(reservation);
                reservationCarList.add(car);
            }
            break;
        }
    }

    /**
     * Verwijder een Car uit de parkeergarage, en voeg deze toe aan de uitgangswachtrij.
     */
    private void carLeavesSpot(Car car){
    	garageLogic.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
