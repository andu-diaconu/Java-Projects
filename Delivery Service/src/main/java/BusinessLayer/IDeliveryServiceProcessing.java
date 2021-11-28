package BusinessLayer;

import javax.swing.*;
import java.io.IOException;
import java.util.HashSet;

public interface IDeliveryServiceProcessing {

    //Client ops:

    /**
     *
     * @param menuItemss set with items selected from Menu
     * @return the total price for menuItems selected
     * @pre menuItems != null
     * @post money>0
     */
    int computePrice(HashSet<MenuItem> menuItemss);

    /**
     *
     * @param order order done by a client
     * @param selectedItems set with items order by a client
     * @pre selectedItems != null
     * @post comenzi!= null
     * @invariant user.Type == Client
     */
    void newOrder(Order order, HashSet<MenuItem> selectedItems);

    /**
     *
     * @param title search after this title
     * @param price search products with maximum price price
     * @return a set with the products found afet filtering them
     * @post searchMenu != null
     * @invariant user.Type == Client
     */
    HashSet<MenuItem> searchInMenu(String title, int price);

    //Admin ops:

    /**
     * Import the products from .csv to a HashMap
     * @post theProducts!= null
     */
    void importProds();

    /**
     *
     * @param baseProduct produsul de adaugat in meniu
     * @pre baseProduct != null
     * @post menuItmes!=null
     */
    void addProd(BaseProduct baseProduct);

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
    void updateProd(int id, String title, double rating, int calories, int protein, int fat, int sodium, int price, JTable table);

    /**
     *
     * @param id product that will be deleted
     * @param table will pe updated
     * @pre id>=0
     */
    void deleteProd(int id, JTable table);

    /**
     *
     * @param startTime start hour in interval
     * @param endTime the last hour from interval
     * @throws IOException if the file cant be open
     * @pre startTime < endTime
     * @post ordersTime != null
     */
    void timeReport(int startTime, int endTime) throws IOException;

    /**
     *
     * @param count order more than this value
     * @throws IOException if the file cant be open
     * @pre count> 0
     * @post results!=null
     */
    void commonProdReport(int count) throws IOException;

    /**
     *
     * @param count minimum order by a client
     * @param amount with a minimum value
     * @throws IOException if the file cant be open
     * @pre count>0 && amount>0
     * @post orders!=null
     */
    void clientsReport(int count, int amount) throws IOException;

    /**
     *
     * @param day day of month
     * @throws IOException if the file cant be open
     * @pre day>=1 && day<=31
     * @post orders!=null
     */
    void countDailyReport(int day) throws IOException;
}
