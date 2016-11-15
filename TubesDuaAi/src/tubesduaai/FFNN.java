/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.util.ArrayList;
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
    private class perceptron{
        public perceptron(double w,double b){
            weight = w;
            bias = b;
        }
        public double weight;
        public double bias;
    }
    
    /** instances yang akan diolah */
    public Instances datas;
    /** kumpulan perceptron yang digambarkan sebagai matriks */
    public ArrayList<ArrayList<perceptron>> perceptrons;
    /** jumlah perceptron pada hidden layer */
    public int hidden_perceptron;
 
    public FFNN(String filepath,int hidden_layer) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes()-1);
        perceptrons = new ArrayList<>();
    }
    
    /** menginisialisasi perceptrons */
    public void initialize_perceptrons(){
        int jml_perceptron = (datas.numAttributes()-1)+hidden_perceptron+datas.numClasses();
        for (int i=0;i<jml_perceptron-1;i++){
            perceptrons.add(new ArrayList<>());
            for (int j=0;j<jml_perceptron;j++){
                if ((i<datas.numAttributes()-1) &&(j<datas.numAttributes()-1)){
                    // jika i < jml attribut, maka i adalah simpul input, w=0
                    perceptrons.get(i).add(new perceptron(0.0,0.0));
                } else if (i==j){
                    //jika i=j, maka jaraknya 0
                    perceptrons.get(i).add(new perceptron(0.0,0.0));
                }
            }
        }
    }
    
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
