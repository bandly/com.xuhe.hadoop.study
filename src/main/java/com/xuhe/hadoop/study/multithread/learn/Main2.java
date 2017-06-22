package com.xuhe.hadoop.study.multithread.learn;

class Thread2 implements Runnable {

    private int count = 5;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "运行" + count--);
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

public class Main2 {
    public static void main(String[] args) {
        Thread2 my = new Thread2();
        new Thread(my, "C").start();//同一个mr 但是在Thread中就不可以，如果用同一个实例化对象mt 就会出现异常
        new Thread(my, "D").start();
        new Thread(my, "E").start();
    }

}
