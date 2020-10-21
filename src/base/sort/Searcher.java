package base.sort;

import model.DataGenerator;
import model.ItemObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Searcher<T extends Comparable<T>> {

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
