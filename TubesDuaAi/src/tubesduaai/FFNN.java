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
 * @author Nugroho Satriyanto <massatriya@gmail.com>
 */
public class FFNN implements Classifier{
    public Instances datas;
    
    public void DataRead(String filepath) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes()-1);
    }
    
    /**
     * Fungsi untuk mengecek nominal
     * @return array yang berisi indeks nominal, true bila nominal
     */
    public boolean[] cek_nominal(){
        boolean[] ret = new boolean[datas.numAttributes()];
        int nom = 0;
        for (int i=0;i<datas.numAttributes();i++){
            if (datas.attribute(i).isNominal())
                ret[i] = true;
            else
                ret[i] = false;
        }
        return ret;
    }
    
    @Override
    /**
     * Make model
     */
    public void buildClassifier(Instances i) throws Exception {
        
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
