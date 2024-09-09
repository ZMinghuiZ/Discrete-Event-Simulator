public class Done extends Event {
    Done(Customer customer) {
        super(customer, false);
    }

    @Override
    public Double getTime() {
        return customer.getArrTime() + customer.getServiceTime();
    }

    @Override
    public String toString() {
        return customer.done();
    }
}
