package br.com.renaninfo.calccomp.components;

import java.util.ArrayList;

public class Group {

    private String Name;
    private Boolean selected;
    private ArrayList<Child> Items;

    public Group(String name, Boolean selected) {
        Name = name;
        this.selected = selected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> Items) {
        this.Items = Items;
    }

}
