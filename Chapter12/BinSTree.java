package Charpter12;

public class BinSTree {

    public static void main(String[] args) {

        int[] A = {1,2,3,4,5,6,7,8,9,10};
        BinTree T = BinTree.buildTree(A);
        BinTree.inorder(T.root);
//        Node x = BinTree.searchTree(T.root, 11);
        Node x = T.iterativeSearchTree(3);
        System.out.println(x);
        System.out.println("the maximum is " + BinTree.maximumTree(T.root));
        System.out.println("the minimum is " + BinTree.minimumTree(T.root));
        System.out.println("the key3's successor is " + BinTree.successorTree(x));
        System.out.println("the key3's predecessor is " + BinTree.predecessorTree(x));
        T.deleteTree(x);
        BinTree.inorder(T.root);

    }
}

class Node {
    int key;
    Node left;
    Node right;
    Node p;

    Node(int key, Node left, Node rigth, Node p) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.p = p;
    }

    Node(int key) {
        this(key, null, null, null);
    }

    Node() {
        this(0);
    }

    public String toString() {
        return "the Node key is " + this.key;
    }
}

class BinTree {
    Node root;

    BinTree() {
        this.root = null;
    }

    public static BinTree buildTree(int[] A) {
        BinTree T = new BinTree();
        int index;
        int tmp;

        for(int end=A.length-1; end>0; end--) {
            index = (int) (Math.random() * end);
            tmp = A[index];
            A[index] = A[end];
            A[end] = tmp;
            insertTree(T, new Node(A[end]));
        }
        insertTree(T, new Node(A[0]));

        return T;

    }

    public static void insertTree(BinTree T, Node z) {
        Node y = null;
        Node x = T.root;
        while(x != null) {
            y = x;
            if(z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.p = y;
        if(y == null) {
            T.root = z;
        } else if(z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }

    }

    public static Node successorTree(Node root) {
        if(root.right != null) {
            return minimumTree(root.right);
        }

        Node y = root.p;
        while(y != null && root == y.right) {
            root = y;
            y = y.p;
        }
        return y;
    }

    public static Node predecessorTree(Node root) {
        if(root.left != null) {
            return maximumTree(root.left);
        }

        Node y = root.p;
        while(y != null && root == y.left) {
            root = y;
            y = y.p;
        }
        return y;
    }

    public static Node searchTree(Node root, int k) {
        if(root == null || k == root.key) {
            return root;
        }

        if(k < root.key) {
            return searchTree(root.left, k);
        } else {
            return searchTree(root.right, k);
        }
    }

    public Node iterativeSearchTree(int k){
        Node x = this.root;

        while(x!=null && k!=x.key) {
            if(k<x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        return x;
    }

    public static Node minimumTree(Node root) {
        if(root.left == null) {
            return root;
        }

        return minimumTree(root.left);

    }

    public static Node maximumTree(Node root) {
        while(root.right != null) {
            root = root.right;
        }
        return root;
    }

    //Delete Node
    //用根为v的子树来代替根为u的子树
    public void transplantTree(Node u, Node v) {
        if(u.p == null) {
            this.root = v;
        } else if(u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }

        if(v != null) {
            v.p = u.p;
        }
        u.p = null;
    }

    public void deleteTree(Node z) {
        if(z.left == null) {
            this.transplantTree(z, z.right);
            z.right = null;
        } else if(z.right == null) {
            this.transplantTree(z, z.left);
            z.left = null;
        } else {
            Node y = BinTree.minimumTree(z);
            if(y.p != z) {
                this.transplantTree(y, y.right);
                y.right = z.right;
                y.right.p = y;
                z.right = null;
            }
            this.transplantTree(z, y);
            y.left = z.left;
            y.left.p = y;
            z.left = null;
        }
    }

    public static void inorder(Node x) {
        if(x != null) {
            inorder(x.left);
            System.out.println(x.key);
            inorder(x.right);
        }
    }
}
