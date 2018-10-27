package Charpter24.DijkstraAlgorithm;

import java.util.PriorityQueue;

public class DijkstraMain {
    public static void main(String[] args) {
        char[] vertex = {'s', 't', 'x', 'y', 'z'};
        char[][] edge = {{'s','t'},{'s','y'},{'t','x'},{'t','y'},{'x','z'},{'y','t'},{'y','x'},{'y','z'},{'z','s'},{'z','x'}};
        int[] w = {10,5,1,2,4,3,7,2,7,6};

        DirectGraph G = new DirectGraph(vertex, edge, w);

        Node s = G.V[4];
        DirectGraph.dijkstra(G, s);

        DirectGraph.printSingleSourcePath(G, s);

    }
}

class Node implements Comparable<Node> {
    int id;
    Node pi;
    int d;

    boolean isQ;

    public Node(int id, Node pi, int d) {
        this.id = id;
        this.pi = pi;
        this.d = d;
        this.isQ = false;
    }

    public Node(int id) {
        this(id, null, 0);
    }

    public Node() {
        this(0);
    }

    public int compareTo(Node v) {
        return Integer.compare(this.d, v.d);
    }

    public String toString() {
        switch (this.id) {
            case 0:
                return "s";
            case 1:
                return "t";
            case 2:
                return "x";
            case 3:
                return "y";
            case 4:
                return "z";
            default:
                return "null";
        }
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
    Node node;
    AdjNode next;
    int w;

    public AdjNode(Node node, AdjNode next, int w) {
        this.node = node;
        this.next = next;
        this.w = w;
    }

    public AdjNode(Node node, AdjNode next) {
        this(node, next, 0);
    }

    public AdjNode() {
        this(null, null);
    }
}

class DirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    public DirectGraph(char[] cid, char[][] ceId, int[] w) {
        //数字化表示
        int[] id = this.charCastInt(cid);
        int[][] eId = this.charCastInt(ceId);

        this.Vsize = id.length;
        this.V = new Node[id.length];
        this.E = new Edge[eId.length];
        this.Adj = new AdjNode[id.length];

        for(int i=0; i<id.length; i++) {
            this.V[i] = new Node(id[i]);
            this.Adj[i] = new AdjNode(this.V[i], null);
        }

        int ui;
        int vi;
        for(int i=0; i<eId.length; i++) {
            ui = eId[i][0];
            vi = eId[i][1];
            this.E[i] = new Edge(this.V[ui], this.V[vi], w[i]);
            this.E[i].w = w[i];
            this.Adj[ui].next = new AdjNode(this.V[vi], this.Adj[ui].next, w[i]);
        }
    }

    private int[][] charCastInt(char[][] c) {
        int[][] res = new int[c.length][];

        for(int i=0; i<c.length; i++) {
            res[i] = charCastInt(c[i]);
        }

        return res;
    }

    private int[] charCastInt(char[] c) {
        int[] res = new int[c.length];

        for(int i=0; i<c.length; i++) {
            res[i] = this.charCastInt(c[i]);
        }

        return res;
    }

    private int charCastInt(char c) {
        int t = 0;

        switch(c) {
            case 's':
                t = 0;
                break;
            case 't':
                t = 1;
                break;
            case 'x':
                t = 2;
                break;
            case 'y':
                t = 3;
                break;
            case 'z':
                t = 4;
                break;
            default:
                t = 0;
        }

        return t;
    }

    public static void dijkstra(DirectGraph G, Node s) {
        DirectGraph.initializeSingleSource(G, s);

        PriorityQueue<Node> Q = new PriorityQueue<Node>(G.Vsize);

        for(int i=0; i<G.Vsize; i++) {
            Q.add(G.V[i]);
            G.V[i].isQ = true;
        }

        Node u;
        while(!Q.isEmpty()) {
            u = Q.poll();
            u.isQ = false;

            for(AdjNode v=G.Adj[u.id].next; v!=null; v=v.next) {
                if(v.node.isQ) {
                    DirectGraph.relax(u, v.node, v.w);
                    Q.remove(v.node);
                    Q.add(v.node);
                }
            }
        }
    }

    private static void initializeSingleSource(DirectGraph G, Node s) {
        for(int i=0; i<G.Vsize; i++) {
            G.V[i].d = Integer.MAX_VALUE;
            G.V[i].pi = null;
        }

        s.d = 0;
    }

    private static void relax(Node u, Node v, int w) {
        int sum = DirectGraph.integerAdd(u.d, w);
        if(v.d > sum) {
            v.d = sum;
            v.pi = u;
        }
    }

    //处理最短路径算法中的无穷大加减法问题以及溢出问题
    private static int integerAdd(int x, int y) {
        if(x==Integer.MAX_VALUE && y!=Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        } else if(x!= Integer.MIN_VALUE && y==Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if(x==Integer.MIN_VALUE && y!=Integer.MAX_VALUE) {
            return Integer.MIN_VALUE;
        } else if(x!=Integer.MAX_VALUE && y==Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        if(x>0 && y>0) {
            if((x+y) < 0) {
                return Integer.MAX_VALUE;
            } else {
                return (x+y);
            }
        } else if(x<0 && y<0) {
            if((x+y) > 0) {
                return Integer.MIN_VALUE;
            } else {
                return (x+y);
            }
        }

        return (x+y);
    }

    public static void printSingleSourcePath(DirectGraph G, Node s) {
        for(int i=0; i<G.Vsize; i++) {
            if(s != G.V[i]) {
                DirectGraph.printPath(s, G.V[i], true);
                System.out.println();
            }
        }
    }

    private static void printPath(Node s, Node v, boolean flag) {
        if(s == v) {
            System.out.print(v + "->");
        } else if(v.pi == null) {
            System.out.print("there is no path from " + s + " to " + v);
        } else {
            DirectGraph.printPath(s, v.pi, false);
            if(!flag)
                System.out.print(v + "->");
            else
                System.out.print(v);
        }
    }
}
