import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final int servedBy; // not served when it is 0
    private final int selfCheckQPos;
    private final double arrivingTime;
    private final Supplier<Double> servingTime;
    private final boolean needWait;

    Customer(int id, int servedBy, int selfCheckQPos,
            double arrivingTime, Supplier<Double> servingTime) {
        this.id = id;
        this.servedBy = servedBy;
        this.selfCheckQPos = selfCheckQPos;
        this.arrivingTime = arrivingTime;
        this.servingTime = servingTime;
        this.needWait = false;
    }

    Customer(int id, int servedBy, int selfCheckQPos, double arrivingTime,
            Supplier<Double> servingTime, boolean needWait) {
        this.id = id;
        this.servedBy = servedBy;
        this.selfCheckQPos = selfCheckQPos;
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
                return new Customer(
                        this.id, index, selfCheckQPos, this.arrivingTime, this.servingTime);
            }
            index++;
        }
        for (int i = 0; i <= selfCheckQPos && i < servers.size(); i++) {
            if (!servers.get(i).isFull()) {
                return new Customer(
                        this.id, i + 1, selfCheckQPos, this.arrivingTime, this.servingTime, true);
            }
        }
        return this;
    }

    ImList<Server> updateServers(ImList<Server> servers) {
        int serverPos = servedBy - 1;
        Server server = servers.get(serverPos);
        if (!needWait()) {
            server = server.update(arrivingTime + getServiceTime());
            servers = servers.set(serverPos, server);
            return isSelfServiced() ? 
                servers.set(selfCheckQPos, servers.get(selfCheckQPos).deQueue()) :
                servers.set(serverPos, server.deQueue());
        }
        return isSelfServiced() ?
            servers.set(selfCheckQPos, servers.get(selfCheckQPos).enQueue(id)) :
            servers.set(serverPos, server.enQueue(id));
    }

    ImList<Server> finishWorkAt(ImList<Server> servers) {
        Server jobDoneServer = servers.get(servedBy - 1);
        double restTime = jobDoneServer.getRestTime();
        return servers.set(servedBy - 1, jobDoneServer.update(arrivingTime + restTime));
    }

    boolean atFrontOf(Server server) {
        return this.id == server.getNextInQ();
    }

    Customer searchQueue(ImList<Server> servers) {
        if (isSelfServiced()) {
            return searchFreeSelfCheck(servers);
        }
        
        Server server = servers.get(servedBy - 1);
        return atFrontOf(server) && server.isAvailbleAt(arrivingTime) ?
            new Customer(this.id, servedBy,
                selfCheckQPos, server.getAvailableTime(), this.servingTime) :
            new Customer(this.id, servedBy,
                selfCheckQPos, server.getAvailableTime(), this.servingTime, true);
    }

    Customer searchFreeSelfCheck(ImList<Server> servers) {
        Server available = servers.get(selfCheckQPos);
        for (int i = selfCheckQPos; i < servers.size(); i++) {
            if (servers.get(i).getAvailableTime() < available.getAvailableTime()) {
                available = servers.get(i);
            }
        }
        return atFrontOf(servers.get(servedBy - 1)) && available.isAvailbleAt(arrivingTime) ? 
            new Customer(this.id, available.getID(), selfCheckQPos,
                    available.getAvailableTime(), this.servingTime) :
                new Customer(this.id, servedBy, selfCheckQPos,
                    available.getAvailableTime(), this.servingTime, true);
    }

    Customer arriveTimeDone(ImList<Server> servers) {
        int serverPos = servedBy - 1;
        Server server = servers.get(serverPos);
        return new Customer(
            id, servedBy, selfCheckQPos, server.getAvailableTime(), this.servingTime);
    }

    boolean canBeServed() {
        return servedBy != 0;
    }

    boolean needWait() {
        return this.needWait;
    }

    boolean isSelfServiced() {
        return servedBy - 1 >= selfCheckQPos;
    }

    String arrives() {
        return String.format("%.3f %d arrives%n", arrivingTime, id);
    }

    String waits() {
        return String.format("%.3f %d waits at %s%d%n", arrivingTime, id,
                isSelfServiced() ? "self-check " : "", servedBy);
    }

    String beingServed() {
        return String.format("%.3f %d serves by %s%d%n", arrivingTime, id,
                isSelfServiced() ? "self-check " : "", servedBy);
    }

    String leaves() {
        return String.format("%.3f %d leaves%n", arrivingTime, id);
    }

    String done() {
        return String.format("%.3f %d done serving by %s%d%n", arrivingTime, id,
                isSelfServiced() ? "self-check " : "", servedBy);
    }
}
