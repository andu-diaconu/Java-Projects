package BusinessLayer;

import java.io.Serializable;

public abstract class MenuItem{
    private String title;
    private int calories, protein, fat, sodium;
    private double rating;
    int id, price;

    public MenuItem(String title, int id, int price, int calories, int protein, int fat, int sodium, double rating){
        super();
        this.title = title;
        this.id = id;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.rating = rating;
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



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public abstract int computePrice();

    @Override
    public String toString() {
        return "MenuItem{ TITLE: "+title+"ID: "+id+" "+
                title+": rating: "+rating+" calories: "+calories+" protein: "+protein+" fat: "+fat+" sodrium: "+sodium+" price: "+price+" lei"+'}';
    }
}
