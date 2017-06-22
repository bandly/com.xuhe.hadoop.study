package com.xuhe.hadoop.study.multithread.learn;

public class Main3 {

    public static void main(String[] args) {
        ThreadYieId yt1 = new ThreadYieId("张三");
        ThreadYieId yt2 = new ThreadYieId("李四");
        yt1.start();
        yt2.start();

    }
}

class ThreadYieId extends Thread {
    public ThreadYieId(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 50; i++) {
            System.out.println(" " + this.getName() + "---------");
            if (i == 30) {
                this.yield();
            }
        }
    }
}