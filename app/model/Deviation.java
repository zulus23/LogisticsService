package model;

/**
 * Created by Zhukov on 19.08.2016.
 */
public enum  Deviation {
    YES("С отклонениями"), NO("Без отклонений");
    private String name;
    Deviation(String name) {
        this.name = name;
    }
    public  String getName(){ return  name;}
}
