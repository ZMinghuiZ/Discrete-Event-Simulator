class Wait extends Event {
    Wait(Customer customer, Double initTime) {
        super(customer, true, initTime);
    }

    public Event updateEvent(ImList<Server> servers) {
        return new Wait(customer.searchQueue(servers), initTime);
    }

    public Event nextEvent() {
        if (customer.needWait()) {
            return new Onhold(customer, initTime);
        }
        return new Serve(customer, initTime);
    }

    public String toString() {
        return customer.waits();
    }
}
