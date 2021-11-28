package Prelucrare;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queuee implements Runnable{
    private final BlockingQueue<Customer> customers;
    private final AtomicInteger waitTime;

    public Queuee(){
        customers = new LinkedBlockingQueue<>();
        waitTime = new AtomicInteger(0);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
        waitTime.addAndGet(customer.getProcTime());
    }


    @Override
    public void run() {
        while(true) {
            while (!customers.isEmpty()) {
                Customer c = customers.peek();
                try {
                    Thread.sleep(1000L);
                    c.timesUp();
                    waitTime.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(c.getProcTime() == 0)
                    customers.remove(c);
            }
        }
    }

    public AtomicInteger getWaitTime() {
        return waitTime;
    }

    public BlockingQueue<Customer> getCustomers() {
        return customers;
    }

}
