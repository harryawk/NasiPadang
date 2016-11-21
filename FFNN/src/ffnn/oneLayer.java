/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn;

/**
 *
 * @author Toshiba
 */
public class oneLayer {
    private node[][] layer;
    private int col; //node kanan layer
    private int row; //node kiri layer
    
    public oneLayer(int row, int col) {
        layer = new node[row+1][col];
        for (int i=0; i < row; i++){
            for (int j=0; j < col; j++){
                layer[i][j] = new node();
            }
        }
        for (int j=0; j < col; j++){ //Bias node
            layer[row][j] = new node(1);
        } 
        this.col = col;
        this.row = row+1;
    }
    
    public node getNode(int row, int col) {
        return layer[row][col];
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public void setNode(int row, int col, node Node) {
        layer[row][col] = Node;
    }
    
    //Value sudah terdefinisi
    public double sigmoid(int col) {
        double perceptron = 0;
        for (int i=0; i < row; i++){
            perceptron += getNode(i, col).getValue()*getNode(i, col).getWeight();
        }
        return (1/(1+(Math.pow(Math.E,-perceptron))));
    }
    
    public void print() {
        for (int i=0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.println("Value "+getNode(i, j).getValue()+" = Weight "+getNode(i, j).getWeight());
            }
            System.out.println("====KOLOM====");
        }
    }
}
