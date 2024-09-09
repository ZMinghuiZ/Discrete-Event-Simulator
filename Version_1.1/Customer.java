public class Customer {
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

    int getID() {
        return id;
    }

    Double getArrTime() {
        return arrivingTime;
    }

    Double getServiceTime() {
        return servingTime;
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
        if (canBeServed()) {
            int serverPos = servedBy - 1;
            Server updatedServer = servers.get(serverPos).update(arrivingTime + servingTime);
            return servers.set(serverPos, updatedServer);
        }
        return servers;
    }

    boolean canBeServed() {
        return servedBy != 0;
    }

    String arrives() {
        return String.format("%.3f %d arrives%n", arrivingTime, id);
    }

    String beingServed() {
        return String.format("%.3f %d serves by %d%n", arrivingTime, id, servedBy);
    }

    String leaves() {
        return String.format("%.3f %d leaves%n", arrivingTime, id);
    }

    String done() {
        return String.format("%.3f %d done serving by %d%n", 
            arrivingTime + servingTime, id, servedBy);
    }
}
