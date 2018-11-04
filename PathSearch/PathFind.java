package PathSearch;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class PathFind {

    public static void main(String[] args) {
        int wall = Integer.MAX_VALUE;
        int graphs[][] = {{1,1,1,1,1,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,wall,1},
                {1,1,1,1,1,1}};

        Graph G = new Graph(graphs);

        coordinate start = new coordinate(9,3);
        coordinate dest = new coordinate(2,5);

        ArrayDeque<node> path = Graph.Dijkstra(start,dest,G);
        for(node n : path) {
            System.out.print(n + "->");
        }
        System.out.println("end");

        G.clear();
        path = Graph.BestFirstSearch(start,dest,G);
        for(node n : path) {
            System.out.print(n + "->");
        }
        System.out.println("end");
    }
}

//二维平面图类
class Graph {
    int N;
    int rows;
    int cols;
    node[][] nodes;

    //四个方向增量
    static int Xadd[] = {1,-1,0,0};
    static int Yadd[] = {0,0,1,-1};

    public Graph(int[][] costs) {
        this.rows = costs.length;
        this.cols = costs[0].length;
        this.N = this.rows*this.cols;

        nodes = new node[this.rows][this.cols];
        for(int i=0; i<this.rows; i++) {
            for(int j=0; j<this.cols; j++) {
                nodes[i][j] = new node(i,j,costs[i][j]);
            }
        }
    }

    //恢复图的原始状态
    public void clear() {
        for(int i=0; i<this.rows; i++) {
            for(int j=0; j<this.cols; j++) {
                nodes[i][j].prev = null;
            }
        }
    }

    //启发式评估函数，用曼哈顿距离
    public static int heuristic(node a, node b) {
        //Manhattan distance on a square grid
        return Math.abs(a.row-b.row) + Math.abs(a.col-b.col);
    }

    //广度优先搜索，只能用于单位权重图，保证能找到最优解
    public static ArrayDeque<node> BreasdthFirstSearch(coordinate start, coordinate dest, Graph G) {

        boolean[][] visit = new boolean[G.rows][G.cols];
        ArrayDeque<node> path = new ArrayDeque<>();//路径存储

        node current; //当前结点

        current = G.nodes[start.x][start.y];
        current.prev = current;
        ArrayDeque<node> frontier = new ArrayDeque<>();//边界结点集合
        frontier.add(current);
        visit[start.x][start.y] = true;
        while(!frontier.isEmpty()) {
            current = frontier.poll();
            //到底终点
            if(current.row==dest.x && current.col==dest.y) {
                break;
            }
            for (int i=0; i<4; i++) {
                int newX = current.row + Graph.Xadd[i];
                int newY = current.col + Graph.Yadd[i];
                node next = G.nodes[newX][newY];
                if(!visit[next.row][next.col] && next.cost < Integer.MAX_VALUE) {
                    frontier.add(next);
                    next.prev = current;
                    visit[next.row][next.col] = true;
                }
            }
        }

        //construct Path
        current = G.nodes[dest.x][dest.y];
        while(current.prev != current) {
            path.addFirst(current);
            current = current.prev;
        }
        path.addFirst(current);

        return path;
    }

    //Dijkstra, 用于权重为正的图，保证能找到最优解
    public static ArrayDeque<node> Dijkstra(coordinate start, coordinate dest, Graph G) {
        boolean visit[][] = new boolean[G.rows][G.cols];
        int costSoFar[][] = new int[G.rows][G.cols]; //从起点到当前结点的距离
        ArrayDeque<node> path = new ArrayDeque<>(); //路径存储
        PriorityQueue<CostFromStart> frontier = new PriorityQueue<>(); //存储边界结点集合，利用最小堆

        CostFromStart currentCost;
        node current;
        node goal = G.nodes[dest.x][dest.y];
        int cost;

        current = G.nodes[start.x][start.y];
        current.prev = current;
        currentCost = new CostFromStart(current,0);

        costSoFar[start.x][start.y] = 0; //起点到起点距离初始化为0
        frontier.add(currentCost); //该最小堆以距离为变量进行存储
        while(!frontier.isEmpty()) {
            currentCost = frontier.poll();
            current = currentCost.current;
            visit[current.row][current.col] = true;
            if(current==goal) {
                break;
            }
            for(int i=0; i<4; i++) {
                int newX = current.row + Graph.Xadd[i];
                int newY = current.col + Graph.Yadd[i];
                node next = G.nodes[newX][newY];
                cost = currentCost.cost + next.cost;
                if(!visit[next.row][next.col] && next.cost<Integer.MAX_VALUE && (costSoFar[next.row][next.col]<=0 || cost<costSoFar[next.row][next.col])) {
                    costSoFar[next.row][next.col] = cost;
                    frontier.add(new CostFromStart(next,cost));
                    next.prev = current;
                }
            }
        }

        //construct Path
        current = G.nodes[dest.x][dest.y];
        while(current.prev!=current) {
            path.addFirst(current);
            current = current.prev;
        }
        path.addFirst(current);

        return path;
    }

