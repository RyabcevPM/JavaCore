package base.sort;

import model.DataGenerator;
import model.ItemObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Searcher<T extends Comparable<T>> {
    public static void testBinarySearch(int itemCount) {
        long start = System.currentTimeMillis();
        ArrayList<ItemObject> list = DataGenerator.initRandArrayList(itemCount);
        Collections.sort(list);
        Searcher<ItemObject> searcher = new Searcher<>();
        Random random = new Random();
        int searchCount = (int) Math.sqrt(itemCount);
        ItemObject findItem;
        ItemObject foundObj;
        for (int i = 0; i < searchCount; i++) {
            findItem = list.get(random.nextInt(itemCount));
            foundObj = list.get(searcher.BinarySearch(findItem, list));
            if (findItem.compareTo(foundObj) != 0)
                System.out.println(String.format("no found: %S != %S", findItem, foundObj));
        }
        System.out.println(String.format("%d found from %d items for %d millis.", searchCount, itemCount, System.currentTimeMillis() - start));
    }

    public int BinarySearch(T item, List<T> list) {
        int left = 0;
        int right = list.size() - 1;
        int center;
        int res;
        T centerItem;
        while (left < right) {
            center = (left + right) / 2;
            centerItem = list.get(center);
            res = centerItem.compareTo(item);
            if (res == 0) return center;
            if (res < 0) left = center + 1;
            else right = center - 1;
        }
        return left;
    }

}
