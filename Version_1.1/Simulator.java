public class Simulator {
    private final ImList<Customer> customers;
    private final ImList<Server> servers;

    Simulator(int numOfServers, ImList<Double> arrivalTimes, ImList<Double> serviceTimes) {
        ImList<Server> servers = new ImList<Server>();
        for (int i = 0; i < numOfServers; i++) {
            servers = servers.add(new Server(0.0));
        }
        this.servers = servers;

        ImList<Customer> customers = new ImList<Customer>();
        for (int i = 0; i < arrivalTimes.size(); i++) {
            customers = customers.add(
                new Customer(i + 1, 0, arrivalTimes.get(i), serviceTimes.get(i)));
        }
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

        for (Customer customer : customers) {
            pQueue = pQueue.add(new Arrival(customer));
        }

        while (!pQueue.isEmpty()) {
            Pair<Event, PQ<Event>> pair = pQueue.poll();
            result += pair.first().toString();
            served += pair.first().served();
            if (pair.first().hasNextEvent()) {
                Event newEvent = pair.first().updateEvent(servers);
                servers = newEvent.updateServer(servers);
                pQueue = pair.second().add(newEvent.nextEvent());
            } else {
                pQueue = pair.second();
            }
        }
        result += String.format("[%d %d]", served, numOfCustomers() - served);
        return result;
    }
}