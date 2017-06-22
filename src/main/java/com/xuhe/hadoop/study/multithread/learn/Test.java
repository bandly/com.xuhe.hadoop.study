package com.xuhe.hadoop.study.multithread.learn;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<String>> callables = new HashSet<>();

        callables.add(new Callable<String>() {
            @Override
            public String call() {
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            @Override
            public String call() {
                return "Task 3";
            }
        });
        callables.add(new Callable<String>() {
            @Override
            public String call() {
                return "Task 4";
            }
        });
        try {
            String result = executorService.invokeAny(callables);
            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("主线程结束");
    }
}
