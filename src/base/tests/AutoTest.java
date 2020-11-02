package base.tests;

import base.sort.BinarySearchTree;
import base.sort.Searcher;
import base.sort.Sorter;
import model.DataGenerator;
import model.ItemObject;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoTest {
    private final static int itemCount = 1000;
    private final static int bound = 0;
    private static ArrayList<ItemObject> inputList;
    private static ArrayList<ItemObject> sortedList;
    long start;

    public AutoTest() {
    }

    @BeforeClass
    public static void PrepareAll() {
        ItemObject.setBound(bound);
        long start;
        inputList = DataGenerator.initRandArrayList(itemCount);

        sortedList = new ArrayList<>(inputList);
        start = System.currentTimeMillis();
        Collections.sort(sortedList);
        System.out.println(String.format("Collections.sort: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));
        if (itemCount == 10) System.out.println(sortedList);
    }

    @Before
    public void BeforeTest() {
        System.out.println("------------------------------------------");
    }

    @After
    public void afterTest() {
//        System.out.println("------------------------------------------");
        System.out.println("  ");
    }

    private void showTestTitle(String name) {
        System.out.println("Test " + name);
        System.out.println(String.format("input item count = %,d", itemCount));
    }

    @Test
    public void testStream(){
        DataGenerator.getStream(10).forEach(i -> System.out.println(i));

    }


    @Test
    public void testExample() {
//        fail(String) – указывает на то что бы тестовый метод завалился при этом выводя текстовое сообщение.
//        assertTrue([message], boolean condition) – проверяет, что логическое условие истинно.
//        assertsEquals([String message], expected, actual) – проверяет, что два значения совпадают.
//        Примечание: для массивов проверяются ссылки, а не содержание массивов.
//        assertNull([message], object) – проверяет, что объект является пустым null.
//        assertNotNull([message], object) – проверяет, что объект не является пустым null.
//        assertSame([String], expected, actual) – проверяет, что обе переменные относятся к одному объекту.
//        assertNotSame([String], expected, actual) – проверяет, что обе переменные относятся к разным объектам
        System.out.println("Example tests");
        Integer A = 24;
        Integer B = 24;
        Double C = 7.0;
        C = null;
        String msg = "Какое-то сообщение";
        if (!A.equals(B)) Assert.fail(msg);
        Assert.assertTrue(msg, A.equals(B));
        Assert.assertNull(C);
        Assert.assertNotNull(B);
        Assert.assertSame(A, B);
        Assert.assertNotSame(A, C);
    }

    @Test(timeout = 100)
    public void testTime() {
        System.out.println("Example time tests ");
        Assert.assertEquals(12, 12);
    }

    @Test(expected = Exception.class)
    public void testException() {
        System.out.println("Example exception tests");
        Integer A = 1;
        Integer B = 2;
        B = null;
        System.out.println(A / B);
    }

    private void startTimer() {
        start = System.currentTimeMillis();
    }

    private long getMillis() {
        return System.currentTimeMillis() - start;
    }

    @Test
    public void TestMyBinarySearchTree() {
        showTestTitle("MyBinarySearchTree");

        BinarySearchTree<ItemObject, ItemObject> tree = new BinarySearchTree<>(ItemObject.getComparator(), true);
        startTimer();
        for (ItemObject io : inputList) tree.Add(io, io);
        System.out.println(String.format("init all item: %d millis.", getMillis()));

        startTimer();
        ArrayList<ItemObject> tempList = tree.infixTraverse();
        System.out.println(String.format("get all sorted item: %d millis.", getMillis()));
        if (itemCount <= 10) System.out.println(tempList);
        System.out.println("max tree depth: " + tree.getMaxDepth());

        if (!tempList.equals(sortedList)) Assert.fail("Error sort");

        startTimer();
        int searchItemCount = itemCount / 10;
        for (int i = 0; i < itemCount / 10; i++) {
            if (tree.Search(inputList.get(i)) == null) Assert.fail("not fount item " + inputList.get(i).toString());
        }
        System.out.println(String.format("find %d item: %d millis.", searchItemCount, getMillis()));
    }

    @Test
    public void TestTreeSet() {
        showTestTitle("TreeSet");

        startTimer();
        TreeSet<ItemObject> treeSet = new TreeSet<>();
        treeSet.addAll(inputList);
        System.out.println(String.format("init all item: %d millis.", getMillis()));

        ArrayList<ItemObject> tempList = new ArrayList<ItemObject>();
        ArrayList<ItemObject> finalTempList = tempList;
        startTimer();
        Iterator<ItemObject> itr = treeSet.iterator();
        itr.forEachRemaining(obj -> finalTempList.add(obj));
        System.out.println(String.format("get all sorted item: %d millis.", getMillis()));
        if (itemCount <= 10) System.out.println(finalTempList);
        if (!finalTempList.equals(sortedList)) Assert.fail("Error sort");

        startTimer();
        int searchItemCount = itemCount / 10;
        for (int i = 0; i < itemCount / 10; i++) {
            if (!treeSet.contains(inputList.get(i))) Assert.fail("not fount item" + inputList.get(i));
        }
        System.out.println(String.format("find %d item: %d millis.", searchItemCount, getMillis()));
    }

    @Test
    public void testMyQuickSort() {
        showTestTitle("my QuickSort for list");

        Sorter<ItemObject> ms = new Sorter<>();
        ArrayList<ItemObject> tempList = new ArrayList<ItemObject>(inputList);
        startTimer();
        ms.QuickSort(tempList);

        System.out.print(String.format("sort time: %d millis.", getMillis()));
        if (!sortedList.equals(tempList)) Assert.fail("Error sort");
        if (itemCount <= 10) System.out.println(tempList);
    }

    @Test
    public void testMyQuickSortArray() {
        showTestTitle("my QuickSort for array");
        Sorter<ItemObject> ms;
        ArrayList<ItemObject> tempList;
        ItemObject[] tempArr;

        ms = new Sorter<>();
        tempList = new ArrayList<>(inputList.size());
        tempArr = new ItemObject[inputList.size()];

        startTimer();
        inputList.toArray(tempArr);
        ms.QuickSortArray(tempArr);

        System.out.print(String.format("sort time: %d millis.", getMillis()));
        Collections.addAll(tempList, tempArr);
        if (!sortedList.equals(tempList)) Assert.fail("Error sort");
        if (itemCount <= 10) System.out.println(tempList);
    }


    @Test
    public void testMyMergeSort() {
        showTestTitle("my MergeSort for array");

        Sorter<ItemObject> ms;
        ArrayList<ItemObject> tempList;
        ItemObject[] tempArr;

        ms = new Sorter<>();
        tempList = new ArrayList<>(inputList.size());
        tempArr = new ItemObject[inputList.size()];

        startTimer();
        inputList.toArray(tempArr);
        ms.MergeSortArray(tempArr);

        System.out.print(String.format("sort time: %d millis.", getMillis()));
        Collections.addAll(tempList, tempArr);
        if (!sortedList.equals(tempList)) Assert.fail("Error sort");
        if (itemCount <= 10) System.out.println(tempList);
    }


    @Test
    public void testPriorityQueueSort() {
        showTestTitle("sort by PriorityQueue");

        Sorter<ItemObject> ms;
        ArrayList<ItemObject> tempList;
        ItemObject[] tempArr;

        ms = new Sorter<>();
        tempList = new ArrayList<>(inputList);
        startTimer();
        Sorter.PriorityQueueSort(tempList);

        System.out.print(String.format("sort time: %d millis.", getMillis()));
        if (!sortedList.equals(tempList)) Assert.fail("Error sort");
        if (itemCount <= 10) System.out.println(tempList);
    }


    @Test
    public void testMyMergeSortList() {
        showTestTitle("my MergeSort for list");

        Sorter<ItemObject> ms;
        ArrayList<ItemObject> tempList;
        ItemObject[] tempArr;

        ms = new Sorter<>();
        tempList = new ArrayList<>(inputList);
        startTimer();
        ms.MergeSort(tempList);

        System.out.print(String.format("sort time: %d millis.", getMillis()));
        if (!sortedList.equals(tempList)) Assert.fail("Error sort");
        if (itemCount <= 10) System.out.println(tempList);
    }


    @Test
    public void testBinarySearch() {
        showTestTitle("my BinarySearch");
        startTimer();
        Searcher<ItemObject> searcher = new Searcher<>();
        Random random = new Random();
//        int searchCount = (int) Math.sqrt(itemCount);
        int searchCount = itemCount / 100;
        ItemObject findItem;
        ItemObject foundObj;
        for (int i = 0; i < searchCount; i++) {
            findItem = sortedList.get(random.nextInt(itemCount));
            foundObj = sortedList.get(searcher.BinarySearch(findItem, sortedList));
            if (findItem.compareTo(foundObj) != 0) Assert.fail("not found item: " + findItem);
        }
        System.out.println(String.format("%,d items searched in %d millis.", searchCount, getMillis()));
    }




}