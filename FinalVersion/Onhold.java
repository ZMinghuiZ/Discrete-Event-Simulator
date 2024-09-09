public class Onhold extends Event {
    Onhold(Customer customer, Double initTime) {
        super(customer, true, initTime);
    }

    int numServed() {
        return 0;
    }
    
    double waitingTime() {
        return 0.0;
    }

    Event updateEvent(ImList<Server> servers) {
        return new Onhold(customer.searchQueue(servers), initTime);
    }

    Event nextEvent() {
        if (customer.needWait()) {
            return this;
        }
        return new Serve(customer, initTime);
    }

    @Override
    public String toString() {
        return "";
    }
    
}
