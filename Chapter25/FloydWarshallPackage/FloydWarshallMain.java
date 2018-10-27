package Charpter25.FloydWarshallPackage;

public class FloydWarshallMain {
    public static void main(String[] args) {
        int[] id = {0,1,2,3,4};
        int[][] eId = {{0,1},{0,2},{0,4},{1,3},{1,4},{2,1},{3,0},{3,2},{4,3}};
        int[] w = {3,8,-4,1,7,4,2,-5,6};

        DirectGraph G = new DirectGraph(id, eId, w);

        DirectGraph.FloydWarshall(G);

        printMat(G.L);

        DirectGraph.printAllPairsPaths(G);
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

    public static void FloydWarshall(DirectGraph G) {
        int n = G.W.length;

        G.L = G.W.clone();
        G.pi = new Node[n][n];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(i==j || G.W[i][j]==Integer.MAX_VALUE) {
                    G.pi[i][j] = null;
                } else {
                    G.pi[i][j] = G.V[i];
                }
            }
        }

        for(int k=0; k<n; k++) {
            FWBottomUp(G, k);
        }

    }

    private static void FWBottomUp(DirectGraph G, int k) {
        int n = G.Vsize;

        int[][] D_tmp = new int[n][n];
        Node[][] pi = new Node[n][n];

        int tmp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp = DirectGraph.integerAdd(G.L[i][k], G.L[k][j]);
                D_tmp[i][j] = (G.L[i][j]<tmp) ? G.L[i][j] : tmp;
                pi[i][j] = (G.L[i][j] > tmp) ? G.pi[k][j] : G.pi[i][j];
            }
        }

        G.pi = pi;
        G.L = D_tmp;
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
