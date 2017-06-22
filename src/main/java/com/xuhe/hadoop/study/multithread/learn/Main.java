package com.xuhe.hadoop.study.multithread.learn;

class Thread1 extends Thread {
    private String name;
    private int    count = 5;

    public Thread1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程" + name + "运行：" + count--);
            try {
                sleep((int) Math.random() * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

public class Main {

    public static void main(String args[]) {
        System.out.println(Thread.currentThread().getName() + " 主线程开始运行");
        Thread t1 = new Thread1("A");
        Thread t2 = new Thread1("B");
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 主线程结束运行");

    }
}
