/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Nugroho Satriyanto <massatriya@gmail.com>
 */
public class FFNN implements Classifier, Serializable {

    /**
     * instances yang akan diolah
     */
    public Instances datas;
    /**
     * kumpulan perceptron yang digambarkan sebagai matriks
     */
    public ArrayList<ArrayList<neuron>> perceptron;
    public ArrayList<ArrayList<Boolean>> connection;
    /**
     * jumlah perceptron pada hidden layer
     */
    public int hidden_neuron = 0;
    Random rnd = new Random();
    public int jml_perceptron;
    public double[][] data;
    public double learning_rate = 0.1;
    public float treserr = 0.5f;
    public float perc;

    private class neuron implements Serializable {

        public neuron(double w, double b) {
            weight = w;
            bias = b;
        }
        public double weight;
        public double bias;
        public float error;
        public float output;

        @Override
        public String toString() {
            return "(" + weight + "," + bias + "," + error + "," + output + ")";
        }
    }

    public boolean is_connected(int i, int j) {
        return connection.get(i).get(j);
    }

    public void save_model() {
        try {
            
            FileWriter file = new FileWriter("coba.txt");
            BufferedWriter bw = new BufferedWriter(file);
            for (int i=0;i<perceptron.size();i++){
                for (int j=0;j<perceptron.get(i).size();j++){
                    bw.write(Double.toString(perceptron.get(i).get(j).weight));
                    bw.write(" ");
                }
                bw.write("\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void load_model() {
        try {
            FileReader file = new FileReader("coba.txt");
            BufferedReader br = new BufferedReader(file);
            String temp;
            int i=0;
            while ((temp = br.readLine()) != null){
                String[] part = temp.split(" ");
                for (int j=0;j<part.length;j++) {
                    perceptron.get(i).get(j).weight = Double.parseDouble(part[j]);
                }
                System.out.println();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param x data ke x
     */
    public void hitung_net(int x) {
        Instance temp = datas.get(x);
        if (hidden_neuron == 0) {
            int i = jml_perceptron - datas.numClasses();
            for (; i < jml_perceptron; i++) {
                float out = 0;
                for (int j = 0; is_input(j); j++) {
                    out += perceptron.get(j).get(i).weight * data[x][j];
                }
                perceptron.get(i).get(i).output = hitung_sigmoid(out);
            }
        }
    }

    /**
     *
     * @param x
     * @return nilai fungsi sigmoid
     */
    public float hitung_sigmoid(float x) {
        return (float) (1 / (1 + Math.exp((double) -x)));
    }

    public FFNN(String filepath, int hidden_layer) throws Exception {
        datas = DataSource.read(filepath);
        datas.setClassIndex(datas.numAttributes() - 1);
        this.hidden_neuron = hidden_layer;
        perceptron = new ArrayList<>();
        connection = new ArrayList<>();
        perc = 1.0f / datas.numClasses();
        data = new double[datas.numInstances()][datas.numAttributes() - 1];
        to_matrix();
        initialize_perceptron();
    }

    public void to_matrix() {
        for (int i = 0; i < datas.numInstances(); i++) {
            data[i] = datas.get(i).toDoubleArray();
        }
    }

    /**
     *
     * @param x indeks perceptron
     * @return apakah perceptron indeks x masuk ke hidden layer
     */
    boolean is_hidden(int x) {
        if (hidden_neuron == 0) {
            return false;
        } else {
            return (x > datas.numAttributes() - 2) && (x <= datas.numAttributes() - 2 + hidden_neuron);
        }
    }

    /**
     *
     * @param x indeks perceptron
     * @return apakah perceptron indeks x termasuk input
     */
    private boolean is_input(int x) {
        return (x < datas.numAttributes() - 1);
    }

    /**
     *
     * @param x indeks perceptron
     * @return apakah perceptron indeks x termasuk output
     */
    private boolean is_output(int x) {
        return (x == jml_perceptron - 1);
    }

    private boolean cek_kelas(double out, int desired) {

        return ((out > desired * perc) && (out <= (desired + 1) * perc));
    }

    private double get_kelas(float x) {
        return Math.floor(x / perc);
    }

    /**
     *
     * @return nilai error
     */
    private void hitung_error(int x) {
        int i = 0;
        int act = (int) datas.get(x).classValue();
        hitung_net(x);
        while (!is_output(i)) {
            i++;
        }
        int first = i;
        int imax = i;
        for (i = i + 1; i < jml_perceptron; i++) {
            if (perceptron.get(i).get(i).output > perceptron.get(imax).get(imax).output) {
                imax = i;
            }
        }
        int desired = (int) datas.get(x).classValue();
        //System.out.println(perceptron.get(datas.numAttributes()-1).get(datas.numAttributes()-1).output+" - "+desired);
        if (!cek_kelas(perceptron.get(datas.numAttributes() - 1).get(datas.numAttributes() - 1).output, desired)) {
            //System.out.println("beda");
            for (int j = first; j < jml_perceptron; j++) {
                if (j == imax) {
                    perceptron.get(j).get(j).error = (float) Math.pow((perceptron.get(j).get(j).output - 1), 2.0);
                } else {
                    perceptron.get(j).get(j).error = (float) Math.pow((perceptron.get(j).get(j).output - 0), 2);
                }
            }
            for (int j = 0; is_input(j); j++) {
                for (int k = first; k < jml_perceptron; k++) {
                    if (perceptron.get(k).get(k).error != 0) {
                        perceptron.get(j).get(k).weight = perceptron.get(j).get(k).weight + learning_rate * (desired * perc - perceptron.get(k).get(k).output) * data[x][j];
                    }
                }
            }
        }
    }

    /**
     * menginisialisasi perceptron
     */
    public void initialize_perceptron() {
        jml_perceptron = (datas.numAttributes() - 1) + hidden_neuron + 1;
        if (hidden_neuron == 0) {
            for (int i = 0; i < jml_perceptron; i++) {
                connection.add(new ArrayList<>());
                perceptron.add(new ArrayList<>());
                for (int j = 0; j <= i; j++) {
                    if ((is_input(i)) && (is_input(j))) {
                        // jika i < jml attribut, maka i adalah simpul input, w=0
                        perceptron.get(i).add(new neuron(0.0, 0.0));
                        connection.get(i).add(false);
                    } else if (i == j) {
                        //jika i=j, maka weightnya 0 dan terdapat bias
                        perceptron.get(i).add(new neuron(0.0, rnd.nextInt(10) + rnd.nextDouble()));
                        connection.get(i).add(false);
                    } else {
                        //selain itu, perceptron mempunyai weight dan tidak mempunyai bias
                        perceptron.get(i).add(new neuron(rnd.nextDouble() - 0.5, 0.0));
                        connection.get(i).add(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < jml_perceptron; i++) {
                perceptron.add(new ArrayList<>());
                connection.add(new ArrayList<>());
                for (int j = 0; j <= i; j++) {
                    if ((is_input(i)) && (is_input(j))) {
                        // jika i < jml attribut, maka i adalah simpul input, w=0
                        perceptron.get(i).add(new neuron(0.0, 0.0));
                        connection.get(i).add(false);
                    } else if (i == j) {
                        //jika i=j, maka weightnya 0 dan terdapat bias
                        perceptron.get(i).add(new neuron(0.0, rnd.nextInt(10) + rnd.nextDouble()));
                        connection.get(i).add(false);
                    } else if (((is_input(i)) && (is_hidden(j))) || ((is_input(j)) && (is_hidden(i)))) {
                        //i perceptron input j perceptron hidden, maka mempunyai weight
                        perceptron.get(i).add(new neuron(rnd.nextInt(10) + rnd.nextDouble(), 0.0));
                        connection.get(i).add(true);
                    } else if (((is_hidden(i)) && (is_output(j))) || ((is_hidden(j)) && (is_output(i)))) {
                        //perceptron mempunyai weight
                        perceptron.get(i).add(new neuron(rnd.nextInt(10) + rnd.nextDouble(), 0.0));
                        connection.get(i).add(true);
                    } else {
                        perceptron.get(i).add(new neuron(0.0, 0.0));
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
    private void copy_init() {
        for (int i = 0; i < jml_perceptron; i++) {
            for (int j = i + 1; j < jml_perceptron; j++) {
                perceptron.get(i).add(perceptron.get(j).get(i));
                connection.get(i).add(connection.get(j).get(i));
            }
        }
    }

    public void print_perceptron() {
        System.out.println("i j (weight,bias,error,output)");
        for (int i = 0; i < perceptron.size(); i++) {
            for (int j = 0; j < perceptron.get(i).size(); j++) {
                System.out.println(i + " " + j + " " + perceptron.get(i).get(j));
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

    //@Override
    /**
     * Make model
     */
    public void buildClassifier(Instances x) throws Exception {
        if (hidden_neuron == 0) {
            for (int i = 0; i < 150; i++) {
                hitung_error(i);
            }
//            hitung_error(0);
        }
    }

    //@Override
    public double classifyInstance(Instance instnc) throws Exception {
        double[] temp = instnc.toDoubleArray();
        float ret = 0;
        int first = 0;
        while (!(is_output(first))) {
            first++;
        }
        int imax = first;
        if (hidden_neuron == 0) {
            int i = jml_perceptron - datas.numClasses();
            for (; i < jml_perceptron; i++) {
                float out = 0;
                for (int j = 0; is_input(j); j++) {
                    out += perceptron.get(j).get(i).weight * temp[j];
                }
                perceptron.get(i).get(i).output = hitung_sigmoid(out);
                ret = hitung_sigmoid(out);
            }
        }
        return get_kelas(ret);
    }

    //@Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
        double temp = classifyInstance(instnc);
        int x = datas.numClasses();
        double[] ret = new double[x];

        for (int i = 0; i < x; i++) {
            ret[i] = (1 - 0.5) / (x - 1);
        }
        ret[(int) temp] = 0.5;
        return ret;
    }

    @Override
    public Capabilities getCapabilities() {
        return null;
    }
}
