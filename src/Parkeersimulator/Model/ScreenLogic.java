package Parkeersimulator.Model;

public class ScreenLogic {
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private Reservation[][][] reservations;

    public ScreenLogic(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        reservations = new Reservation[numberOfFloors][numberOfRows][numberOfPlaces];
        setDefaultReservations(1,4, 30);
    }

    private void setDefaultReservations(int floors, int rows, int places) {
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
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
        // TODO: numberOfOpenSpots is not correct or updating at the wrong moment
    	return numberOfOpenSpots;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public void setReservation(SimulatorLogic simulator) {
        Location loc = getFirstFreeLocation(false);
        if (loc != null) {
            CarReservation reservation = new CarReservation(loc, simulator);
            reservations[loc.getFloor()][loc.getRow()][loc.getPlace()] = reservation;
            numberOfOpenSpots--;
        } else {
            // TODO: Handle no free location regarding queues & new customers
        }
    }

    public Reservation getReservationAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return reservations[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public void removeReservationAt(Location location) {
        if (locationIsValid(location)) {
            Reservation reservation = getReservationAt(location);
            if (reservation != null) {
                reservations[location.getFloor()][location.getRow()][location.getPlace()] = null;
                reservation.setLocation(null);
                numberOfOpenSpots++;
            }
        }
    }


    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

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
                            if (reservations[floor][row][place] instanceof CarReservation) {
                                // Do nothing
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

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    Reservation reservation = getReservationAt(location);
                    if (car != null) {
                        car.tick();
                    }
                    if (reservation != null) {
                        reservation.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
}
