class Event {
    protected final Customer customer;
    protected final boolean hasNextEvent;
    protected final Double initTime;

    Event(Customer customer, boolean hasNextEvent) {
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
        this.initTime = customer.getArrTime();
    }

    Event(Customer customer, boolean hasNextEvent, Double initTime) {
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
        this.initTime = initTime;
    }

    int getID() {
        return customer.getID();
    }

    Double getTime() {
        return customer.getArrTime();
    }

    boolean hasNextEvent() {
        return this.hasNextEvent;
    }

    Event updateEvent(ImList<Server> servers) {
        return this;
    }

    int served() {
        return 0;
    }

    Event updateArriveTime(ImList<Server> servers) {
        return this;
    }

    ImList<Server> updateServer(ImList<Server> servers) {
        return servers;
    }

    Event nextEvent() {
        return new Event(customer, true);
    }

    Double waitingTime() {
        return 0.0;
    }
}
