package base.sort;

import model.DataGenerator;
import model.ItemObject;
import sun.reflect.generics.tree.Tree;

import java.sql.SQLOutput;
import java.util.*;

interface ISearchTree<Key, Value> {
    /*4.1	Поиск элемента (FIND)
4.2	Добавление элемента (INSERT)
4.3	Удаление узла (REMOVE)
4.4	Обход дерева (TRAVERSE)
4.5	Разбиение дерева по ключу
4.6	Объединение двух деревьев в одно
    5	Балансировка дерева
FIND(K) — поиск узла, в котором хранится пара (key, value) с key = K.
INSERT(K, V) — добавление в дерево пары (key, value) = (K, V).
REMOVE(K) — удаление узла, в котором хранится пара (key, value) с key = K.

INFIX_TRAVERSE (tr) — обойти всё дерево, следуя порядку (левое поддерево, вершина, правое поддерево). Элементы по возрастанию
PREFIX_TRAVERSE (tr) — обойти всё дерево, следуя порядку (вершина, левое поддерево, правое поддерево). Элементы, как в дереве
POSTFIX_TRAVERSE (tr) — обойти всё дерево, следуя порядку (левое поддерево, правое поддерево, вершина). Элементы в обратном порядке, как в дереве

Объединение двух деревьев в одно
Балансировка дерева
    */
    Value Search(Key key);

    void Add(Key key, Value value);

    void Delete(Key key);

    ArrayList<Value> infixTraverse();
}


public class BinarySearchTree<Key, Value> implements ISearchTree<Key, Value> {
    private Comparator<Key> keyComparator;
    private int itemCount;
    //    private int depth;
    private TreeItem head;
    private boolean isSaveAll;

    public BinarySearchTree(Comparator keyComparator, boolean isSaveAllItem) {
        this.keyComparator = keyComparator;
        this.isSaveAll = isSaveAllItem;
    }

