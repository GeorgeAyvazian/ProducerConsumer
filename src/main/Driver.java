package main;

import consumer.Consumer;
import producer.Producer;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private static final int CAPACITY = 10;
    public final List<String> strings = new ArrayList<>(CAPACITY);
    public final Object lock = new Object();

    public static final Driver driver = new Driver();
    public static final Producer producer = new Producer();
    public static final Consumer consumer = new Consumer();

    public static void main(String... args) {
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}