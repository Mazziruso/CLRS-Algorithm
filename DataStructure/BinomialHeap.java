package DataStructure;

public class BinomialHeap {
}

class NodeBHeap<T> {
    T key;
    int degree;
    NodeBHeap child;
    NodeBHeap sibling;

    public NodeBHeap(T key, int degree, NodeBHeap child, NodeBHeap sibling) {
        this.key = key;
        this.degree = degree;
        this.child = child;
        this.sibling = sibling;
    }

    public NodeBHeap(T key, int degree) {
        this(key, degree, null, null);
    }

    public NodeBHeap() {
        this(null, 0);
    }

}

class BHeap {

    NodeBHeap head;

    BHeap(NodeBHeap node) {
        this.head = node;
    }

    BHeap() {
        this(null);
    }

    public static BHeap UnionBHeap(BHeap h1, BHeap h2) {
        NodeBHeap firstNode = new NodeBHeap();
        BHeap result = new BHeap(firstNode);
        //指示性指针
        NodeBHeap p = new NodeBHeap();
        NodeBHeap p1 = h1.head;
        NodeBHeap p2 = h2.head;

        if(p1 == null || p2 == null) {
            if(p1 == null) {
                firstNode = p2;
            } else {
                firstNode = p1;
            }

            return result;
        }

        if(p1.degree < p2.degree) {
            firstNode = p1;
            p = firstNode;
            p1 = p1.sibling;
        } else {
            firstNode = p2;
            p = firstNode;
            p2 = p2.sibling;
        }

        while(p1 != null && p2 != null) {
            if(p1.degree < p2.degree) {
                p.sibling = p1;
                p = p1;
                p1 = p1.sibling;
            } else {
                p.sibling = p2;
                p = p2;
                p2 = p2.sibling;
            }

        }

        if(p1 != null) {
            p.sibling = p1;
        } else {
            p.sibling = p2;
        }

        return result;

    }

    public static void insertNode(NodeBHeap x, BHeap bh) {
        BHeap h1 = new BHeap(x);
        bh.head = UnionBHeap(h1, bh).head;
    }

}
