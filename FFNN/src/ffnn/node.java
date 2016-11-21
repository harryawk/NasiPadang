/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffnn;

import java.util.Random;

/**
 *
 * @author Toshiba
 */
public class node {
    private double weight;
    private double value;
    
    public node() {
        value = 0;
        //Random weight assign
        double rangeMin = -0.5;
        double rangeMax = 0.5;
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        weight = randomValue;
    }
    
    public node(double v) {
        value = v;
        //Random weight assign
        double rangeMin = -0.5;
        double rangeMax = 0.5;
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        weight = randomValue;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public double getValue() {
        return value;
    }
    
    public void setWeight(double w) {
        weight = w;
    }
    
    public void setValue(double v) {
        value = v;
    }
}
