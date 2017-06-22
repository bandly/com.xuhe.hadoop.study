package com.xuhe.hadoop.study.tree;

public class BiTreeArray {

    int   maxSize   = 16;
    int[] treeData  = new int[maxSize];
    int[] rightNode = new int[maxSize];
    int[] leftNode  = new int[maxSize];

    public BiTreeArray() {
        int i;//循环变量
        for (i = 0; i < maxSize; i++) {
            treeData[i] = 0;
            rightNode[i] = -1;
            leftNode[i] = -1;
        }
    }

    //建立二叉树
    public void create(int data) {
        int i;
        int level = 0;//树的层数
        int position = 0;
        for (i = 0; i <= maxSize; i++) {
            if (treeData[i] == 0) {
                treeData[i] = data;
                break;
            }
        }
        if (i == 0) {
            return;
        }
        while (true) {//寻找结点位置
            //判断左子树还是右子树
            if (data > treeData[level]) {
                //右子树是否有下一层
                if (rightNode[level] != -1) {
                    level = rightNode[level];
                } else {
                    position = -1;//设置为右子树
                    break;
                }
            } else {
                //左子树是否有下一层
                if (leftNode[level] != -1) {
                    level = leftNode[level];
                } else {
                    position = 1;//设置为左子树
                    break;
                }
            }
        }

        if (position == 1) {//建立结点的左右连接
            leftNode[level] = i;
        } else {
            rightNode[level] = i;//连接右子树连接
        }
    }

    //打印所有二叉树结点值
    public void printAll() {
        System.out.println("二叉树结点值");
        System.out.println(" [lchild] [data] [rchild]");
        for (int i = 0; i < maxSize; i++) {
            if (treeData[i] != 0) {
                System.out.print("Node" + i);
                System.out.print("   [" + leftNode[i] + "]");
                System.out.print("   [" + treeData[i] + "]");
                System.out.println("   [" + rightNode[i] + "]");
            }
        }
    }

}
