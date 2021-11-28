package PresentationLayer;

import javax.swing.*;
import java.awt.*;

public class GUIClient {
    JFrame mainCFrame;
    JPanel mainCPanel;
    JButton viewMenu;
    JButton search;
    JButton order;
    public JTextField title;

   public GUIClient(){
        mainCFrame =  new JFrame("CLIENT");
        mainCFrame.setLayout(new GridLayout());
        mainCFrame.setSize(500,300);
        mainCPanel = new JPanel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.setPreferredSize(new Dimension(400,50));
        jPanel.add(new JLabel("Menu: ",JLabel.LEFT));
        viewMenu = new JButton("View Menu");
        jPanel.add(viewMenu);
        mainCPanel.add(jPanel);
        viewMenu.addActionListener(new Controller.ViewMenu());

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());
        jPanel1.setPreferredSize(new Dimension(400,50));
        jPanel1.add(new JLabel("Search product",JLabel.LEFT));
        search =  new JButton("Search");
        jPanel1.add(search);
        mainCPanel.add(jPanel1);
        search.addActionListener(new Controller.Search());

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new FlowLayout());
        jPanel2.setPreferredSize(new Dimension(400,50));
        jPanel2.add(new JLabel("Order",JLabel.LEFT));
        order = new JButton("Order");
        jPanel2.add(order);
        mainCPanel.add(jPanel2);
        order.addActionListener(new Controller.OrderController());


        mainCFrame.add(mainCPanel);
        mainCFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainCFrame.setVisible(true);

    }
    JFrame orderFrame;
    public void makeOrder(){
       orderFrame = new JFrame("ORDER");
       orderFrame.setLayout(new GridLayout());
       orderFrame.setSize(410,200);
       JPanel panel = new JPanel();

       title = new JTextField(30);
       JButton ok,next;
       ok = new JButton("OK");
       next =  new JButton("NEXT");

       JPanel panel1 = new JPanel();
       panel1.setLayout(new FlowLayout());
       panel1.setPreferredSize(new Dimension(400,50));
       panel1.add(new JLabel("Title: ",JLabel.LEFT));
       panel1.add(title);
       panel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.setPreferredSize(new Dimension(400,50));
        panel2.add(ok);
        panel2.add(next);
        panel.add(panel2);
        ok.addActionListener(new Controller.OrderDone());
        next.addActionListener(new Controller.NextItem());

        orderFrame.add(panel);
       orderFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       orderFrame.setVisible(true);
    }
}
