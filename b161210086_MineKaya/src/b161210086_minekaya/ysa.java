
package b161210086_minekaya;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Mine KAYA B161210086
 */
public class ysa {
    private static final File egitimDosya=new File(ysa.class.getResource("egitim.txt").getPath());
    private static final File testDosya=new File(ysa.class.getResource("test.txt").getPath());
    private final double [] maksimumlar;
    private final double [] minimumlar;
    private final DataSet egitimVeriSeti;
    private final DataSet testVeriSeti;
    private final int araKatmanNoron;
    MomentumBackpropagation bp;
    
   // double epochh;
   //  double []eldeEdilenHatalar;
    
    
    public ysa(int araKatmanNoron,double momentum,double ok,double error,int epoch) throws FileNotFoundException{
        maksimumlar=new double[5];
        minimumlar=new double[5];
        for(int i=0;i<5;i++){
            maksimumlar[i]=Double.MIN_VALUE;
            minimumlar[i]=Double.MAX_VALUE;
            
        }
        EgitimVeriSetiMak();
        TestVeriSetiMaks();
        egitimVeriSeti=EgitimVeriSeti();
        testVeriSeti=TestVeriSeti();
        bp=new MomentumBackpropagation();
        bp.setMomentum(momentum);
        bp.setLearningRate(ok);
        bp.setMaxError(error);
        bp.setMaxIterations(epoch);
        this.araKatmanNoron=araKatmanNoron;
        
        // eldeEdilenHatalar=new double[(int)maksEpoch];
        // this.epochh=epoch;
    }
    
  
     private double min_max(double max, double min, double x) {
        return (x-min)/(max-min);
    }
    private void EgitimVeriSetiMak() throws FileNotFoundException {
        Scanner oku=new Scanner(egitimDosya);
        while(oku.hasNextDouble()){
            for(int i=0;i<5;i++){
                double d=oku.nextDouble();
                if(d>maksimumlar[i])   maksimumlar[i]=d;
                if(d<minimumlar[i])    minimumlar[i]=d;
            }
            oku.nextDouble();;
        }
        oku.close();
    }

    private void TestVeriSetiMaks() throws FileNotFoundException {
    Scanner oku=new Scanner(testDosya);
        while(oku.hasNextDouble()){
            for(int i=0;i<5;i++){
                double d=oku.nextDouble();
                if(d>maksimumlar[i])maksimumlar[i]=d;
                if(d<minimumlar[i])minimumlar[i]=d;
            }
            oku.nextDouble();
        }
        oku.close();
    }

    private DataSet EgitimVeriSeti() throws FileNotFoundException {
        Scanner oku=new Scanner(egitimDosya);
        DataSet egitim=new org.neuroph.core.data.DataSet(5,1);
        while(oku.hasNextDouble()){
            double [] inputs=new double[5];
            for(int i=0;i<5;i++){
                double d=oku.nextDouble();
                inputs[i]=min_max(maksimumlar[i],minimumlar[i],d);
            }
            DataSetRow satir = new DataSetRow(inputs,new double[]{oku.nextDouble()});
            egitim.add(satir);
        }
        oku.close();
        return egitim;
    }

    private DataSet TestVeriSeti() throws FileNotFoundException {
        Scanner oku=new Scanner(testDosya);
        DataSet test=new DataSet(5,1);
        while(oku.hasNextDouble()){
            double [] inputs=new double[5];
            for(int i=0;i<5;i++){
                double d=oku.nextDouble();
                inputs[i]=min_max(maksimumlar[i],minimumlar[i],d);
            }
            DataSetRow satir = new DataSetRow(inputs,new double[]{oku.nextDouble()});
            test.add(satir);
        }
        oku.close();
        return test;
    }
    public void egit() throws FileNotFoundException{
        MultiLayerPerceptron sinirselAg=new MultiLayerPerceptron(TransferFunctionType.SIGMOID,5,araKatmanNoron,1);
        sinirselAg.setLearningRule(bp);
        sinirselAg.learn(egitimVeriSeti);
        
       // for(int i=0;i<this.epochh;i++){
        //    sinirselAg.getLearningRule().doOneLearningIteration(EgitimVeriSeti());
        //    if(i==0)eldeEdilenHatalar[i]=1;
        //    else eldeEdilenHatalar[i]=sinirselAg.getLearningRule().getPreviousEpochError();
       // }
       
        sinirselAg.save("ag.nnet");
        System.out.println("Eğitim tamamlandı");
    }
    

    
    
    double mse(double [] beklenen,double [] cikti){
        double satirHata=0;
        for(int i=0;i<1;i++){
            satirHata+=Math.pow(beklenen[i]-cikti[i],2);   
        }
        return satirHata;
    }
    public double test(){
        NeuralNetwork sinirselAg=NeuralNetwork.createFromFile("ag.nnet");
        double toplamHata=0;
        for(DataSetRow r:testVeriSeti){
            sinirselAg.setInput(r.getInput());
            sinirselAg.calculate();
            toplamHata+=mse(r.getDesiredOutput(),sinirselAg.getOutput());            
        }
        return toplamHata/testVeriSeti.size();
    }
    public String sonuc(double []outputs){
        double indeks=outputs[0];
     
           
        if(indeks==1)return "nonpro";
       
        else return "pro";
    }
    public String tekTest(double [] inputs){
        for(int i=0;i<5;i++){
            inputs[i]=min_max(maksimumlar[i],minimumlar[i],inputs[i]);
        }
        NeuralNetwork sinirselAg=NeuralNetwork.createFromFile("ag.nnet");
        sinirselAg.setInput(inputs);
        sinirselAg.calculate();
        return sonuc(sinirselAg.getOutput());
    }

    public double egitimHata(){
        return bp.getTotalNetworkError();// ulaşılan en son hata
    }
    
   /**
     * 
     * public double[] egitimHatalarListesi(){
     *  return eldeEdilenHatalar; // iterasyondaki hatalar
     *  }
     *
     *  @Override
     *  public String toString(){
     *  
     *   int epoch=epochh;
     *   String cikti = "hatalar = ";
     *    for(int i=0;i<epoch;i++)
     *        cikti += (i+".  :"+eldeEdilenHatalar[i]+"\n");
     *        return cikti;
     *       }
     */
     
  
   
     public DataSet getEgitimVeriSeti(){
      
        return egitimVeriSeti;
       
   }
    public DataSet getTestVeriSeti(){
       return testVeriSeti;
   }
    
}
