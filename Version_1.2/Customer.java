import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final int servedBy; // not served when it is 0
    private final Double arrivingTime;
    private final Supplier<Double> servingTime;
    private final boolean needWait;

    Customer(int id, int servedBy, Double arrivingTime, Supplier<Double> servingTime) {
        this.id = id;
        this.servedBy = servedBy;
        this.arrivingTime = arrivingTime;
        this.servingTime = servingTime;
        needWait = false;
    }

    Customer(int id, int servedBy, Double arrivingTime, Supplier<Double> servingTime, 
        boolean needWait) {
        this.id = id;
        this.servedBy = servedBy;
        this.arrivingTime = arrivingTime;
        this.servingTime = servingTime;
        this.needWait = needWait;
    }

    int getID() {
        return id;
    }

    Double getArrTime() {
        return arrivingTime;
    }

    Double getServiceTime() {
        return this.servingTime.get();
    }

    Customer searchServer(ImList<Server> servers) {
        int index = 1;
        for (Server server : servers) {
            if (server.isAvailbleAt(this.arrivingTime)) {
                return new Customer(this.id, index, this.arrivingTime, this.servingTime);
            }
            index++;
        }
        index = 1;
        for (Server server : servers) {
            if (!server.isFull()) {
                return new Customer(this.id, index, this.arrivingTime, this.servingTime, true);
            }
            index++;
        }
        return this;
    }

    ImList<Server> updateServers(ImList<Server> servers) {
        int serverPos = servedBy - 1;
        if (!needWait()) {
            Server updatedServer = servers.get(serverPos).deQueue();
            updatedServer = updatedServer.update(arrivingTime + getServiceTime());
            return servers.set(serverPos, updatedServer);
        }
        Server updatedServer = servers.get(serverPos).enQueue(this.getID());
        return servers.set(serverPos, updatedServer);
    }

    boolean atFrontOf(Server server) {
        return this.id == server.getNextInQ();
    }

    Customer searchQueue(ImList<Server> servers) {
        int serverPos = servedBy - 1;
        Server server = servers.get(serverPos);
        if (atFrontOf(server)) {
            return new Customer(this.id, servedBy, server.getAvailableTime(), this.servingTime);
        }
        return new Customer(this.id, servedBy, server.getAvailableTime(), this.servingTime, true);
    }

    Customer arriveTimeDone(ImList<Server> servers) {
        int serverPos = servedBy - 1;
        Server server = servers.get(serverPos);
        return new Customer(id, servedBy, server.getAvailableTime(), this.servingTime);
    }

    boolean canBeServed() {
        return servedBy != 0;
    }

    boolean needWait() {
        return this.needWait;
    }

    String arrives() {
        return String.format("%.3f %d arrives%n", arrivingTime, id);
    }

    String waits() {
        return String.format("%.3f %d waits at %d%n", arrivingTime, id, servedBy);
    }

    String beingServed() {
        return String.format("%.3f %d serves by %d%n", arrivingTime, id, servedBy);
    }

    String leaves() {
        return String.format("%.3f %d leaves%n", arrivingTime, id);
    }

    String done() {
        return String.format("%.3f %d done serving by %d%n", arrivingTime, id, servedBy);
    }
}
