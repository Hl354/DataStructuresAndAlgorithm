package dataStruct;

/**
 * 二叉排序树(在严蔚敏老师的教材中，不允许出现相同键值的节点)：一颗空树或者满足如下定义
 * 1.若左子树不空，则左子树上所有结点的值均小于答它版的根结点的值
 * 2.若右子树不空，则右子树上所有结点的值均大于它的根结点的值
 * 3.左、右子树也分别为二叉排序树
 */
public class BinarySortTree {

    // 节点值
    private int val;

    // 左孩子
    private BinarySortTree leftChild;

    // 右孩子
    private BinarySortTree rightChild;

    public BinarySortTree(int val, BinarySortTree leftChild, BinarySortTree rightChild) {
        this.val = val;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // 查找
    public boolean findNode(BinarySortTree tree, int target) {
        if (tree == null) return false;
        if (tree.val == target) return true;
        return findNode(tree.leftChild, target)
                || findNode(tree.rightChild, target);
    }

    // 增加节点
    public boolean addNode(BinarySortTree tree, int target) {
        // 节点是否已经存在,在的话不需要添加
        if (findNode(tree, target)) {
            return false;
        }
        return recursiveSearchAdd(tree, tree, target);
    }

    // 递归查找添加
    public boolean recursiveSearchAdd(BinarySortTree parent, BinarySortTree tree, int target) {
        // 是否为一颗空树
        if (tree == null) {
            tree = new BinarySortTree(target, null, null);
            if (parent != null) {
                if (parent.val > target) {
                    parent.leftChild = tree;
                } else {
                    parent.rightChild = tree;
                }
            }
            return true;
        }
        // 小于根节点
        if (tree.val > target) return recursiveSearchAdd(tree, tree.leftChild, target);
        // 大于根节点
        else return recursiveSearchAdd(tree, tree.rightChild, target);
    }

    // 删除节点
    public boolean deleteNode(BinarySortTree tree, int target) {
        // 节点是否已经存在，在的话才可以删除
        if (!findNode(tree, target)) {
            return false;
        }
        // 判断根节点是否是查找的元素
        recursiveSearchDelete(tree, tree, target);
        return true;
    }

    // 递归查找删除
    public void recursiveSearchDelete(BinarySortTree parent, BinarySortTree tree, int target) {
        if (tree.val == target) {
            keepTree(parent, tree);
        } else if (tree.val > target && tree.leftChild != null) {
            recursiveSearchDelete(tree, tree.leftChild, target);
        } else if (tree.rightChild != null) {
            recursiveSearchDelete(tree, tree.rightChild, target);
        }
    }

    // 保持树的性质
    public void keepTree(BinarySortTree parent, BinarySortTree deletedNode) {
        int parentVal = parent.val, childVal = deletedNode.val;
        if (deletedNode.leftChild == null && deletedNode.rightChild == null) {
            deletedNode = null;
        } else if (deletedNode.leftChild == null) {
            if (parentVal > childVal) parent.leftChild = deletedNode.rightChild;
            else parent.rightChild = deletedNode.rightChild;
        } else if (deletedNode.rightChild == null) {
            if (parentVal > childVal) parent.leftChild = deletedNode.leftChild;
            else parent.rightChild = deletedNode.leftChild;
        } else {
            BinarySortTree[] successorNodes = searchSuccessorNode(parent, deletedNode);
            deletedNode.val = successorNodes[1].val;
            keepTree(successorNodes[0], successorNodes[1]);
        }
    }

    // 寻找一个节点中序遍历的后继节点
    public BinarySortTree[] searchSuccessorNode(BinarySortTree parent, BinarySortTree tree) {
        if (tree.leftChild != null) {
            return searchSuccessorNode(tree, tree.leftChild);
        }
        return new BinarySortTree[]{parent, tree};
    }

    // 中序遍历
    public void midOrder(BinarySortTree root) {
        if (root == null) {
            return;
        }
        midOrder(root.leftChild);
        System.out.print(root.val + " ");
        midOrder(root.rightChild);
    }

    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree(5, null, null);
        System.out.println(tree.findNode(tree, 5));
        System.out.println(tree.findNode(tree, 3));
        System.out.println("增加节点");
        tree.addNode(tree, 3);
        tree.addNode(tree, 6);
        tree.addNode(tree, 2);
        tree.addNode(tree, 1);
        tree.addNode(tree, 8);
        tree.midOrder(tree);
        System.out.println("删除节点");
        tree.deleteNode(tree, 2);
        tree.midOrder(tree);
    }
}
