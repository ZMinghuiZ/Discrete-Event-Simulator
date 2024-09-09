public class Simulator {
    private final ImList<Customer> customers;
    private final ImList<Server> servers;

    Simulator(ImList<Customer> customers, ImList<Server> servers) {
        this.customers = customers;
        this.servers = servers;
    }

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
        return this.customers.size();
    }

    Simulator runService() {
        ImList<Customer> customers = this.customers;
        ImList<Server> servers = this.servers;

        for (int i = 0; i < this.numOfCustomers(); i++) {
            Customer simulated = customers.get(i).searchServer(servers);
            customers = customers.set(i, simulated);
            servers = customers.get(i).updateServers(servers);
        }
        return new Simulator(customers, servers);
    }

    String simulate() {
        Simulator simulator = this.runService();
        String result = "";
        String servedAndLeft;
        int served = 0;
        int left;

        for (int i = 0; i < this.numOfCustomers(); i++) {
            if (simulator.customers.get(i).isServed()) {
                served += 1;
            }
            result += simulator.customers.get(i).showStatus();
        }

        left = simulator.numOfCustomers() - served;
        servedAndLeft = "[" + served + " " + left + "]";
        result += servedAndLeft;
        return result;
    }
}                                                                                             
