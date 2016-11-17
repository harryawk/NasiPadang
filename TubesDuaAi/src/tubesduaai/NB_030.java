/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;
import static jdk.nashorn.internal.objects.NativeDebug.map;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

/**
 *
 * @author harry
 */
public class NB_030 implements Classifier {
    
    public Instances datas;
    public double[][] temp;
    
    public NB_030() {
        
    }
    
    
    public void Discretize() throws Exception {
        Discretize discretize = new Discretize();
//        String[] options = new String[4];
//        options[0] = "-R";
//        options[1] = "first-last";
//        options[2] = "-precision";
//        options[3] = "6";
//        discretize.setOptions(options);
        discretize.setInputFormat(datas);
        datas = Filter.useFilter(datas,discretize);
    }
    
    public void DataRead(String filepath) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes()-1);
    }
    
    public int numOfDistinctVal(double[] arr) {
        
        return 0;
    }
    
    public String distinctVals(String[] arr, int index) {
        return datas.get(index).stringValue(datas.numAttributes()-1);
    }
    
    @Override
    /**
     * Make model
     */
    public void buildClassifier(Instances inst) throws Exception {
        temp = new double[datas.numInstances()][];
        Discretize();
        
        // Call a cell : <Instances.get(instance_index).stringValues(attr_index)>
        
        for (int j=0; j < datas.numInstances(); j++) {
            for (int k=0; k < datas.numAttributes(); k++) {
                if (k != datas.numAttributes()-1)
                    System.out.print(datas.get(j).stringValue(k));
            }
            System.out.println();
        }
        
        // STRUKTUR DATA MODEL
        HashMap<String, HashMap<String, HashMap<String, Integer>>> map_attribute = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
        HashMap<String, HashMap<String, Integer>> map_distinct =  new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, Integer> map_class = new HashMap<String, Integer>();
        
        // KEYS UNTUK STRUKTUR DATA
        String irisVirginica = datas.attribute(datas.classIndex()).value(0);
        String irisVersiColor = datas.attribute(datas.classIndex()).value(1);
        String irisSetosa = datas.attribute(datas.classIndex()).value(2);
        
        
        int irisVirginica_ = 0;
        int irisVersiColor_ = 0;
        int irisSetosa_ = 0;
        for (int j=0; j < datas.numAttributes(); j++) {
            
            for (int i=0; i < datas.attribute(j).numValues(); i++) {
                // values @ current attribute
            }
            for (int k=0; k < datas.numInstances(); k++) {
                if (datas.get(k).classValue() == 0.0) {
                    map_class.put(irisVirginica, ++irisVirginica_);
                } else if (datas.get(k).classValue() == 1.0) {
                    map_class.put(irisVersiColor, ++irisVersiColor_);
                } else if (datas.get(k).classValue() == 2.0) {
                    map_class.put(irisSetosa, ++irisSetosa_);
                }
            }
        }

//        for (int j=0; j < datas.numInstances(); j++) {
//            for (int k=0; k < datas.numAttributes(); k++) {
//                // Sum of all attributes
//                
//            }
//        }
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        return 1.1;
    }

    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
