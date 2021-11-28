package PresentationLayer;

import javax.swing.*;
import java.awt.*;

public class Login{
    JFrame mainFrame;
    JPanel mainPanel;
    JTextField user,pass;
    JButton login, register;

    public JTextField getUser() {
        return user;
    }

    public JTextField getPass() {
        return pass;
    }

    public Login(){
        mainFrame = new JFrame("Login");
        setFrame(mainFrame);
        mainPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JPanel jPanel2 = new JPanel();
        JPanel jPanel3 = new JPanel();
        user= new JTextField(20);
        pass= new JTextField(20);
        login = new JButton("LOGIN");
        register = new JButton("REGISTER");
        login.addActionListener(new Controller.LoginController());
        register.addActionListener(new Controller.RegisterController());

        setInput(jPanel1,user,"USERNAME:");
        setInput(jPanel2,pass,"PASSWORD:");
        jPanel3.setLayout(new FlowLayout());
        jPanel3.setPreferredSize(new Dimension(400,50));
        jPanel3.add(register);
        jPanel3.add(login);
        mainPanel.add(jPanel3);


        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public void setFrame(JFrame jf){
        jf.setLayout(new GridLayout());
        jf.setSize(550,200);
    }

    public void setInput(JPanel jp, JTextField tf, String details){
        jp.setLayout(new FlowLayout());
        jp.setPreferredSize(new Dimension(400,50));
        jp.add(new JLabel(details,JLabel.LEFT));
        jp.add(tf);
        mainPanel.add(jp);
    }
}
