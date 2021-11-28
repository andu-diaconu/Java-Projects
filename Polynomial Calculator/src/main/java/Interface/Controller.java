package Interface;

import Logical.Model;
import Logical.Polinom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Controller {
    public static View view;
    public static Model model;

    public Controller(View view, Model model) {
        Controller.view = view;
        Controller.model = model;
    }

    private static void printResult() {
        Font font = new Font("Courier", Font.BOLD, 14);
        view.rezultat.setText(model.toString());
        view.rezultat.setFont(font);
    }


    public static class Contrl implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    /*Inner Classes*/
    public static class Controller1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(view.myFrame, "Polinomul a fost retinut: " + view.p1.getText());
            String text = view.p1.getText();
            view.poli1 = new Polinom(text);
            view.rezultat.setText("");
        }
    }

    public static class Controller2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(view.myFrame, "Polinomul a fost retinut: " + view.p2.getText());
            String text = view.p2.getText();
            view.poli2 = new Polinom(text);
            view.rezultat.setText("");
        }
    }


    public static class SumController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.adunare(view.poli1, view.poli2);
            printResult();
        }
    }

    public static class DifController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.scadere(view.poli1, view.poli2);
            printResult();
        }
    }

    public static class ProdController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.inmultire(view.poli1, view.poli2);
            printResult();
        }
    }

    public static class DerivController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.derivare(view.poli1);
            printResult();
        }
    }

    public static class DivisController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = new Font("Courier", Font.BOLD, 14);
            view.rezultat.setText(model.impartire(view.poli1, view.poli2));
            view.rezultat.setFont(font);
        }
    }

    public static class IntegController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font font = new Font("Courier", Font.BOLD, 14);
            view.rezultat.setText(model.integrare(view.poli1));
            view.rezultat.setFont(font);
        }
    }
}