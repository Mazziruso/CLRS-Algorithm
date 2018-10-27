package Charpter23;

import java.util.PriorityQueue;
import java.util.Vector;

public class MSTMain {
    public static void main(String[] args) {
        int[] id = {1,2,3,4,5,6,7,8,9};
        int[][] eId = {{1,2}, {1,8}, {2,3}, {2,8}, {3,4}, {3,6}, {3,9}, {4,5}, {4,6}, {5,6}, {6,7}, {7,8}, {7,9}, {8,9}};
        int[] w = {4,8,8,11,7,4,2,9,14,10,2,1,6,7};

        Graph G = new Graph(id, eId, w);

        int[][] A = Graph.MSTPrim(G, G.V[0]);

        System.out.println();

    }
}

class Node implements Comparable<Node> {
    int id;
    int key; //连接v和树中结点的所有边中最小边的权重
    Node pi;

    boolean isQ;

    public Node(int id, int key, Node pi, boolean isQ) {
        this.id = id;
        this.key = key;
        this.pi = pi;
        this.isQ = isQ;
    }

    public Node(int id, Node pi)
    {
        this(id, Integer.MAX_VALUE, pi, false);
    }

    public Node(int id) {
        this(id, null);
    }

    public Node() {
        this(0, null);
    }

    public int compareTo(Node v) {
        return Integer.compare(this.key, v.key);
    }
}

class Edge {
    Node u;
    Node v;
    int w;

    public Edge(Node u, Node v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public Edge(Node u, Node v) {
        this(u, v, 0);
    }

    public Edge() {
        this(null, null);
    }
}

class AdjNode {
    Node u;
    AdjNode next;
    int w;

    public AdjNode(Node u, AdjNode v, int w) {
        this.u = u;
        this.next = v;
        this.w = w;
    }

    public AdjNode(Node u, AdjNode v) {
        this(u, v, 0);
    }

    public AdjNode() {
        this(null, null);
    }
}

class Graph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    public Graph(int[] id, int[][] eId, int[] w) {
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
            this.E[i] = new Edge(this.V[ui], this.V[vi], w[i]);
            this.Adj[ui].next = new AdjNode(this.V[vi], this.Adj[ui].next, w[i]);
            this.Adj[vi].next = new AdjNode(this.V[ui], this.Adj[vi].next, w[i]);
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

    public static int[][] MSTPrim(Graph G, Node r) {
        int[][] A = new int[G.Vsize-1][];

        for(int i=0; i<G.Vsize; i++) {
            G.V[i].key = Integer.MAX_VALUE;
            G.V[i].pi = null;
        }

        r.key = 0;

        PriorityQueue<Node> Q = new PriorityQueue<Node>(G.Vsize);
        for(int i=0; i<G.Vsize; i++) {
            Q.add(G.V[i]);
            G.V[i].isQ = true;
        }

        int index = 0;
        Node u;
        while(!Q.isEmpty()) {
            u = Q.poll();
            u.isQ = false;
            for(AdjNode v=G.Adj[G.findNode(u.id)].next; v!=null; v=v.next) {
                if(v.u.isQ && v.w<v.u.key) {
                    v.u.pi = u;
                    v.u.key = v.w;
                    Q.remove(v.u);
                    Q.add(v.u);
                }
            }

            if(u.pi != null) {
                A[index] = new int[]{u.pi.id, u.id};
                index++;
            }
        }

        return A;
    }
}
