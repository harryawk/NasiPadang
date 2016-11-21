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
    public HashMap<String, Float> map;
    public String[][] string;
    public Float[] num;
    
    public NB_030() {
        map = new HashMap<String, Float>();
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
        string = new String[datas.numAttributes()][];
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
        
        // STRUKTUR DATA MODEL
//        HashMap<String, HashMap<String, HashMap<String, Integer>>> map_attribute = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
//        HashMap<String, HashMap<String, Integer>> map_distinct =  new HashMap<String, HashMap<String, Integer>>();
        
        
        // KEYS UNTUK STRUKTUR DATA
        String irisVirginica = datas.attribute(datas.classIndex()).value(0);
        String irisVersiColor = datas.attribute(datas.classIndex()).value(1);
        String irisSetosa = datas.attribute(datas.classIndex()).value(2);
                
        // INSERT DATA KE STRUKTUR DATA
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            System.out.println(datas.get(j).attribute(j));
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0), new Float(1));
                            break;
                        case 1:
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1), new Float(1));
                            break;
                        case 2:
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2), new Float(1));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
////DEBUGGING : ////////////////////////////////////LOOOOOOOOPPPP>????????//////////////////////        
        System.out.println("Before : ");
        System.out.println("==============================================");
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            System.out.println(datas.attribute(j).name());
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
//                            System.out.print(datas.get(0).attribute(0).name()+distincts[m]+irisVirginica);
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0)));
                            break;
                        case 1:
//                            System.out.println(datas.get(0).attribute(0).name()+distincts[m]+irisVersiColor);
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1)));
                            break;
                        case 2:
//                            System.out.println(datas.get(0).attribute(0).name()+distincts[m]+irisSetosa);
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2)));
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
////DEBUGGING : ///////////////////////////LOOOOPPPPP/////////////////////////////////////////
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println(datas.get(0).attribute(0).name());
        System.out.println(datas.attribute(1).name());
        System.out.println(datas.attribute(2).name());
        System.out.println(datas.attribute(3).name());
        System.out.println(datas.attribute(4).name());

///////////////////////////////MAPPING//////////////////////////////////////////////
        num = new Float[datas.numClasses()];
        for (int j=0; j < datas.numClasses(); j++) {
            num[j] = new Float(0);
        }
        System.out.println(datas.classAttribute().value(0));
        System.out.println(datas.classAttribute().value(1));
        System.out.println(datas.classAttribute().value(2));
        for (int k=0; k < datas.numInstances(); k++) {
            if (datas.get(k).stringValue(datas.classIndex()).equals(datas.classAttribute().value(0))) {
                num[0] += 1;
            } else if (datas.get(k).stringValue(datas.classIndex()).equals(datas.classAttribute().value(1))) {
                num[1] += 1;
            } else if (datas.get(k).stringValue(datas.classIndex()).equals(datas.classAttribute().value(2))) {
                num[2] += 1;
            }
        }
        Float y;
        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            string[j] = distincts;
            for (int k=0; k < datas.numInstances(); k++) {
                for (int m=0; m < distincts.length; m++) {
                    if (datas.get(k).stringValue(j) == distincts[m]) {
                        if (datas.get(k).stringValue(datas.classAttribute()) == datas.classAttribute().value(0)) {
                            Float x = map.get(datas.get(k).attribute(j).name()+
                                    distincts[m]+datas.classAttribute().value(0))+ 1;
                            map.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +datas.classAttribute().value(0), x);
                            break;
                        } else if (datas.get(k).stringValue(datas.classAttribute()) == datas.classAttribute().value(1)) {
                            Float x = map.get(datas.get(k).attribute(j).name()+
                                            distincts[m]+datas.classAttribute().value(1))+1;
                            map.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +datas.classAttribute().value(1), x);
                            break;
                        } else if (datas.get(k).stringValue(datas.classAttribute()) == datas.classAttribute().value(2)) {
                            Float x = map.get(datas.get(k).attribute(j).name()+
                                            distincts[m]+datas.classAttribute().value(2))+1;
                            map.put(datas.get(k).attribute(j).name()+distincts[m]
                                    +datas.classAttribute().value(2), x);
                            break;
                        }
                    }
                }
            }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int j=0; j < datas.numAttributes()-1; j++) {
            String[] distincts = distinctVals(j);
            System.out.println(datas.attribute(j).name());
            for (int m=0; m < distincts.length; m++) {
                for (int i=0; i < 3; i++) {
                    switch (i) {
                        case 0:
//                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0)));
                            y = map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0));
                            System.out.println(y/num[0]);
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0), y/num[0]);
                            break;
                        case 1:
