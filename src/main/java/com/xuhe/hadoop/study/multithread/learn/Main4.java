package com.xuhe.hadoop.study.multithread.learn;

public class Main4 {

    public static void main(String[] args) {
        Thread t1 = new Thread4("A");
        Thread t2 = new Thread4("B");
        Thread t3 = new Thread4("C");

    }

}

class Thread4 extends Thread {

    private String name;

    public Thread4(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程" + name + "");
        }
    }

}
