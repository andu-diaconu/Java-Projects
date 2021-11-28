package Logical;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinom {
    private ArrayList<Monom> monoame;
    private final String text;


    public Polinom(String text){
        this.text = text;
        createPolinom();
        eficientizare(this.monoame);
    }

    private void createPolinom(){
        monoame = new ArrayList<>();
        for (int index=0; index<getNumarMonoame(); index++){
                Monom m = new Monom(text);
                m.createMonom(index);
                monoame.add(m);
        }
    }

    public void eficientizare(ArrayList<Monom> monoame) {

        int[] visitedExp = new int[100];
        boolean isVisited = false;
        int index=0;

        for(Monom m : monoame)
            for (Monom m1 : monoame)
                if (m1.getExp() == m.getExp() && m != m1) {
                    for (int i = 0; i < index; i++)
                        if (visitedExp[i] == m1.getExp()) {
                            isVisited = true;
                            break;
                        }
                    if (!isVisited) {
                        m.setCoef(m.getCoef() + m1.getCoef());
                        visitedExp[index++] = m.getExp();
                    } else{
                        isVisited = false;
                        m.setCoef(0);
                    }
                }
    }

    private int getNumarMonoame() {
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(text);
        return (int) matcher.results().count();
    }

    public ArrayList<Monom> getMonoame() {
        return monoame;
    }
}
