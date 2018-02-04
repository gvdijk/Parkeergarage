package Parkeersimulator.Model;

import java.util.ArrayList;
import javax.swing.*;
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

    private int day;    // huidige dag in de week
    private int hour;   // huidige uur op de dag
    private int minute; // huidige minuut in het uur

    private int tickPause;     // pauze tussen ticks in milliseconden
    private int currentTick;    // huidige tick
    private int maxTicks = 10079;   // maximale hoeveelheid ticks om 1 week te simuleren

    private int totalEarnings;      //Totaal bedrag verdient tijdens de simulatie
    private int dayValue;       //Verdiensten per simulatiedag
    private HashMap<Integer, Integer> dayEarnings;      //HashMap met de verdiensten per dag erin
    private int moneyDue;       //Geld dat nog binnen zou komen als iedereen weg zou rijden
    private int parkingFee = 1;     //Bedrag dat betaald moet worden per 20 minuten parkeren

    private int normalCars;     //Aantal normale auto's in de parkeergarage
    private int passCars;       //Aantal pashouders in de parkeergarage
    private int reservationCars;        //Aantal auto's met een reservatie
    private int[] carPercentages;       //Array met de percentageverdeling van de auto's

    int weekDayArrivals= 80;        // gemiddelde hoeveelheid AdHocCars die doordeweeks arriveren per uur
    int weekendArrivals = 160;      // gemiddelde hoeveelheid AdHocCars die in het weekend arriveren per uur
    int weekDayPassArrivals= 30;    // gemiddelde hoeveelheid ParkingPassCars die doordeweeks arriveren per uur
    int weekendPassArrivals = 5;    // gemiddelde hoeveelheid ParkingPassCars die in het weekend arriveren per uur
    int weekDayReservations= 2;     // gemiddelde hoeveelheid CarReservations die doordeweeks gemaakt worden per uur
    int weekendReservations = 8;    // gemiddelde hoeveelheid CarReservations die in het weekend gemaakt worden per uur

    private int[] hourlyArrivals = new int[60];     // de kwantiteit van aankomende AdHocCars voor dit uur
    private int[] hourlyPassArrivals = new int[60]; // de kwantiteit van aankomende ParkingPassCars voor dit uur
    private int[] hourlyReservations = new int[60]; // de kwantiteit van toegevoegde CarReservations voor dit uur

    int enterSpeed = 3;     // de hoeveelheid Cars die naar binnen kunnen per minuut per ingang
    int paymentSpeed = 7;   // de hoeveelheid Cars die kunnen betalen per minuut
    int exitSpeed = 5;      // de hoeveelheid Cars die naar buiten kunned per minuut per uitgang


    /**
     * Constructor voor objecten van klasse SimulatorLogic.
     */
    public SimulatorLogic(JPanel init, JPanel sim) {
        super(init, sim);
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        dayEarnings = new HashMap<>();
        carPercentages = new int[3];
        reset();
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
     * Reset alle waardes en objecten van de simulatie zodat een nieuwe simulatie kan worden gemaakt
     */
    public void reset() {
        entranceCarQueue.clearQueue();
        entrancePassQueue.clearQueue();
        paymentCarQueue.clearQueue();
        exitCarQueue.clearQueue();
        currentTick = 0;
        day = 0;
        hour = 0;
        minute = 0;
        resetCarCount();
        resetEarnings();
        resetCarPercentages();
    }

    /**
     * Zet de nieuwe waardes voor de variabelen en objecten voor de aankomende simulatie
     *
     * @param tickPause De pauze in milliseconden die na elke simulatiestap gewacht wordt
     * @param garage    De grootte die de garage moet worden voor deze simulatie
     */
    public void initialize(int tickPause, int[] garage){
        garageLogic = new GarageLogic(garage[0], garage[1], garage[2]);
        this.tickPause = tickPause;
        garageLogic.tick();
        updateViews();
    }

    /**
     * Overridden van Runnable
     * Laat de simulatie doorspelen zolang deze gestart is.
     */
    @Override
    public void run() {
        while (currentTick <= maxTicks && run){
            tick (true, 1);
        }
    }

    /**
     * Zet de simulatie 1 tick (oftewel, 1 minuut) vooruit.
     * Pauseer de simulatie voor een kort ogenblik.
     * @param pause Een boolean die bepaald of de tick pauzeert of niet
     * @param times een integer voor hoevaak de simulatie moet ticken.
     */
    public void tick(boolean pause, int times) {
        for (int i = 0; i < times; i++){
            advanceTime();
            handleExit();
            garageLogic.tick();
            updateEarnings();
            updateCarPercentages();
            moneyDue = garageLogic.getMoneyDue();
            updateViews();
            // Pause.
            if (pause){
                try {
                    Thread.sleep(tickPause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tickReservations();
            handleEntrance();
            currentTick++;
            if (currentTick == maxTicks){
                pause();
                currentTick = 0;
                reset();
            }
        }
    }

    /**
     * @return de GarageLogic die de meeste logistieke logica regelt.
     */
    public GarageLogic getGarageLogic() { return garageLogic; }

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

    /**
     * @return De normale auto's die op het moment in de parkeergarage staan
     */
    public int getNormalCars() { return normalCars; }

    /**
     * @return  De pashouders die op het moment in de parkeergarage staan
     */
    public int getPassCars() { return passCars; }

    /**
     * @return  De auto's met een reservatie die op het moment in de parkeergarage staan
     */
    public int getReservationCars() { return reservationCars; }

    /**
     * @return  Array met de percentageverdeling van de huidige auto's in de garage
     */
    public int[] getCarPercentages() { return carPercentages; }

    /**
     * @return  Totaalbedrag verdient aan parkeergeld
     */
    public int getTotalEarnings() { return totalEarnings; }

    /**
     * @return  Geld dat nog binnen moet komen van geparkeerde auto's
     */
    public int getMoneyDue() { return moneyDue; }

    /**
     * @return  HashMap van alle bedragen die per dag verdiend zijn
     */
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

    /**
     * Berekent hoeveel geld een auto moet betalen bij het wegrijden
     *
     * @param car De auto die moet betalen
     */
    private void handlePayment(Car car){
        int feeTimes = (int) Math.floor(car.getStayMinutes() / 20);
        if (car.getStayMinutes() % 20 > 0){ feeTimes++; }

        int paymentAmount = feeTimes * parkingFee;

        totalEarnings += paymentAmount;
        dayValue += paymentAmount;
    }

    /**
     * Voegt de winst van elke dag toe aan de array die dit bijhoudt
     */
    private void updateEarnings(){
        if (hour == 23 && minute == 59) {
            dayEarnings.put(day, dayValue);
            dayValue = 0;
        }
    }

    /**
     * Zet alle variabelen die met winst temaken hebben terug naar 0
     */
    private void resetEarnings(){
        for (int i=0; i < 7; i++){
            dayEarnings.put(i, 0);
        }
        totalEarnings = 0;
        dayValue = 0;
        moneyDue = 0;
    }

    /**
     * Bepaalt welk soort auto is binnengekomen en houdt het bij in variabelen
     *
     * @param increment Boolean die bepaald of de functie moet optellen of aftrekken
     * @param car Het auto-object om te bepalen welk soort auto het is
     */
    private void updateCarCount(boolean increment, Car car){
        if (car instanceof AdHocCar){
            if (increment){ normalCars++; }
            else { normalCars--; }
        }else if(car instanceof ParkingPassCar){
            if (increment){ passCars++; }
            else { passCars--; }
        }else if (car instanceof ReservationCar){
            if (increment){ reservationCars++; }
            else { reservationCars--; }
        }
    }

    /**
     * Update de percentageverdeling van de huidige auto's in de garage
     */
    private void updateCarPercentages(){
        int totalCars = normalCars + passCars + reservationCars;

        if (totalCars != 0) {
            int normalPercent = Math.round((normalCars * 100) / totalCars);
            carPercentages[0] = normalPercent;
            int passPercent = Math.round((passCars * 100) / totalCars);
            carPercentages[1] = passPercent;
            int reservationPercent = Math.round((reservationCars * 100) / totalCars);
            carPercentages[2] = reservationPercent;
        }
    }

    /**
     * Reset de percentageverdeling van de auto's
     */
    private void resetCarPercentages(){
        for (int i=0; i < 3; i++){
            carPercentages[i] = 0;
        }
    }

    /**
     * Reset de auto's die momenteel in de parkeergarage staan
     */
    private void resetCarCount(){
        normalCars = 0;
        passCars = 0;
        reservationCars = 0;
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
            updateCarCount(true, car);
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
    	updateCarCount(false, car);
        exitCarQueue.addCar(car);
    }

}
