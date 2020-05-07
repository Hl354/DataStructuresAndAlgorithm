package dataStruct;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;

/**
 * 2-3树
 */
public class TwoThreeTree {

    public Node root;

    class Node {
        public Integer minValue;
        public Integer midValue;
        public Integer maxValue;
        public Node parent;
        public Node lChild;
        public Node mChild;
        public Node rChild;
        public Node tChild;

        public Node(Integer minValue) {
            this.minValue = minValue;
        }

        public Node(Integer minValue, Node parent) {
            this.minValue = minValue;
            this.parent = parent;
        }
    }

    public boolean search(Node node, int val) {
        if (node == null) return false;
        if (val < node.minValue) return search(node.lChild, val);
        if (val > node.minValue && val < node.maxValue) return search(node.mChild, val);
        if (val > node.maxValue) return search(node.rChild, val);
        return true;
    }

    public void insert(int val) {
        if (root == null) {
            root = new Node(val);
        } else {
            insert(root, val);
        }
    }

    private void insert(Node node, int val) {
        if (node == null) return;
        if (node.lChild != null) {
            if (val < node.minValue) {
                insert(node.lChild, val);
            } else {
                if (node.maxValue != null && val < node.maxValue) {
                    insert(node.mChild, val);
                } else {
                    insert(node.rChild, val);
                }
            }
        } else {
            if (node.maxValue == null) {
                setNodeVal(node, val);
            } else {
                node.minValue = getMinVal(node.minValue, node.maxValue, val);
                node.midValue = getMidVal(node.minValue, node.maxValue, val);
                node.maxValue = getMaxVal(node.minValue, node.maxValue, val);
                nodeSplit(node, val);
            }
        }
    }

    private void nodeSplit(Node node, int val) {
        if (node == null) return;
        if (node.parent == null) {
            Node lChild = new Node(node.minValue, node);
            lChild.lChild = node.lChild;
            if (lChild.lChild != null) lChild.lChild.parent = lChild;
            lChild.rChild = node.tChild;
            if (lChild.rChild != null) lChild.rChild.parent = lChild;
            Node rChild = new Node(node.maxValue, node);
            rChild.lChild = node.mChild;
            if (rChild.lChild != null) rChild.lChild.parent = rChild;
            rChild.rChild = node.rChild;
            if (rChild.rChild != null) rChild.rChild.parent = rChild;
            node.minValue = node.midValue;
            node.midValue = null;
            node.maxValue = null;
            node.lChild = lChild;
            node.rChild = rChild;
            node.tChild = null;
            node.mChild = null;
        } else if (node.parent.maxValue == null) {
            if (val < node.parent.minValue) {
                Node mChild = new Node(node.maxValue, node.parent);
                node.parent.mChild = mChild;
                mChild.lChild = node.mChild;
                mChild.rChild = node.rChild;
                node.rChild = node.tChild;
            } else {
                Node mChild = new Node(node.minValue, node.parent);
                node.parent.mChild = mChild;
                mChild.lChild = node.lChild;
                mChild.rChild = node.tChild;
                node.minValue = node.maxValue;
                node.lChild = node.mChild;
            }
            setNodeVal(node.parent, node.midValue);
            node.midValue = null;
            node.maxValue = null;
        } else {
            int midVal = getMidVal(node.minValue, node.maxValue, val);
            if (val < node.parent.minValue) {
                Node tChild = new Node(getMaxVal(node.minValue, node.maxValue, val), node.parent);
                tChild.lChild = node.mChild;
                tChild.rChild = node.rChild;
                node.parent.tChild = tChild;
                node.rChild = node.tChild;
            } else if (val > node.parent.maxValue) {
                Node tChild = new Node(getMinVal(node.minValue, node.maxValue, val), node.parent);
                node.parent.tChild = node.parent.mChild;
                node.parent.mChild = tChild;
                tChild.lChild = node.lChild;
                tChild.rChild = node.tChild;
                node.lChild = node.tChild;
                node.minValue = node.maxValue;
            } else {
                Node tChild = new Node(getMinVal(node.minValue, node.maxValue, val), node.parent);
                tChild.lChild = node.lChild;
                tChild.rChild = node.tChild;
                node.lChild = node.mChild;
                node.parent.tChild = tChild;
            }
            node.midValue = null;
            node.maxValue = null;
            node.tChild = null;
            node.mChild = null;
            node.parent.minValue = getMinVal(node.parent.minValue, node.parent.maxValue, midVal);
            node.parent.midValue = getMidVal(node.parent.minValue, node.parent.maxValue, midVal);
            node.parent.maxValue = getMaxVal(node.parent.minValue, node.parent.maxValue, midVal);
            nodeSplit(node.parent, val);
        }
    }

