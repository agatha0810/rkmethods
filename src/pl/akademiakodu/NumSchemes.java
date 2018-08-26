package pl.akademiakodu;

import java.awt.*;

class NumSchemes extends Canvas {

    //pomocnicze parametry
    int S;
    int W;
    int Ox;
    int Oy;
    double whatJx;
    double whatJy;

    double h;
    double x_init;
    double y_init;

    int counter =1;

    boolean ifAnalytics;
    boolean ifEulerF;
    boolean ifEulerB;
    boolean ifEulerM;

    int whatFunction;
    //===============

    NumSchemes(int width, int height){ //konstruktor dla klasy
        S = width;
        W = height;
        Ox = 50; //point (0,0)
        Oy = 500;

        setSize(S,W);

        whatJx = 0.01;
        whatJy = 0.01;
        h=0.1;
        x_init = 0.0;
        y_init= 1.0;

        whatFunction = 0;

        ifAnalytics = true;
        ifEulerF = true;
        ifEulerB = true;
        ifEulerM = true;

    }


    //rozwiazanie analityczne (named Solution) !!
    double analyticFunction (double x) {
        double y = 0.0;

        if (whatFunction == 0) y = Math.sqrt(x*x+1);
        return y;
    }


    double differenceMethod(double x, double y){
        double f = 0.0;
        if (whatFunction == 0) f=x/y;
        return f;
    }


    public void paint (Graphics g){
        int wherePi =10;
        g.setColor(Color.white);
        g.fillRect(1, 1, S, W);

        //axis
        g.setColor(Color.pink);
        g.drawLine(1, Oy, S, Oy);
        g.drawLine(Ox, 1, Ox, W);

        g.drawLine(Ox-3, Oy-15, Ox+3, Oy-15); //arrowY
        g.drawLine(Ox+3* wherePi, Oy-3, Ox+3* wherePi, Oy+3); //arrowX

        g.drawString("x", S-10, Oy+10);
        g.drawString("y", Ox+5, 10);


        //rozwiązanie analityczne (Solution):
        if ( ifAnalytics == true ) {
            for (int i=0; i<S; i++){
                double x = (i-Ox)* whatJx;
                double y = analyticFunction(x);
                int whereY = Oy + (int)(-y/ whatJy);
                g.setColor(Color.black);
                g.drawRect(i, whereY, 1, 1);
            }
        }


        if ( ifEulerF == true) {

            //EulerForward
            double oldX = x_init;
            double oldY = y_init;
            g.setColor(Color.blue);

            for (int i=Ox; i<S; i++){
                double newX = oldX + h;
                double newY = oldY + h * differenceMethod(oldX, oldY);

                //wypisywanie:
                //System.out.println("i="+i+", nx="+newX+", ny="+newY);
                int whereY = Oy + (int)(-newY / whatJy);
                int whereX = Ox + (int)(newX / whatJx);//newX na pixel
                g.drawRect(whereX, whereY, 2, 2);

                //ponizej przypisanie nowych wartosci do starych
                oldX = newX;
                oldY = newY;
            }
        }


        //========================================
        //TraditionalEulerSchema is RungeKuttaNo1.
        //========================================
        //Below we have RungeKutta (of 4th order):

        if ( ifEulerM == true) {

            double oldX = x_init;
            double oldY = y_init;
            g.setColor(Color.orange);

            for (int n = Ox; n < S; n++) {

                double newX = oldX + h;

                double k1 = h * differenceMethod(oldX, oldY);
                double k2 = h * differenceMethod(oldX + h / 2.0, oldY + k1 / 2.0);
                double k3 = h * differenceMethod(oldX + h / 2.0, oldY + k2 / 2.0);
                double k4 = h * differenceMethod(oldX + h , oldY + k3);

                double newY = oldY + (k1 + 2.0 * (k2 + k3) + k4) / 6.0;

                //wypisywanie wartości
                //System.out.println("i="+i+", nx="+ newX +", ny="+ newY);
                int whereY = Oy + (int)(-newY / whatJy);
                int whereX = Ox + (int)(newX / whatJx);//newX pixel
                g.fillRect(whereX, whereY, 2, 2);

                //ponizej przypisanie nowych wartosci do starych
                oldX = newX;
                oldY = newY;

            }

        }


        if ( ifEulerB == true) {

            //EulerBackward
            double oldX = x_init;
            double oldY = y_init;
            g.setColor(Color.red);

            for (int i=Ox; i<S; i++){
                double newX = oldX + h;
                double newY = oldY + h*(differenceMethod(newX, oldY + h* differenceMethod(oldX, oldY)));

                //double newY=
                //=oldY+0.5*h*(differenceMethod(newX,newY)+differenceMethod(oldX, oldY));

                //wypisywanie wartości:
                //System.out.println("i="+i+", nx="+ newX +", ny="+ newY);
                int whereY = Oy + (int)(-newY / whatJy);
                int whereX = Ox + (int)(newX / whatJx);//newX pixel
                g.fillOval(whereX, whereY , 3, 3);

                //ponizej przypisanie nowych wartosci do starych
                oldX = newX;
                oldY = newY;
            }
        }

        System.out.println("Kolejne wywołanie: zliczanie kontrolne: " + counter++);

    }
}

