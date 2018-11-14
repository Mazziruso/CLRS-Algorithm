#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>

using namespace std;

int m = 44851; //选择靠近最大元素数的最小质数
int hashTable[45000];

//插入元素，返回冲突次数
int hashInsert(int key) {
	int cnt = 0;
	int gk = (key + 1) % (m - 2);
	int hk = key % m;
	while (hashTable[hk] >= 0 && hashTable[hk] != key) {
		hk = (hk + gk) % m;
		cnt++;
	}
	hashTable[hk] = key;
	return cnt;
}

//查找元素是否在表里
bool hashCount(int key) {
	int gk = (key + 1) % (m - 2);
	int hk = key % m;
	while (hashTable[hk]>=0 && hashTable[hk] != key) {
		hk = (hk + gk) % m;
	}
	return hashTable[hk] >= 0;
}

int main() {
	
	fill_n(hashTable, 45000, -1);

	int cnt = 0;
	int N = m-100;
	int seq;
	for (int i = 0; i < N; i++) {
		seq = rand() % 10000;
		cnt += hashInsert(seq);
	}

	printf("%d %.2f%%\n", cnt, (1.0*cnt) / N); //总共冲突次数和冲突率

	system("pause");
	return 0;
}
