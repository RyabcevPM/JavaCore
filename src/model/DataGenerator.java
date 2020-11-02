package model;


import java.util.ArrayList;
import java.util.stream.Stream;

public class DataGenerator {
    public static ArrayList<ItemObject> initRandArrayList(int count) {
        ArrayList<ItemObject> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new ItemObject());
        }
        return list;
    }


    public static Stream<ItemObject> getStream(int count){
        return Stream.generate(()->new ItemObject()).limit(count);
    }
}
