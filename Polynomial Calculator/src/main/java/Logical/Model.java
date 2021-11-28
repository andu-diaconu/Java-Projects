package Logical;

import java.util.ArrayList;

public class Model {
    public ArrayList<Monom> polinomRezultat;

    public Model() {}

    public void scadere(Polinom poli1, Polinom poli2){

        polinomRezultat = new ArrayList<>();
        for (Monom m : poli2.getMonoame()) {
            if (m.getCoef() != 0) {
                Monom aux = new Monom();
                aux.setExp(m.getExp());
                aux.setCoef(-m.getCoef());
                for (Monom m1 : poli1.getMonoame())
                    if (m.getExp() == m1.getExp())
                        aux.setCoef(aux.getCoef() + m1.getCoef());
                polinomRezultat.add(aux);
            }
        }
        checkSkipMonom(poli1,poli2);
    }

    public void adunare(Polinom poli1, Polinom poli2) {

        polinomRezultat = new ArrayList<>();
        for (Monom m : poli2.getMonoame()) {
            if(m.getCoef()!=0) {
                Monom aux = new Monom();
                aux.setExp(m.getExp());
                aux.setCoef(m.getCoef());

                for (Monom m1 : poli1.getMonoame())
                    if (aux.getExp() == m1.getExp())
                        aux.setCoef(aux.getCoef() + m1.getCoef());
                polinomRezultat.add(aux);
            }
        }
        checkSkipMonom(poli1,poli2);
    }

    private void checkSkipMonom(Polinom poli1, Polinom poli2){
        boolean dejaIntrodus = false;

        for (Monom m2 : poli1.getMonoame()) {
            if (m2.getCoef() != 0) {
                for (Monom m3 : poli2.getMonoame())
                    if (m2.getExp() == m3.getExp()) {
                        dejaIntrodus = true;
                        break;
                    }
                if (!dejaIntrodus) {
                    Monom aux = new Monom();
                    aux.setExp(m2.getExp());
                    aux.setCoef(m2.getCoef());
                    polinomRezultat.add(aux);
                } else
                    dejaIntrodus = false;
            }
        }
    }

    public void inmultire(Polinom poli1, Polinom poli2){
        polinomRezultat = new ArrayList<>();
        for(Monom m:poli1.getMonoame())
            for(Monom m1:poli2.getMonoame())
                if(m.getCoef()!=0 && m1.getCoef()!=0)
                {
                    Monom aux = new Monom();
                    aux.setCoef(m.getCoef()*m1.getCoef());
                    aux.setExp(m.getExp()+m1.getExp());
                    polinomRezultat.add(aux);
                }
        poli1.eficientizare(polinomRezultat);
    }



    public void derivare(Polinom poli1){
        polinomRezultat = new ArrayList<>();
        for(Monom m : poli1.getMonoame())
            if(m.getCoef()!=0) {
                Monom aux = new Monom();
                if (m.getExp() == 0)
                    aux.setCoef(0);
                else {
                    aux.setExp(m.getExp() - 1);
                    aux.setCoef(m.getCoef() * m.getExp());
                }
                polinomRezultat.add(aux);
            }
    }

    public String impartire(Polinom poli1 , Polinom poli2){

        ArrayList<Monom> q = new ArrayList<>();
        ArrayList<Monom> r = poli1.getMonoame();
        r.sort((o1, o2) -> Integer.compare(o2.getExp(), o1.getExp()));
        poli2.getMonoame().sort((o1, o2) -> Integer.compare(o2.getExp(), o1.getExp()));

        while(!r.isEmpty() && (r.get(0).getExp() >= poli2.getMonoame().get(0).getExp())){

            int c = r.get(0).getCoef()/poli2.getMonoame().get(0).getCoef();
            int e = r.get(0).getExp()-poli2.getMonoame().get(0).getExp();

            Monom aux = new Monom(c + "x^" + e);
            aux.createMonom(0);
            q.add(aux);


            for(Monom m : poli2.getMonoame())
                if(m.getCoef()!=0 && aux.getCoef()!=0)
                {
                    Monom t = new Monom();
                    t.setCoef(-(aux.getCoef()*m.getCoef()));
                    t.setExp(aux.getExp()+m.getExp());
                    r.add(t);
                }

            poli1.eficientizare(r);
            r.sort((o1, o2) -> Integer.compare(o2.getExp(), o1.getExp()));
             if(r.get(0).getCoef()==0) {
                 Monom m = r.get(0);
                 r.remove(m);
             }

        }

        StringBuilder polinom = new StringBuilder();
        polinom.append(" Q = ");
        setTheBuilder(q,polinom);

        polinom.append("    R = ");
        setTheBuilder(r,polinom);

        return polinom.toString();
    }

    public void setTheBuilder(ArrayList<Monom> r,StringBuilder polinom){
        for (Monom m : r) {
            if (m.getCoef() > 0) {
                polinom.append("+").append(m.getCoef());
                if(m.getExp() == 1)
                    polinom.append("x");
                if(m.getExp() >1)
                    polinom.append("x^").append(m.getExp());

            }
            if (m.getCoef() < 0) {
                polinom.append(m.getCoef());
                if (m.getExp() == 1)
                    polinom.append("x");
                if (m.getExp() > 1)
                    polinom.append("x^").append(m.getExp());
            }
        }
    }


    public String integrare(Polinom poli1){
        StringBuilder polinom = new StringBuilder();

        for (Monom m : poli1.getMonoame()) {
            if (m.getCoef() > 0) {
                polinom.append("+");
                if (m.getExp() == 0)
                    polinom.append(m.getCoef()).append("x");
                if (m.getExp() > 0)
                    polinom.append(m.getCoef()).append("/").append(m.getExp()+1).append("x^").append(m.getExp()+1).append("  ");
            }
            if (m.getCoef() < 0) {
                if (m.getExp() == 0)
                    polinom.append(m.getCoef()).append("x");
                if (m.getExp() > 0)
                    polinom.append(m.getCoef()).append("/").append(m.getExp()+1).append("x^").append(m.getExp()+1).append("  ");
            }
        }
        polinom.append("+C");
        return polinom.toString();
    }




    @Override
    public String toString() {
        StringBuilder raspuns = new StringBuilder();
        polinomRezultat.sort((o1, o2) -> Integer.compare(o2.getExp(), o1.getExp()));
        raspuns.setLength(0);
        for (Monom mm : polinomRezultat) {
            if (mm.getCoef() > 0) {
                raspuns.append("+").append(mm.getCoef());
                if (mm.getExp() == 1)
                    raspuns.append("x ");
                if (mm.getExp() > 1)
                    raspuns.append("x^").append(mm.getExp()).append(" ");
            }

            if (mm.getCoef() < 0) {
                raspuns.append(mm.getCoef());
                if (mm.getExp() == 1)
                    raspuns.append("x ");
                if (mm.getExp() > 1)
                    raspuns.append("x^").append(mm.getExp()).append(" ");
            }
        }
        return raspuns.toString();
    }
}
