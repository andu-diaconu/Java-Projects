package BusinessLayer;

import PresentationLayer.GUIEmp;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeliveryServiceProcessing extends Observable implements IDeliveryServiceProcessing{

    private final Map<Order, HashSet<MenuItem>> comenzi = new HashMap<>();
    private final HashSet<MenuItem> menuItems = new HashSet<>();

    public HashSet<MenuItem> getMenuItems() {
        return menuItems;
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    private final LinkedList<User> users = new LinkedList<>();
    DeliveryServiceProcessing(){
        users.add(new Admin("andu_admin","pass_admin",Type.ADMIN,1));
        users.add(new Employe("andu_emp","pass_emp", Type.EMPLOYE,2));
    }

    public void register(User user){
        users.add(user);
    }

    public boolean isWellFormed(){
        // If its a Client he can : order , compute price and search in Menu
        for(User user: users)
            if(user.getType() == Type.CLIENT)
                return true;
        return false;
    }

    /**
     *
     * @param menuItemss set with items selected from Menu
     * @return the total price for menuItems selected
     * @pre menuItems != null
     * @post money>0
     */
    @Override
    public int computePrice(HashSet<MenuItem> menuItemss) {
        assert(menuItemss!=null) :"0 menu items selected";
        int money = 0;
        for(MenuItem menuItem : menuItemss)
            money+=menuItem.getPrice();
        assert(money>0):"Total price should not be 0(free) or lower";
        return money;

    }

    public void popUp(int idOrder){
        setChanged();
        notifyObservers(idOrder);
    }

    /**
     *
     * @param order order done by a client
     * @param selectedItems set with items order by a client
     * @pre selectedItems != null
     * @post comenzi!= null
     * @invariant user.Type == Client
     */
    @Override
    public void newOrder(Order order, HashSet<MenuItem> selectedItems) {
        assert(selectedItems!=null):"0 menu itmes selected";
        GUIEmp observer = new GUIEmp();
        this.addObserver(observer);
        this.popUp(order.getOrderID());
        HashSet<MenuItem> copy = new HashSet<> (selectedItems);
        doReceipt(order,copy);
        comenzi.put(order,copy);
        assert(comenzi!=null):"The order is not valid";
        assert(isWellFormed());
    }



    public void printOrders(){
        for(HashSet<MenuItem> comanda : comenzi.values()){
            for(MenuItem menuItem : comanda)
                System.out.println(menuItem.toString());
            System.out.println("!!");
        }
    }

    public void doReceipt(Order order, HashSet<MenuItem> selectedItems){
        try {
            FileWriter log1 = new FileWriter("0Receipt.txt");
            PrintWriter printWriter1 = new PrintWriter(log1);
            printWriter1.println("");
            printWriter1.close();
            log1.close();

            try {
                FileWriter log = new FileWriter("0Receipt.txt",true);
                PrintWriter printWriter = new PrintWriter(log);
                for(MenuItem menuItem : selectedItems)
                    printWriter.println(menuItem.getTitle()+",");
                printWriter.println();
                printWriter.println();
                printWriter.println("Client:"+ order.getClientID()+", on "+order.getOrderDate()+" has to pay "+computePrice(selectedItems)+"$");
                printWriter.close();
                log.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private HashSet<BaseProduct> theProducts = new HashSet<>();


    //Search by title and highest price
    /**
     *
     * @param title search after this title
     * @param price search products with maximum price price
     * @return a set with the products found afet filtering them
     * @post searchMenu != null
     * @invariant user.Type == Client
     */
    @Override
    public HashSet<MenuItem> searchInMenu(String title, int price) {
        //Same principle : the start point is -10
        //price is the highest price the client wants :)
        assert(isWellFormed());
        HashSet<MenuItem> searchInMenu = menuItems;
        if(price>-10)
            searchInMenu = (HashSet<MenuItem>) menuItems
                    .stream()
                    .filter(menuItem -> menuItem.getPrice() <= price)
                    .collect(Collectors.toSet());
        if(!title.equals("")&& price == -10)
            searchInMenu =
                    (HashSet<MenuItem>) menuItems
                            .stream()
                            .filter(item -> item.getTitle().contains(title))
                            .collect(Collectors.toSet());
        if(!title.equals("") && price>-10)
            searchInMenu =
                    (HashSet<MenuItem>) searchInMenu
                            .stream()
                            .filter(item -> item.getTitle().contains(title))
                            .collect(Collectors.toSet());
        assert(searchInMenu!=null):"No items found";
        assert(isWellFormed());
        return searchInMenu;
    }


    public static <T> Predicate<T> distinctByTitle(Function<? super T, ?> keyExtractor) {
        Set<Object> stackOverFlow = ConcurrentHashMap.newKeySet();
        return t -> stackOverFlow.add(keyExtractor.apply(t));
    }

    /**
     * Import the products from .csv to a HashMap
     * @post theProducts!= null
     */
    @Override
    public void importProds() {
        String title;
        double rating = 0;
        int id=-1, calories, protein, fat, sodium, price;

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("products.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert bufferedReader != null;
        List<String[]> extract = bufferedReader.lines()
                .skip(1)
                .map(e -> e.split(","))
                .collect(Collectors.toList());

        for(String [] data : extract){
            title = data [0];
            id++;
            rating = Double.parseDouble(data[1]);
            calories = Integer.parseInt(data[2]);
            protein = Integer.parseInt(data[3]);
            fat = Integer.parseInt(data[4]);
            sodium = Integer.parseInt(data[5]);
            price = Integer.parseInt(data[6]);

            // add in the products list every single product
            BaseProduct product = new BaseProduct(title, id, rating, calories, protein, fat, sodium, price);
            theProducts.add(product);
        }
        assert (theProducts!=null):"File not found";
        writeTheMenu();// remove the clones
    }

    public HashSet<BaseProduct> getTheProducts() {
        return theProducts;
    }

    int auxiliarID = -1;
    public void writeTheMenu(){

        // clean the clones from basePrdouct
        theProducts = (HashSet<BaseProduct>) theProducts
                .stream()
                .filter(distinctByTitle(MenuItem::getTitle))
                .collect(Collectors.toSet());

        // If the menu is not empty ... it should be!
        menuItems.clear();

        //Fill up the menu again
        for(BaseProduct product : theProducts){
            product.setId(++auxiliarID);
            menuItems.add(product);
        }

    }

    int row=0;
    public void WriteInTheTable(JTable table){
        row = 0;
        for (MenuItem menuItem : menuItems) {
            int col = 0;
            table.setValueAt(menuItem.getTitle(),row,col++);
            table.setValueAt(String.valueOf(menuItem.getRating()),row,col++);
            table.setValueAt(String.valueOf(menuItem.getCalories()),row,col++);
            table.setValueAt(String.valueOf(menuItem.getProtein()),row,col++);
            table.setValueAt(String.valueOf(menuItem.getFat()),row,col++);
            table.setValueAt(String.valueOf(menuItem.getSodium()),row,col++);
            table.setValueAt(String.valueOf(menuItem.getPrice()),row,col++);
            row++;
        }
        int skipDel = row;
        table.setValueAt("",skipDel,0);
        table.setValueAt("",skipDel,1);
        table.setValueAt("",skipDel,2);
        table.setValueAt("",skipDel,3);
        table.setValueAt("",skipDel,4);
        table.setValueAt("",skipDel,5);
        table.setValueAt("",skipDel,6);

    }

    public void addTheTable(JTable table){

        MenuItem ultimu = null;
        for(MenuItem menuItem : menuItems)
            if(menuItem.getId() == auxiliarID) {
                ultimu = menuItem;
                break;
            }

        table.setValueAt(ultimu.getTitle(),row,0);
        table.setValueAt(String.valueOf(ultimu.getRating()),row,1);
        table.setValueAt(String.valueOf(ultimu.getCalories()),row,2);
        table.setValueAt(String.valueOf(ultimu.getProtein()),row,3);
        table.setValueAt(String.valueOf(ultimu.getFat()),row,4);
        table.setValueAt(String.valueOf(ultimu.getSodium()),row,5);
        table.setValueAt(String.valueOf(ultimu.getPrice()),row,6);
        row++;

    }


    public void addProduct(CompositeProduct compositeProduct){
        auxiliarID++;
        menuItems.add(compositeProduct);
    }
    /**
     *
     * @param baseProduct produsul de adaugat in meniu
     * @pre baseProduct != null
     * @post menuItmes!=null
     */
    @Override
    public void addProd(BaseProduct baseProduct) {
        assert(baseProduct != null);
        auxiliarID++;
        menuItems.add(baseProduct);
        assert(menuItems!=null):"Item was not added";
    }

    /**
     *
     * @param id product id that will be updated
     * @param title name of product
     * @param rating rating of product
     * @param calories calories from product
     * @param protein protien from product
     * @param fat fat in product
     * @param sodium sodium in product
     * @param price costs the product
     * @param table will pe display
     * @pre id>=0
     */
    @Override
    public void updateProd(int id, String title, double rating, int calories, int protein, int fat, int sodium, int price,JTable table) {
        assert(id>-1);
        for(MenuItem product : menuItems){
            if(product.getId() == id) {
                if (!title.equals("")) {
                    product.setTitle(title);
                    table.setValueAt(title, id, 0);
                }
                if (rating > -10) {
                    product.setRating(rating);
                    table.setValueAt(String.valueOf(rating), id, 1);
                }
                if (calories > -10) {
                    product.setRating(calories);
                    table.setValueAt(String.valueOf(calories), id, 2);
                }
                if (protein > -10){
                    product.setRating(protein);
                    table.setValueAt(String.valueOf(protein), id, 3);
                }
                if(fat > -10) {
                    product.setRating(fat);
                    table.setValueAt(String.valueOf(fat), id, 4);
                }
                if(sodium > -10) {
                    product.setRating(sodium);
                    table.setValueAt(String.valueOf(sodium), id, 5);
                }
                if(price > -10) {
                    product.setRating(price);
                    table.setValueAt(String.valueOf(price), id, 6);
                }

            }
        }
    }

    /**
     *
     * @param id product that will be deleted
     * @param table will pe updated
     * @pre id>=0
     */
    @Override
    public void deleteProd(int id,JTable table) {
        assert (id>-1);
        menuItems.removeIf(item -> item.getId() == id);
        WriteInTheTable(table);
    }

    /**
     *
     * @param startTime start hour in interval
     * @param endTime the last hour from interval
     * @throws IOException if the file cant be open
     * @pre startTime < endTime
     * @post ordersTime != null
     */
    @Override
    public void timeReport(int startTime, int endTime) throws IOException {
        assert(startTime < endTime);

        FileWriter log1 = new FileWriter("0timeReport.txt");
        PrintWriter printWriter1 = new PrintWriter(log1);
        printWriter1.println("");
        printWriter1.close();
        log1.close();

        FileWriter log = new FileWriter("0timeReport.txt",true);
        PrintWriter printWriter = new PrintWriter(log);
        printWriter.println("Start: "+startTime);

        List<Order> ordersTime = new LinkedList<>();
        ordersTime = comenzi.keySet()
                .stream()
                .filter(order -> order.getOrderDate().getHour()<=endTime && order.getOrderDate().getHour()>=startTime)
                .collect(Collectors.toList());
        ordersTime.stream()
                .forEach(order -> printWriter.println(order.toString()+","));

        printWriter.print("\nDone: "+endTime);
        printWriter.close();
        log.close();
        assert(ordersTime!=null):"No order found between your times";

    }

    /**
     *
     * @param count order more than this value
     * @throws IOException if the file cant be open
     * @pre count> 0
     * @post results!=null
     */
    @Override
    public void commonProdReport(int count) throws IOException {
        assert count>0 : "Every single product is ordered more than 0 times!";
        FileWriter log1 = new FileWriter("0commonProductReport.txt");
        PrintWriter printWriter1 = new PrintWriter(log1);
        printWriter1.println("");
        printWriter1.close();
        log1.close();

        FileWriter log = new FileWriter("0commonProductReport.txt",true);
        PrintWriter printWriter = new PrintWriter(log);
        printWriter.println("We are looking for products ordered more than "+count+" times!");

        HashMap<MenuItem, Integer> realTimeOrder = new HashMap<>();
        for(Set<MenuItem> order : comenzi.values())
            order.stream().
                    forEach(menuItem -> realTimeOrder.put(menuItem,realTimeOrder.containsKey(menuItem)? realTimeOrder.get(menuItem)+1:1));

        Set<MenuItem> results = realTimeOrder.keySet().stream()
                .filter(hatz -> realTimeOrder.get(hatz) >= count)
                .collect(Collectors.toSet());

        results.stream()
                .forEach(menuItem -> printWriter.println(menuItem.getTitle()+","));

        printWriter.println("DONE");
        printWriter.close();
        log.close();
        assert (results!=null):"No products order more than selected times";
    }

    /**
     *
     * @param count minimum order by a client
     * @param amount with a minimum value
     * @throws IOException if the file cant be open
     * @pre count>0 && amount>0
     * @post orders!=null
     */
    @Override
    public void clientsReport(int count, int amount) throws IOException {
        assert count >0 && amount >0 :"Should be at least 1";
        FileWriter log1 = new FileWriter("0clientsReport.txt");
        PrintWriter printWriter1 = new PrintWriter(log1);
        printWriter1.println("");
        printWriter1.close();
        log1.close();

        FileWriter log = new FileWriter("0clientsReport.txt",true);
        PrintWriter printWriter = new PrintWriter(log);
        printWriter.println("We are looking for clients that order more than "+count+" times and bill more than "+amount);

        Set<Order> orders = new HashSet<>();
        orders = comenzi.keySet().stream()
                .filter(comanda -> computePrice(comenzi.get(comanda))> amount)
                .collect(Collectors.toSet());

        HashMap<Integer, Integer> results = new HashMap<>();
        orders.stream()
                .forEach(order -> results.put(order.getClientID(),results.containsKey(order.getClientID())?results.get(order.getClientID())+1:1));

        Set<Integer> idClients = new HashSet<>();
        idClients =  results.keySet().stream()
                .filter(result -> results.get(result)>=count)
                .collect(Collectors.toSet());
        idClients.stream()
                .forEach(id->printWriter.println("Client with ID:"+id));
        printWriter.close();
        log.close();
        assert(orders!=null):"Amount selected its too much";
    }

    /**
     *
     * @param day day of month
     * @throws IOException if the file cant be open
     * @pre day>=1 && day<=31
     * @post orders!=null
     */
    @Override
    public void countDailyReport(int day) throws IOException {
        assert(day>0 && day<=31):"1-31 days on a month";
        FileWriter log1 = new FileWriter("0countDailyReport.txt");
        PrintWriter printWriter1 = new PrintWriter(log1);
        printWriter1.println("");
        printWriter1.close();
        log1.close();

        FileWriter log = new FileWriter("0countDailyReport.txt",true);
        PrintWriter printWriter = new PrintWriter(log);
        printWriter.println("We are looking for day "+day);

        Set<Order> orders = comenzi.keySet().stream()
                .filter(order -> order.getOrderDate().getDayOfMonth() == day)
                .collect(Collectors.toSet());

        HashMap<MenuItem, Integer> realTimeOrder = new HashMap<>();
        HashSet<MenuItem> actualItems = new HashSet<>();
        for(Order order : orders){
            actualItems = comenzi.get(order);
            for(MenuItem menuItem : actualItems) {
                if (realTimeOrder.containsKey(menuItem))
                    realTimeOrder.put(menuItem, realTimeOrder.get(menuItem) + 1);
                if(!realTimeOrder.containsKey(menuItem))
                    realTimeOrder.put(menuItem,1);
            }
            actualItems.clear();
        }

        realTimeOrder.keySet().stream()
                .forEach(menuItem -> printWriter.println(menuItem.getTitle()+" is order "+realTimeOrder.get(menuItem)+" times"));


        printWriter.close();
        log.close();
        assert (orders!=null):"No order in that day";
    }
}
