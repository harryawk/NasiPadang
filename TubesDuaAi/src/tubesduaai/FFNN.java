/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.util.ArrayList;
import java.util.Random;
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
public class FFNN implements Classifier {

    private class perceptron {

        public perceptron(double w, double b) {
            weight = w;
            bias = b;
        }
        public double weight;
        public double bias;

        @Override
        public String toString() {
            return "(" + weight + "," + bias + ")";
        }
    }

    /**
     * instances yang akan diolah
     */
    public Instances datas;
    /**
     * kumpulan perceptron yang digambarkan sebagai matriks
     */
    public ArrayList<ArrayList<perceptron>> perceptrons;
    public ArrayList<ArrayList<Boolean>> connection;
    /**
     * jumlah perceptron pada hidden layer
     */
    public int hidden_perceptron;
    Random rnd = new Random();
    public int jml_perceptron;

    public FFNN(String filepath, int hidden_layer) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes() - 1);
        perceptrons = new ArrayList<>();
        initialize_perceptrons();
    }
    
    /**
     * 
     * @param x indeks perceptron
     * @return apakah perceptron indeks x masuk ke hidden layer
     */
    boolean is_hidden(int x){
        if (hidden_perceptron==0)
            return false;
        else {
            if ((x>=datas.numAttributes()) && (x<datas.numAttributes()+hidden_perceptron))
                return true;
            else
                return false;
        }
    }
    
    /**
     * 
     * @param x indeks perceptron
     * @return apakah perceptron indeks x termasuk input
     */
    private boolean is_input(int x){
        if (x<datas.numAttributes()-1)
            return true;
        return false;
    }
    
    /**
     * 
     * @param x indeks perceptron
     * @return apakah perceptron indeks x termasuk output
     */
    private boolean is_output(int x){
        if (x>datas.numAttributes()+hidden_perceptron-1)
            return true;
        return false;
    }
    
    /**
     * 
     * @param i indeks
     * @param j indeks
     * @return apakah terkoneksi sinaptik
     */
    private boolean is_connected(int i,int j){
        return connection.get(i).get(j);
    }

    /**
     * menginisialisasi perceptrons
     */
    public void initialize_perceptrons() {
        jml_perceptron = (datas.numAttributes() - 1) + hidden_perceptron + datas.numClasses();
        if (hidden_perceptron == 0) {
            for (int i = 0; i < jml_perceptron; i++) {
                connection.add(new ArrayList<>());
                perceptrons.add(new ArrayList<>());
                for (int j = 0; j < jml_perceptron-i; j++) {
                    if ((i < datas.numAttributes() - 1) && (j < datas.numAttributes() - 1)) {
                        // jika i < jml attribut, maka i adalah simpul input, w=0
                        perceptrons.get(i).add(new perceptron(0.0, 0.0));
                        connection.get(i).add(false);
                    } else if (i == j) {
                        //jika i=j, maka weightnya 0 dan terdapat bias
                        perceptrons.get(i).add(new perceptron(0.0, rnd.nextInt(10) + rnd.nextDouble()));
                        connection.get(i).add(false);
                    } else {
                        //selain itu, perceptron mempunyai weight dan tidak mempunyai bias
                        perceptrons.get(i).add(new perceptron(rnd.nextInt(10) + rnd.nextDouble(), 0.0));
                        connection.get(i).add(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < jml_perceptron; i++) {
                perceptrons.add(new ArrayList<>());
                connection.add(new ArrayList<>());
                for (int j = 0; j < jml_perceptron-i; j++) {
                    if ((is_input(i)) && (is_input(j))) {
                        // jika i < jml attribut, maka i adalah simpul input, w=0
                        perceptrons.get(i).add(new perceptron(0.0, 0.0));
                        connection.get(i).add(false);
                    } else if (i == j) {
                        //jika i=j, maka weightnya 0 dan terdapat bias
                        perceptrons.get(i).add(new perceptron(0.0, rnd.nextInt(10) + rnd.nextDouble()));
                        connection.get(i).add(false);
                    } else if ((is_input(i))&& (is_hidden(j))){
                        //i perceptron input j perceptron hidden, maka mempunyai weight
                        perceptrons.get(i).add(new perceptron(rnd.nextInt(10) + rnd.nextDouble(), 0.0));
                        connection.get(i).add(true);
                    } else if ((is_hidden(i))&&(is_output(j))){
                        //perceptron mempunyai weight
                        perceptrons.get(i).add(new perceptron(rnd.nextInt(10) + rnd.nextDouble(), 0.0));
                        connection.get(i).add(true);
                    } else {
                        perceptrons.get(i).add(new perceptron(0.0, 0.0));
                        connection.get(i).add(false);
                    }
                }
            }
        }
        copy_init();
    }
    
    /**
     * menyelesaikan setengah inisialisasi lainnya
     */
    private void copy_init(){
        for (int i = 0; i < jml_perceptron; i++) {
            for (int j=jml_perceptron-i;j<jml_perceptron;j++){
                perceptrons.get(i).add(perceptrons.get(j).get(i));
                connection.get(i).add(connection.get(j).get(i));
            }
        }
    }

    public void print_perceptrons() {
        for (int i = 0; i < perceptrons.size(); i++) {
            for (int j = 0; j < perceptrons.get(i).size(); j++) {
                System.out.println(i + " " + j + " " + perceptrons.get(i).get(j));
            }
        }
    }

    public void DataRead(String filepath) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes() - 1);
    }

    /**
     * Fungsi untuk mengecek nominal
     *
     * @return array yang berisi indeks nominal, true bila nominal
     */
    public boolean[] cek_nominal() {
        boolean[] ret = new boolean[datas.numAttributes()];
        int nom = 0;
        for (int i = 0; i < datas.numAttributes(); i++) {
            if (datas.attribute(i).isNominal()) {
                ret[i] = true;
            } else {
                ret[i] = false;
            }
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
