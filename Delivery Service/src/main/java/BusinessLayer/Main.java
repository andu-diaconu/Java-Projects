package BusinessLayer;

import PresentationLayer.Controller;
import PresentationLayer.Login;

public class Main {
    public static void main(String [] args){
        DeliveryServiceProcessing dsp = new DeliveryServiceProcessing();
        Login login = new Login();
        new Controller(login,dsp);
    }
}
