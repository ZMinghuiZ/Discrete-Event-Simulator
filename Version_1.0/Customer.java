class Customer {
    private final int id;
    private final int servedBy; // not served when it is 0
    private final Double arrivingTime;
    private final Double servingTime;

    Customer(int id, int servedBy, Double arrivingTime, Double servingTime) {
        this.id = id;
        this.servedBy = servedBy;
        this.arrivingTime = arrivingTime;
        this.servingTime = servingTime;
    }

    Customer searchServer(ImList<Server> servers) {
        int index = 1;
        for (Server server : servers) {
            if (server.isAvailbleAt(this.arrivingTime)) {
                return new Customer(this.id, index, this.arrivingTime, this.servingTime);
            }
            index++;
        }
        return this;
    }

    ImList<Server> updateServers(ImList<Server> servers) {
        if (isServed()) {
            int serverPos = servedBy - 1;
            Server updatedServer = servers.get(serverPos).update(arrivingTime + servingTime);
            return servers.set(serverPos, updatedServer);
        }
        return servers;
    }

    boolean isServed() {
        return servedBy != 0;
    }

    String showStatus() {
        String start = String.format("%.3f", arrivingTime) + " customer " + this.id + " arrives\n";
        if (servedBy == 0) {
            return start + String.format("%.3f", arrivingTime) + " customer " + 
                this.id + " leaves\n";
        }
        return start + String.format("%.3f", arrivingTime) + " customer " +
            this.id + " served by server " + this.servedBy + "\n";
    }
}
