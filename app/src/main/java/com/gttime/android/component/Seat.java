package com.gttime.android.component;

public class Seat {
    int seatCapacity;
    int seatActual;
    int seatRemaining;
    int waitlistCapacity;
    int waitlistActual;
    int waitlistRemaining;

    public Seat(int seatCapacity, int seatActual, int seatRemaining, int waitlistCapacity, int waitlistActual, int waitlistRemaining) {
        this.seatCapacity = seatCapacity;
        this.seatActual = seatActual;
        this.seatRemaining = seatRemaining;
        this.waitlistCapacity = waitlistCapacity;
        this.waitlistActual = waitlistActual;
        this.waitlistRemaining= waitlistRemaining;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public int getSeatActual() {
        return seatActual;
    }

    public int getSeatRemaining() {
        return seatRemaining;
    }

    public int getWaitlistCapacity() {
        return waitlistCapacity;
    }

    public int getWaitlistActual() {
        return waitlistActual;
    }

    public int getWaitlistRemaining() {
        return waitlistRemaining;
    }
}
