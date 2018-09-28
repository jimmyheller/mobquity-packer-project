package com.mobiquityinc.domain;

import java.io.Serializable;

/**
 * Created by Jimmy Heller on 9/27/2018.
 */
public class Item implements Serializable, Comparable<Item> {
    private Integer index;
    private Float weight;
    private Integer price;

    public Item(String index, String weight, String price) {
        this.index = Integer.parseInt(index);
        this.weight = Float.parseFloat(weight);
        this.price = Integer.parseInt(price);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "index='" + index + '\'' +
                ", weight='" + weight + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public int compareTo(Item o) {
        int compareReuslt = o.price.compareTo(this.price);
        if (compareReuslt == 0) {
            return this.getWeight().compareTo(o.getWeight());
        } else {
            return compareReuslt;
        }

    }
}
