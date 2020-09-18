package base.collection;

import model.ItemObject;

import java.util.*;

public class Example {
    long start;
    Random rand;
    private int itemCount = 100000;


    public Example(int itemCount) {
        this.itemCount = itemCount;
        rand = new Random();
        System.out.println("item count = " + itemCount);
    }

    private void StartTimer() {
        start = System.currentTimeMillis();
    }

    private void StopTimer(String format) {
        System.out.print(String.format(format, System.currentTimeMillis() - start));
        StartTimer();
    }




    private void initCollection(Collection<ItemObject> list) {
        list.clear();
        for (int i = 0; i < itemCount; i++) {
            ItemObject item = new ItemObject();
            list.add(item);
        }
    }


    private void runForPriorityQueue(PriorityQueue<ItemObject> queue) {
        StartTimer();
        start = System.currentTimeMillis();
        initCollection(queue);
        StopTimer(queue.getClass().getName() + ": init - %d ms; ");
        for (int i = 0; i < itemCount; i++) {
            queue.poll();
        }
        StopTimer("poll all - %s ms; \n");
    }


    private void runForDeQueue(ArrayDeque<ItemObject> queue) {
        StartTimer();
        start = System.currentTimeMillis();
        initCollection(queue);
        StopTimer(queue.getClass().getName() + ": init - %d ms; ");
        for (int i = 0; i < itemCount; i++) {
            queue.poll();
        }
        StopTimer("poll all - %s ms; ");
        for (int i = 0; i < itemCount; i++) {
            queue.addFirst(new ItemObject());
        }
        StopTimer(String.format("%s: init_addFirst - %%d ms; ", queue.getClass().getName()));
        for (int i = 0; i < itemCount; i++) {
            queue.pollFirst();
        }
        StopTimer("poll_all_first - %s ms; \n");
    }


    private void runForList(List<ItemObject> list) {

        StartTimer();
        start = System.currentTimeMillis();
        initCollection(list);
        StopTimer(String.format("%s: init - %%d ms; ", list.getClass().getName()));

        Collections.sort(list);
        StopTimer("sort - %s ms; ");
        for (int i = 0; i < itemCount; i++) {
            list.remove(0);
        }
        StopTimer("remove all first - %s ms; ");


        initCollection(list);
        StartTimer();
        for (int i = 0; i < itemCount; i++) {
            list.remove(itemCount - i - 1);
        }
        StopTimer("remove all last - %s ms;  ");

        initCollection(list);
        StartTimer();
        for (int i = 0; i < itemCount; i++) {
            ItemObject item = list.get(rand.nextInt(itemCount));
            if (item.getName().equals("none")) System.out.println(item);
        }
        StopTimer("get - %s ms;");
        for (int i = 0; i < itemCount/10; i++) {
            list.contains(list.get(rand.nextInt(itemCount)));
        }
        StopTimer("contains10pct - %s ms;\n");
        list.clear();

    }

    public void run() {
        runForList(new ArrayList<>());
//        runForList(new LinkedList<>());
        runForList(new Vector<>());
        runForList(new Stack<>());
        runForDeQueue(new ArrayDeque<>());
        runForPriorityQueue(new PriorityQueue<>());

        ArrayList<ItemObject> list = new ArrayList<>();

    }


}
