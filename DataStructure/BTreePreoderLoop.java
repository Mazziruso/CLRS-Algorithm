package DataStructure;

public class BTreePreoderLoop {

    public static void main(String[] args) {
        int[] A = {1,2,3,4,5,6,7,8};
        BTree bt = new BTree(A);
        Node p = bt.preorder(bt.root, 6);
        System.out.println(p.key);
    }
}

class Node {
    int key;
    Node left;
    Node right;

    Node(int key, Node left, Node right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    Node(int key) {
        this(key, null, null);
    }

    Node() {
        this(0, null,null);
    }

}

class BTree {
    Node root;
    int NumOfNodes;

    BTree(int[] A) {
        this.NumOfNodes = A.length;
        this.root = createTree(A, 0, this.NumOfNodes-1);
    }

    public Node createTree(int[] A, int start, int end) {

        if(start == end) {
            return new Node(A[start], null, null);
        } else if(start > end) {
            return null;
        }

        int mid = (start+end)/2;
        int tmp = A[mid];
        return new Node(tmp, createTree(A, start, mid-1), createTree(A, mid+1, end));

    }

//    public void preorder(Node root) {
//        if(root != null) {
//            preorder(root.left);
//            System.out.println(root.key);
//            preorder(root.right);
//        }
//    }

    public Node preorder(Node root, int index) {

        Node result = null;

        if(root != null) {
            Node p = root;
            Stack<Node> s = new Stack(30);
            boolean stop = false;
            int nodesVisited = 0;

            while((!stop) && (!s.isEmpty() || (p!=null))) {
                while(p!=null) {
                    s.push(p);
                    p = p.left;
                }
                if(!s.isEmpty()) {
                    p = s.pop();

                    result = p;
                    nodesVisited++;

                    p = p.right;
                }
                if(nodesVisited == index) {
                    stop = true;
                }
            }
        }

        return result;
    }
}

class Stack<T> {
    T[] arrays;
    int numOfEntries;
    static final int DEFAULT_CAPACITY = 30;
    static final int MAX_CAPACITY = 100;

    public Stack(int capacity) {
        if(capacity <= MAX_CAPACITY) {

            @SuppressWarnings("unchecked")
            T[] tmp = (T[]) new Object[capacity];
            this.arrays = tmp;
            this.numOfEntries = 0;
        } else {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds allowed maximum.");
        }
    }

    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public boolean isEmpty() {
        return (this.numOfEntries>0) ? false : true;
    }

    public boolean push(T anEntry) {

        if(this.numOfEntries == this.arrays.length) {
            return false;
        }
        arrays[this.numOfEntries] = anEntry;
        this.numOfEntries++;
        return true;
    }

    public T pop() {
        if(this.numOfEntries == 0) {
            return null;
        }

        T result = this.arrays[this.numOfEntries-1];
        this.arrays[this.numOfEntries-1] = null;
        this.numOfEntries--;
        return result;
    }
}
