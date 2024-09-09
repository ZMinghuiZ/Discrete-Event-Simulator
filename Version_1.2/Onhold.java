public class Onhold extends Event {
    Onhold(Customer customer, Double initTime) {
        super(customer, true, initTime);
    }

    @Override
    public Event updateEvent(ImList<Server> servers) {
        return new Onhold(customer.searchQueue(servers), initTime);
    }

    @Override
    public Event nextEvent() {
        if (customer.needWait()) {
            return this;
        }
        return new Serve(customer, initTime);
    }

    public String toString() {
        return "";
    }
    
}
