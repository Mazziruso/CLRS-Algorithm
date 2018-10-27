package Charpter25;

public class FastAllPairsSPahthsMain {
    public static void main(String[] args) {
        int[] id = {0,1,2,3,4};
        int[][] eId = {{0,1},{0,2},{0,4},{1,3},{1,4},{2,1},{3,0},{3,2},{4,3}};
        int[] w = {3,8,-4,1,7,4,2,-5,6};

        DirectGraph G = new DirectGraph(id, eId, w);

        DirectGraph.fastAllPairsShortPaths(G);

        printMat(G.L);

        DirectGraph.printAllPairsPaths(G);

        System.out.println();
    }

    private static void printMat(int[][] L) {
        for(int i=0; i<L.length; i++) {
            for(int j=0; j<L[i].length; j++) {
                System.out.print(String.format("%7d", (L[i][j])));
            }
            System.out.println();
        }
    }
}

class Node {
    int id;

    Node pi;

    public Node(int id, Node pi) {
        this.id = id;
        this.pi = pi;
    }

    public Node(int id) {
        this(id,null);
    }

    public Node() {
        this(0);
    }

    public String toString() {
        return "Node" + this.id;
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

class DirectGraph {
    int Vsize;
    Node[] V;
    Edge[] E;
    int[][] W; //邻接矩阵

    Node[][] pi;
    int[][] L;

    public DirectGraph(int[] id, int[][] eId, int[] w) {
        this.Vsize = id.length;
        this.V = new Node[this.Vsize];
        this.E = new Edge[eId.length];
        this.W = new int[this.Vsize][this.Vsize];

        for(int i=0; i<this.Vsize; i++) {
            this.V[i] = new Node(id[i]);
            for(int j=0; j<this.Vsize; j++) {
                this.W[i][j] = (i!=j) ? Integer.MAX_VALUE : 0;
            }
        }

        for(int i=0; i<eId.length; i++) {
            this.E[i] = new Edge(this.V[eId[i][0]], this.V[eId[i][1]], w[i]);
            this.W[eId[i][0]][eId[i][1]] = w[i];
        }
    }

    public static void fastAllPairsShortPaths(DirectGraph G) {
        G.pi = new Node[G.Vsize][G.Vsize];
        int n = G.W.length;
        int[][] L = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                L[i][j] = G.W[i][j];
            }
        }

        int m = 1;

        while(m < (n-1)) {
            L = DirectGraph.extendShortPaths(L, L);
            m = 2 * m;
        }

        G.L = L;

        DirectGraph.parentVertexMatrix(G);
    }

    public static void slowAllPairsShortPaths(DirectGraph G) {
        G.pi = new Node[G.Vsize][G.Vsize];
        int n = G.W.length;
        int[][] L = new int[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                L[i][j] = (i==j) ? 0 : Integer.MAX_VALUE;
            }
        }

        for(int i=0; i<n; i++) {
            L = DirectGraph.extendShortPaths(L, G.W);
        }

        G.L = L;

        DirectGraph.parentVertexMatrix(G);
    }

    private static int[][] extendShortPaths(int[][] L, int[][] W) {
        int n = L.length;
        int[][] L_tmp = new int[n][n];

        int sum;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                L_tmp[i][j] = Integer.MAX_VALUE;

                for(int k=0; k<n; k++) {
                    sum = DirectGraph.integerAdd(L[i][k], W[k][j]);
                    if(sum < L_tmp[i][j]) {
                        L_tmp[i][j] = sum;
                    }
                }
            }
        }

        return L_tmp;
    }

    private static void parentVertexMatrix(DirectGraph G) {
        int n = G.Vsize;
        G.pi = new Node[n][n];

        int tmp;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i != j) {
                    for(int k=0; k<n; k++) {
                        if(k != j) {
                            tmp = DirectGraph.integerAdd(G.L[i][j], DirectGraph.integerInverse(G.W[k][j]));
                            if(tmp == G.L[i][k]) {
                                G.pi[i][j] = G.V[k];
                            }
                        }
                    }
                }
            }
        }
    }

    private static int integerInverse(int x) {
        if(x == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        } else if(x == Integer.MAX_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return -x;
        }
    }

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

    public static void printAllPairsPaths(DirectGraph G) {
        int n = G.Vsize;

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i != j) {
                    System.out.print("From " + i + " to " + j + ": ");
                    DirectGraph.printPath(G, i, j, true);
                    System.out.println();
                }
            }
        }
    }

    private static void printPath(DirectGraph G, int i, int j, boolean flag) {
        if(i == j) {
            System.out.print(i + "->");
        } else if(G.pi[i][j] == null) {
            System.out.print("no path from " + i + " to " + j + " exists");
        } else {
            DirectGraph.printPath(G, i, G.pi[i][j].id, false);
            if(!flag) {
                System.out.print(j + "->");
            } else {
                System.out.print(j);
            }
        }
    }

}
