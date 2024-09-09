import java.util.function.Supplier;

class HumanServer implements Server {
    private final int serverNum;
    private final double availableTime;
    private final ImList<Integer> queue;
    private final int qmax;
    private final Supplier<Double> restTime;
   
    HumanServer(int serverNum, double availableTime, int qmax, Supplier<Double> restTime) {
        this.serverNum = serverNum;
        this.availableTime = availableTime;
        queue = new ImList<Integer>();
        this.qmax = qmax;
        this.restTime = restTime;
    }

    HumanServer(int serverNum, double availableTime, ImList<Integer> queue, int qmax,
        Supplier<Double> restTime) {
        this.serverNum = serverNum;
        this.availableTime = availableTime;
        this.queue = queue;
        this.qmax = qmax;
        this.restTime = restTime;
    }

    public int getID() {
        return this.serverNum;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public double getRestTime() {
        return this.restTime.get();
    }

    public boolean isAvailbleAt(Double currTime) {
        return currTime.compareTo(this.getAvailableTime()) >= 0;
    }
    
    public boolean isFull() {
        return this.queue.size() == qmax;
    }
    
    public Server enQueue(int id) {
        return new HumanServer(this.serverNum, availableTime, queue.add(id), qmax, restTime);
    }
    
    public Server deQueue() {
        if (queue.size() == 0) {
            return this;
        }
        return new HumanServer(this.serverNum, availableTime, queue.remove(0), qmax, restTime);
    }

    public int getNextInQ() {
        return queue.get(0);
    }

    public Server update(Double time) {
        return new HumanServer(this.serverNum, time, queue, qmax, restTime);
    }

    public String waitPos() {
        return this.toString();
    }

    public String toString() {
        return String.format("%d", this.serverNum);
    }
}
