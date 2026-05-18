package rvt;

import java.util.HashMap;

public class IOweYou {

  
    
    private HashMap<String, Double> hashmap;
    public IOweYou() {
        this.hashmap = new HashMap<>();
    }

    public void setSum(String toWhom, double amount) {
        this.hashmap.put(toWhom, amount);
    }

    public double howMuchDoIOweTo(String toWhom) {
        return this.hashmap.getOrDefault(toWhom, 0.0);
    }

    public static void main(String[] args) {
        IOweYou iou = new IOweYou();
        iou.setSum("Andrejs", 50.5);
        iou.setSum("Janis", 23);

        System.out.println(iou.howMuchDoIOweTo("Andrejs"));
        System.out.println(iou.howMuchDoIOweTo("Janis"));
    }
}