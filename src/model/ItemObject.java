package model;

import java.util.Comparator;
import java.util.Random;


public class ItemObject implements Comparable<ItemObject> {
    static private Random rand = new Random();
    static private int bound = 0;
    String name;
    Long price;

    public ItemObject() {
        if (bound <= 0) {
            this.name = "User#" + String.valueOf(rand.nextLong());
            this.price = rand.nextLong();
        } else {
            this.name = "User#" + String.valueOf(rand.nextInt(bound));
            this.price = Long.valueOf(rand.nextInt(bound));
        }
    }

    public static void setBound(int bound) {
        ItemObject.bound = bound;
    }

    public static Comparator<ItemObject> getComparator() {
        return new Comparator<ItemObject>() {
            public int compare(ItemObject o1, ItemObject o2) {
                return o1.compareTo(o2);
            }
        };
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemObject)) return false;
        return (this.compareTo((ItemObject) obj) == 0);
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public int compareTo(ItemObject o) {
        if (Long.compare(price, o.price) != 0) return price.compareTo(o.price);
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("{name=%s; price=%d}", name, price);
    }
}