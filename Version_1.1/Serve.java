public class Serve extends Event {
    Serve(Customer customer) {
        super(customer, true);
    }

    @Override
    public Double getTime() {
        return customer.getArrTime();
    }

    @Override
    Event updateEvent(ImList<Server> servers) {
        return this;
    }

    @Override
    public Event nextEvent() {
        return new Done(customer);
    }

    @Override
    public int served() {
        return 1;
    }
    
    @Override
    public String toString() {
        return customer.beingServed();
    }
}
