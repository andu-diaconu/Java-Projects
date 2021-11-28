package Prelucrare;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler{

    private final List<Queuee> queues;
    public AtomicInteger minimum;
    public Scheduler(int noQueues) {
        queues = new LinkedList<>();
        for (int i = 1; i <= noQueues; i++) {
            Queuee q = new Queuee();
            queues.add(q);
            Thread t = new Thread(q);
            t.start();
        }
    }

    public void attCustomer(Customer customer){
        AtomicInteger min = new AtomicInteger(100);
        for(Queuee q : queues)
            if(q.getWaitTime().get()< min.get())
                min.set(q.getWaitTime().get());
        for(Queuee q : queues)
            if(q.getWaitTime().get() == min.get()) {
                q.addCustomer(customer);
                break;
            }
        minimum = min;
    }


    public List<Queuee> getQueues() {
        return queues;
    }
}
