package Interface;

import Logical.Polinom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class View extends JFrame {

    public boolean isSelected = false;
    public Polinom poli1,poli2;
    public JFrame myFrame;
    public JTextField p1,p2,rezultat;
    public JButton ok1,ok2, sum,dif,prod,divis,deriv,integ,del,plus,minus,ics,pow;
    public ArrayList<JButton> keyboard;
    public JPanel intro_p1,intro_p2,intro_rezultat,col1,col2;
    public View(){
        dooLayout();
    }

    private void dooLayout(){

        myFrame = new JFrame("Calculator Polinoame");
        myFrame.setLayout(new GridLayout(1,2));
        myFrame.setSize(950,600);
        col1 = new JPanel();
        col2 = new JPanel();
        col1.setBackground(Color.LIGHT_GRAY);
        col2.setBackground(Color.LIGHT_GRAY);
        myFrame.add(col1);
        myFrame.add(col2);

        tastatura(col2);
        inputLayout(col1);

        ok1.addActionListener(new Controller.Controller1());
        ok2.addActionListener(new Controller.Controller2());

        outputLayout(col1);
        operatii(col1);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

    private void inputLayout(JPanel col1) {
        intro_p1 = new JPanel();
        p1 = new JTextField(30);
        ok1 = new JButton("OK");
        setLayout(intro_p1,ok1,p1);

        intro_p2 = new JPanel();
        p2 = new JTextField(30);
        ok2 = new JButton("OK");
        setLayout(intro_p2,ok2,p2);

        p1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { isSelected = true; }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        p2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { isSelected = false; }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        col1.add(intro_p1);
        col1.add(intro_p2);

    }

    private void setLayout(JPanel jp,JButton jb,JTextField jt){
        jp.setBackground(Color.LIGHT_GRAY);
        jp.setLayout(new FlowLayout());
        jp.add(new JLabel("Polinom:",JLabel.LEFT));
        jt.setBorder(BorderFactory.createLineBorder(Color.black));
        jp.add(jt);
        jb.setBackground(Color.green);
        jp.add(jb);
    }

    private void outputLayout(JPanel col1) {
        intro_rezultat = new JPanel();
        intro_rezultat.setBackground(Color.lightGray);
        intro_rezultat.setLayout(new FlowLayout());
        intro_rezultat.add(new JLabel("Rezultatul= ",JLabel.LEFT));
        rezultat = new JTextField(30);
        rezultat.setEditable(false);
        rezultat.setBackground(Color.LIGHT_GRAY);
        rezultat.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        intro_rezultat.add(rezultat);
        col1.add(intro_rezultat);
    }

    private void operatii(JPanel col1) {

        sum = new JButton("Adunare (+)");
        dif = new JButton("Scadere (-)");
        prod = new JButton("Inmultirea (*)");
        divis = new JButton("Impartirea (/)");
        deriv = new JButton("Derivare (dx)");
        integ = new JButton("Integrare");

        setOperationsLayout(sum, col1);
        setOperationsLayout(dif, col1);
        setOperationsLayout(prod, col1);
        setOperationsLayout(divis, col1);
        setOperationsLayout(deriv, col1);
        setOperationsLayout(integ, col1);


        sum.addActionListener(new Controller.SumController());
        dif.addActionListener(new Controller.DifController());
        prod.addActionListener(new Controller.ProdController());
        deriv.addActionListener(new Controller.DerivController());
        divis.addActionListener(new Controller.DivisController());
        integ.addActionListener(new Controller.IntegController());

    }

    private void setOperationsLayout(JButton jb, JPanel jpc){
        JPanel jp = new JPanel();
        jp.setBackground(Color.lightGray);
        jp.setLayout(new FlowLayout());
        jb.setBorder(BorderFactory.createLineBorder(Color.black));
        jb.setPreferredSize(new Dimension(400,50));
        jp.add(jb);
        jpc.add(jp);
    }

    private void tastatura(JPanel col2) {

        keyboard = new ArrayList<>();

        col2.setLayout(new GridLayout(5,3));
        col2.setSize(new Dimension(300,300));
        col2.setBorder(BorderFactory.createTitledBorder("Tastatura"));

        for(int index=0;index<10;index++){
            JButton jb = new JButton(String.valueOf(index));
            keyboard.add(jb);
        }

        plus = new JButton("+");
        keyboard.add(plus);
        minus = new JButton("-");
        keyboard.add(minus);
        pow = new JButton("POW");
        keyboard.add(pow);
        ics = new JButton("X");
        keyboard.add(ics);
        del = new JButton("DEL");
        keyboard.add(del);

        for (JButton jb:
             keyboard) {
            jb.setBackground(Color.ORANGE);
            jb.setBorder(BorderFactory.createLineBorder(Color.black));
            col2.add(jb);
        }

        for (JButton jb:
             keyboard) {
            jb.addActionListener(e->{
                if(jb.getText().equals("POW")) {
                    if(isSelected) p1.setText(p1.getText()+"^");
                    if(!isSelected) p2.setText(p2.getText()+"^");
                }

                else if(jb.getText().equals("DEL")){
                    if(isSelected && p1.getText().length()!=0) p1.setText(p1.getText().substring(0, p1.getText().length()-1));
                    if(!isSelected && p2.getText().length()!=0) p2.setText(p2.getText().substring(0,p2.getText().length()-1));
                }
                else {
                    if (isSelected) p1.setText(p1.getText() + jb.getText());
                    if (!isSelected) p2.setText(p2.getText() + jb.getText());
                }
            });
        }
    }

}
