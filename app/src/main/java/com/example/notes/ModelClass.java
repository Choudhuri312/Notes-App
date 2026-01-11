package com.example.notes;

public class ModelClass {
    int id;
    String title, desc;
    boolean isSelected = false;
    public ModelClass(int id, String title, String desc){
        this.id = id;
        this.title = title;
        this.desc = desc;
    }
    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getDesc() {return desc;}
    // Add getter and setter for isSelected
    public boolean isSelected(){ return isSelected;}
    public void setSelected(boolean selected){ isSelected = selected; }
}
