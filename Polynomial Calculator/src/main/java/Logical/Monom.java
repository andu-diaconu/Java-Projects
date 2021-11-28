package Logical;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monom {
    private int coef;
    private int exp;
    private String text;


    public Monom(){}

    public Monom(String text){
        this.text = text;
    }

    public void createMonom(int index) {
            int check = 0;
            boolean booleanCheck = false;
            String  buffer;
            Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find() && !booleanCheck) {
                if(check == index) {
                    boolean aTrecut = false;
                    buffer = matcher.group();
                    StringBuilder sExp = new StringBuilder();
                    StringBuilder sCoef = new StringBuilder();

                    for (int i = 0; i < buffer.length(); i++) {
                        char c = buffer.charAt(i);
                        if (((c > 47 && c < 58) || c == 45) && !aTrecut) sCoef.append(c);
                        if(c == 120) aTrecut = true;
                        if (((c > 47 && c < 58) || c == 45) && aTrecut) sExp.append(c);
                    }

                    if(sCoef.length() ==0)
                        setCoef(1);
                    else if(sCoef.toString().equals("-"))
                        setCoef(-1);
                    else
                        setCoef(Integer.parseInt(sCoef.toString()));


                    if(sExp.length() ==0 && aTrecut)
                        setExp(1);
                    else if (sExp.length() ==0)
                        setExp(0);
                    else
                        setExp(Integer.parseInt(sExp.toString()));

                    booleanCheck = true;
                }
                else
                    check++;
            }
    }

    public void setCoef(int coef){
        this.coef = coef;
    }

    public int getCoef(){
        return coef;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public int getExp(){
        return exp;
    }
}

