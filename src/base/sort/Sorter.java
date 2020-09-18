package base.sort;


import model.DataGenerator;
import model.ItemObject;

import java.util.*;

public class Sorter<T extends Comparable<T>> {

    public static void testAllSort(int itemCount) {
        long start;
        ArrayList<ItemObject> inputList = DataGenerator.initRandArrayList(itemCount);
        Sorter<ItemObject> ms = new Sorter<>();
        ArrayList<ItemObject> tempList;
        ArrayList<ItemObject> sortedList;
        ItemObject[] tempArr;

        sortedList = new ArrayList<>(inputList);
        start = System.currentTimeMillis();
        Collections.sort(sortedList);
        System.out.println(String.format("Collections.sort: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        if (itemCount <= 10) System.out.println(sortedList);

        tempList = new ArrayList<ItemObject>(inputList);
        start = System.currentTimeMillis();
        ms.QuickSort(tempList);
        System.out.print(String.format("QuickSort: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);

        tempList = new ArrayList<>(inputList.size());
        tempArr = new ItemObject[inputList.size()];
        start = System.currentTimeMillis();
        inputList.toArray(tempArr);
        ms.QuickSortArray(tempArr);
        Collections.addAll(tempList, tempArr);
        System.out.print(String.format("QuickSortArray: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);

        tempList = new ArrayList<>(inputList.size());
        tempArr = new ItemObject[inputList.size()];
        start = System.currentTimeMillis();
        inputList.toArray(tempArr);
        ms.MergeSortArray(tempArr);
        Collections.addAll(tempList, tempArr);
        System.out.print(String.format("MergeSortArray: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);



        tempList = new ArrayList<>(inputList);
        start = System.currentTimeMillis();
        PriorityQueueSort(tempList);
        System.out.print(String.format("PriorityQueueSort: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);

        tempList = new ArrayList<ItemObject>(inputList);
        start = System.currentTimeMillis();
        ms.MergeSort(tempList);
        System.out.print(String.format("MergeSortList: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);

        tempList = new ArrayList<>(inputList);
        start = System.currentTimeMillis();
        TreeSetDistinctSort(tempList);
        System.out.print(String.format("TreeSetDistinctSort: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
        if (itemCount <= 10) System.out.println(tempList);

//        tempList = new ArrayList<>(inputList);
//        start = System.currentTimeMillis();
//        TreeSetSort(tempList);
//        System.out.print(String.format("TreeSetSort: %d item sorted in %d millis.", itemCount, System.currentTimeMillis() - start));
//        System.out.println(!sortedList.equals(tempList) ? " - Error sort" : " ");
//        if (itemCount <= 10) System.out.println(tempList);

    }

    public static void PriorityQueueSort(ArrayList<ItemObject> list) {
        PriorityQueue<ItemObject> pq = new PriorityQueue(list.size());
        int count = list.size();
        pq.addAll(list);
        list.clear();
        for (int i = 0; i < count; i++) {
            list.add(pq.poll());
        }
    }

    public static void TreeSetSort(ArrayList<ItemObject> list) {
        TreeSet<ItemObject> treeSet = new TreeSet<>();
        HashMap<ItemObject, Integer> map = new HashMap<>();
        treeSet.addAll(list);
        for (ItemObject itemObject : list) {
            Integer key = map.get(itemObject);
            if (key == null) map.put(itemObject, 1);
            else map.put(itemObject, key + 1);
        }
        list.clear();
        Iterator<ItemObject> itr = treeSet.iterator();
        ItemObject item;
        while (itr.hasNext()) {
            item = itr.next();
            for (int i = 0; i < map.get(item); i++) {
                list.add(item);
            }
        }
    }


    public static void TreeSetDistinctSort(ArrayList<ItemObject> list) {
        TreeSet<ItemObject> treeSet = new TreeSet<>();
        treeSet.addAll(list);
        list.clear();
        Iterator<ItemObject> itr = treeSet.iterator();
        while (itr.hasNext()) {
            list.add(itr.next());
        }
    }

    void Swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public void MergeSortArray(ItemObject[] arrayIn) {
        if (arrayIn.length < 2) return;
        int center = arrayIn.length / 2;
        ItemObject[] arrayA = new ItemObject[center];
        System.arraycopy(arrayIn, 0, arrayA, 0, center);
        ItemObject[] arrayB = new ItemObject[arrayIn.length - center];
        System.arraycopy(arrayIn, center, arrayB, 0, arrayIn.length - center);
        MergeSortArray(arrayA);
        MergeSortArray(arrayB);
        int i = 0;
        int j = 0;
        for (int e = 0; e < arrayIn.length; e++) {
            if (i < arrayA.length && j < arrayB.length) {
                if (arrayA[i].compareTo(arrayB[j]) < 0) arrayIn[e] = arrayA[i++];
                else arrayIn[e] = arrayB[j++];
            } else {
                if (i < arrayA.length) arrayIn[e] = arrayA[i++];
                else arrayIn[e] = arrayB[j++];
            }
        }
    }

    public void MergeSort(List<T> list) {
        if (list.size() == 1) return;
        int center = list.size() / 2;
        List<T> list1 = new ArrayList<>(list.subList(0, center));
        MergeSort(list1);
        List<T> list2 = new ArrayList<>(list.subList(center, list.size()));
        MergeSort(list2);
        int i = 0;
        int j = 0;
        for (int e = 0; e < list.size(); e++) {
            if (i < list1.size() && j < list2.size())
                if (list1.get(i).compareTo(list2.get(j)) < 0) list.set(e, list1.get(i++));
                else list.set(e, list2.get(j++));
            else {
                if (i < list1.size()) list.set(e, list1.get(i++));
                else list.set(e, list2.get(j++));
            }
        }
    }

    public void QuickSort(List<T> list) {
        if (list.size() < 2) return;
        int start = 0;
        int end = list.size();
        int center = (start + end) / 2;
        T centerItem = list.get(center);
        ArrayList<T> lessItems = new ArrayList<>();
        ArrayList<T> centerItems = new ArrayList<>();
        ArrayList<T> betterItems = new ArrayList<>();
        int compareRes;
        for (int i = start; i < end; i++) {
            compareRes = centerItem.compareTo(list.get(i));
            if (compareRes == 0) centerItems.add(list.get(i));
            if (compareRes < 0) betterItems.add(list.get(i));
            if (compareRes > 0) lessItems.add(list.get(i));
        }
        QuickSort(lessItems);
        QuickSort(betterItems);
        list.clear();
        list.addAll(lessItems);
        list.addAll(centerItems);
        list.addAll(betterItems);
    }

    public void QuickSortArray(ItemObject[] arrayIn) {
        if (arrayIn.length < 2) return;
        int center = arrayIn.length / 2;
        ItemObject centerItem = arrayIn[center];
        int lessItemCount = 0;

        for (int e = 0; e < arrayIn.length; e++)
            if (e != center && centerItem.compareTo(arrayIn[e]) > 0) lessItemCount += 1;

        ItemObject[] arrayA = new ItemObject[lessItemCount];
        ItemObject[] arrayB = new ItemObject[arrayIn.length - lessItemCount - 1];
        int i = 0;
        int j = 0;
        for (int e = 0; e < arrayIn.length; e++)
            if (e != center) {
                if (centerItem.compareTo(arrayIn[e]) > 0) arrayA[i++] = arrayIn[e];
                else arrayB[j++] = arrayIn[e];
            }
        MergeSortArray(arrayA);
        MergeSortArray(arrayB);
        System.arraycopy(arrayA, 0, arrayIn, 0, arrayA.length);
        arrayIn[arrayA.length] = centerItem;
        System.arraycopy(arrayB, 0, arrayIn, arrayA.length + 1, arrayB.length);
    }


}
