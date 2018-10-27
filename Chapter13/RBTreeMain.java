package Charpter13;

public class RBTreeMain {

    public static void main(String[] args) {

//        int[] A = new int[1024];
//        for(int i=0; i<A.length; i++) {
//            A[i] = i+1;
//        }
//        randomizedInPlace(A);
//        printA(A);

//        try{
//            Thread.currentThread().sleep(2000);
//            System.out.println("Build Tree Starting...");
//            System.out.println();
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        RBTree T = RBTree.buildRBTree(A);
//        RBTree.inorder(T, T.root);
//        System.out.println();
//        System.out.println("the root of T is :");
//        System.out.println(T.root);
//        System.out.println();
//        System.out.println("the high of T is :");
//        System.out.println(RBTree.getTreeHigh(T));

        //13.4-7
        int[] A = {41, 38, 31, 12, 19, 8, 39, 50};
        RBTree T = RBTree.buildRBTree(A);
        System.out.println(T.root);
        RBTree.TreeInsert(T, new RBNode(45));
        System.out.println(T.root);
        RBNode z = RBTree.TreeSearch(T, T.root, 45);
        RBTree.TreeDelete(T, z);
        System.out.println(T.root);

    }

    public static void randomizedInPlace(int[] A) {
        int len = A.length;
        int temp;
        int index;

        for(int i=0; i<len-1; i++) {
            index = (int)(Math.random() * (len-1-i)) + i;
            temp = A[i];
            A[i] = A[index];
            A[index] = temp;
        }
    }

