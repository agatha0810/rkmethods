package pl.akademiakodu;

import java.awt.*;
import java.awt.event.*;


class Design extends Frame {

    Panel controlPanel;

    Panel forEffects;
    Panel forGraphs;
    NumSchemes forDrawing;

    TextField mac = new TextField("0.1");
    TextField ox = new TextField("0.01");
    TextField oy = new TextField("0.01");

    Button button = new Button("OK");
    Button buttonX = new Button("X");
    Button buttonY = new Button("Y");

    Button color = new Button("Kolor");

    Choice colorM = new Choice();

    Checkbox chAnalytics = new Checkbox("Solution", true);
    Checkbox chEulerF = new Checkbox("Euler Forward", true);
    Checkbox chEulerB = new Checkbox("Euler Backwords", true);
    Checkbox chEulerM = new Checkbox("Runge Kutta No4", true);

    public Design() {

        forEffects = new Panel();
        forEffects.add(colorM);

        //ustawienie koloru RGB dla Panelu
        forEffects.setBackground(new Color(51, 153, 255));

        forEffects.setBounds(10, 50, 160, 620);
        add(forEffects); //dodanie Panelu do okna

        // ponizej Kontrolki
        forEffects.add(new Label("Podaj h"));
        forEffects.add(mac); // dodaje do panelu pole tekstowe
        forEffects.add(button);

        button.addActionListener(new Click());
        button.setBackground(Color.yellow);
        button.setForeground(Color.black);

        forEffects.add(color);
        forEffects.add(colorM);
        colorM.add("blue");
        colorM.add("red");
        colorM.add("pink");
        colorM.add("green");

        forEffects.add(chAnalytics);

        color.addActionListener(new ColorChoice());

        chAnalytics.addItemListener(new Optional());

        forEffects.add(chEulerF);
        chEulerF.addItemListener(new Optional());
        forEffects.add(chEulerB);
        chEulerB.addItemListener(new Optional());
        forEffects.add(chEulerM);
        chEulerM.addItemListener(new Optional());

        forEffects.add(new Label("Choice unit for axis Ox"));

        forEffects.add(ox);
        forEffects.add(buttonX);

        buttonX.addActionListener(new ClickX());

        forEffects.add(new Label("Choice unit for axis Oy"));

        // Kontrolka jednostki oy
        forEffects.add(oy);
        forEffects.add(buttonY);

        buttonY.addActionListener(new ClickY());

        forGraphs = new Panel();
        forGraphs.setBounds(180, 50, 600, 620);
        forGraphs.setBackground(new Color(230, 207, 42));

        colorM.addItemListener(new TheChoice());

        forDrawing = new NumSchemes(forGraphs.getWidth() - 10, forGraphs.getHeight() - 10);
        forGraphs.add(forDrawing);

        add(forGraphs);
        add(new Panel()); //ostatnia dodana rzecz rozpycha sie na cale okno

        setSize(800, 700);
        setVisible(true);
        setTitle("Window");
        addWindowListener(new WindowClosing());
    }

    public class ColorChoice implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String color = colorM.getItem(colorM.getSelectedIndex());
            if (color.equals("blue")) {
                forEffects.setBackground(Color.blue);
                forDrawing.repaint(); //odswiezenie, odmalowanie
            }
            if (color.equals("red")) {
                forEffects.setBackground(Color.red);
                forDrawing.repaint(); //odswiezenie, odmalowanie
            }
            if (color.equals("pink")) {
                forEffects.setBackground(Color.pink);
                forDrawing.repaint(); //odswiezenie, odmalowanie
            }
            if (color.equals("green")) {
                forEffects.setBackground(Color.green);
                forDrawing.repaint(); //odswiezenie, odmalowanie
            }

        }
    }


    public class Click implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                forDrawing.h = Double.parseDouble(mac.getText());
                if (forDrawing.h < 0) {
                    System.out.println("GIVE ME a number bigger that 0!!");
                    forDrawing.h = 0.1; //z powrotem poczatkowe wartosci
                    mac.setText("0.1"); //uzupelnienie poczatkowej wartosci
                }
                forDrawing.repaint(); //odswiezenie
            } catch (NumberFormatException exception) {
                System.out.println("GIVE ME Natural Number!!");
            }
        }
    }


    public class ClickX implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            forDrawing.whatJx = Double.parseDouble(ox.getText());
            if (forDrawing.whatJx < 0) {
                System.out.println("GIVE ME a number bigger that 0!!");
                forDrawing.whatJx = 0.05; //z powrotem poczatkowe wartosci
                ox.setText("0.05"); //uzupelnienie poczatkowej wartosci
            }

            forDrawing.repaint(); //odswiezenie
        }
    }


    public class ClickY implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            forDrawing.whatJy = Double.parseDouble(oy.getText());
            if (forDrawing.whatJy < 0) {
                System.out.println("GIVE ME a number bigger that 0!!");
                forDrawing.whatJy = 0.025; //z powrotem poczatkowe wartosci
                oy.setText("0.025"); //uzupelnienie poczatkowej wartosci
            }
            forDrawing.repaint(); //odswiezenie

        }

    }


    public class TheChoice implements ItemListener {
        public void itemStateChanged(ItemEvent w) {

            forDrawing.repaint(); //odswiezenie

        }
    }


    public class Optional implements ItemListener {
        public void itemStateChanged(ItemEvent w) {
            forDrawing.ifAnalytics = chAnalytics.getState(); //klikniety, to rysuje
            forDrawing.ifEulerF = chEulerF.getState();
            forDrawing.ifEulerB = chEulerB.getState();
            forDrawing.ifEulerM = chEulerM.getState();
            forDrawing.repaint(); //odswiezenie

        }
    }


    static class WindowClosing extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);

        }
    }
}

