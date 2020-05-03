package dataStruct;

/**
 * 平衡二叉树(ALV树)：再二叉排序树的基础上，保证所有的左子树和右子树的高度差不超过一
 * 并且左子树和右子树同时又是平衡二叉树
 */
public class BalanceBinaryTree {

    private int val;
    private BalanceBinaryTree parent;
    private BalanceBinaryTree lChild;
    private BalanceBinaryTree rChild;

    public BalanceBinaryTree(int val) {
        this.val = val;
        this.parent = null;
        this.lChild = null;
        this.rChild = null;
    }

    public BalanceBinaryTree(int val, BalanceBinaryTree parent) {
        this.val = val;
        this.parent = parent;
        this.lChild = null;
        this.rChild = null;
    }

    // 插入节点
    public void insertNewNode(BalanceBinaryTree root, int val) {
        if (root == null) return;
        if (root.val > val && root.lChild == null) {
            root.lChild = new BalanceBinaryTree(val, root);
            adjustNode(root);
        } else if (root.val < val && root.rChild == null) {
            root.rChild = new BalanceBinaryTree(val, root);
            adjustNode(root);
        } else if (root.val > val) {
            insertNewNode(root.lChild, val);
        } else if (root.val < val) {
            insertNewNode(root.rChild, val);
        }
    }

    // 调整节点
    public void adjustNode(BalanceBinaryTree root) {
        if (root == null) return;
        int balanceFactory = getBalanceFactor(root);
        if (Math.abs(balanceFactory) <= 1) {
            adjustNode(root.parent);
        } else {
            if (balanceFactory > 1) {
                int childBalanceFactory = getBalanceFactor(root.lChild);
                if (childBalanceFactory > 0) {
                    swapNodeVal(root, root.lChild);
                    BalanceBinaryTree rightSon = root.lChild;
                    root.lChild = root.lChild.lChild;
                    root.lChild.parent = root;
                    swapChildNode(rightSon);
                    rightSon.rChild = root.rChild;
                    if (root.rChild != null) root.rChild.parent = rightSon;
                    root.rChild = rightSon;
                } else {
                    swapNodeVal(root, root.lChild.rChild);
                    BalanceBinaryTree grandLeftSon = root.lChild.rChild;
                    root.lChild.rChild = grandLeftSon.lChild;
                    if (grandLeftSon.lChild != null) {
                        root.lChild.rChild.parent = root.lChild;
                        grandLeftSon.lChild = null;
                    } else {
                        grandLeftSon.lChild = grandLeftSon.rChild;
                    }
                    grandLeftSon.rChild = root.rChild;
                    if (root.rChild != null) root.rChild.parent = grandLeftSon;
                    root.rChild = grandLeftSon;
                }
            } else {
                int childBalanceFactory = getBalanceFactor(root.rChild);
                if (childBalanceFactory < 0) {
                    swapNodeVal(root, root.rChild);
                    BalanceBinaryTree leftSon = root.rChild;
                    root.rChild = root.rChild.rChild;
                    root.rChild.parent = root;
                    swapChildNode(leftSon);
                    leftSon.lChild = root.lChild;
                    if (root.lChild != null) root.lChild.parent = leftSon;
                    root.lChild = leftSon;
                } else {
                    swapNodeVal(root, root.rChild.lChild);
                    BalanceBinaryTree grandRightSon = root.rChild.lChild;
                    root.rChild.lChild = grandRightSon.rChild;
                    if (grandRightSon.rChild != null) {
                        root.rChild.lChild.parent = root.rChild;
                        grandRightSon.rChild = null;
                    } else {
                        grandRightSon.rChild = grandRightSon.lChild;
                    }
                    grandRightSon.lChild = root.lChild;
                    if (root.lChild != null) root.lChild.parent = grandRightSon;
                    grandRightSon.parent = root;
                    root.lChild = grandRightSon;
                }
            }
            return;
        }
    }

    // 获取某个节点的平衡因子
    public int getBalanceFactor(BalanceBinaryTree root) {
        if (root == null) return 0;
        return calculateDepth(root.lChild) - calculateDepth(root.rChild);
    }

    // 计算一个节点的深度
    public int calculateDepth(BalanceBinaryTree root) {
        if (root == null) return 0;
        return 1 + Math.max(calculateDepth(root.lChild), calculateDepth(root.rChild));
    }

    // 交换两个节点的值
    public void swapNodeVal(BalanceBinaryTree source, BalanceBinaryTree target) {
        if (source != null && target != null) {
            int tempVal = source.val;
            source.val = target.val;
            target.val = tempVal;
        }
    }

    // 交换节点的左右孩子
    public void swapChildNode(BalanceBinaryTree root) {
        if (root != null) {
            BalanceBinaryTree lChild = root.lChild;
            root.lChild = root.rChild;
            root.rChild = lChild;
        }
    }

    // 中序遍历
    public void midOrder(BalanceBinaryTree root) {
        if (root == null) return;
        midOrder(root.lChild);
        String parentVal = root.parent != null ? String.valueOf(root.parent.val) : "空";
        System.out.println("节点值:" + root.val + ", 父节点值:" + parentVal);
        midOrder(root.rChild);
    }

    public static void main(String[] args) {
        BalanceBinaryTree root = new BalanceBinaryTree(20);
        root.insertNewNode(root, 18);
        root.insertNewNode(root, 50);
        root.insertNewNode(root, 45);
        root.insertNewNode(root, 60);
        root.insertNewNode(root, 65);
        root.midOrder(root);
    }

}
