class SelfCheck implements Server {
    private final int serverNum;
    private final Double availableTime;
   
    SelfCheck(int serverNum, Double availableTime) {
        this.serverNum = serverNum;
        this.availableTime = availableTime;
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
        return true;
    }
    
    public Server enQueue(int id) {
        return this;
    }

    public Server deQueue() {
        return this;
    }

    public int getNextInQ() {
        return 0;
    }

    public Server update(Double time) {
        return new SelfCheck(this.serverNum, time);
    }

    @Override
    public String toString() {
        return String.format("self-check %d", this.serverNum);
    }
}
    

