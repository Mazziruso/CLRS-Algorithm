package Charpter24.BellFordAlgorithm;

public class BellmanFordMain {
    public static void main(String[] args) {
//        char[] id = {'s', 't', 'x', 'y', 'z'};
//        char[][] eId = {{'t','x'},{'t','y'},{'t','z'},{'y','x'},{'y','z'},{'z','x'},{'z','s'},{'s','t'},{'s','y'}, {'x','t'}};
//        int[] w = {5,8,-4,-3,9,4,2,6,7,-2};

        //24.4-1 problem
        int[] id = {0, 1, 2, 3, 4, 5, 6};
        int[][] eId = {{2,1},{4,1},{3,2},{5,2},{6,2},{6,3},{2,4},{1,5},{4,5},{3,6},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6}};
        int[] w = {1,-4,2,7,5,10,2,-1,3,-8,0,0,0,0,0,0};

//        //24.4-2 problem
//        int[] id = {0,1,2,3,4,5};
//        int[][] eId = {{2,1},{5,1},{4,2},{2,3},{1,4},{3,4},{5,4},{3,5},{4,5},{0,1},{0,2},{0,3},{0,4},{0,5}};
//        int[] w = {4,5,-6,1,3,5,10,-4,-8,0,0,0,0,0};

        DirectGraph G = new DirectGraph(id, eId, w);

        Node s = G.V[0];
        boolean existFlag = DirectGraph.BellmanFord(G, s);

        if(existFlag) {
            DirectGraph.printSingleSourcePath(G, s);
        } else {
            System.out.println("there is a ring with negative weight");
        }

        System.out.println();
    }
}

class Node {
    int id;
    int d;
    Node pi;

    public Node(int id, int d, Node pi) {
        this.id = id;
        this.d = d;
        this.pi = pi;
    }

    public Node(int id) {
        this(id, 0, null);
    }

    public Node() {
        this(0);
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

//针对有向图
class DirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    AdjNode[] Adj;

    public DirectGraph(char[] cid, char[][] ceId, int[] w) {
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
        for(int i=0; i<eId.length; i++) {
            int ui = this.findNode(eId[i][0]);
            int vi = this.findNode(eId[i][1]);
            this.E[i] = new Edge(this.V[ui], this.V[vi], w[i]);
            this.E[i].w = w[i];
            this.Adj[ui].next = new AdjNode(this.V[vi], this.Adj[ui].next, w[i]);
        }
    }

    public DirectGraph(int[] id, int[][] eId, int[] w) {
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
            this.E[i].w = w[i];
            this.Adj[ui].next = new AdjNode(this.V[vi], this.Adj[ui].next, w[i]);
        }
    }

    private int findNode(char id) {
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

    public static boolean BellmanFord(DirectGraph G, Node s) {
        DirectGraph.initializeSingleSource(G, s);

        for(int i=0; i<(G.Vsize-1); i++) {
            for(int j=0; j<G.E.length; j++) {
                DirectGraph.relax(G.E[j].u, G.E[j].v, G.E[j].w);
            }
        }

        for(int i=0; i<G.E.length; i++) {
            if(G.E[i].v.d > (DirectGraph.integerAdd(G.E[i].u.d, G.E[i].w))) {
                return false;
            }
        }

        return true;
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
            System.out.print(v.id + "->");
        } else if(v.pi == null) {
            System.out.println("there is no path from " + s.id + " to " + v.id);
        } else {
            DirectGraph.printPath(s, v.pi, false);
            if(!flag)
                System.out.print(v.id + "->");
            else
                System.out.print(v.id + "   (" + v.d + ")");
        }
    }
}
