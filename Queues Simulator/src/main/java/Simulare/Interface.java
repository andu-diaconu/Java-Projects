package Simulare;

import Prelucrare.Customer;
import Prelucrare.Queuee;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Interface {

    private final JFrame myFrame;
    private final JPanel col1;
    private final JPanel col2;
    public JTextField s1,s2,s3,s4,s5,s6,s7;
    private JButton k;
    private JTextField t,w;
    private final JTextField [] clienti = new JTextField[100];
    private boolean itsOk = false;

    public Interface(){
        myFrame = new JFrame("Queue Simulator");
        myFrame.setLayout(new GridLayout(1,2));
        myFrame.setSize(970,430);

        col1 = new JPanel();
        col2 = new JPanel();
        col1.setBackground(Color.LIGHT_GRAY);
        col2.setBackground(Color.LIGHT_GRAY);
        col1.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        col2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        myFrame.add(col1);
        myFrame.add(col2);

        input();
        output();

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

    private void input() {
        JPanel sett1 = new JPanel();
        s1 = new JTextField(5);
        setInputCSS(sett1,s1,"Number of clients: ");

        JPanel sett2 = new JPanel();
        s2 = new JTextField(5);
        setInputCSS(sett2,s2,"Number of queues: ");

        JPanel sett3 = new JPanel();
        s3 = new JTextField(5);
        setInputCSS(sett3,s3,"Simulation interval: ");

        JPanel sett4 = new JPanel();
        s4 = new JTextField(5);
        s5 = new JTextField(5);
        setInputCSS(sett4,s4,"Arrival time - minimum: ");
        setInputCSS(sett4,s5," maximum : ");

        JPanel sett5 = new JPanel();
        s6 = new JTextField(5);
        s7 = new JTextField(5);
        setInputCSS(sett5,s6,"Service time - minimum: ");
        setInputCSS(sett5,s7," maximum : ");

        k = new JButton("SIMULATE");
        k.setBackground(Color.green);
        k.setLayout(new FlowLayout());
        k.setPreferredSize(new Dimension(120,30));
        k.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        col1.add(sett1);
        col1.add(sett2);
        col1.add(sett3);
        col1.add(sett4);
        col1.add(sett5);
        col1.add(k);

    }

    private void setInputCSS(JPanel sett, JTextField s, String details) {
        sett.setBackground(Color.LIGHT_GRAY);
        sett.setLayout(new FlowLayout());
        sett.setPreferredSize(new Dimension(400,50));
        sett.add(new JLabel(details,JLabel.LEFT));
        s.setBorder(BorderFactory.createLineBorder(Color.black));
        sett.add(s);
    }

    private void output() {
        JPanel time = new JPanel();
        t = new JTextField(3);
        setOutputCSS(time,t,"TIME:");

        JPanel waitingC =  new JPanel();
        w = new JTextField(37);
        setOutputCSS(waitingC,w,"Waiting clients:");

        col2.add(time);
        col2.add(waitingC);
    }

    private void setOutputCSS(JPanel jp, JTextField jt, String s) {

        jp.setBackground(Color.LIGHT_GRAY);
        jp.setLayout(new FlowLayout());
        jp.add(new JLabel(s,JLabel.LEFT));
        jt.setBackground(Color.LIGHT_GRAY);
        jt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jt);

    }

    public void setT(int time){
        t.setText(String.valueOf(time));
    }

    public void setW(java.util.List<Customer> customers) {
        w.setText("");
        for(Customer c : customers)
            w.setText(w.getText()+c.toString()+" ");
    }

    public void setSet(List<Queuee> queues) {
        int index =0;
        for (Queuee q : queues) {
            index ++;
            if (q.getCustomers().size() == 0)
                clienti[index].setText("CLOSED\n");
            else {
                String s = new String();
                for (Customer c : q.getCustomers())
                        s+=c.toString();
                clienti[index].setText(s);
            }
        }
    }

    public void createQ(int size) {
        JPanel [] cozies = new JPanel[size+1];
        for(int i=1;i<=size;i++) {
            cozies[i] = new JPanel();
            clienti[i] = new JTextField(37);
            setOutputCSS(cozies[i],clienti[i],"Queue"+i+" :");
            col2.add(cozies[i]);
        }
        myFrame.setVisible(true);
    }

    public boolean checkOk(){
        k.addActionListener(e -> itsOk = true);
        return itsOk;
    }

}