    public static void printA(int[] A) {
        for(int i=0; i<A.length; i++) {
            System.out.print(A[i] + " ");
            if((i+1)%20 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }

}

class RBNode {
    int key;
    RBNode left;
    RBNode right;
    RBNode p;
    boolean color; //if node is black then color refers to true, otherwise false

    public RBNode(int key, RBNode p, RBNode left, RBNode right, boolean color) {
        this.key = key;
        this.p = p;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    public RBNode(int key, boolean color) {
        this(key, null, null, null, color);
    }

    public RBNode(int key) {
        this(key, null, null, null, false); //Default node's color is red
    }

    public RBNode() {
        this(0, true); //NIL node's color is black
    }

    public String toString() {
        return "the Node: key=" + this.key + ", color=" + (this.color ? "BLACK" : "RED");
    }

}

class RBTree {
    RBNode root;
    RBNode Tnil;

    RBTree() {
        this.Tnil = new RBNode(); //NIL node, BLACK
        this.root = this.Tnil; //root node refers to T.nil node
    }

    //Build Red-Black Tree using insert operation
    public static RBTree buildRBTree(int[] A) {
        RBTree T = new RBTree();

        /*随机化版本
        int index;
        int tmp;

        for(int end=A.length-1; end>0; end--) {
            index = (int) (Math.random() * end);
            tmp = A[index];
            A[index] = A[end];
            A[end] = tmp;
            RBTree.TreeInsert(T, new RBNode(A[end]));
        }
        RBTree.TreeInsert(T, new RBNode(A[0]));
        */

        for(int i=0; i<A.length; i++) {
            RBTree.TreeInsert(T, new RBNode(A[i]));
        }

        return T;
    }

    //------------TREE INSERT-------------------
    //---------------ROTATE--------------------

    //Left Rotate
    private static void leftRotate(RBTree T, RBNode x) {
        RBNode y = x.right;

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

        y.left = x;
        x.p = y;
    }

    //Right Rotate
    private static void rightRotate(RBTree T, RBNode y) {
        RBNode x = y.left;

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

        x.right = y;
        y.p = x;
    }

    //Red-Black Tree Insert
    public static void TreeInsert(RBTree T, RBNode z) {
        RBNode y = T.Tnil;
        RBNode x = T.root;
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
        z.color = false; //RED

        RBTree.TreeInsertFixup(T, z);
    }

    /*
    if the color of z'parent node is black, the Tree need not fix up;
    There are three cases to process while the color of z'parent node is red:
    case 1: z'uncle node is red;
    case 2: z'uncle node is black and z is right-child node, then turn to case 3;
    case 3: z'uncle node is black and z is left-child node.
     */
    //Red-Black Tree Insert Fixup
    private static void TreeInsertFixup(RBTree T, RBNode z) {
        RBNode y;

        while(z.p.color == false) {
            if(z.p == z.p.p.left){
                y = z.p.p.right; //z'uncle node

                if(y.color == false) {
                    z.p.color = true;
                    y.color = true;
                    z.p.p.color = false;
                    z = z.p.p;
                } else{
                    if(z == z.p.right) {
                        z = z.p;
                        RBTree.leftRotate(T, z);
                    }
                    z.p.color = true;
                    z.p.p.color = false;
                    RBTree.rightRotate(T, z.p.p);
                }
            } else{
                y = z.p.p.left;

                if(y.color == false) {
                    z.p.color = true;
                    y.color = true;
                    z.p.p.color = false;
                    z = z.p.p;
                } else {
                    if(z == z.p.left) {
                        z = z.p;
                        RBTree.rightRotate(T, z);
                    }
                    z.p.color = true;
                    z.p.p.color = false;
                    RBTree.leftRotate(T, z.p.p);
                }
            }
        }

        T.root.color = true;
    }

    //--------------TREE DELETE-------------
    //--------------------------------------

    //Tree Transplant
    private static void TreeTransplant(RBTree T, RBNode u, RBNode v) {
        if(u.p == T.Tnil) {
            T.root = v;
        } else if(u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }

        v.p = u.p;
    }

    /*
    there are three cases like BinTree:
    case 1: Non-child or only one child;
    case 2: successor is z's right child;
    case 3: successor is not z's right child.
     */
    //Red-Black Tree Delete
    public static void TreeDelete(RBTree T, RBNode z) {
        RBNode x;
        RBNode y = z;
        boolean yOriginalColor = y.color;

        if(z.left == T.Tnil) {
            x = z.left;
            RBTree.TreeTransplant(T, z, z.right);
        } else if(z.right == T.Tnil) {
            x = z.right;
            RBTree.TreeTransplant(T, z, z.left);
        } else {
            y = RBTree.TreeMinimum(T, z.right);
            yOriginalColor = y.color;
            x = y.right;
            if(y.p == z) {
                x.p = y;
            } else {
                RBTree.TreeTransplant(T, y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            RBTree.TreeTransplant(T, z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }

        z.p = null;
        z.right = null;
        z.left = null;

        if(yOriginalColor == true) {
            RBTree.TreeDeleteFixup(T, x);
        }
    }

    /*
    there are four cases while the color of x is black and x is not the root of T:
    case 1: x'sliping node(w) is red color
    case 2: x'sliping node(w) is black color and the colors of w'children both are black
    case 3: x'sliping node(w) is black color and the colors of w'rChild is black and the other one is red node;
    case 4: x'sliping node(w) is black color and the colors of w'rChild is red and the other one could be any color.
     */
    //Red-Black Tree Delete Fix up
    private static void TreeDeleteFixup(RBTree T, RBNode x) {
        RBNode w;

        while(x != T.root && x.color == true) {
            if(x == x.p.left) {
                w = x.p.right;
                if(w.color == false) {
                    w.color = true;
                    x.p.color = false;
                    RBTree.leftRotate(T, x.p);
                    w = x.p.right;
                }

                //case 1与case 2、3、4相互独立，case 2与case 3、4互斥
                //case 2使额外的黑点上移，再次迭代
                //case 3转为case 4，case 4直接结束迭代
                if(w.left.color == true && w.right.color == true){
                    w.color = false;
                    x = x.p;
                } else {
                    if(w.right.color == true) {
                        w.color = false;
                        w.left.color = true;
                        RBTree.rightRotate(T, w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = true;
                    w.right.color = true;
                    RBTree.leftRotate(T, x.p);
                    x = T.root;
                }
            } else {
                w = x.p.left;
                if(w.color == false) {
                    w.color = true;
                    x.p.color = false;
                    RBTree.rightRotate(T, x.p);
                    w = x.p.right;
                }

                if(w.left.color == true && w.right.color == true) {
                    w.color = false;
                    x = x.p;
                } else {
                    if(w.left.color == true) {
                        w.color = false;
                        w.right.color = true;
                        RBTree.leftRotate(T, w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = true;
                    w.left.color = true;
                    RBTree.rightRotate(T, x.p);
                    x = T.root;
                }
            }
        }

        x.color = true;
    }


    //-----------------------------------------------------------------
    //Dynamic collection operation
    //Minimum, Maximum, Search, Successor, Predecessor, all need O(lgn)
    //------------------------------------------------------------------

    public static RBNode TreeMinimum(RBTree T, RBNode root) {
        while(root.right != T.Tnil) {
            root = root.right;
        }
        return root;
    }

    public static RBNode TreeMaximum(RBTree T, RBNode root) {
        if(root.left == T.Tnil) {
            return root;
        }
        return RBTree.TreeMaximum(T, root.left);
    }

    public static RBNode TreeSearch(RBTree T, RBNode root, int k) {
        if(root == T.Tnil || k == root.key) {
            return root;
        }

        if(k < root.key) {
            return RBTree.TreeSearch(T, root.left, k);
        } else {
            return RBTree.TreeSearch(T, root.right, k);
        }
    }

    public static RBNode TreeSuccessor(RBTree T, RBNode root) {
        if(root.right != T.Tnil) {
            return RBTree.TreeMinimum(T, root.right);
        }

        RBNode y = root.p;
        while(y != T.Tnil && root == y.right) {
            root = y;
            y = y.p;
        }
        return y;
    }

    public static RBNode TreePredecessor(RBTree T, RBNode root) {
        if(root.left != T.Tnil) {
            return RBTree.TreeMaximum(T, root.left);
        }

        RBNode y = root.p;
        while(y != T.Tnil && root == y.left) {
            root = y;
            y = y.p;
        }
        return y;
    }

    //返回红黑树的树高
    public static int getTreeHigh(RBTree T) {
        return orderHigh(T, T.root, 0);
    }

    private static int orderHigh(RBTree T, RBNode root, int count) {
        int maxHigh = count-1;
        if (root != T.Tnil) {
            int maxHighLeft = RBTree.orderHigh(T, root.left, count+1);
            int maxHighRight = RBTree.orderHigh(T, root.right, count+1);
            maxHigh = maxHighLeft > maxHighRight ? maxHighLeft : maxHighRight;
        }
        return maxHigh;//树高不算根结点这一层
    }

    public static void inorder(RBTree T, RBNode root) {
        if(root != T.Tnil) {
            RBTree.inorder(T, root.left);
            System.out.println(root.key);
            RBTree.inorder(T, root.right);
        }
    }

}
