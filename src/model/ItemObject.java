package model;

import java.util.Random;


public class ItemObject implements Comparable<ItemObject> {
    static private Random rand = new Random();
    String name;
    Long price;

    public ItemObject() {
//        this.name = "User#" + String.valueOf(rand.nextInt(10));
//        this.price = Long.valueOf(rand.nextInt(10));
        this.name = "User#" + String.valueOf(rand.nextLong());
        this.price = rand.nextLong();
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