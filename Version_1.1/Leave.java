public class Leave extends Event {
    Leave(Customer customer) {
        super(customer, false);
    }

    @Override
    public Double getTime() {
        return customer.getArrTime();
    }

    @Override
    public String toString() {
        return customer.leaves();
    }
}
