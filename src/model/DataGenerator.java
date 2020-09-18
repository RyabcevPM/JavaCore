package model;


import java.util.ArrayList;

public class DataGenerator {
    public static ArrayList<ItemObject> initRandArrayList(int count) {
        ArrayList<ItemObject> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new ItemObject());
        }
        return list;
    }
}
