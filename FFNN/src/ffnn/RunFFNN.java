/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author Toshiba
 */
public class RunFFNN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Classifier ffnn = new FFNN();
        Instances datas = ConverterUtils.DataSource.read("c:\\Program Files\\Weka-3-8\\data\\iris.arff");
        int number_attribute = datas.numAttributes();
        datas.setClassIndex(number_attribute - 1);//set label
        ffnn.buildClassifier(datas);
        
        Evaluation eval = new Evaluation(datas);
        eval.evaluateModel(ffnn, datas);
        System.out.println("=====Run Information======");
        System.out.println("======Classifier Model======");
        //System.out.println(ffnn.toString());
        System.out.println(eval.toSummaryString("====Stats======\n", false));
        //System.out.println(eval.toClassDetailsString("====Detailed Result=====\n"));
        //System.out.println(eval.toMatrixString("======Confusion Matrix======\n"));
    }
    
}
