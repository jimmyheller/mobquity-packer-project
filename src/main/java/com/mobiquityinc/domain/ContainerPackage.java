package com.mobiquityinc.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jimmy Heller on 9/28/2018.
 */
public class ContainerPackage {
    private Float maxWeight;
    private List<Item> items;

    public ContainerPackage() {
        this.items = new LinkedList<Item>();
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "ContainerPackage{" +
                "maxWeight=" + maxWeight +
                ", items=" + items +
                '}';
    }
}
