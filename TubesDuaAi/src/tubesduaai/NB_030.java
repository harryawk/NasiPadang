/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

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
    
    public NB_030() {
        
    }
    
    public Instances Discretize() throws Exception {
        Discretize discretize = new Discretize();
        String[] options = new String[2];
        options[0] = "-R";                                    
        options[1] = "1";                                     
        discretize.setOptions(options);
        discretize.setInputFormat(datas);
        return Filter.useFilter(datas,discretize);
    }
    
    public void DataRead(String filepath) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes()-1);
    }
    
    public void MakeModel() {
        System.out.println("============INSTANCES=================");
        for (int i=0; i < datas.numInstances(); i++) {
            System.out.println(datas.get(i));
        }
    }

    @Override
    public void buildClassifier(Instances i) throws Exception {
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
