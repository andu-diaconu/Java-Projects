package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;

public class Controller {
    public static Login login;
    public static DeliveryServiceProcessing dsp;
    public static GUIAdmin guiAdmin;
    public static GUIClient guiClient;
    private static JTable menu;
    private static int idRegister = 3;
    private static int idOrder = 0;

    public Controller(Login login, DeliveryServiceProcessing dsp){
        Controller.login = login;
        Controller.dsp = dsp;
    }

    public static void setDialogAdd(JPanel panel,String details,JTextField textField){
        panel.add(new JLabel(details));
        panel.add(textField);
        panel.add(Box.createHorizontalStrut(3));
    }

    private static int id=12359;
    public static class LoginController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean wrong = true;
            for(User user: dsp.getUsers()){
              if(user.getUsername().equals(login.getUser().getText()) && user.getPassword().equals(login.getPass().getText())){
                  wrong = false;
                  switch (user.getType()){
                      case CLIENT:
                          guiClient = new GUIClient();
                          break;
                      case ADMIN:
                          guiAdmin =  new GUIAdmin();
                          break;
                      case EMPLOYE:
                          new GUIEmp();
                          break;
                  }
              }
            }

            if(wrong)
                JOptionPane.showMessageDialog(login.mainFrame,"USERNAME OR PASSWORD INCORRECT!");
        }
    }

    public static class ViewMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiAdmin.displayMenu(Controller.menu);
        }
    }

    public static class ImportMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[][] rows = new String [12500][7];
            String[] coloane = {"Title", "Rating", "Calories", "Proteins", "Fat", "Sodium", "Price"};
            Controller.menu = new JTable(rows, coloane);
            dsp.importProds();

            dsp.WriteInTheTable(Controller.menu);
            guiAdmin.displayMenu(Controller.menu);
        }
    }

    public static class AddProduct implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JTextField title, price, calories, protein, fat, sodium, rating;
            title = new JTextField(7);
            price = new JTextField(5);
            calories = new JTextField(5);
            protein = new JTextField(5);
            fat = new JTextField(5);
            sodium = new JTextField(5);
            rating = new JTextField(5);

            JPanel panel = new JPanel();
            Controller.setDialogAdd(panel, "Title: ", title);
            Controller.setDialogAdd(panel, "Rating: ", rating);
            Controller.setDialogAdd(panel, "Calories: ", calories);
            Controller.setDialogAdd(panel, "Protein: ", protein);
            Controller.setDialogAdd(panel, "Fat: ", fat);
            Controller.setDialogAdd(panel, "Sodium: ", sodium);
            Controller.setDialogAdd(panel, "Price: ", price);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Please enter the criterias for your product: ", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                BaseProduct baseProduct = new BaseProduct
                                (title.getText(),
                                Controller.id, Double.parseDouble(rating.getText()),
                                Integer.parseInt(calories.getText()),
                                Integer.parseInt(protein.getText()),
                                Integer.parseInt(fat.getText()),
                                Integer.parseInt(sodium.getText()),
                                Integer.parseInt(price.getText()));
                dsp.addProd(baseProduct);
                dsp.addTheTable(menu);
                id++;
            }
        }
    }

    public static class EditProduct implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField title, id, price, calories, protein, fat, sodium, rating;
            title = new JTextField(7);
            title.setText("");
            id = new JTextField(5);
            price = new JTextField(5);
            price.setText("-10");
            calories = new JTextField(5);
            calories.setText("-10");
            protein = new JTextField(5);
            protein.setText("-10");
            fat = new JTextField(5);
            fat.setText("-10");
            sodium = new JTextField(5);
            sodium.setText("-10");
            rating = new JTextField(5);
            rating.setText("-10");

            JPanel panel = new JPanel();
            Controller.setDialogAdd(panel,"!ID:",id);
            Controller.setDialogAdd(panel, "Title: ", title);
            Controller.setDialogAdd(panel, "Rating: ", rating);
            Controller.setDialogAdd(panel, "Calories: ", calories);
            Controller.setDialogAdd(panel, "Protein: ", protein);
            Controller.setDialogAdd(panel, "Fat: ", fat);
            Controller.setDialogAdd(panel, "Sodium: ", sodium);
            Controller.setDialogAdd(panel, "Price: ", price);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Please enter the fields that need to be edit: ", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                dsp.updateProd(Integer.parseInt(id.getText()),
                        title.getText(),
                        Double.parseDouble(rating.getText()),
                        Integer.parseInt(calories.getText()),
                        Integer.parseInt(protein.getText()),
                        Integer.parseInt(fat.getText()),
                        Integer.parseInt(sodium.getText()),
                        Integer.parseInt(price.getText()),menu);
            }
        }
    }

    public static class DeleteProduct implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField id = new JTextField(5);
            JPanel panel =  new JPanel();
            Controller.setDialogAdd(panel,"ID:",id);
            int result = JOptionPane.showConfirmDialog(null,panel,"DELETE A PRODUCT",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                dsp.deleteProd(Integer.parseInt(id.getText()),menu);
            }
        }
    }

    public static class CreateProduct implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField howMany = new JTextField(5);
            JTextField name = new JTextField(10);
            JPanel panel =  new JPanel();
            Controller.setDialogAdd(panel,"HOW MANY ITEMS :",howMany);
            Controller.setDialogAdd(panel,"WHAT S THE NAME: ",name);
            int result = JOptionPane.showConfirmDialog(null,panel,"CREATE A COMPOSITE PRODUCT",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                JPanel panel1 =  new JPanel();
                int produse  = Integer.parseInt(howMany.getText());
                JTextField[] texts = new JTextField[produse];
                for(int i=0;i<produse;i++) {
                    texts[i] = new JTextField(3);
                    Controller.setDialogAdd(panel1,"ID:",texts[i]);
                }
                int result1 = JOptionPane.showConfirmDialog(null,panel1,"ENTER THE VALID ID FOR PRODUCTS",JOptionPane.OK_CANCEL_OPTION);
                if(result1 == JOptionPane.OK_OPTION){
                    HashSet<BaseProduct> products = new HashSet<>();
                    for(int i=0;i<produse;i++) {
                        for (BaseProduct baseProduct : dsp.getTheProducts())
                            if (baseProduct.getId() == Integer.parseInt(texts[i].getText())){
                                products.add(baseProduct);
                                break;
                            }
                    }

                    CompositeProduct compositeProduct = new CompositeProduct(name.getText(),id,0,0,0,0,0,0);
                    compositeProduct.setProducts(products);
                    compositeProduct.setPrice(compositeProduct.computePrice());
                    dsp.addProduct(compositeProduct);
                    dsp.addTheTable(menu);


                }
            }
        }
    }

    public static class Search implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dsp.printOrders();
            JTextField title = new JTextField(7);
            title.setText("");
            JTextField price = new JTextField(3);
            price.setText("-10");

            JPanel panel = new JPanel();
            Controller.setDialogAdd(panel,"Title:",title);
            Controller.setDialogAdd(panel,"Price:",price);

            int result = JOptionPane.showConfirmDialog(null,panel,"Search:",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                HashSet<MenuItem> filteredMenu = new HashSet<>();
                filteredMenu = dsp.searchInMenu(title.getText(),Integer.parseInt(price.getText()));

                JFrame searchFrame = new JFrame("SEARCH TABLE");
                searchFrame.setLayout(new GridLayout());
                searchFrame.setSize(600,800);
                JPanel tabelPanel = new JPanel();

                String[][] rows = new String [12500][7];
                String[] coloane = {"Title", "Rating", "Calories", "Proteins", "Fat", "Sodium", "Price"};
                JTable newMenu = new JTable(rows,coloane);
                JScrollPane scrollPane = new JScrollPane(newMenu);
                int row = 0;
                for (MenuItem menuItem : filteredMenu) {
                    int col = 0;
                    newMenu.setValueAt(menuItem.getTitle(),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getRating()),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getCalories()),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getProtein()),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getFat()),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getSodium()),row,col++);
                    newMenu.setValueAt(String.valueOf(menuItem.getPrice()),row,col++);
                    row++;
                }
                tabelPanel.add(scrollPane);
                searchFrame.add(tabelPanel);
                searchFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                searchFrame.setVisible(true);
            }
        }
    }

    public static class RegisterController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField usern = new JTextField(7);
            JTextField pass = new JTextField(7);
            JPanel panel = new JPanel();
            Controller.setDialogAdd(panel,"Your username:",usern);
            Controller.setDialogAdd(panel,"Your password:",pass);
            int result = JOptionPane.showConfirmDialog(null,panel,"REGISTER",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION){
                User user = new User(usern.getText(),pass.getText(),Type.CLIENT,idRegister);
                dsp.register(user);
                idRegister++;
            }

        }
    }

    public static class OrderController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiClient.makeOrder();
        }
    }


    public static HashSet<MenuItem> itemsOrder = new HashSet<>();
    public static class OrderDone implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(MenuItem menuItem : dsp.getMenuItems()) {
                if (menuItem.getTitle().contains(guiClient.title.getText())) {
                    itemsOrder.add(menuItem);
                    break;
                }
            }
            guiClient.orderFrame.dispose();
            dsp.newOrder(new Order(idOrder++,idRegister, LocalDateTime.now()),itemsOrder);
            itemsOrder.clear();
        }
    }

    public static class NextItem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(MenuItem menuItem : dsp.getMenuItems()) {
                if (menuItem.getTitle().contains(guiClient.title.getText())) {
                    itemsOrder.add(menuItem);
                    break;
                }
            }
            guiClient.title.setText("");
        }
    }

    public static class TimeControllerReport implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField start = new JTextField(5);
            JTextField end = new JTextField(5);
            JPanel panel = new JPanel();
            setDialogAdd(panel,"Start hour: ",start);
            setDialogAdd(panel,"End hour: ",end);
            int result = JOptionPane.showConfirmDialog(null,panel,"Time Report",JOptionPane.OK_CANCEL_OPTION);
            if(result ==  JOptionPane.OK_OPTION) {
                try {
                    dsp.timeReport(Integer.parseInt(start.getText()),Integer.parseInt(end.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static class ProductOrderControllerReport implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField times = new JTextField(5);
            JPanel panel = new JPanel();
            setDialogAdd(panel,"How many times: ",times);
            int result = JOptionPane.showConfirmDialog(null,panel,"Product Order Report",JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION) {
                try {
                    dsp.commonProdReport(Integer.parseInt(times.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static class ClientControllerReport implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField times = new JTextField(5);
            JTextField amount = new JTextField(5);
            JPanel panel = new JPanel();
            setDialogAdd(panel,"Minim orders by a client: ",times);
            setDialogAdd(panel,"Minimum bill from order: ",amount);
            int result = JOptionPane.showConfirmDialog(null,panel,"Client Report",JOptionPane.OK_CANCEL_OPTION);
            if(result ==  JOptionPane.OK_OPTION) {
                try {
                    dsp.clientsReport(Integer.parseInt(times.getText()),Integer.parseInt(amount.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static class SpecifyDayControllerReport implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField day = new JTextField(5);
            JPanel panel = new JPanel();
            setDialogAdd(panel,"DAY OF MOUNTH: ",day);
            int result = JOptionPane.showConfirmDialog(null,panel,"SPECIFY DAY REPORT",JOptionPane.OK_CANCEL_OPTION);
            if(result ==  JOptionPane.OK_OPTION) {
                try {
                    dsp.countDailyReport(Integer.parseInt(day.getText()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}

