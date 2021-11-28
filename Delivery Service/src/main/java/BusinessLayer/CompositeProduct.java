package BusinessLayer;

import java.io.Serializable;
import java.util.HashSet;

public class CompositeProduct extends MenuItem {
    private HashSet<BaseProduct> products = new HashSet<>();
    private int finalPrice = 0;

    public CompositeProduct(String title, int id, double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title, id, price, calories, protein, fat, sodium, rating);
    }

    private void setFinalPrice(int finalPrice){
        this.finalPrice = finalPrice;
    }

    private int getFinalPrice(){
        return finalPrice;
    }

    public HashSet<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(HashSet<BaseProduct> products) {
        this.products = products;
    }

    @Override
    public int computePrice() {
        this.setFinalPrice(0);
        for(BaseProduct product : products)
            this.setFinalPrice(this.getFinalPrice()+product.getPrice());
        return this.getFinalPrice();
    }
}
