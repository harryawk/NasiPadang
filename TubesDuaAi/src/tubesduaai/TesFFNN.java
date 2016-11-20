/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instances;

/**
 *
 * @author Nugroho Satriyanto <massatriya@gmail.com>
 */
public class TesFFNN {
    public static Instances data;
    public static void tes() throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Program Files\\Weka-3-8\\data\\iris.arff"));
        data = new Instances(reader);
        reader.close();
        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);
        Instances dummy = null;
        FFNN nn = new FFNN("C:\\Program Files\\Weka-3-8\\data\\iris.arff",0);
        boolean[] nom = nn.cek_nominal();
        nn.buildClassifier(data);
        nn.print_perceptron();
    }
    
    public static void main(String[] args){
        try{
            tes();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
