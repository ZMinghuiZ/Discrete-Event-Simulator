public class Arrival extends Event {
    Arrival(Customer customer) {
        super(customer, true);
    }

    @Override
    public Double getTime() {
        return customer.getArrTime();
    }

    @Override
    public Event updateEvent(ImList<Server> servers) {
        return new Arrival(customer.searchServer(servers));
    }

    @Override
    public ImList<Server> updateServer(ImList<Server> servers) {
        if (customer.needWait()) {
            return customer.updateServers(servers);
        }
        return servers;
    }

    @Override
    public Event nextEvent() {
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
