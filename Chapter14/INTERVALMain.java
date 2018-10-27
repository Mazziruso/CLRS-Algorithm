package Charpter14;

public class INTERVALMain {

    public static void main(String[] args) {

        Interval[] A = new Interval[10];
        for(int i=0; i<A.length; i++) {
            A[i] = new Interval(4*i, 4*i+3);
        }
        randomizedInPlace(A);
        printA(A);

//        try{
//            Thread.currentThread().sleep(2000);
//            System.out.println("Build Tree Starting...");
//            System.out.println();
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//
        IntervalTree T = IntervalTree.buildRBTree(A);
        IntervalTree.inorder(T, T.root);
        System.out.println();
        System.out.println("the root of T is :");
        System.out.println(T.root);
        System.out.println();
        System.out.println("the high of T is :");
        System.out.println(IntervalTree.getTreeHigh(T));
        System.out.println();
        System.out.println("the [2, 5] of T is :");
        Node x = IntervalTree.TreeSearch(T, new Interval(2, 8));
        System.out.println(x);
        System.out.println();

    }

    public static void randomizedInPlace(Interval[] A) {
        int len = A.length;
        Interval temp;
        int index;

        for(int i=0; i<len-1; i++) {
            index = (int)(Math.random() * (len-1-i)) + i;
            temp = A[i];
            A[i] = A[index];
            A[index] = temp;
        }
    }

