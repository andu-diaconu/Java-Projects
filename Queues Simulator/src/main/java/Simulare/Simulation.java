package Simulare;

import Prelucrare.Customer;
import Prelucrare.Queuee;
import Prelucrare.Scheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Simulation implements Runnable{

    public int noCustomers;
    public int noQueues;
    public int minProcTime ;
    public int maxProcTime;
    public int minArrTime ;
    public int maxArrTime ;
    public int timeSimulation;
    private double avgWait;
    private double avgServ;
    private int peakHour,auxPeakHour;
    private final Scheduler scheduler;
    private final List<Customer> customers;
    private final Interface window;

    public Simulation(){
        window = new Interface();
        while(true){
            if(window.checkOk()) {
                setInput(window);
                window.createQ(noQueues);
                break;
            }
        }

        customers = new LinkedList<>();
        generateRandomCustomers();
        scheduler = new Scheduler(noQueues);

    }

    private void setInput(Interface window){
        if(!window.s1.getText().equals(""))
            noCustomers = Integer.parseInt(window.s1.getText());
        if(!window.s2.getText().equals(""))
            noQueues = Integer.parseInt(window.s2.getText());
        if(!window.s4.getText().equals(""))
            minArrTime = Integer.parseInt(window.s4.getText());
        if(!window.s5.getText().equals(""))
            maxArrTime = Integer.parseInt(window.s5.getText());
        if(!window.s6.getText().equals(""))
            minProcTime = Integer.parseInt(window.s6.getText());
        if(!window.s7.getText().equals(""))
            maxProcTime = Integer.parseInt(window.s7.getText());
        if(!window.s3.getText().equals(""))
            timeSimulation = Integer.parseInt(window.s3.getText());
    }

    private void generateRandomCustomers(){
        Random rand = new Random();
        for(int i=1;i<=noCustomers;i++){
            int arr = rand.nextInt(maxArrTime - minArrTime + 1) + minArrTime;
            int proc = rand.nextInt(maxProcTime - minProcTime + 1) + minProcTime;
            customers.add(new Customer(i,arr,proc));
        }
        customers.sort(Comparator.comparingInt(Customer::getArrTime));
    }

    private void logFile(int currTime) throws IOException {
        FileWriter log = new FileWriter("test1.txt",true);
        PrintWriter printWriter = new PrintWriter(log);
        int index=0;
        printWriter.print("\nTime:"+currTime+"\nWAITING CLIENTS:");
        for (Customer c2 : customers)
            printWriter.print(c2.toString());

        printWriter.println();
        for (Queuee q : scheduler.getQueues()) {
            index++;
            printWriter.print("QUEUE:"+index+": ");
            if (q.getCustomers().size() == 0)
                printWriter.println("CLOSED\n");
            else {
                for (Customer c : q.getCustomers())
                    printWriter.print(c.toString());
                printWriter.println();
            }
        }
        printWriter.close();
        log.close();
    }

    @Override
    public void run() {
        int currTime = 0;
        int maxim = 0;
        try {
            FileWriter log = new FileWriter("test1.txt");
            PrintWriter printWriter = new PrintWriter(log);
            printWriter.print("");
            printWriter.close();
            log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (currTime < timeSimulation) {

            while (!customers.isEmpty() && currTime >= customers.get(0).getArrTime()) {
                scheduler.attCustomer(customers.get(0));
                avgWait+=scheduler.minimum.get();
                auxPeakHour = scheduler.minimum.get();
                if(auxPeakHour>maxim) {
                    maxim = auxPeakHour;
                    peakHour = currTime;
                }
                avgServ+=customers.get(0).getProcTime();
                customers.remove(0);
            }
            try {
                logFile(currTime);
            } catch (IOException e) {
                e.printStackTrace();
            }
            window.setT(currTime);
            window.setW(customers);
            window.setSet(scheduler.getQueues());

            if (queuesClosed() && customers.isEmpty())
                break;

            currTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter log = new FileWriter("test1.txt",true);
            PrintWriter printWriter = new PrintWriter(log);
            printWriter.println("AVG WAIT "+avgWait/(noCustomers-customers.size()));
            printWriter.println("AVG SERV "+avgServ/(noCustomers-customers.size()));
            printWriter.println("PEAK HOUR "+peakHour);
            printWriter.close();
            log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private boolean queuesClosed(){
        for(Queuee q : scheduler.getQueues())
            if(q.getWaitTime().get() != 0)
                return false;
        return true;
    }


    public static void main(String [] args){
        Simulation st = new Simulation();
        Thread t = new Thread(st);
        t.start();
    }
}
