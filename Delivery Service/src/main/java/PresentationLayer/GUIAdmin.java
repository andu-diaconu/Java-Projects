package PresentationLayer;

import javax.swing.*;
import java.awt.*;

public class GUIAdmin {
    JFrame mainAFrame;
    JPanel mainAPanel;
    JButton populate;
    JButton add, delete, edit;
    JButton create;
    JButton time,product,clients,day;

    public  GUIAdmin(){
        mainAFrame =  new JFrame("ADMIN");
        mainAFrame.setLayout(new GridLayout());
        mainAFrame.setSize(620,300);
        mainAPanel = new JPanel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.setPreferredSize(new Dimension(300,50));
        jPanel.add(new JLabel("Import products : ",JLabel.LEFT));
        populate = new JButton("IMPORT");
        jPanel.add(populate);
        mainAPanel.add(jPanel);
        populate.addActionListener(new Controller.ImportMenu());

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());
        jPanel1.setPreferredSize(new Dimension(600,50));
        jPanel1.add(new JLabel("Manage Products: ",JLabel.LEFT));
        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        jPanel1.add(add);
        jPanel1.add(edit);
        jPanel1.add(delete);
        mainAPanel.add(jPanel1);
        add.addActionListener(new Controller.AddProduct());
        edit.addActionListener(new Controller.EditProduct());
        delete.addActionListener(new Controller.DeleteProduct());

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new FlowLayout());
        jPanel2.setPreferredSize(new Dimension(400,50));
        jPanel2.add(new JLabel("Create a product: ",JLabel.LEFT));
        create = new JButton("Create product");
        jPanel2.add(create);
        mainAPanel.add(jPanel2);
        create.addActionListener(new Controller.CreateProduct());

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new FlowLayout());
        jPanel3.setPreferredSize(new Dimension(600,50));
        time = new JButton("Time Report");
        jPanel3.add(time);
        product = new JButton("Products Order Report");
        jPanel3.add(product);
        clients = new JButton("Client Report");
        jPanel3.add(clients);
        day = new JButton("Specify Day Report");
        jPanel3.add(day);
        mainAPanel.add(jPanel3);
        time.addActionListener(new Controller.TimeControllerReport());
        product.addActionListener(new Controller.ProductOrderControllerReport());
        clients.addActionListener(new Controller.ClientControllerReport());
        day.addActionListener(new Controller.SpecifyDayControllerReport());

        mainAFrame.add(mainAPanel);
        mainAFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainAFrame.setVisible(true);

    }

    public void displayMenu(JTable menu){
        JFrame tabelFrame = new JFrame("Menu");
        tabelFrame.setLayout(new GridLayout());
        tabelFrame.setSize(600,800);
        JPanel jPanel1 = new JPanel();

        JScrollPane sp = new JScrollPane(menu);
        jPanel1.add(sp);
        tabelFrame.add(jPanel1);

        tabelFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        tabelFrame.setVisible(true);
    }
}
