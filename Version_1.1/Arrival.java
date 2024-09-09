public class Arrival extends Event {
    Arrival(Customer customer) {
        super(customer, true);
    }

    @Override
    public Double getTime() {
        return customer.getArrTime();
    }
    
    @Override
    Event updateEvent(ImList<Server> servers) {
        return new Arrival(customer.searchServer(servers));
    }

    @Override
    public Event nextEvent() {
        if (customer.canBeServed()) {
            return new Serve(customer);
        }
        return new Leave(customer);
    }
    
    @Override
    public String toString() {
        return customer.arrives();
    }
}
