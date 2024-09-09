class Wait extends Event {
    Wait(Customer customer, double initTime) {
        super(customer, true, initTime);
    }

    int numServed() {
        return 0;
    }
    
    double waitingTime() {
        return 0.0;
    }

    Event updateEvent(ImList<Server> servers) {
        return new Wait(customer.searchQueue(servers), initTime);
    }

    Event nextEvent() {
        return new Onhold(customer, initTime);
    }

    @Override
    public String toString() {
        return customer.waits();
    }
}
