public interface Server {
    int getID();

    double getAvailableTime();

    double getRestTime();

    boolean isAvailbleAt(Double currTime);
    
    boolean isFull();
    
    Server enQueue(int id);

    Server deQueue();

    int getNextInQ();

    Server update(Double time);
}
