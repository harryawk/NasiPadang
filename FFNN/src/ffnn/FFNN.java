/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn;

import java.io.Serializable;
import static java.lang.StrictMath.abs;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author Toshiba
 */
public class FFNN implements Classifier, Serializable {
    
    private oneLayer in_hid;
    private oneLayer hid_out;
    private double[] out_error;
    private double[] hide_error;
    private double[] outRep;
    Instances datas;
    int number_attribute;
    double learning_rate;
    
    public FFNN(String filepath, int hidden_number, double lr) throws Exception {
        learning_rate = lr;
        datas = ConverterUtils.DataSource.read(filepath);
        number_attribute = datas.numAttributes();
        datas.setClassIndex(number_attribute - 1);//set label
        //row, jumlah atribut input + bias
        in_hid = new oneLayer(number_attribute-1,hidden_number);
        //row, jumlah hidden layer + bias
        //col, jumlah nilai pada atribut output 
        hid_out = new oneLayer(hidden_number,datas.attribute(number_attribute - 1).numValues());
        hide_error = new double[in_hid.getCol()+1];
        out_error = new double[hid_out.getCol()];
    }
    
    public FFNN() throws Exception {
        
    }
    
    public void doForInstance(Instance data) {
        //Assign value berdasarkan instance untuk input
        //row terakhir merupakan bias
        for (int i=0; i < (in_hid.getRow()-1); i++) {
            for (int j=0;j < in_hid.getCol(); j++) {
                node Node = in_hid.getNode(i, j);
                Node.setValue(data.value(i));
                in_hid.setNode(i, j, Node);
            }
        }
        //Assign value berdasarkan in_hid untuk hidden layer
        for (int i=0; i < (hid_out.getRow()-1); i++) {;
            double sigmoidResult = in_hid.sigmoid(i);
            for (int j=0; j < hid_out.getCol(); j++) {
                node Node = hid_out.getNode(i, j);
                Node.setValue(sigmoidResult);
                hid_out.setNode(i, j, Node);
            }
        }
        
        //Assign error
        outRep = outRepresentation(data.value(number_attribute-1));
        calOutError(outRep);
        calHideError();
        
        
        //Update weight
        for(int i=0; i < in_hid.getRow(); i++) {
            for (int j = 0; j < in_hid.getCol(); j++) {
                node Node = in_hid.getNode(i, j);
                double new_weight = Node.getWeight() + learning_rate*hide_error[j]*Node.getValue();
                Node.setWeight(new_weight);
                in_hid.setNode(i, j, Node);
            }
        }
        
        for(int i=0; i < hid_out.getRow(); i++) {
            for (int j = 0; j < hid_out.getCol(); j++) {
                node Node = hid_out.getNode(i, j);
                double new_weight = Node.getWeight() + learning_rate*out_error[j]*Node.getValue();
                Node.setWeight(new_weight);
                hid_out.setNode(i, j, Node);
            }
        }
        
    }
    
    public double[] outRepresentation(double s) {
        Attribute label =  datas.attribute(number_attribute - 1);
        double[] temp = new double[label.numValues()];
        for (int i= 0; i < label.numValues(); i++) {
            if ( abs(i-s) < 0.0000001) {
                temp[i] = 1;
            } else {
                temp[i] = 0;
            }
        }
        /*
        for (int i=0; i < temp.length ; i++){
            System.out.println("CEKKKKKK "+ temp[i]);
        } */
        return temp;
    }
    
    public void printModel() {
        
        System.out.println("== TABEL ==");
        in_hid.print();
        System.out.println("");
        System.out.println("== TABEL ==");
        hid_out.print();
        System.out.println("== ERROR OUT ==");
        for (int i=0; i < out_error.length;i++){
            System.out.println(out_error[i]);
        }
        System.out.println("== ERROR HIDE ==");
        for (int i=0; i < hide_error.length;i++){
            System.out.println(hide_error[i]);
        }
    }
    
    public void calOutError(double[] outRep) {
        for (int i=0; i < out_error.length; i++){
            double outSigmoidResult = hid_out.sigmoid(i);
            out_error[i] = outSigmoidResult*(1-outSigmoidResult)*(outRep[i]-outSigmoidResult);
        }
    }
    
