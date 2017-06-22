package com.xuhe.hadoop.study.tree;

import java.util.Scanner;

public class BitTree {
    public static void main(String[] args) {

        int i;//循环变量
        int index = 1;//数组下标变量
        int data; //读取输入值的临时变量
        BiTreeArray biTree = new BiTreeArray(); //声明二叉树数组
        System.out.println("请输入二叉树结点值（输入0 退出0）");
        Scanner s = new Scanner(System.in);
        //System.out.println("Data" + index + " : ");
        while (true) {
            System.out.println("Data " + index + " : ");
            data = s.nextInt();
            if (data == 0) {
                break;
            }
            biTree.create(data);//建立二叉树
            index++;
            biTree.printAll();//输出二叉树的内容
        }
        biTree.printAll();//输出二叉树的内容
    }
}
