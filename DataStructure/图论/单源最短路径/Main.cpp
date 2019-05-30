#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>

using namespace std;

namespace GraphOps {
    template<typename T>
    class Graph {
    public:
        Graph(vector<vector<int>> Gtable) {
            this->N = Gtable.size();
            visit.resize(N, false);
            G.resize(N, vector<int>());
            for(int i=0; i<N; i++) {
                for(int j=i+1; j<N; j++) {

                }
            }
        }
        void DFS(int);
        void BFS(int);
    private:
        int N;
        vector<bool> visit;
        vector<vector<int>> G;
    };

    template<typename T>
    void Graph<T>::DFS(int node) {
        if(visit[node]) {
            return;
        }
        visit[node] = true;
        cout << node << ", ";
        for(const auto &v : G[node]) {
            DFS(v);
        }
        visit[node] = false;
    }

    template<typename T>
    void Graph<T>::BFS(int s) {
        //initial
        fill_n(visit.begin(), N, false);
        //
        queue<int> q;
        int node;
        //
        q.push(s);
        visit[s] = true;
        while(!q.empty()) {
            node = q.front();
            q.pop();
            cout << node << ",";
            for(const auto &v : G[node]) {
                if(!visit[v]) {
                    q.push(v);
                    visit[v] = true;
                }
            }
        }
    }
}

namespace GraphDef {
    template<typename T>
    class GraphBase {
    protected:
        int N;
        vector<T> dist;
        vector<bool> visit;
        vector<vector<pair<int,T>>> G;
    public:
        GraphBase(int N) {
            this->N = N;
            visit.resize(N, false);
            dist.resize(N);
            G.resize(N,vector<pair<int,T>>());
        }
        GraphBase(vector<vector<T>> Gtable) {
            this->N = Gtable.size();
            visit.resize(N, false);
            dist.resize(N);
            G.resize(N, vector<pair<int,T>>());
            for(int i=0; i<N; i++) {
                for(int j=i+1; j<N; j++) {
                    if(Gtable[i][j] > 0) {
                        G[i].push_back(pair<int,T>(j,Gtable[i][j]));
                        G[j].push_back(pair<int,T>(i,Gtable[j][i]));
                    }
                }
            }
        }
        void printD();
        virtual void shortDist(int);
    };

    template<typename T>
    void GraphBase<T>::printD() {
        for(const auto &d : dist) {
            cout << d << ", ";
        }
        cout << endl;
    }

    template<typename T>
    void GraphBase<T>::shortDist(int s) {
        fill_n(dist.begin(), N, INT32_MAX);
        dist[s] = 0;
    }
}

namespace DijkstraDist {
    using GraphDef::GraphBase;

    template<typename T>
    class Graph : public GraphBase<T> {
    public:
        Graph(vector<vector<T>> Gtable) : GraphBase<T>(Gtable) {};
        //dijkstra
        void shortDist(int);
    };

    template<typename T>
    void Graph<T>::shortDist(int s) {
        //inital
        fill_n(this->dist.begin(), this->N, INT32_MAX);
        fill_n(this->visit.begin(), this->N, false);
        //
        this->dist[s] = 0;
        int k;
        for(int i=1; i<this->N; i++) {
            k = -1;
            for(int j=0; j<this->N; j++) {
                if(!this->visit[j] && (k<0 || this->dist[j]<this->dist[k])) {
                        k = j;
                }
            }
            this->visit[k] = true;
            for(auto &node : this->G[k]) {
                if(!this->visit[node.first] && this->dist[node.first]>this->dist[k]+node.second) {
                    this->dist[node.first] = this->dist[k] + node.second;
                }
            }
        }
    }
}

namespace HeapDist {
    using GraphDef::GraphBase;

    template<typename T>
    class Graph : public GraphBase<T> {
    public:
        Graph(vector<vector<T>> Gtable) : GraphBase<T>(Gtable) {};
        void shortDist(int);
    };

    template<typename T>
    void Graph<T>::shortDist(int s) {
            //initial
            fill_n(this->dist.begin(), this->N, INT32_MAX);
            fill_n(this->visit.begin(), this->N, 0);
            //
            priority_queue<pair<T,int>,vector<pair<T,int>>,greater<pair<T,int>>> q;
            int node;

            this->dist[s] = 0;
            q.emplace(0,s);
            while(!q.empty()) {
                node = q.top().second;
                q.pop();
                if(this->visit[node]) {
                    continue;
                }
                this->visit[node] = true;
                for(const auto &qbase : this->G[node]) {
                    if(!this->visit[qbase.first] && this->dist[qbase.first]>this->dist[node]+qbase.second) {
                        this->dist[qbase.first] = this->dist[node] + qbase.second;
                        q.emplace(this->dist[qbase.first], qbase.first);
                    }
                }
            }
        }
}

namespace SPFADist {
    using GraphDef::GraphBase;

    template<typename T>
    class Graph : public GraphBase<T> {
    public:
        Graph(vector<vector<T>> Gtable) : GraphBase<T>(Gtable) {};
        void shortDist(int);
    };

    template<typename T>
    void Graph<T>::shortDist(int s) {
            //initial
            fill_n(this->dist.begin(), this->N, INT32_MAX);
            fill_n(this->visit.begin(), this->N, false);

            queue<int> q;
            int node;
            //
            q.push(s);
            this->dist[s] = 0;
            this->visit[s] = true;
            while(!q.empty()) {
                node =q.front();
                q.pop();
                for(const auto &v : this->G[node]) {
                    if(this->dist[v.first] > this->dist[node]+v.second) {
                        this->dist[v.first] = this->dist[node] + v.second;
                        if(!this->visit[v.first]) {
                            q.push(v.first);
                        }
                    }
                }
            }
        }
}


int main(int argc, char *argv[]) {

    vector<vector<int>> Gtable({{0,1,3,4},{1,0,2,2},{3,2,0,1},{4,2,1,0}});

    SPFADist::Graph<int> G(Gtable);
    DijkstraDist::Graph<int> G1(Gtable);
    HeapDist::Graph<int> G2(Gtable);

    G.shortDist(0);
    G.printD();

    G1.shortDist(0);
    G1.printD();

    G2.shortDist(0);
    G2.printD();

    exit(0);
}
