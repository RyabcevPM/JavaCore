package base.sort;

import java.util.ArrayList;
import java.util.Comparator;

interface ISearchTree<Key, Value> {
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

    private int getMaxDepth(TreeItem item) {
        if (item == null) return 0;
        if (item.isLeaf()) return item.depth;
        return Math.max(getMaxDepth(item.left), getMaxDepth(item.right));
    }

    public int getMaxDepth() {
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

    private TreeItem getMinValue(TreeItem item) {
        TreeItem res = item;
        while (res.left != null) res = res.left;
        return res;
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
