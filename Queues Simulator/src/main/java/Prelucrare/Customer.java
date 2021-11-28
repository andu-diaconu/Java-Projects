package Prelucrare;

public class Customer {
    private final int id;
    private final int arrTime;
    private int procTime;

    public Customer(int id,int arrTime,int procTime){
        this.id = id;
        this.arrTime = arrTime;
        this.procTime = procTime;
    }

    public int getProcTime() {
        return procTime;
    }

    public int getArrTime() {
        return arrTime;
    }

    public void timesUp(){this.procTime --;}

    @Override
    public String toString(){
        return " ("+id+" "+arrTime+" "+procTime+");";
    }

}
