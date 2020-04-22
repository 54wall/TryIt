package pri.weiqiang.java.algorithm;

import java.util.ArrayList;
import java.util.Stack;

/*
Java二叉树构建及深度优先遍历和广度优先遍历
https://blog.csdn.net/qq_38442065/article/details/81667819
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        /*
         *                  1
         *                 /  \
         *               2     3
         *              /  \   /\
         *             4  5   6  7
         */
        int[] arr = new int[]{
                1, 2, 3, 4, 5, 6, 7
        };
        /*
         *                  13
         *                 /  \
         *               65    5
         *              /  \    \
         *             97  25   37
         *            /    /\   /
         *           22   4 28 32
         */
//        int[] arr={13,65,5,97,25,0,37,22,0,4,28,0,0,32,0};//非完全二叉树，拿0补位，后续再在判断值是否为0，本demo没有进行
        //可参考java-二叉树广度优先实现、深度优先之前序实现(非递归) https://blog.csdn.net/liuxiao723846/article/details/42004003

        TreeNode root = getBinaryTree(arr, 0);
        getDFS(root);
        getBFS(root);

    }

    //获取二叉树
    private static TreeNode getBinaryTree(int[] arr, int index) {
        // TODO Auto-generated method stub
        TreeNode node = null;
        if (index < arr.length) {
            int value = arr[index];
            node = new TreeNode(value);
            node.left = getBinaryTree(arr, index * 2 + 1);
            node.right = getBinaryTree(arr, index * 2 + 2);
            return node;
        }
        return node;
    }

    //深度优先遍历
    private static void getDFS(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode temp = stack.peek();
            System.out.print(temp.value + "\t");
            stack.pop();
            //这里利用了堆的--先进后出的特性，所以右节点要在左节点前入堆，这里如果不好理解建议在Debug下，查看stack的变化内容就比较容易理解了
            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }
        }
        System.out.println("深度优先遍历结束");
    }

    //广度优先遍历
    private static void getBFS(TreeNode root) {
        // TODO Auto-generated method stub
        if (root == null) {
            return;
        }
        ArrayList<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (queue.size() > 0) {
            TreeNode temp = queue.get(0);
            queue.remove(0);
            System.out.print(temp.value + "\t");
            //这里利用了队列的--先进先出的特性，所以左节点要在右节点前入堆，这里如果不好理解建议在Debug下，查看stack的变化内容就比较容易理解了
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        System.out.println("广度优先遍历结束");
    }

    //二叉树结构
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            // TODO Auto-generated constructor stub
            this.value = value;
        }
    }

}