    //Best First Search(BFS)，最佳优先搜索
    public static ArrayDeque<node> BestFirstSearch(coordinate start, coordinate dest, Graph G) {
        boolean visit[][] = new boolean[G.rows][G.cols];
        ArrayDeque<node> path = new ArrayDeque<>(); //路径存储
        PriorityQueue<CostFromStart> frontier = new PriorityQueue<>();

        node goal = G.nodes[dest.x][dest.y];
        node current;
        CostFromStart currentCost;
        int priority;//当前结点到目标的评估距离

        current = G.nodes[start.x][start.y];
        current.prev = current;
        currentCost = new CostFromStart(current,0);
        frontier.add(currentCost);

        while(!frontier.isEmpty()) {
            currentCost = frontier.poll();
            current = currentCost.current;
            visit[current.row][current.col] = true;
            if(current==goal) {
                break;
            }
            for(int i=0; i<4; i++) {
                int newX = current.row + Graph.Xadd[i];
                int newY = current.col + Graph.Yadd[i];
                node next = G.nodes[newX][newY];
                if(!visit[newX][newY] && next.cost<Integer.MAX_VALUE) {
                    priority = heuristic(goal,next);
                    frontier.add(new CostFromStart(next,priority));
                    next.prev = current;
                }
            }
        }

        //construct Path
        current = goal;
        while(current.prev!=current) {
            path.addFirst(current);
            current = current.prev;
        }
        path.addFirst(current);

        return path;
    }

    //A star algorithm, 在Dijkstra和最佳优先搜索之间折衷
    public static ArrayDeque<node> AStarSearch(coordinate start, coordinate dest, Graph G) {
        boolean visit[][] = new boolean[G.rows][G.cols];
        int costSoFar[][] = new int[G.rows][G.cols]; //从起点到当前结点的距离
        ArrayDeque<node> path = new ArrayDeque<>(); //路径存储
        PriorityQueue<CostFromStart> frontier = new PriorityQueue<>(); //边界结点集合

        int cost; //当前结点到起点的最小距离
        int priority; //当前结点到终点的评估距离与cost之间的trade-off，权重比为1:1

        node goal = G.nodes[dest.x][dest.y];
        node current;
        CostFromStart currentCost;

        current = G.nodes[start.x][start.y];
        current.prev = current;
        currentCost = new CostFromStart(current, 0);

        costSoFar[current.row][current.col] = 0;
        frontier.add(currentCost);
        while(!frontier.isEmpty()) {
            currentCost = frontier.poll();
            current = currentCost.current;
            visit[current.row][current.col] = true;

            if(current==goal) {
                break;
            }

            for(int i=0; i<4; i++) {
                int newX = current.row + Graph.Xadd[i];
                int newY = current.col + Graph.Yadd[i];
                node next = G.nodes[newX][newY];
                cost = costSoFar[next.row][next.col] + next.cost;
                if(!visit[next.row][next.col] && next.cost<Integer.MAX_VALUE && (costSoFar[next.row][next.col]<=0 || cost<costSoFar[next.row][next.col])) {
                    costSoFar[next.row][next.col] = cost;
                    priority = heuristic(goal, next) + cost;
                    frontier.add(new CostFromStart(next,priority));
                    next.prev = current;
                }
            }
        }

        current = goal;
        while(current.prev != current) {
            path.addFirst(current);
            current = current.prev;
        }
        path.addFirst(current);

        return path;
    }

}

//结点与到起点/终点距离类，用于Dijkstra和最佳优先搜索
class CostFromStart implements Comparable<CostFromStart> {
    node current;
    int cost;
    public CostFromStart(node current, int cost) {
        this.current = current;
        this.cost = cost;
    }
    public CostFromStart() {
        this(null,0);
    }
    public int compareTo(CostFromStart o) {
        return Integer.compare(this.cost,o.cost);
    }
}

//结点类
class node {
    int row;
    int col;

    int cost; //当cost为无穷大时，代表此结点不通

    node prev;

    public node(int row, int col, int cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
    }
    public node(int cost) {
        this(0,0,cost);
    }
    public node() {
        this(0);
    }

    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}

//坐标类
class coordinate{
    int x;
    int y;
    public coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public coordinate() {
        this(0,0);
    }
}
