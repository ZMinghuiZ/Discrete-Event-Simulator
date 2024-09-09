abstract class Event {
    protected final Customer customer;
    protected final boolean hasNextEvent;
    protected final double initTime;

    Event(Customer customer, boolean hasNextEvent) {
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
        this.initTime = customer.getArrTime();
    }

    Event(Customer customer, boolean hasNextEvent, double initTime) {
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
        this.initTime = initTime;
    }
    
    abstract Event nextEvent();
    
    abstract int numServed();
    
    abstract double waitingTime();
    
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

    Event updateArriveTime(ImList<Server> servers) {
        return this;
    }

    ImList<Server> updateServer(ImList<Server> servers) {
        return servers;
    }

    ImList<Server> restServer(ImList<Server> servers) {
        return servers;
    }
}