    public static void Test() {
        ItemObject.setBound(0);
        int itemCount = 1000_000;
        long start;
        ArrayList<ItemObject> inputList = DataGenerator.initRandArrayList(itemCount);
        ArrayList<ItemObject> sortedList;

        sortedList = new ArrayList<>(inputList);
        start = System.currentTimeMillis();
        Collections.sort(sortedList);
        System.out.println(String.format("Collections.sort: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));
        if (itemCount <= 10) System.out.println(sortedList);
//        sortedList.iterator().forEachRemaining(x-> System.out.println(x));

        BinarySearchTree<ItemObject, ItemObject> tree = new BinarySearchTree<>(ItemObject.getComparator(), false);
        start = System.currentTimeMillis();
        for (ItemObject io : inputList) {
            tree.Add(io, io);
        }
        System.out.println(String.format("init my SearchBinaryTree: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ArrayList<ItemObject> tempList = tree.infixTraverse();
        System.out.println(String.format("get all sorted items from my SearchBinaryTree: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));
        if (itemCount <= 10) System.out.println(tempList);
        System.out.println(tempList.equals(sortedList));
        System.out.println("my SearchBinaryTree depth = " + tree.getMaxDepth());

        start = System.currentTimeMillis();
        TreeSet<ItemObject> treeSet = new TreeSet<>();
        treeSet.addAll(inputList);
//        for (ItemObject io : inputList) {
//            treeSet.add(io);
//        }
        System.out.println(String.format("TreeSet add all: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));

        tempList = new ArrayList<ItemObject>();
        ArrayList<ItemObject> finalTempList = tempList;
        start = System.currentTimeMillis();
        Iterator<ItemObject> itr = treeSet.iterator();
        itr.forEachRemaining(obj -> finalTempList.add(obj));
        System.out.println(String.format("TreeSet get all sorted item: %d item in %d millis.", itemCount, System.currentTimeMillis() - start));
        System.out.println(tempList.equals(sortedList));
        if (itemCount <= 10) System.out.println(finalTempList);
//            itr.forEachRemaining(obj -> System.out.println(obj));


    }

    private int getMaxDepth(TreeItem item) {
        if (item == null) return 0;
        if (item.isLeaf()) return item.depth;
        return Math.max(getMaxDepth(item.left), getMaxDepth(item.right));
    }

    int getMaxDepth() {
        return getMaxDepth(head);
    }

    private ArrayList<Value> infixTraverse(TreeItem item) {
        ArrayList<Value> list = new ArrayList<>();
        if (item.left != null) list = infixTraverse(item.left);
        if (list == null) list = new ArrayList<>();
        list.add(item.value);
        if (isSaveAll && item.valueList != null) list.addAll(item.valueList);
        if (item.right != null) list.addAll(infixTraverse(item.right));
        return list;
    }

    public ArrayList<Value> infixTraverse() {
        return infixTraverse(head);
    }

    @Override
    public Value Search(Key key) {
        int res = 1;
        TreeItem current = head;
        while (res != 0 && current != null) {
            res = keyComparator.compare(key, current.key);
            if (res == 0) return current.value;
            if (res < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    @Override
    public void Add(Key key, Value value) {
        if (itemCount == 0) {
            itemCount = 1;
            head = new TreeItem(key, value, null);
            return;
        }
        int res = 1;
        TreeItem current = head;
        while (res != 0) {
            res = keyComparator.compare(key, current.key);
            if (res == 0) {
                if (isSaveAll) current.addValue(value);
                else current.value = value;
            }
            if (res < 0) {
                if (current.left == null) {
                    TreeItem item = new TreeItem(key, value, current);
                    current.left = item;
                    res = 0;
                } else current = current.left;
            }
            if (res > 0) {
                if (current.right == null) {
                    TreeItem item = new TreeItem(key, value, current);
                    current.right = item;
                    res = 0;
                } else current = current.right;
            }
        }
        itemCount += 1;

    }

    @Override
    public void Delete(Key key) {
        int res = 1;
        TreeItem current = head;
        boolean isLeftChild = false;
        while (res != 0 && current != null) {
            res = keyComparator.compare(key, current.key);
            if (res == 0) {
                // Если в дереве только один этот элемент
                if (itemCount == 1) {
                    head = null;
                    itemCount = 0;
                    return;
                }
                TreeItem next = null;

                //Если оба ребёнка присутствуют
                if (current.left != null && current.right != null) {
                    TreeItem rightLeft;
                    TreeItem rightLeftRight;
                    TreeItem parentRightLeft;
                    TreeItem rightItem = current.right;

                    rightLeft = rightItem;
                    while (rightLeft.left != null) rightLeft = rightLeft.left;
                    parentRightLeft = rightLeft.parent;
                    rightLeftRight = rightLeft;
                    while (rightLeftRight.right != null) rightLeftRight = rightLeftRight.right;

                    parentRightLeft.left = null;
                    rightLeft.parent = current.parent;
                    if (isLeftChild) current.parent.left = rightLeft;
                    else current.parent.right = rightLeft;

                    rightItem.parent = rightLeftRight;
                    rightLeftRight.right = rightItem;
                    if (current.left != null) current.left.parent = rightLeft;
                    rightLeft.left = current.left;
                } else {
                    //Если обоих детей нет, то удаляем текущий узел и обнуляем ссылку на него у родительского узла
                    if (current.left == null && current.right == null) next = null;

                    //Если одного из детей нет
                    if (current.left == null && current.right != null) next = current.right;
                    if (current.left != null && current.right == null) next = current.left;

                    if (isLeftChild) current.parent.left = next;
                    else current.parent.right = next;
                }
                return;
            }
            isLeftChild = res < 0;
            if (isLeftChild) current = current.left;
            else current = current.right;
        }
    }

    private class TreeItem {
        Value value;
        Key key;
        ArrayList<Value> valueList;
        TreeItem left;
        TreeItem right;
        TreeItem parent;
        int depth;
        boolean isList;

        public TreeItem(Key key, Value value, TreeItem parent) {
            this.value = value;
            this.key = key;
            this.parent = parent;
            this.isList = false;
            if (parent != null) depth = parent.depth + 1;
            else depth = 1;
        }

        void addValue(Value newValue) {
            this.isList = true;
            this.valueList = new ArrayList<>();
            this.valueList.add(newValue);
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

}
