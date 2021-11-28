package PresentationLayer;


import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class GUIEmp implements Observer {
    JFrame frame = new JFrame("EMPLOYE");

    @Override
    public void update(Observable o, Object order) {
        JOptionPane.showMessageDialog(frame,"NEW ORDER WITH ID  : "+order);

    }
}
