package pri.weiqiang.tryit.lib.treetest;

class MaxDepth {
    public static void main(String[] args) {

        TreeNode t2 = new TreeNode(9);
        TreeNode t4 = new TreeNode(15);
        TreeNode t5 = new TreeNode(7);
        TreeNode t3 = new TreeNode(20, t4, t5);
        TreeNode t1 = new TreeNode(3, t2, t3);
        System.out.println("maxDepth(t1):" + maxDepth(t1));
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }else {
            int leftDepth = maxDepth(root.left);
            int rightDepth  = maxDepth(root.right);
            return Math.max(leftDepth,rightDepth)+1;
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {

        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
