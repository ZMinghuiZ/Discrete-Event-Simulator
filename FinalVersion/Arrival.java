public class Arrival extends Event {
    Arrival(Customer customer) {
        super(customer, true);
    }

    int numServed() {
        return 0;
    }

    double waitingTime() {
        return 0.0;
    }

    Event updateEvent(ImList<Server> servers) {
        return new Arrival(customer.searchServer(servers));
    }
    
    ImList<Server> updateServer(ImList<Server> servers) {
        if (customer.needWait()) {
            return customer.updateServers(servers);
        }
        return servers;
    }

    Event nextEvent() {
        if (customer.canBeServed()) {
            if (customer.needWait()) {
                return new Wait(customer, initTime);
            }
            return new Serve(customer, initTime);
        }
        return new Leave(customer);
    }
    
    @Override
    public String toString() {
        return customer.arrives();
    }
}
