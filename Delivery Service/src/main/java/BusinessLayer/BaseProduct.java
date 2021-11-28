package BusinessLayer;

import java.io.Serializable;

public class BaseProduct extends MenuItem{
    //id,Title and price set by MenuItem already !

    private int calories, protein, fat, sodium;
    private double rating;

    public BaseProduct(String title, int id, double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title, id, price, calories, protein, fat, sodium, rating);
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int computePrice() {
        return getPrice();//MenuItem
    }

    @Override
    public String toString() {
        return "BaseProduct{" +
                "calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", rating=" + rating +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
