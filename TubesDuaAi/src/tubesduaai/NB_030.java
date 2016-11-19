/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.util.Arrays;
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
    HashMap<String, Integer> map_class;
    
    public NB_030() {
        map_class = new HashMap<String, Integer>();
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
        
    public String[] distinctVals(int index) {
        int nums = datas.numDistinctValues(index);
        String[] dist = new String[nums];
        int j = 0;
        for (int i=0; i < datas.numInstances(); i++) {
            if (!Arrays.asList(dist).contains(datas.get(i).stringValue(index))) {
                dist[j] = datas.get(i).stringValue(index);
                j++;
            }
        }
        return dist;
    }
    
    @Override
    /**
     * Make model
     */
    public void buildClassifier(Instances inst) throws Exception {
        temp = new double[datas.numInstances()][];
        Discretize();
        
        // Call a cell : <Instances.get(instance_index).stringValues(attr_index)>
//        System.out.println(datas);
        
        // STRUKTUR DATA MODEL
//        HashMap<String, HashMap<String, HashMap<String, Integer>>> map_attribute = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
//        HashMap<String, HashMap<String, Integer>> map_distinct =  new HashMap<String, HashMap<String, Integer>>();
        
        
        // KEYS UNTUK STRUKTUR DATA
        String irisVirginica = datas.attribute(datas.classIndex()).value(0);
        String irisVersiColor = datas.attribute(datas.classIndex()).value(1);
        String irisSetosa = datas.attribute(datas.classIndex()).value(2);
                

        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            map_class.put(datas.attribute(j).name()+distincts[m]+irisVirginica, 0);
                            break;
                        case 1:
                            map_class.put(datas.attribute(j).name()+distincts[m]+irisVersiColor, 0);
                            break;
                        case 2:
                            map_class.put(datas.attribute(j).name()+distincts[m]+irisSetosa, 0);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
////////////////////////////////////////LOOOOOOOOPPPP>????????//////////////////////        
        System.out.println("Before : ");
        System.out.println("==============================================");
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
//                            System.out.print(datas.get(0).attribute(0).name()+distincts[m]+irisVirginica);
                            System.out.print(map_class.get(datas.attribute(0).name()+distincts[m]+irisVirginica));
                            break;
                        case 1:
//                            System.out.println(datas.get(0).attribute(0).name()+distincts[m]+irisVersiColor);
                            System.out.print(map_class.get(datas.attribute(0).name()+distincts[m]+irisVersiColor));
                            break;
                        case 2:
//                            System.out.println(datas.get(0).attribute(0).name()+distincts[m]+irisSetosa);
                            System.out.print(map_class.get(datas.attribute(0).name()+distincts[m]+irisSetosa));
                            break;
                        default:
                            break;
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
        System.out.println("====================================================");
///////////////////////////////LOOOOPPPPP/////////////////////////////////////////
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(datas.get(0).attribute(0).name());
        System.out.println(datas.attribute(1).name());
        System.out.println(datas.attribute(2).name());
        System.out.println(datas.attribute(3).name());
        System.out.println(datas.attribute(4).name());
///////////////////////////////MAPPING//////////////////////////////////////////////
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            for (int k=0; k < datas.numInstances(); k++) {
                for (int m=0; m < distincts.length; m++) {
                    if (datas.get(k).stringValue(j) == distincts[m]) {
                        if (datas.get(k).classValue() == 0.0) {
                            int x = map_class.get(datas.get(k).attribute(j).name()+
                                    distincts[m]+irisVirginica)+1;
                            map_class.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +irisVirginica, x);
                            break;
                        } else if (datas.get(k).classValue() == 1.0) {
                            int x = map_class.get(datas.get(k).attribute(j).name()+
                                            distincts[m]+irisVersiColor)+1;
                            map_class.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +irisVersiColor, x);
                            break;
                        } else if (datas.get(k).classValue() == 2.0) {
                            int x = map_class.get(datas.get(k).attribute(j).name()+
                                            distincts[m]+irisSetosa)+1;
                            map_class.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +irisSetosa, x);
                            break;
                        }
                    }
                }
            }
        }
/////////////////////////////////////ENDDOFMAPPING//////////////////////////////////////
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("After : ");
        System.out.println("==========================");
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            System.out.println(datas.attribute(j).name());
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            System.out.print(map_class.get(datas.attribute(j).name()+distincts[m]+irisVirginica));
                            break;
                        case 1:
                            System.out.print(map_class.get(datas.attribute(j).name()+distincts[m]+irisVersiColor));
                            break;
                        case 2:
                            System.out.print(map_class.get(datas.attribute(j).name()+distincts[m]+irisSetosa));
                            break;
                        default:
                            break;
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////        
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        // Pengklasifikasi instance baru
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
