import Interface.Controller;
import Interface.View;
import Logical.Model;

public class Main {
    public static void main (String [] args){
        View v = new View();
        Model m = new Model();
        new Controller(v,m);
    }

}
