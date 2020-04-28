package dataStruct;

public class BinaryTree {

    // 节点值
    public int val;

    // 左孩子
    public BinaryTree leftChild;

    // 右孩子
    public BinaryTree rightChild;

    public BinaryTree(int val, BinaryTree leftChild, BinaryTree rightChild) {
        this.val = val;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // 根据一个数组，采取层次的方式构建一颗树
    public static BinaryTree createTree(int[] nums) {
        BinaryTree[] trees = new BinaryTree[nums.length];
        // 为每一个值创建一个树节点
        for (int i = 0, len = nums != null ? nums.length : 0; i < len; i++) {
            trees[i] = new BinaryTree(nums[i], null, null);
        }
        // 为每一个节点分配孩子节点
        for (int i = 0, len = trees.length; i < len; i++) {
            if (2 * i + 1 < len) {
                trees[i].leftChild = trees[2 * i + 1];
            }
            if (2 * i + 2 < len) {
                trees[i].rightChild = trees[2 * i + 2];
            }
        }
        // 根据树的状态返回根节点
        return trees.length > 0 ? trees[0] : null;
    }

    // 前序遍历
    public void preOrder(BinaryTree root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.leftChild);
        preOrder(root.rightChild);
    }

    // 中序遍历
    public void midOrder(BinaryTree root) {
        if (root == null) {
            return;
        }
        midOrder(root.leftChild);
        System.out.print(root.val + " ");
        midOrder(root.rightChild);
    }

    //后序遍历
    public void postOrder(BinaryTree root) {
        if (root == null) {
            return;
        }
        postOrder(root.leftChild);
        postOrder(root.rightChild);
        System.out.print(root.val + " ");
    }

    public static void main(String[] args) {
        BinaryTree tree = BinaryTree.createTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        System.out.println("\n前序遍历");
        tree.preOrder(tree);
        System.out.println("\n中序遍历");
        tree.midOrder(tree);
        System.out.println("\n后序遍历");
        tree.postOrder(tree);
    }

}
