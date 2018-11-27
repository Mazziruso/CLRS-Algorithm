#include <iostream>
#include <cmath>
#include <cstdio>
#include <algorithm>
#include <vector>
#include <queue>
#define maxn 100001

using namespace std;

int N;
vector<int> graph[maxn];
int inDegree[maxn];
vector<int> path; //topological path

void topologicalSort() {
	//initializaiton
	queue<int> q;

	//find inDegree of vertice is 0, and inqueue
	for (int i = 1; i < N; i++) {
		if (inDegree[i] == 0) {
			q.push(i);
		}
	}

	int node;
	while (!q.empty()) {
		node = q.front();
		q.pop();
		path.push_back(node);
		for (auto i : graph[node]) {
			inDegree[i]--;
			if (inDegree[i] == 0) {
				q.push(i);
			}
		}
		//graph[node].clear();
	}
}

void printPath() {
	printf("%d", path[0]);
	for (int i = 1; i < path.size(); i++) {
		printf("->%d", path[i]);
	}
	printf("\n");
}

int main() {

	//construcrt directed acyclic graph
	//...

	topologicalSort();
	printPath();

	system("pause");
	return 0;
}