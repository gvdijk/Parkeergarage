package Parkeersimulator.Model;

import java.util.Random;

public class SimulatorLogic extends AbstractModel implements Runnable{

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private ScreenLogic screenLogic;
    private boolean run;

    private int day = 0;
    private int hour = 8;
    private int minute = 0;

    private int tickPause = 40;
    private int currentTick = 0;
    private int maxTicks = 10000;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 40; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    private int[] hourlyArrivals = new int[60];
    private int[] hourlyPassArrivals = new int[60];

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    public SimulatorLogic() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        screenLogic = new ScreenLogic(3, 6, 30);
    }

    public void start(){
        new Thread(this).start();
    }

    public void pause() {
        run=false;
    }

    @Override
    public void run() {
        run=true;
        while (currentTick < maxTicks && run){
            tick (1);
        }
        run=false;
        currentTick = 0;
    }

    public ScreenLogic getScreenLogic() {
        return screenLogic;
    }

    public void tick(int times) {
        for (int i = 0; i < times; i++){
            advanceTime();
            handleExit();
            screenLogic.tick();
            updateViews();
            // Pause.
            try {
                Thread.sleep(tickPause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handleEntrance();
            currentTick++;
        }
    }

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

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals, hourlyArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals, hourlyPassArrivals);
        addArrivingCars(numberOfCars, PASS);    	
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        boolean isPass = false;
        if (queue == entrancePassQueue) {isPass = true;}
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			screenLogic.getNumberOfOpenSpots()>0 &&
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = screenLogic.getFirstFreeLocation(isPass);
            screenLogic.setCarAt(freeLocation, car);
            i++;
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = screenLogic.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = screenLogic.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend, int[] target){
        if (minute == 1) {
            Random random = new Random();

            // Get the average number of cars that arrive per hour.
            int averageNumberOfCarsPerHour = day < 5
                    ? weekDay
                    : weekend;

            // Minder drukte in de nachturen
            if (hour <= 6 || hour >= 19) { averageNumberOfCarsPerHour *= 0.4; }

            // Extra autos voor de schouwburgvoorstellingen
            if (day >= 4 && hour >= 18 && hour <= 19 && target == hourlyArrivals) { averageNumberOfCarsPerHour += 250; }

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
    	}
    }
    
    private void carLeavesSpot(Car car){
    	screenLogic.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

}