    private void setNodeVal(Node node, int val) {
        if (node == null) return;
        node.maxValue = val > node.minValue ? val : node.minValue;
        node.minValue = val < node.minValue ? val : node.minValue;
    }

    private int getMinVal(int val1, int val2, int val3) {
        return Math.min(Math.min(val1, val2), val3);
    }

    private int getMidVal(int val1, int val2, int val3) {
        return Math.min(Math.max(val1, val2), val3);
    }

    private int getMaxVal(int val1, int val2, int val3) {
        return Math.max(Math.max(val1, val2), val3);
    }

    public void delete(int val) {
        delete(root, val);
    }

    public void delete(Node node, int val) {
        if (node == null) return;
        if (node.minValue == val || (node.maxValue != null && node.maxValue == val)) {
            deleteKey(node, val);
        } else if (val < node.minValue) {
            delete(node.lChild, val);
        } else if (node.maxValue != null && val < node.maxValue) {
            delete(node.mChild, val);
        } else {
            delete(node.rChild, val);
        }
    }

    public void deleteKey(Node node, int val) {
        if (node == null) return;
        if (node.lChild != null) {
            Node deletedNode;
            if (node.maxValue != null) {
                if (node.minValue == val) {
                    deletedNode = getSuccessorNode(node.mChild);
                    swapNodeVal(node, deletedNode, true);
                } else {
                    deletedNode = getSuccessorNode(node.rChild);
                    swapNodeVal(node, deletedNode, false);
                }
            } else {
                deletedNode = getSuccessorNode(node.rChild);
                swapNodeVal(node, deletedNode, true);
            }
            deleteKey(deletedNode, val);
        } else {
            if (node.maxValue != null) {
                node.minValue = node.maxValue;
                node.maxValue = null;
            } else {
                if (node.parent.maxValue == null) {
                    if (val < node.parent.minValue) {
                        if (node.parent.rChild.maxValue != null) {
                            node.minValue = node.parent.minValue;
                            node.parent.minValue = node.parent.rChild.minValue;
                            node.parent.rChild.minValue = node.parent.rChild.maxValue;
                            node.parent.rChild.maxValue = null;
                        } else {
                            // 需要向爷爷的孩子借值或者需要缩小高度
                            if (node.parent.parent == null) {
                                node.parent.maxValue = node.parent.rChild.minValue;
                                node.parent.lChild = null;
                                node.parent.rChild = null;
                            } else if (node.parent.parent.maxValue != null) {
                                if (node.parent.parent.mChild.maxValue == null) {
                                    node.minValue = node.parent.minValue;
                                    node.maxValue = node.parent.rChild.minValue;
                                    node.parent.minValue = node.parent.parent.minValue;
                                    node.parent.maxValue = node.parent.parent.mChild.minValue;
                                    node.parent.mChild = node.parent.parent.mChild.lChild;
                                    node.parent.mChild.parent = node.parent;
                                    node.parent.rChild = node.parent.parent.mChild.rChild;
                                    node.parent.rChild.parent = node.parent;
                                    node.parent.parent.mChild = null;
                                    node.parent.parent.minValue = node.parent.parent.maxValue;
                                    node.parent.parent.maxValue = null;
                                } else {
                                    node.minValue = node.parent.minValue;
                                    node.maxValue = node.parent.rChild.minValue;
                                    node.parent.rChild = node.parent.parent.mChild.lChild;
                                    node.parent.rChild.parent = node.parent;
                                    node.parent.parent.mChild.lChild = node.parent.parent.mChild.mChild;
                                    node.parent.parent.mChild.mChild = null;
                                    node.parent.parent.minValue = node.parent.parent.mChild.minValue;
                                    node.parent.parent.mChild.minValue = node.parent.parent.mChild.maxValue;
                                    node.parent.parent.mChild.maxValue = null;
                                    node.parent.minValue = node.parent.parent.minValue;
                                }
                            } else {
                                if (node.parent.parent.rChild.maxValue == null 
                                        && node.parent.parent.rChild.lChild.maxValue == null
                                        && node.parent.parent.rChild.rChild.maxValue == null) {
                                    node.parent.parent.maxValue = node.parent.parent.rChild.minValue;
                                    node.parent.maxValue = node.parent.rChild.minValue;
                                    node.parent.lChild = null;
                                    node.parent.rChild = null;
                                    node.parent.parent.maxValue = node.parent.parent.rChild.minValue;
                                    node.parent.parent.rChild.minValue = node.parent.parent.rChild.lChild.minValue;
                                    node.parent.parent.rChild.maxValue = node.parent.parent.rChild.lChild.maxValue;
                                    node.parent.parent.lChild.lChild = null;
                                    node.parent.parent.lChild.rChild = null;
                                    node.parent.parent.rChild.lChild = null;
                                    node.parent.parent.rChild.rChild = null;
                                } else {
                                    if (node.parent.parent.rChild.maxValue == null) {
                                        node.minValue = node.parent.minValue;
                                        node.maxValue = node.parent.rChild.minValue;
                                        node.parent.minValue = node.parent.parent.minValue;
                                        node.parent.maxValue = node.parent.parent.rChild.minValue;
                                        node.parent.mChild = node.parent.parent.rChild.lChild;
                                        node.parent.mChild.parent = node.parent;
                                        node.parent.rChild = node.parent.parent.rChild.rChild;
                                        node.parent.rChild.parent = node.parent;
                                        node.parent.parent.rChild = null;
                                        node.parent.parent.minValue = node.parent.parent.maxValue;
                                        node.parent.parent.maxValue = null;
                                    } else {
                                        node.minValue = node.parent.minValue;
                                        node.maxValue = node.parent.rChild.minValue;
                                        node.parent.rChild = node.parent.parent.rChild.lChild;
                                        node.parent.rChild.parent = node.parent;
                                        node.parent.parent.rChild.lChild = node.parent.parent.rChild.mChild;
                                        node.parent.parent.rChild.mChild = null;
                                        node.parent.parent.minValue = node.parent.parent.rChild.minValue;
                                        node.parent.parent.rChild.minValue = node.parent.parent.rChild.maxValue;
                                        node.parent.parent.rChild.maxValue = null;
                                        node.parent.minValue = node.parent.parent.minValue;
                                    }
                                }
                            }
                        }
                    } else {
                        if (node.parent.lChild.maxValue != null) {
                            node.minValue = node.parent.minValue;
                            node.parent.minValue = node.parent.lChild.maxValue;
                            node.parent.lChild.maxValue = null;
                        } else {
                            if (node.parent.parent == null) {
                                node.parent.maxValue = node.parent.minValue;
                                node.parent.minValue = node.minValue;
                                node.parent.lChild = null;
                                node.parent.rChild = null;
                            } else if (node.parent.parent.maxValue != null) {
                                if (node.parent.parent.mChild.maxValue == null) {
                                    node.minValue = node.parent.minValue;
                                    node.maxValue = node.parent.rChild.minValue;
                                    node.parent.minValue = node.parent.parent.minValue;
                                    node.parent.maxValue = node.parent.parent.mChild.minValue;
                                    node.parent.mChild = node.parent.parent.mChild.lChild;
                                    node.parent.mChild.parent = node.parent;
                                    node.parent.rChild = node.parent.parent.mChild.rChild;
                                    node.parent.rChild.parent = node.parent;
                                    node.parent.parent.mChild = null;
                                    node.parent.parent.minValue = node.parent.parent.maxValue;
                                    node.parent.parent.maxValue = null;
                                } else {
                                    node.minValue = node.parent.minValue;
                                    node.maxValue = node.parent.rChild.minValue;
                                    node.parent.rChild = node.parent.parent.mChild.lChild;
                                    node.parent.rChild.parent = node.parent;
                                    node.parent.parent.mChild.lChild = node.parent.parent.mChild.mChild;
                                    node.parent.parent.mChild.mChild = null;
                                    node.parent.parent.minValue = node.parent.parent.mChild.minValue;
                                    node.parent.parent.mChild.minValue = node.parent.parent.mChild.maxValue;
                                    node.parent.parent.mChild.maxValue = null;
                                    node.parent.minValue = node.parent.parent.minValue;
                                }
                            } else {
                                if (node.parent.parent.lChild.maxValue == null
                                        && node.parent.parent.lChild.lChild.maxValue == null
                                        && node.parent.parent.lChild.rChild.maxValue == null) {
                                    node.parent.parent.maxValue = node.parent.parent.rChild.minValue;
                                    node.parent.maxValue = node.parent.rChild.minValue;
                                    node.parent.lChild = null;
                                    node.parent.rChild = null;
                                    node.parent.parent.maxValue = node.parent.parent.rChild.minValue;
                                    node.parent.parent.rChild.minValue = node.parent.parent.rChild.lChild.minValue;
                                    node.parent.parent.rChild.maxValue = node.parent.parent.rChild.lChild.maxValue;
                                    node.parent.parent.lChild.lChild = null;
                                    node.parent.parent.lChild.rChild = null;
                                    node.parent.parent.rChild.lChild = null;
                                    node.parent.parent.rChild.rChild = null;
                                } else {
                                    if (node.parent.parent.rChild.maxValue == null) {
                                        node.minValue = node.parent.minValue;
                                        node.maxValue = node.parent.rChild.minValue;
                                        node.parent.minValue = node.parent.parent.minValue;
                                        node.parent.maxValue = node.parent.parent.rChild.minValue;
                                        node.parent.mChild = node.parent.parent.rChild.lChild;
                                        node.parent.mChild.parent = node.parent;
                                        node.parent.rChild = node.parent.parent.rChild.rChild;
                                        node.parent.rChild.parent = node.parent;
                                        node.parent.parent.rChild = null;
                                        node.parent.parent.minValue = node.parent.parent.maxValue;
                                        node.parent.parent.maxValue = null;
                                    } else {
                                        node.minValue = node.parent.minValue;
                                        node.maxValue = node.parent.rChild.minValue;
                                        node.parent.rChild = node.parent.parent.rChild.lChild;
                                        node.parent.rChild.parent = node.parent;
                                        node.parent.parent.rChild.lChild = node.parent.parent.rChild.mChild;
                                        node.parent.parent.rChild.mChild = null;
                                        node.parent.parent.minValue = node.parent.parent.rChild.minValue;
                                        node.parent.parent.rChild.minValue = node.parent.parent.rChild.maxValue;
                                        node.parent.parent.rChild.maxValue = null;
                                        node.parent.minValue = node.parent.parent.minValue;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (val < node.parent.minValue) {
                        if (node.parent.mChild.maxValue == null) {
                            node.minValue = node.parent.minValue;
                            node.maxValue = node.parent.mChild.minValue;
                            node.parent.minValue = node.parent.maxValue;
                            node.parent.maxValue = null;
                            node.parent.mChild = null;
                        } else {
                            node.minValue = node.parent.minValue;
                            node.parent.minValue = node.parent.mChild.minValue;
                            node.parent.mChild.minValue = node.parent.mChild.maxValue;
                            node.parent.mChild.maxValue = null;
                        }
                    } else if (val > node.parent.maxValue) {
                        if (node.parent.rChild.maxValue == null) {
                            node.parent.mChild = null;
                            node.parent.rChild.maxValue = node.parent.rChild.minValue;
                            node.parent.rChild.minValue = node.parent.maxValue;
                            node.parent.maxValue = null;
                        } else {
                            node.minValue = node.parent.maxValue;
                            node.parent.maxValue = node.parent.rChild.minValue;
                            node.parent.rChild.minValue = node.parent.rChild.maxValue;
                            node.parent.rChild.maxValue = null;
                        }
                    } else {
                        if (node.parent.mChild.maxValue == null) {
                            node.parent.rChild.minValue = node.parent.mChild.minValue;
                            node.parent.rChild.maxValue = node.parent.maxValue;
                            node.parent.maxValue = null;
                            node.parent.mChild = null;
                        } else {
                            node.parent.rChild.minValue = node.parent.maxValue;
                            node.parent.maxValue = node.parent.mChild.maxValue;
                            node.parent.mChild.maxValue = null;
                        }
                    }
                }
            }
        }
    }

    private Node getSuccessorNode(Node node) {
        if (node.lChild == null) {
            return node;
        } else {
            return getSuccessorNode(node.lChild);
        }
    }

    private void swapNodeVal(Node node1, Node node2, boolean isLeft) {
        int temp = 0;
        if (isLeft) {
            temp = node1.minValue;
            node1.minValue = node2.minValue;
        } else {
            temp = node1.maxValue;
            node1.maxValue = node2.minValue;
        }
        node2.minValue = temp;
    }

    public void midOrder(Node node) {
        if (node == null) return;
        midOrder(node.lChild);
        System.out.print(node.minValue + " ");
        midOrder(node.mChild);
        if (node.maxValue != null) System.out.print(node.maxValue + " ");
        midOrder(node.rChild);
    }

    public static void main(String[] args) {
        TwoThreeTree tree = new TwoThreeTree();
        tree.insert(20);
        tree.insert(50);
        tree.insert(35);
        tree.insert(15);
        tree.insert(70);
        tree.insert(25);
        tree.insert(60);
        tree.midOrder(tree.root);
    }

}

