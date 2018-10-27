package Charpter24.DagShortPath;

public class DagShortPathMain {
    public static void main(String[] args) {
        char[] id = {'r','s','t','x','y','z'};
        char[][] eId = {{'r','s'},{'r','t'},{'s','t'},{'s','x'},{'t','x'},{'t','y'},{'t','z'},{'x','y'},{'x','z'},{'y','z'}};
        int[] w = {5, 3, 2, 6, 7, 4, 2, -1, 1, -2};

        DirectGraph G = new DirectGraph(id, eId, w);
        G.DFS();

        Node s = G.V[0];
        DirectGraph.dagShortestPaths(G, s);

        DirectGraph.printSingleSourcePath(G, s);

        System.out.println();
    }
}

class Node {
    int id;
    int d;
    int f;
    Node pi;
    char color;

    int distance;

    public Node(int id, char color, int d, int f, Node pi, int distance) {
        this.id = id;
        this.color = color;
        this.d = d;
        this.f = f;
        this.pi = pi;
        this.distance = distance;
    }

    public Node(int id, char color, int d, Node pi) {
        this(id, color, d, 0, pi, Integer.MAX_VALUE);
    }

    public Node(int id) {
        this(id, 'W', Integer.MAX_VALUE, null);
    }

    public Node() {
        this('\u0000');
    }

    public String toString() {
        switch(this.id) {
            case 0:
                return "r";
            case 1:
                return "s";
            case 2:
                return "t";
            case 3:
                return "x";
            case 4:
                return "y";
            case 5:
                return "z";
            default:
                return "0";
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

//针对有向无环图
class DirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    AdjNode NodeList;//维持一个结点链表并以拓扑顺序排列

    static int time; //时间戳

    public DirectGraph(char[] cid, char[][] ceId, int[] w) {
        //数字化表示
        int[] id = this.charCastInt(cid);
        int[][] eId = this.charCastInt(ceId);

        this.NodeList = null;

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
            case 'r':
                t = 0;
                break;
            case 's':
                t = 1;
                break;
            case 't':
                t = 2;
                break;
            case 'x':
                t = 3;
                break;
            case 'y':
                t = 4;
                break;
            case 'z':
                t = 5;
                break;
            default:
                t = 0;
        }

        return t;
    }

    private int findNode(int id) {
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

    public static void dagShortestPaths(DirectGraph G, Node s) {
        G.DFS();
        DirectGraph.initializeSingleSource(G, s);

        //or u!=null(it also can work)
        for(AdjNode u=G.NodeList; u.next!=null; u=u.next) {
            for(AdjNode v=G.Adj[u.node.id].next; v!=null; v=v.next) {
                DirectGraph.relax(u.node, v.node, v.w);
            }
        }
    }

    private static void initializeSingleSource(DirectGraph G, Node s) {
        for(int i=0; i<G.Vsize; i++) {
            G.V[i].distance = Integer.MAX_VALUE;
            G.V[i].pi = null;
        }

        s.distance = 0;
    }

    private static void relax(Node u, Node v, int w) {
        int sum = DirectGraph.integerAdd(u.distance, w);
        if(v.distance > sum) {
            v.distance = sum;
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

    public void DFS() {

        //Initialization all nodes
        for(int i=0; i<this.Vsize; i++) {
            this.V[i].color = 'W';
            this.V[i].pi = null;
        }
        this.NodeList = null;

        DirectGraph.time = 0;

        for(int i =0; i<this.Vsize; i++) {
            if(this.V[i].color == 'W') {
                this.DFSVisit(this.V[i]);
            }
        }

    }

    private void DFSVisit(Node u) {
        DirectGraph.time++;
        u.d = DirectGraph.time;
        u.color = 'G';
        for(AdjNode v=this.Adj[u.id]; v!=null; v=v.next) {
            if(v.node.color == 'W') {
                v.node.pi = u;
                this.DFSVisit(v.node);
            }
        }
        u.color = 'B';
        DirectGraph.time++;
        u.f = DirectGraph.time;
        NodeList = new AdjNode(u, NodeList);
    }
}
