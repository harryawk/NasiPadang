/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.util.Scanner;
import weka.classifiers.Evaluation;
import weka.core.Debug;
import weka.core.Debug.Random;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author harry
 */
public class TubesDuaAi {
    
    public static void demoNb() throws Exception {
        Scanner s = new Scanner(System.in);
        Instances dummy = null;
        NB_030 nb = new NB_030();
        
        System.out.println("masukkan indeks kelas. indeks pertama = 0. file : iris.arff");
        Integer classidx = s.nextInt();
//        nb.load_model("C:\\Users\\harry\\GitHub\\NasiPadang\\TubesDuaAi\\coba2.txt");
//        nb.DataRead("C:\\Users\\harry\\Downloads\\mush.arff", classidx, 0);
        nb.DataRead("C:\\Users\\harry\\Documents\\assignments\\ai\\student-train.arff", classidx, 0);
//        nb.DataRead("C:\\Users\\harry\\Documents\\assignments\\ai\\student-train.arff", 26, 0);
//        nb.DataRead("C:\\Users\\harry\\Documents\\assignments\\ai\\mush_test.arff", classidx, 1);
//        nb.buildClassifier(dummy);
//        int trainSize = (int) Math.round(nb.datas.numInstances() * 0.8);
//        int testSize = nb.datas.numInstances() - trainSize;
//        Instances backup = nb.datas;
//        Instances train = new Instances(nb.datas, 0, trainSize);
//        Instances test = new Instances(nb.datas, trainSize, testSize);
//        System.out.println(nb.datas.numInstances());
//        nb.datas = train;
//        System.out.println(nb.datas.numInstances());
        
        nb.buildClassifier(dummy);
        
        nb.save_model();
//        nb.DataRead("C:\\Users\\harry\\Documents\\assignments\\ai\\mush_test.arff", classidx, 1);
        
        nb.DataRead("C:\\Users\\harry\\Documents\\assignments\\ai\\student-mat-test.arff", classidx, 1);
        
//        double x = nb.classifyInstance(nb.datas.instance(0));
//        Evaluation eval = new Evaluation(nb.datas);
//        nb.data_test = test;
//        nb.data_test.setClassIndex(classidx);
//        nb.Discretize("data_test");
        Evaluation eval = new Evaluation(nb.data_test);
//        eval.crossValidateModel(nb, nb.datas, 10, new Random(1));
        
        eval.evaluateModel(nb, nb.data_test);
////        System.out.println(eval.toSummaryString("=====Sumarry====", true));
        
        System.out.println(eval.toSummaryString("=====Sumarry=======", true));
        System.out.println(eval.toMatrixString("Confusion matrix : "));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            demoNb();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            System.out.println("Error ");
             e.printStackTrace();
        }
    }
    
}
