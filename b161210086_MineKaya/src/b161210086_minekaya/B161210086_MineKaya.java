
package b161210086_minekaya;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Mine Kaya b161210086
 */
public class B161210086_MineKaya {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(System.in);
        int araKatmanNoron;
        double momentum,ok,error;
        int epoch,sec;
        
        ysa ysa = null;
        do {
            System.out.println("1. Eğit ");
            System.out.println("2. Test");
            System.out.println("3. Tek Test");
            System.out.println("4. Çıkış");
            System.out.println("=>");
            sec = in.nextInt();
            switch(sec){
                case 1:
                    System.out.print("Ara Katman Noron Sayısı : ");
                    araKatmanNoron = in.nextInt();
                    System.out.print("Momentum : ");
                    momentum = in.nextDouble();
                    System.out.print("Öğrenme Katsayısı : ");
                    ok = in.nextDouble();
                    System.out.print("Min Error : ");
                    error = in.nextDouble();            
                    System.out.print("Maks Epoch Sayısı : ");
                    epoch = in.nextInt();
                    ysa = new ysa(araKatmanNoron, momentum, ok, error, epoch);
                    ysa.egit();
                    System.out.println("Eğitimdeki Hata Değeri : "+ ysa.egitimHata());
                    
                   
                    break;
                case 2:
                    if(ysa == null)
                    {
                        System.out.println("Önce Eğitim");
                        System.in.read();
                    }
                    else
                    {
                    
                    double d = ysa.test();
                    System.out.println("Test Verisindeki Hata Değeri : "+ d);
                    
                    
                    }
                    break;
                case 3:
                    double[] inputs = new double[5];
                    if(ysa == null)
                    {
                        System.out.println("Önce Eğitim");
                        System.in.read();
                    }
                    else
                    {

                        System.out.println("DEGREE(1-3) : ");
                        inputs[0] = in.nextDouble();
                        System.out.println("CAPRICE (1-3) : ");
                        inputs[1] = in.nextDouble();
                        System.out.println("TOPIC (1-5) : ");
                        inputs[2] = in.nextDouble();
                        System.out.println("LMT (1-2): ");
                        inputs[3] = in.nextDouble();
                        System.out.println("LPSS (1-2) : ");
                        inputs[4] = in.nextDouble();
                       
                    }
                    System.out.println("PRO BLOGGER : "+ysa.tekTest(inputs));
                    System.in.read();
                    break;
            }
        } while (sec != 4);
    
}
}
