class SelfCheckHead implements Server {
    private final int serverNum;
    private final Double availableTime;
    private final int qmax;
    private final ImList<Integer> selfCheckQ;
   
    SelfCheckHead(int serverNum, Double availableTime, int qmax) {
        this.serverNum = serverNum;
        this.availableTime = availableTime;
        this.qmax = qmax;
        this.selfCheckQ = new ImList<Integer>();
    }

    SelfCheckHead(int serverNum, Double availableTime, ImList<Integer> queue, int qmax) {
        this.serverNum = serverNum;
        this.availableTime = availableTime;
        this.selfCheckQ = queue;
        this.qmax = qmax;
    }

    public int getID() {
        return this.serverNum;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public double getRestTime() {
        return 0.0;
    }

    public boolean isAvailbleAt(Double currTime) {
        return currTime.compareTo(this.getAvailableTime()) >= 0;
    }
    
    public boolean isFull() {
        return selfCheckQ.size() == qmax;
    }
    
    public Server enQueue(int id) {
        return new SelfCheckHead(this.serverNum, availableTime, selfCheckQ.add(id), qmax);
    }

    public Server deQueue() {
        if (selfCheckQ.size() == 0) {
            return this;
        }
        return new SelfCheckHead(this.serverNum, availableTime, selfCheckQ.remove(0), qmax);
    }

    public int getNextInQ() {
        return selfCheckQ.get(0);
    }

    public Server update(Double time) {
        return new SelfCheckHead(this.serverNum, time, selfCheckQ, qmax);
    }

    public String toString() {
        return String.format("self-check %d", this.serverNum);
    }
}