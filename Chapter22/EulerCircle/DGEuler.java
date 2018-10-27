package Charpter22.EulerCircle;
import java.util.*;

public class DGEuler {

    public static void main(String[] args) {
        int[] id = {1,2,3,4};
        int[][] eId = {{1,2},{1,3},{2,3},{3,1},{3,4},{4,1}};
        DirectG G = new DirectG(id, eId);
        LinkedList<Node> T = EulerTour(G);
        for(Iterator<Node> iter = T.iterator(); iter.hasNext();) {
            System.out.println(iter.next());
        }
    }

    private static LinkedList<Node> EulerTour(DirectG G) {
        LinkedList<Node> T = new LinkedList<Node>();
        LinkedList<Node> C;

        Stack<ExtendNode> L = new Stack<ExtendNode>();

        L.push(new ExtendNode(G.V[0], -1));

        ExtendNode eleL;
        Node v;
        int pos; //指向T中結點v的位置
        while(!L.empty()) {
            eleL = L.pop();
            v = eleL.u;
            pos = eleL.pos;

            C = visitNode(G, v, L);
            if(pos == -1) {
                T = C;
            } else {
                T.addAll(pos, C);
            }
        }

        T.add(T.getFirst()); //最後加上起始點，構成迴路

        return T;
    }

    private static LinkedList<Node> visitNode(DirectG G, Node v, Stack<ExtendNode> L) {
        LinkedList<Node> C = new LinkedList<Node>();

        AdjNode u = G.Adj[G.findNode(v.id)];
        AdjNode w;
        while(u!=null && u.u.outDegree>0) {
            w = u.next;
            G.removeAdjTopNode(u, w);
            C.add(u.u);
            u.u.outDegree--;
            if(u.u.outDegree > 0) {
                L.push(new ExtendNode(u.u, C.indexOf(u.u)));
            }
            u = (w==null) ? null : G.Adj[G.findNode(w.u.id)];
        }

        return C;
    }
}

//用來存儲鏈錶L中的元素
class ExtendNode {
    Node u;
    int pos;

    ExtendNode(Node u, int pos) {
        this.u = u;
        this.pos = pos;
    }

    ExtendNode() {
        this(null, 0);
    }
}

class Node {
    int id;
    int outDegree;
    int inDegree;

    Node(int id, int outDegree, int inDegree) {
        this.id = id;
        this.outDegree = outDegree;
        this.inDegree = inDegree;
    }

    Node(int id) {
        this(id, 0, 0);
    }

    Node() {
        this(0);
    }

    public String toString() {
        return "Node" + this.id;
    }

}

class Edge {
    Node u;
    Node v;

    Edge(Node u, Node v) {
        this.u = u;
        this.v = v;
    }

    Edge() {
        this(null, null);
    }
}

class AdjNode {
    Node u;
    AdjNode next;

    AdjNode(Node u, AdjNode next) {
        this.u = u;
        this.next = next;
    }

    AdjNode(Node u) {
        this(u, null);
    }

    AdjNode() {
        this(null);
    }
}

class DirectG {

    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    DirectG(int[] id, int[][] eId) {
        this.Vsize = id.length;
        this.V = new Node[id.length];
        this.E = new Edge[eId.length];
        this.Adj = new AdjNode[id.length];

        for(int i=0; i<id.length; i++) {
            this.V[i] = new Node(id[i]);
            this.Adj[i] = new AdjNode(this.V[i], null);
        }
        for(int i=0; i<eId.length; i++) {
            int ui = this.findNode(eId[i][0]);
            int vi = this.findNode(eId[i][1]);
            this.E[i] = new Edge(this.V[ui], this.V[vi]);
            this.Adj[ui].next = new AdjNode(this.V[vi], this.Adj[ui].next);
            this.V[ui].outDegree++; //out-degree of u
            this.V[vi].inDegree++; //in-degree of v
        }
    }

    public int findNode(int id) {
        int start = 0;
        int end = this.Vsize-1;
        int mid;

        while(start <= end) {
            mid = (start+end)/2;
            if(id < this.V[mid].id) {
                end = mid-1;
            } else if(id > this.V[mid].id) {
                start = mid+1;
            } else {
                return mid;
            }
        }

        return 0;
    }

    public void removeAdjTopNode(AdjNode u, AdjNode w) {
        if(w != null && u != null) {
            u.next = w.next;
            w.next = null;
        } else if(u != null) {
            u.next = null;
        }
    }

}
