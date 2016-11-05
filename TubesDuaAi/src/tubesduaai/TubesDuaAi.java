/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubesduaai;

/**
 *
 * @author harry
 */
public class TubesDuaAi {
    
    public static void demoNb() throws Exception {
        NB_030 nb = new NB_030();
        nb.DataRead("C:\\Program Files\\Weka-3-8\\data\\iris.arff");
//        System.out.println(nb.datas);
        nb.Discretize();
        nb.MakeModel();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            demoNb();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
