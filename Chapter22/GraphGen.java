package Charpter22;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphGen {
    public static void main(String[] args) {
//        int[] id = {0, 1, 2, 3, 4, 5, 6};
//        int[][] eId = {{0, 1}, {1, 2}, {1, 3}, {2, 3}, {3, 4}, {6, 5}};
////        int[] id = {2, 4, 8};
////        int[][] eId = {{2, 4}, {4, 8}, {8, 2}};
//
//        UndirectGraph G = new UndirectGraph(id, eId);
//
//        System.out.println();
//        for(int i=0; i<G.V.length; i++) {
//            System.out.println(G.V[i]);
//        }
//        System.out.println();
//
//        System.out.println();
//        for(int i=0; i<G.E.length; i++) {
//            System.out.println(G.E[i]);
//        }
//        System.out.println();
//
//        G.printAdj();
//
//        BFS(G, G.V[2]);
//
//        G.printBFSPath(G.V[2], G.V[4]);

        int[] id = {1, 2, 3, 4, 5, 6};
        int[][] eId = {{1, 4}, {1, 2}, {2, 3}, {2, 5}, {3, 5}, {3, 6}, {4, 2}, {5, 4}, {6, 6}};

        DirectGraph G = new DirectGraph(id, eId);

        G.printAdj();

        G.DFS();
        System.out.println();

        G.BFS(G.V[0]);
        G.printBFSPath(G.V[0], G.V[4]);

    }

    private static void BFS(UndirectGraph G, Node s) {

        //Initialization all Node in G
        for(int i=0; i<G.Vsize; i++) {
            if(G.V[i] != s) {
                G.V[i].color = 'W';
                G.V[i].d = Integer.MAX_VALUE;
                G.V[i].pi = null;
            }
        }
        s.color = 'G';
        s.d = 0;
        s.pi = null;

        //Load Queue
        Node u;
        int ui;
        ConcurrentLinkedQueue<Node> Q = new ConcurrentLinkedQueue<Node>();
        Q.offer(s);
        while(!Q.isEmpty()) {
            u = Q.poll();
            ui = G.findNode(u.id);
            for(AdjNode v=G.Adj[ui]; v!=null; v=v.next) {
                if(v.u.color == 'W') {
                    v.u.color = 'G';
                    v.u.d = u.d + 1;
                    v.u.pi = u;
                    Q.offer(v.u);
                }
            }
            u.color = 'B';
        }

    }
}

class Node {
    int id;
    char color;
    int d;
    int f; //Apply for DFS
    Node pi;

    public Node(int id, char color, int d, int f, Node pi) {
        this.id = id;
        this.color = color;
        this.d = d;
        this.f = f;
        this.pi = pi;
    }

    public Node(int id, char color, int d, Node pi) {
        this(id, color, d, 0, pi);
    }

    public Node(int id) {
        this(id, 'W', Integer.MAX_VALUE, null);
    }

    public Node() {
        this(0);
    }

    public String toString() {
        return "Node: " + this.id + ", " + this.color + ", " + this.d + ", " + ((this.pi == null) ? -1 : this.pi.id);
    }

}

class Edge {
    Node u;
    Node v;

    public Edge(Node u, Node v) {
        this.u = u;
        this.v = v;
    }

    public Edge() {
        this(null,null);
    }

    public String toString() {
        return "(" + this.u.id + ", " + this.v.id + ")";
    }
}

class AdjNode {
    Node u;
    AdjNode next;

    public AdjNode(Node u, AdjNode next) {
        this.u = u;
        this.next = next;
    }

    public AdjNode() {
        this(null, null);
    }
}

class UndirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    static int time ; //timestamp

    public UndirectGraph(int[] id, int[][] eId) {
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
            this.Adj[vi].next = new AdjNode(this.V[ui], this.Adj[vi].next);
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

    public void BFS(Node s) {

        //Initialization all Node in G
        for(int i=0; i<this.Vsize; i++) {
            if(this.V[i] != s) {
                this.V[i].color = 'W';
                this.V[i].d = Integer.MAX_VALUE;
                this.V[i].pi = null;
            }
        }
        s.color = 'G';
        s.d = 0;
        s.pi = null;

        //Load Queue
        Node u;
        int ui;
        ConcurrentLinkedQueue<Node> Q = new ConcurrentLinkedQueue<Node>();
        Q.offer(s);
        while(!Q.isEmpty()) {
            u = Q.poll();
            ui = this.findNode(u.id);
            for(AdjNode v=this.Adj[ui]; v!=null; v=v.next) {
                if(v.u.color == 'W') {
                    v.u.color = 'G';
                    v.u.d = u.d + 1;
                    v.u.pi = u;
                    Q.offer(v.u);
                }
            }
            u.color = 'B';
        }
    }

    public void printBFSPath(Node s, Node v) {
        if(v == s) {
            System.out.print(v.id + ", ");
        } else if(v.pi == null) {
            System.out.println("no path from " + s.id + " to " + v.id + " exists.");
        } else {
            this.printBFSPath(s, v.pi);
            System.out.print(v.id + ", ");
        }
    }

    public void DFS() {

        //Initialization all nodes
        for(int i=0; i<this.Vsize; i++) {
            this.V[i].color = 'W';
            this.V[i].pi = null;
        }

        UndirectGraph.time = 0;

        for(int i =0; i<this.Vsize; i++) {
            if(this.V[i].color == 'W') {
                this.DFSVisit(this.V[i]);
            }
        }

    }

    public void DFSVisit(Node u) {
        UndirectGraph.time++;
        u.d = UndirectGraph.time;
        u.color = 'G';
        int ui = this.findNode(u.id);
        for(AdjNode v=this.Adj[ui]; v!=null; v=v.next) {
            if(v.u.color == 'W') {
                v.u.pi = u;
                this.DFSVisit(v.u);
            }
        }
        u.color = 'B';
        UndirectGraph.time++;
        u.f = UndirectGraph.time;
    }


    public void printAdj() {
        System.out.println();
        for(int i=0; i<this.Vsize; i++) {
            AdjNode u = this.Adj[i];
            while(u != null) {
                System.out.print(u.u.id);
                u = u.next;
                if(u != null) {
                    System.out.print("->");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

class DirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    static int time ; //timestamp

    public DirectGraph(int[] id, int[][] eId) {
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

    public void BFS(Node s) {

        //Initialization all Node in G
        for(int i=0; i<this.Vsize; i++) {
            if(this.V[i] != s) {
                this.V[i].color = 'W';
                this.V[i].d = Integer.MAX_VALUE;
                this.V[i].f = 0;
                this.V[i].pi = null;
            }
        }
        s.color = 'G';
        s.d = 0;
        s.f = 0;
        s.pi = null;

        //Load Queue
        Node u;
        int ui;
        ConcurrentLinkedQueue<Node> Q = new ConcurrentLinkedQueue<Node>();
        Q.offer(s);
        while(!Q.isEmpty()) {
            u = Q.poll();
            ui = this.findNode(u.id);
            for(AdjNode v=this.Adj[ui]; v!=null; v=v.next) {
                if(v.u.color == 'W') {
                    v.u.color = 'G';
                    v.u.d = u.d + 1;
                    v.u.pi = u;
                    Q.offer(v.u);
                }
            }
            u.color = 'B';
        }
    }

    public void printBFSPath(Node s, Node v) {
        if(v == s) {
            System.out.print(v.id + ", ");
        } else if(v.pi == null) {
            System.out.println("no path from " + s.id + " to " + v.id + " exists.");
        } else {
            this.printBFSPath(s, v.pi);
            System.out.print(v.id + ", ");
        }
    }

    public void DFS() {

        //Initialization all nodes
        for(int i=0; i<this.Vsize; i++) {
            this.V[i].color = 'W';
            this.V[i].pi = null;
        }

        DirectGraph.time = 0;

        for(int i =0; i<this.Vsize; i++) {
            if(this.V[i].color == 'W') {
                this.DFSVisit(this.V[i]);
            }
        }

    }

    public void DFSVisit(Node u) {
        DirectGraph.time++;
        u.d = DirectGraph.time;
        u.color = 'G';
        int ui = this.findNode(u.id);
        for(AdjNode v=this.Adj[ui]; v!=null; v=v.next) {
            if(v.u.color == 'W') {
                v.u.pi = u;
                this.DFSVisit(v.u);
            }
        }
        u.color = 'B';
        DirectGraph.time++;
        u.f = DirectGraph.time;
    }


    public void printAdj() {
        System.out.println();
        for(int i=0; i<this.Vsize; i++) {
            AdjNode u = this.Adj[i];
            while(u != null) {
                System.out.print(u.u.id);
                u = u.next;
                if(u != null) {
                    System.out.print("->");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

