package Parkeersimulator.Model;

import java.util.Random;

public class GarageLogic {
    private int numberOfFloors;     // hoeveelheid verdiepingen in de parkeergarage
    private int numberOfRows;       // hoeveelheid rijen per verdieping
    private int numberOfPlaces;     // hoeveelheid plaatsen per rij

    private Car[][][] cars;                 // array met Cars met locaties als indices
    private Reservation[][][] reservations; // array met Reservations met locaties als indices

    private double parkingFee;

    /**
     * Constructor voor objecten van klasse ScreenLogic.
     */
    public GarageLogic(int numberOfFloors, int numberOfRows, int numberOfPlaces, double parkingFee) {
        this.parkingFee = parkingFee;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        reservations = new Reservation[numberOfFloors][numberOfRows][numberOfPlaces];
        setPassReservations(1,4, numberOfPlaces);
    }

    /**
     * Zet alle gereserveerde plekken voor pashouders.
     */
    public void setPassReservations(int floors, int rows, int places) {
        // Zorg ervoor dat de ingevoerde waardes kleiner of gelijk zijn aan de groottes van de parkeergarage
        floors = floors > numberOfFloors ? numberOfFloors : floors;
        rows = rows > numberOfRows ? numberOfRows : rows;
        places = places > numberOfPlaces ? numberOfPlaces : places;

        // Verwijder alle oude gereserveerde plekken
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    reservations[floor][row][place] = null;
                }
            }
        }

        // Plaats de nieuwe reserveringen
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int place = 0; place < places; place++) {
                    Location location = new Location(floor, row, place);
                    if (locationIsValid(location)) {
                        PassReservation reservation = new PassReservation(location);
                        reservations[floor][row][place] = reservation;
                    }
                }
            }
        }
    }

    /**
     * @return een integer met de hoeveelheid verdiepingen in de parkeergarage.
     */
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * @return een integer met de hoeveelheid rijen per verdieping.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @return een integer met de hoeveelheid plaatsen per rij.
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Verkrijg een Car die op de meegegeven Location staat, indien deze geldig is en er een Car staat.
     * @param location een Location om te controleren.
     * @return een Car op de meegegeven Location.
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) { return null; }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Plaats een CarReservation op de eerste beschikbare Location.
     * @param reservation een CarReservation die op de eerste beschikbare Location geplaatst moet worden.
     * @return een boolean of de actie succesvol is afgerond
     */
    public boolean setReservation(CarReservation reservation) {
        Location loc = getFirstFreeLocation(false);
        if (loc != null) {
            reservation.setLocation(loc);
            reservations[loc.getFloor()][loc.getRow()][loc.getPlace()] = reservation;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verkrijg een Reservation die op de meegegeven Location staat, indien deze geldig is en er een Reservation staat.
     * @param location een Location om te controleren.
     * @return een Reservation op de meegegeven Location.
     */
    public Reservation getReservationAt(Location location) {
        if (!locationIsValid(location)) { return null; }
        return reservations[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Verwijder een Reservation op de meegegeven Location.
     * @param location een Location waar de Reservation verwijderd moet worden.
     */
    public void removeReservationAt(Location location) {
        if (locationIsValid(location)) {
            Reservation reservation = getReservationAt(location);
            if (reservation != null) {
                reservations[location.getFloor()][location.getRow()][location.getPlace()] = null;
                reservation.setLocation(null);
            }
        }
    }

    /**
     * Plaats een Car op de meegegeven Location.
     * @param location een Location waar de Car geplaatst moet worden.
     * @param car een Car die op de Location geplaatst moet worden.
     * @return een boolean of de actie succesvol is afgerond
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) { return false; }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            Random random = new Random();
            boolean isBadAtParking = (random.nextDouble() > 0.99);
            if (isBadAtParking && location.getPlace() > 0 && location.getPlace() < numberOfPlaces - 1) {
                Location prevSpot = new Location(location.getFloor(), location.getRow(), location.getPlace() - 1);
                Location nextSpot = new Location(location.getFloor(), location.getRow(), location.getPlace() + 1);
                Car occupyingCar = getCarAt(prevSpot);
                if (occupyingCar == null) {
                    car.setBadAtParking(true);
                    DoubledCar doubledCar = new DoubledCar(prevSpot, car);
                    cars[prevSpot.getFloor()][prevSpot.getRow()][prevSpot.getPlace()] = doubledCar;
                    doubledCar.setLocation(prevSpot);
                } else {
                    occupyingCar = getCarAt(nextSpot);
                    if (occupyingCar == null) {
                        car.setBadAtParking(true);
                        DoubledCar doubledCar = new DoubledCar(nextSpot, car);
                        cars[nextSpot.getFloor()][nextSpot.getRow()][nextSpot.getPlace()] = doubledCar;
                        doubledCar.setLocation(nextSpot);
                    }
                }
            }
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }

    /**
     * Verwijder een Car op de meegegeven Location.
     * @param location een Location waar de Car verwijderd moet worden.
     * @return een Car als deze succesvol verwijderd is.
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) { return null; }
        Car car = getCarAt(location);
        if (car == null) { return null; }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        return car;
    }

    /**
     * Controlleer of de meegegeven Location geldig is binnen de huidige parkeergarage.
     * @param isPass een boolean of de Location voor pashouders kan zijn.
     * @return de eerste beschikbare Location in de parkeergarage.
     */
    public Location getFirstFreeLocation(boolean isPass) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        if (reservations[floor][row][place] != null) {
                            if (reservations[floor][row][place] instanceof  PassReservation && isPass) {
                                return location;
                            }
                        } else {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Vind de eerste vertrekkende Car in de parkeergarage.
     * @return de eerste vertrekkende Car in de parkeergarage.
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        if (car instanceof DoubledCar) {
                            removeCarAt(car.getLocation());
                        } else {
                            return car;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Tick alle Cars en CarReservations die op het moment in de parkeergarage zijn.
     * Als een CarReservation zijn tijd voorbij is, verwijder deze uit de parkeergarage.
     */
    public void tick() {
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    Reservation reservation = getReservationAt(location);
                    if (car != null) { car.tick(); }
                    if (reservation != null && reservation instanceof CarReservation) {
                        CarReservation res =  (CarReservation) reservation;
                        res.tick();
                        if (res.getMinutesToGo() < -30) { removeReservationAt(res.getLocation()); }
                    }
                }
            }
        }
    }

    /**
     * Controlleer of de meegegeven Location geldig is binnen de huidige parkeergarage.
     * @param location een Location om te controleren.
     * @return of de Location geldig is.
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return floor >= 0 && floor < numberOfFloors && row >= 0 && row <= numberOfRows && place >= 0 && place <= numberOfPlaces;
    }

    /**
     * Berekent voor elke auto in de garage wat ze moeten gaan betalen als ze wegrijden
     *
     * @return  Het geldbedrag dat verdient zou worden als alle auto's in de garage nu zouden betalen
     */
    public double getMoneyDue(){
        double money = 0;
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getHasToPay()) {
                        int feeTimes = (int) Math.floor(car.getStayMinutes() / 20);
                        if (car.getStayMinutes() % 20 > 0){ feeTimes++; }
                        money += feeTimes * parkingFee;
                    }
                }
            }
        }
        return money;
    }
}

