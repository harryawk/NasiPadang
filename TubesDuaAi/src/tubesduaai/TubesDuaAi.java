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
//        nb.DataRead("C:\\Users\\harry\\Downloads\\mush.arff");
        nb.DataRead("C:\\Program Files\\Weka-3-8\\data\\iris.arff", classidx);
        nb.buildClassifier(dummy);
//        double x = nb.classifyInstance(nb.datas.instance(0));
        Evaluation eval = new Evaluation(nb.datas);
////        eval.evaluateModel(nb, nb.datas);
////        System.out.println(eval.toSummaryString("=====Sumarry====", true));
        eval.crossValidateModel(nb, nb.datas, 10, new Random(1));
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
