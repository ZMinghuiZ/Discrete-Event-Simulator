public class Server {
    private final Double availableTime;
    private final ImList<Integer> queue;
    private final int qmax;
   
    Server(Double availableTime, int qmax) {
        this.availableTime = availableTime;
        queue = new ImList<Integer>();
        this.qmax = qmax;
    }

    Server(Double availableTime, ImList<Integer> queue, int qmax) {
        this.availableTime = availableTime;
        this.queue = queue;
        this.qmax = qmax;
    }

    Double getAvailableTime() {
        return this.availableTime;
    }

    boolean isAvailbleAt(Double currTime) {
        return currTime.compareTo(this.availableTime) >= 0;
    }
    
    boolean isFull() {
        return queue.size() == qmax;
    }
    
    Server enQueue(int id) {
        return new Server(availableTime, queue.add(id), qmax);
    }

    Server deQueue() {
        if (queue.size() == 0) {
            return this;
        }
        return new Server(availableTime, queue.remove(0), qmax);
    }

    Integer getNextInQ() {
        return queue.get(0);
    }

    Server update(Double time) {
        return new Server(time, queue, qmax);
    }
}
