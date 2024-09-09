public class Server {
    private final Double availableTime;

    Server(Double availableTime) {
        this.availableTime = availableTime;
    }

    boolean isAvailbleAt(Double currTime) {
        return currTime.compareTo(this.availableTime) >= 0;
    }

    Server update(Double time) {
        return new Server(time);
    }
}
