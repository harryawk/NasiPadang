/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

import weka.core.Instances;

/**
 *
 * @author Nugroho Satriyanto <massatriya@gmail.com>
 */
public class TesFFNN {
    public static void tes() throws Exception{
        Instances dummy = null;
        FFNN nn = new FFNN("C:\\Program Files\\Weka-3-8\\data\\iris.arff",0);
        boolean[] nom = nn.cek_nominal();
        for (int i=0;i<nom.length;i++){
            System.out.println(nom[i]);
        }
    }
    
    public static void main(String[] args){
        try{
            tes();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