    public void calHideError () {
        for (int i=0; i < hide_error.length; i++) {
            double sumError = 0;
            for (int j=0; j < hid_out.getCol(); j++) {
                sumError += (hid_out.getNode(i, j).getWeight()*out_error[j]);
            }
            hide_error[i] = hid_out.getNode(i, 2).getValue()*(1-hid_out.getNode(i, 2).getValue())*sumError;
        }
    }

    public double meanSquareError(double[] outRep) {
        double MSE = 0;
        for(int i=0; i<hid_out.getCol(); i++){
            double out = hid_out.sigmoid(i);
            MSE += Math.pow((out-outRep[i]),2);
        }
        return MSE/hid_out.getCol();
    }
    
    public static Instances filter(Instances x) throws Exception{
        String[] options = new String[4];
        options[0] = "-S";
        options[1] = "1.0";
        options[2] = "-T";
        options[3] = "0.0";
        Normalize dis = new Normalize();
        dis.setOptions(options);
        dis.setInputFormat(x);
        return Filter.useFilter(x, dis);
    }
    
    @Override
    public void buildClassifier(Instances ins) throws Exception {
        double lr = 0.005;
        int hidden_number = 8;
        double MSE = 10;
        double pMSE = 1;
        double lMSE = 1;
        double threshold = 0.01;
        int counter = 0;
        int i = 0;
        
        learning_rate = lr;
        this.datas = ins;
        number_attribute = datas.numAttributes();
        datas.setClassIndex(number_attribute - 1);//set label
        //row, jumlah atribut input + bias
        datas = filter(datas);
        while (MSE - threshold > 0.001) {
            if (counter == 0) {
                in_hid = new oneLayer(number_attribute-1,hidden_number);
                //row, jumlah hidden layer + bias
                //col, jumlah nilai pada atribut output 
                hid_out = new oneLayer(hidden_number,datas.attribute(number_attribute - 1).numValues());
                hide_error = new double[in_hid.getCol()];
                out_error = new double[hid_out.getCol()];
            }
            i++;
            for (int iter=0; iter < ins.numInstances(); i++){
                Instance data = ins.get(iter);
                doForInstance(data);
            }
            MSE = meanSquareError(outRep);
            if (abs(MSE-pMSE) < 0.000001) {
                counter--;
            } else {
                counter++;
            }
            pMSE = MSE;
            if ((MSE-lMSE) < 0) {
                lMSE = MSE;
            }
            if (counter == 0){
                threshold = (threshold*3+lMSE)/2;
            }
            if (threshold - lMSE > 0){
                lMSE = threshold;
            }
        }
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        //Assign value berdasarkan instance untuk input
        //row terakhir merupakan bias
        for (int i=0; i < (in_hid.getRow()-1); i++) {
            for (int j=0;j < in_hid.getCol(); j++) {
                node Node = in_hid.getNode(i, j);
                Node.setValue(instnc.value(i));
                in_hid.setNode(i, j, Node);
            }
        }
        //Assign value berdasarkan in_hid untuk hidden layer
        for (int i=0; i < (hid_out.getRow()-1); i++) {
            double sigmoidResult = in_hid.sigmoid(i);
            for (int j=0; j < hid_out.getCol(); j++) {
                node Node = hid_out.getNode(i, j);
                Node.setValue(sigmoidResult);
                hid_out.setNode(i, j, Node);
            }
        }
        
        double maxSigmoidResult = hid_out.sigmoid(0);
        //System.out.println("======");
        //System.out.println(maxSigmoidResult);
        double idx_max = 0;
        for (int i=1; i < hid_out.getCol(); i++) {
            double outSigmoidResult = hid_out.sigmoid(i);
            //System.out.println(outSigmoidResult);
            if (maxSigmoidResult - outSigmoidResult < 0) {
                idx_max = (double) i;
            }
        }
        return idx_max;
    }

    @Override
    public double[] distributionForInstance(Instance instnc) throws Exception {
	double temp = classifyInstance(instnc);
        int x = datas.attribute(number_attribute-1).numValues();
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
