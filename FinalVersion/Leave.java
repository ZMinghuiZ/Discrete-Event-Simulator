public class Leave extends Event {
    Leave(Customer customer) {
        super(customer, false);
    }

    int numServed() {
        return 0;
    }

    double waitingTime() {
        return 0.0;
    }

    Event nextEvent() {
        return this;
    }

    @Override
    public String toString() {
        return customer.leaves();
    }
}