    public static void printA(Interval[] A) {
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

class Interval {
    int low;
    int high;

    Interval(int low, int high) {
        this.low = low;
        this.high = high;
    }

    Interval() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public String toString() {
        return "[" + this.low + ", " + this.high + "]";
    }
}

class Node {
    int key;
    int max;
    Interval xint;
    Node left;
    Node right;
    Node p;
    boolean color; //if node is black then color refers to true, otherwise false

    public Node(Interval xint, Node p, Node left, Node right, boolean color) {
        this.key = xint.low;
        this.xint = xint;
        this.max = xint.high;//默认当前区间的最大值
        this.p = p;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    public Node(Interval xint, boolean color) {
        this(xint, null, null, null, color);
    }

    public Node(Interval xint) {
        this(xint, null, null, null, false); //Default node's color is red
    }

    public Node() {
        this(new Interval(), true); //NIL node's color is black
    }

    public String toString() {
        return "the Node: int=" + this.xint + ", color=" + (this.color ? "BLACK" : "RED") + ", max=" + this.max;
    }

}

class IntervalTree {
    Node root;
    Node Tnil;

    IntervalTree() {
        this.Tnil = new Node(); //NIL node, BLACK
        this.root = this.Tnil; //root node refers to T.nil node
    }

    //Build Red-Black Tree using insert operation
    public static IntervalTree buildRBTree(Interval[] A) {
        IntervalTree T = new IntervalTree();
        int index;
        Interval tmp;

        for(int end=A.length-1; end>0; end--) {
            index = (int) (Math.random() * end);
            tmp = A[index];
            A[index] = A[end];
            A[end] = tmp;
            IntervalTree.TreeInsert(T, new Node(A[end]));
        }
        IntervalTree.TreeInsert(T, new Node(A[0]));

        return T;
    }

    //------------TREE INSERT-------------------
    //---------------ROTATE--------------------

    private static void IntervalMax(IntervalTree T, Node x) {
        if(x.xint.high >= x.left.max) {
            x.max = (x.xint.high >= x.right.max) ? x.xint.high : x.right.max;
        } else {
            x.max = (x.left.max > x.right.max) ? x.left.max : x.right.max;
        }
    }

    //Left Rotate
    private static void leftRotate(IntervalTree T, Node x) {
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

        y.left = x;
        x.p = y;

        //维护max属性
        y.max = x.max;
        IntervalTree.IntervalMax(T, x);
    }

    //Right Rotate
    private static void rightRotate(IntervalTree T, Node y) {
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

        x.right = y;
        y.p = x;

        //维护max的属性
        x.max = y.max;
        IntervalTree.IntervalMax(T, y);
    }

    //Red-Black Tree Insert
    public static void TreeInsert(IntervalTree T, Node z) {
        Node y = T.Tnil;
        Node x = T.root;
        while(x != T.Tnil) {
            y = x;
            if(z.xint.high > y.max) {
                y.max = z.xint.high;
            }
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

        IntervalTree.TreeInsertFixup(T, z);
    }

    /*
    if the color of z'parent node is black, the Tree need not fix up;
    There are three cases to process while the color of z'parent node is red:
    case 1: z'uncle node is red;
    case 2: z'uncle node is black and z is right-child node, then turn to case 3;
    case 3: z'uncle node is black and z is left-child node.
     */
    //Red-Black Tree Insert Fixup
    private static void TreeInsertFixup(IntervalTree T, Node z) {
        Node y;

        while(z.p.color == false) {
            if(z.p == z.p.p.left){
                y = z.p.p.right; //z'uncle node

                if(!y.color) {
                    z.p.color = true;
                    y.color = true;
                    z.p.p.color = false;
                    z = z.p.p;
                } else{
                    if(z == z.p.right) {
                        z = z.p;
                        IntervalTree.leftRotate(T, z);
                    }
                    z.p.color = true;
                    z.p.p.color = false;
                    IntervalTree.rightRotate(T, z.p.p);
                }
            } else{
                y = z.p.p.left;

                if(!y.color) {
                    z.p.color = true;
                    y.color = true;
                    z.p.p.color = false;
                    z = z.p.p;
                } else {
                    if(z == z.p.left) {
                        z = z.p;
                        IntervalTree.rightRotate(T, z);
                    }
                    z.p.color = true;
                    z.p.p.color = false;
                    IntervalTree.leftRotate(T, z.p.p);
                }
            }
        }

        T.root.color = true;
    }

    //--------------TREE DELETE-------------
    //--------------------------------------

    //Tree Transplant
    private static void TreeTransplant(IntervalTree T, Node u, Node v) {
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
    public static void TreeDelete(IntervalTree T, Node z) {
        Node x;
        Node y = z;
        boolean yOriginalColor = y.color;

        if(z.left == T.Tnil) {
            x = z.left;
            IntervalTree.TreeTransplant(T, z, z.right);
        } else if(z.right == T.Tnil) {
            x = z.right;
            IntervalTree.TreeTransplant(T, z, z.left);
        } else {
            y = IntervalTree.TreeMinimum(T, z.right);
            yOriginalColor = y.color;
            x = y.right;
            if(y.p == z) {
                x.p = y;
            } else {
                IntervalTree.TreeTransplant(T, y, x);
                IntervalTree.IntervalMax(T, x.p);
                y.right = z.right;
                y.right.p = y;
            }
            IntervalTree.TreeTransplant(T, z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
            IntervalTree.IntervalMax(T, y);
        }

        z.p = null;
        z.right = null;
        z.left = null;

        //向上逐层维护父结点的max属性
        while(y.p != T.Tnil) {
            IntervalTree.IntervalMax(T, y.p);
            y = y.p;
        }

        if(yOriginalColor == true) {
            IntervalTree.TreeDeleteFixup(T, x);
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
    private static void TreeDeleteFixup(IntervalTree T, Node x) {
        Node w;

        while(x != T.root && x.color) {
            if(x == x.p.left) {
                w = x.p.right;
                if(!w.color) {
                    w.color = true;
                    x.p.color = false;
                    IntervalTree.leftRotate(T, x.p);
                    w = x.p.right;
                }

                //case 1与case 2、3、4相互独立，case 2与case 3、4互斥
                //case 2使额外的黑点上移，再次迭代
                //case 3转为case 4，case 4直接结束迭代
                if(w.left.color && w.right.color){
                    w.color = false;
                    x = x.p;
                } else {
                    if(w.right.color) {
                        w.color = false;
                        w.left.color = true;
                        IntervalTree.rightRotate(T, w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = true;
                    w.right.color = true;
                    IntervalTree.leftRotate(T, x.p);
                    x = T.root;
                }
            } else {
                w = x.p.left;
                if(w.color) {
                    w.color = true;
                    x.p.color = false;
                    IntervalTree.rightRotate(T, x.p);
                    w = x.p.right;
                }

                if(w.left.color && w.right.color) {
                    w.color = false;
                    x = x.p;
                } else {
                    if(w.left.color) {
                        w.color = false;
                        w.right.color = true;
                        IntervalTree.leftRotate(T, w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = true;
                    w.left.color = true;
                    IntervalTree.rightRotate(T, x.p);
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

    public static Node TreeMinimum(IntervalTree T, Node root) {
        while(root.right != T.Tnil) {
            root = root.right;
        }
        return root;
    }

    public static Node TreeMaximum(IntervalTree T, Node root) {
        if(root.left == T.Tnil) {
            return root;
        }
        return IntervalTree.TreeMaximum(T, root.left);
    }

    public static Node TreeSearch(IntervalTree T, Interval i) {
        Node x = T.root;
        boolean flag = false;

        while(x != T.Tnil && !flag) {
            if(x.left.max >= i.low) {
                flag = isOverlap(x.xint, i) && !isOverlap(x.left.xint, i);
                x = flag ? x : x.left;
            } else if(!flag){
                flag = isOverlap(x.xint, i) && !isOverlap(x.left.xint, i);
                x = flag ? x : x.right;
            }
        }

        return x;
    }

    public static Node TreeSuccessor(IntervalTree T, Node root) {
        if(root.right != T.Tnil) {
            return IntervalTree.TreeMinimum(T, root.right);
        }

        Node y = root.p;
        while(y != T.Tnil && root == y.right) {
            root = y;
            y = y.p;
        }
        return y;
    }

    public static Node TreePredecessor(IntervalTree T, Node root) {
        if(root.left != T.Tnil) {
            return IntervalTree.TreeMaximum(T, root.left);
        }

        Node y = root.p;
        while(y != T.Tnil && root == y.left) {
            root = y;
            y = y.p;
        }
        return y;
    }

    private static boolean isOverlap(Interval i, Interval xi) {
        if(i.low <= xi.high && xi.low <= i.high) {
            return true;
        } else {
            return false;
        }
    }

    //返回红黑树的树高
    public static int getTreeHigh(IntervalTree T) {
        return orderHigh(T, T.root, 0);
    }

    private static int orderHigh(IntervalTree T, Node root, int count) {
        int maxHigh = count-1;
        if (root != T.Tnil) {
            int maxHighLeft = IntervalTree.orderHigh(T, root.left, count+1);
            int maxHighRight = IntervalTree.orderHigh(T, root.right, count+1);
            maxHigh = maxHighLeft > maxHighRight ? maxHighLeft : maxHighRight;
        }
        return maxHigh;//树高不算根结点这一层
    }

    public static void inorder(IntervalTree T, Node root) {
        if(root != T.Tnil) {
            IntervalTree.inorder(T, root.left);
            System.out.println(root.xint);
            IntervalTree.inorder(T, root.right);
        }
    }

}
