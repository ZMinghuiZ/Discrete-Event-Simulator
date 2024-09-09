class Event {
    protected final Customer customer;
    protected final boolean hasNextEvent;

    Event(Customer customer, boolean hasNextEvent) {
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
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

    ImList<Server> updateServer(ImList<Server> servers) {
        return customer.updateServers(servers);
    }

    Event nextEvent() {
        return new Event(customer, true);
    }
}
