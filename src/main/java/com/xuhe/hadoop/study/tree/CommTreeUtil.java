package com.xuhe.hadoop.study.tree;

public class CommTreeUtil {

    //求二叉树的最大深度 二叉树的深度为根节点到最远叶子节点的距离。
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = maxDepth(root.getLeft());
        int right = maxDepth(root.getRight());
        return Math.max(left, right) + 1;
    }

    //求二叉树的最小深度
    /*  public int minDepth(TreeNode root) {
    
    }*/
}