//                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1)));
                            y = map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1));
                            System.out.println(y/num[1]);
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1), y/num[1]);
                            break;
                        case 2:
//                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2)));
                            y = map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2));
                            System.out.println(y/num[2]);
                            map.put(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2), y/num[2]);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println(num[0]);
        System.out.println(num[1]);
        System.out.println(num[2]);
///////////////////////////CALCULATING ACCURACY///////////////////////////////////////////////
        for (int i=0; i < datas.numInstances(); i++) {
            float[] argmax = new float[3];
            for (int q=0; q < datas.numClasses(); q++) {
                float prob = 0;
                
            }
        }

///////////////////////////////////////////////////////////////////////////////////////////////////
/////DEBUGGING : ////////////////////////////////ENDDOFMAPPING//////////////////////////////////////
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
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(0)));
                            break;
                        case 1:
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(1)));
                            break;
                        case 2:
                            System.out.print(map.get(datas.attribute(j).name()+distincts[m]+datas.classAttribute().value(2)));
                            break;
                        default:
                            break;
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////// CALCULATE ACCURACY AND CLASSIFY ACCURACY //////////////////////////////////
        /////////////////MEMASUKKAN KELAS MENURUT MODEL KE ARRAY
//        Double[] hasilTraining = new Double[datas.numInstances()];
//        for (int i=0; i <  datas.numInstances(); i++) {
//            /// CALCULATION OF PROBABILITY
//            hasilTraining[i] = 1.0;
//            /// KEYS UNTUK KE MAP
//            double current_argmax;
//            for (int k=0; k < datas.numClasses(); k++) {
//                current_argmax = 0.0;
//                for (int j=0; j  < datas.numAttributes()-1; j++) {
//                    String atribut = datas.attribute(j).name();
//                    System.out.println("atribut " + atribut);
//                    String value = datas.get(i).stringValue(j);
//                    System.out.println("value " + value);
//                    String kelas = datas.classAttribute().value(k);
//                    System.out.println("kelas " + kelas);
//                    hasilTraining[i] *= map.get(atribut+value+kelas);
//                }
//                if (current_argmax < hasilTraining[i]) current_argmax = hasilTraining[i];
//            }
//            
////            hasilTraining[i] *= ;
//            System.out.println("hasil training " + i + " : " + hasilTraining[i]);
//        }
        ////////////////////
//        for (int i=0; i < datas.numInstances(); i++) {
////            if (datas.get(i).stringValue(datas.classIndex()) == )
//        }
//////END OF METHOD////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        // Pengklasifikasi instance baru
        int NUM_CLASSES = datas.classAttribute().numValues();
//        System.out.println("------------------------------");
//        System.out.println(datas.get(1));
//        System.out.println(datas.get(1).stringValue(2));
        // Probability of classes
        int arg = 0;
        Double argmax = 0.0;
        Double temp = 1.0;
        for (int i=0; i < NUM_CLASSES; i++) { // arg max (vj E enum.attributes(datas))
            // P(kelas)*P(atribut|kelas)
            temp = ((new Double(num[i]))/datas.numInstances());
            for (int j=0; j < datas.numAttributes()-1; j++) {
//                System.out.println("--------------");
//                System.out.println(map.get(instnc.attribute(j).name()+instnc.stringValue(j)+datas.classAttribute().value(i)));
                temp *= map.get(instnc.attribute(j).name()+instnc.stringValue(j)+datas.classAttribute().value(i));
            }
            if (temp > argmax) {
                argmax = temp;
                arg = i;
            }
        }
        switch (arg) {
            case 0 :
                return 0.0;
            case 1 :
                return 1.0;
            case 2 :
                return 2.0;
            default:
                return 0.0;
        }
    }

    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        double temp = classifyInstance(instnc);
        int x = 3;
        double[] ret = new double[x];
        
        for (int i=0;i<x;i++){
            ret[i] = (1-0.5)/(x-1);
        }
        ret[(int) temp] = 0.5;
        return ret;
    }

    @Override
    public Capabilities getCapabilities() {
        return null;
    }
}
