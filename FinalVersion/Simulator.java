import java.util.function.Supplier;

public class Simulator {
    private final ImList<Customer> customers;
    private final ImList<Server> servers;

    Simulator(int numOfServers, int numOfSelfChecks, int qmax, 
        ImList<Double> arrivalTimes, Supplier<Double> serviceTime, Supplier<Double> restTime) {
        ImList<Server> servers = new ImList<Server>();
        ImList<Customer> customers = new ImList<Customer>();

        for (int i = 1; i <= numOfServers; i++) {
            servers = servers.add(new HumanServer(i, 0.0, qmax, restTime));
        }
        for (int i = 1; i <= numOfSelfChecks; i++) {
            servers = i == 1 ? servers.add(new SelfCheckHead(numOfServers + i, 0.0, qmax)) :
                servers.add(new SelfCheck(numOfServers + i, 0.0));
        }
        for (int i = 0; i < arrivalTimes.size(); i++) {
            customers = customers.add(
                new Customer(i + 1, 0, numOfServers, arrivalTimes.get(i), serviceTime));
        }
        this.servers = servers;
        this.customers = customers;
    }

    private int numOfCustomers() {
        return customers.size();
    }

    String simulate() {
        ImList<Server> servers = this.servers;
        PQ<Event> pQueue = new PQ<Event>(new EventCmp());
        String result = "";
        int served = 0;
        Double waitingTime = 0.0;

        for (Customer customer : customers) {
            pQueue = pQueue.add(new Arrival(customer));
        }

        while (!pQueue.isEmpty()) {
            Pair<Event, PQ<Event>> pair = pQueue.poll();
            Event event = pair.first();
            result += event.toString();
            served += event.numServed();
            waitingTime += event.waitingTime();

            event = event.updateEvent(servers);
            servers = event.updateServer(servers);
            event = event.updateArriveTime(servers);
            servers = event.restServer(servers);
            pQueue = event.hasNextEvent() ? 
                pair.second().add(event.nextEvent()) : pair.second();
        }
        result += String.format("[%.3f %d %d]", served == 0 ? 0.0 : waitingTime / served, served, 
            numOfCustomers() - served);
        return result;
    }
}