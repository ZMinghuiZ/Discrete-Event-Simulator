public class Serve extends Event {
    Serve(Customer customer, Double initTime) {
        super(customer, true, initTime);
    }

    @Override
    public ImList<Server> updateServer(ImList<Server> servers) {
        return customer.updateServers(servers);
    }

    @Override
    Event updateArriveTime(ImList<Server> servers) {
        return new Serve(customer.arriveTimeDone(servers), initTime);
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
    Double waitingTime() {
        return customer.getArrTime() - initTime;
    }
    
    @Override
    public String toString() {
        return customer.beingServed();
    }
}
