public class Done extends Event {
    Done(Customer customer) {
        super(customer, false);
    }

    ImList<Server> restServer(ImList<Server> servers) {
        return customer.finishWorkAt(servers);
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
        return customer.done();
    }
}
