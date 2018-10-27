package AVLTREE;

public class AVLMain {

    public static void main(String[] args) {
        int[] A = {41, 38, 31, 12, 19, 8, 39, 50};
        AVLTree T = AVLTree.buildAVLTree(A);
        System.out.println(T.root);
        Node z = AVLTree.search(T, T.root, 38);
        AVLTree.delete(T, z);
        System.out.println(T.root);
    }
}


/*
结点的高度包括当前结点
 */
class Node {
    int key;
    Node left;
    Node right;
    Node p;
    int h;

    Node(int key, Node left, Node right, Node p, int h) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.p = p;
        this.h = h;
    }

    Node(int key, int h) {
        this(key, null, null, null, h);
    }

    Node(int key) {
        this(key, 0);
    }

    Node() {
        this(0);
    }

    public String toString() {
        return "the Node: key is " + this.key + " height is " + this.h;
    }

}

class AVLTree {
    Node root;
    Node Tnil;

    AVLTree() {
        this.Tnil = new Node();
        this.root = this.Tnil;
    }

    //------------TREE BUILD----------------
    //Build
    public static AVLTree buildAVLTree(int[] A) {
        AVLTree T = new AVLTree();

        for(int i=0; i<A.length; i++) {
            AVLTree.insert(T, new Node(A[i]));
        }

        return T;
    }

    //------------TREE INSERT---------------
    //---------------ROTATE-----------------

    //Left Rotate
    public static void leftRotate(AVLTree T, Node x) {
        Node y = x.right;

        x.right = y.left;
        if(y.left != T.Tnil) {
            y.left.p = x;
        }

        y.p = x.p;
        if(x.p == T.Tnil) {
            T.root = y;
        } else if(x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }

        x.p = y;
        y.left = x;
    }

    //Right Rotate
    public static void rightRotate(AVLTree T, Node y) {
        Node x = y.left;

        y.left = x.right;
        if(x.right != T.Tnil) {
            x.right.p = y;
        }

        x.p = y.p;
        if(y.p == T.Tnil) {
            T.root = x;
        } else if(y == y.p.left) {
            y.p.left = x;
        } else {
            y.p.right = x;
        }

        y.p = x;
        x.right = y;
    }

    /*
    计算结点的高度
     */
    //Calculate Node Height
    public static void calHeight(AVLTree T, Node x) {

        x.h = (x.right.h>x.left.h) ? (x.right.h+1) : (x.left.h+1);

    }

    /*
    这个函数用来修正插入新结点后新树的平衡，并返回子树的根结点
     */
    //BALANCE
    public static Node balance(AVLTree T, Node x) {
        Node y;
        Node res = x;

        if(!AVLTree.isBalance(T, x)) {
            if(x.left.h > x.right.h) {
                y = x.left;
                if(y.right.h > y.left.h) {
                    AVLTree.leftRotate(T, y);
                    AVLTree.rightRotate(T, x);
                    //维护当前结点树高
                    res = y.p;
                    x.h = x.right.h + 1;
                    y.h = x.right.h + 1;
                    res.h = x.right.h + 2;
                    //
                } else {
                    AVLTree.rightRotate(T, x);
                    //维护当前结点树高
                    res = y;
                    x.h = x.right.h + 1;
                    res.h = x.right.h + 2;
                    //
                }
            } else {
                y = x.right;
                if(y.left.h > y.right.h) {
                    AVLTree.rightRotate(T, y);
                    AVLTree.leftRotate(T, x);
                    //维护当前结点树高
                    res = y.p;
                    x.h = x.left.h + 1;
                    y.h = x.left.h + 1;
                    res.h = x.right.h + 2;
                    //
                } else {
                    AVLTree.leftRotate(T, x);
                    //维护当前结点树高
                    res = y;
                    x.h = x.left.h + 1;
                    res.h = x.left.h + 2;
                    //
                }
            }
        }

        return res;
    }

    //INSERT
    public static void insert(AVLTree T, Node z) {
        Node y = T.Tnil;
        Node x = T.root;

        while(x != T.Tnil) {
            y = x;
            if(z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.p = y;
        if(y == T.Tnil) {
            T.root = z;
        } else if(z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = T.Tnil;
        z.right = T.Tnil;
        z.h = 1;

        AVLTree.treeFixup(T, y);
    }

    //向上回溯维护结点高度
    public static void treeFixup(AVLTree T, Node y) {
        while(y != T.Tnil) {
            AVLTree.calHeight(T, y);
            if(!AVLTree.isBalance(T, y)) {
                y = AVLTree.balance(T, y);
            }
            y = y.p;
        }
    }

    //
    public static boolean isBalance(AVLTree T, Node x) {

        return (Math.abs(x.right.h-x.left.h)>1) ? false : true;

    }

    //----------------TREE DELETE-------------
    //-----------------TRANSPLANT-------------

    //TRANSPLANT
    public static void transplant(AVLTree T, Node u, Node v) {

        if(u.p == T.Tnil) {
            T.root = v;
        } else if(u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }

        v.p = u.p;
    }

    //删除之后要逐层向上回溯维护结点树高
    //DELETE
    public static void delete(AVLTree T, Node z) {
        Node x = T.Tnil;//记录树高变化的最近结点
        Node y = T.Tnil;//记录z的后继结点

        if(z.left == T.Tnil) {
            x = z.p;
            AVLTree.transplant(T, z, z.right);
        } else if(z.right == T.Tnil) {
            x = z.p;
            AVLTree.transplant(T, z, z.left);
        } else {
            y = AVLTree.minimum(T, z.right);
            x = y.p;

            if(y.p != z) {
                AVLTree.transplant(T, y, y.right);
                y.right = z.right;
                z.right.p = y;
            }
            AVLTree.transplant(T, z, y);
            y.left = z.left;
            z.left.p = y;
            y.h = z.h;
        }

        //清空要删除的结点
        z.right = null;
        z.left = null;
        z.p = null;

        AVLTree.treeFixup(T, x);
    }


    //------------------BASIC OPERATION-----------

    //MINIMUM
    public static Node minimum(AVLTree T, Node x) {

        while(x.left != T.Tnil) {
            x = x.left;
        }

        return x;
    }


    //MAXIMUM
    public static Node maximum(AVLTree T, Node x) {
        if(x.right == T.Tnil) {
            return x;
        }

        return AVLTree.maximum(T, x.right);
    }

    //SEARCH
    public static Node search(AVLTree T, Node root, int k) {
        if(root == T.Tnil || root.key == k) {
            return root;
        }

        if(k < root.key) {
            return AVLTree.search(T, root.left, k);
        } else {
            return AVLTree.search(T, root.right, k);
        }
    }

    //SUCCESSOR
    public static Node successor(AVLTree T, Node z) {
        if(z.right != T.Tnil) {
            return AVLTree.minimum(T, z.right);
        }

        Node y = z.p;
        while((y!=T.Tnil) && (z==y.right)) {
            z = y;
            y = z.p;
        }

        return y;
    }

    //PREDECESSOR
    public static Node predecessor(AVLTree T, Node z) {
        if(z.left != T.Tnil) {
            return AVLTree.maximum(T, z.left);
        }

        Node y = z.p;
        while((y!=T.Tnil) && (z==y.left)) {
            z = y;
            y = y.p;
        }

        return y;
    }

}
