public class Serve extends Event {
    Serve(Customer customer, double initTime) {
        super(customer, true, initTime);
    }

    int numServed() {
        return 1;
    }

    double waitingTime() {
        return customer.getArrTime() - initTime;
    }

    ImList<Server> updateServer(ImList<Server> servers) {
        return customer.updateServers(servers);
    }
    
    Event updateArriveTime(ImList<Server> servers) {
        return new Serve(customer.arriveTimeDone(servers), initTime);
    }

    Event nextEvent() {
        return new Done(customer);
    }

    @Override
    public String toString() {
        return customer.beingServed();
    }
}
