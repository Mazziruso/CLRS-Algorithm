#include <iostream>
#include <cstdio>
#include <cmath>
#include <algorithm>
#include <vector>
#include <map>

using namespace std;

struct node {
	int key;
	struct node *l;
	struct node *r;
	int H;
	node() {
		this->key = 0;
		l = nullptr;
		r = nullptr;
		this->H = 0;
	}
};

class AVLTree {
public:
	node *root;
	int N;

	static void preOrder(node *root) {
		if (root->H != 0) {
			printf("%d ", root->key);
			preOrder(root->l);
			preOrder(root->r);
		}
	}

	static void inOrder(node *root) {
		if (root->H != 0) {
			inOrder(root->l);
			printf("%d ", root->key);
			inOrder(root->r);
		}
	}

	static void postOrder(node *root) {
		if (root->H != 0) {
			postOrder(root->l);
			postOrder(root->r);
			printf("%d ", root->key);
		}
	}

	static void update(node *root) {
		root->H = max(root->l->H, root->r->H) + 1;
	}

	static node* left_rotate(node *root) {
		node *rchild = root->r;

		root->r = rchild->l;
		update(root);

		rchild->l = root;
		update(rchild);

		return rchild;
	}

	static node* right_rotate(node *root) {
		node *lchild = root->l;

		root->l = lchild->r;
		update(root);

		lchild->r = root;
		update(lchild);

		return lchild;
	}

	static node* LL(node *root) {
		return right_rotate(root);
	}

	static node* LR(node *root) {
		root->l = left_rotate(root->l);
		return right_rotate(root);
	}

	static node* RL(node *root) {
		root->r = right_rotate(root->r);
		return left_rotate(root);
	}

	 static node* RR(node *root) {
		return left_rotate(root);
	}

	static node* insert(node *root, int key) {
		//recursive bound
		if (root->H == 0) {
			root->key = key;
			root->H = 1;
			root->l = new node();
			root->r = new node();
			return root;
		}

		if (key < root->key) {
			root->l = insert(root->l, key);
			if (root->l->H > root->r->H + 1) {
				if (root->l->l->H > root->l->r->H) {
					root = LL(root);
				}
				else {
					root = LR(root);
				}
			}
		}
		else {
			root->r = insert(root->r, key);
			if (root->r->H > root->l->H + 1) {
				if (root->r->l->H > root->r->r->H) {
					root = RL(root);
				}
				else {
					root = RR(root);
				}
			}
		}

		update(root); //if no-rebalance to do, then need update the height of root
		return root;
	}

	static node* deleteNode(node *root, int key) {
		if (root->H == 0) {
			return nullptr;
		}

		if (key < root->key) {
			root->l = deleteNode(root->l, key);
			if (root->l->H + 1 < root->r->H) {
				if (root->r->l->H > root->r->r->H) {
					root = RL(root);
				}
				else {
					root = RR(root);
				}
			}
		}
		else if (key == root->key) { //find the succesive node and alternate the current node
			if (root->r->H == 0) {
				root = root->l;
			}
			else {
				node *sucParent;
				node *suc;
				if (root->r->H == 1) {
					sucParent = root;
					suc = root->r;
					root->r = new node();
				}
				else {
					sucParent = findSuccessor(root->r);
					suc = sucParent->l;
					sucParent->l = new node();
				}

				suc->l = root->l;
				suc->r = root->r;
				root = suc;

				if (root->r->H + 1 < root->l->H) {
					if (root->l->l->H > root->l->r->H) {
						root = LL(root);
					}
					else {
						root = LR(root);
					}
				}
			}
		}
		else {
			root->r = deleteNode(root->r, key);
			if (root->r->H + 1 < root->l->H) {
				if (root->l->l->H > root->l->r->H) {
					root = LL(root);
				}
				else {
					root = LR(root);
				}
			}
		}
		update(root);
		return root;
	}

	static node* findSuccessor(node *root) { //find the parent node of successor node recursively
		if (root->l->H == 1) {
			return root;
		}
		return findSuccessor(root->l);
	}

	static node* search(node *root, int key) {
		if (root->H == 0) {
			return nullptr;
		}

		if (key < root->key) {
			return search(root->l, key);
		}
		else if (key == root->key) {
			return root;
		}
		else {
			return search(root->r, key);
		}
	}

	static node* minimum(node *root) {
		if (root->H == 1) { //left node is null
			return root;
		}
		return minimum(root->l);
	}

	static node* maximum(node *root) {
		if (root->H == 1) { //right node is null
			return root;
		}
		return maximum(root->r);
	}
};

int main() {

	node *Tree = new node();

	Tree = AVLTree::insert(Tree, 88);
	Tree = AVLTree::insert(Tree, 70);
	Tree = AVLTree::insert(Tree, 61);
	Tree = AVLTree::insert(Tree, 96);
	Tree = AVLTree::insert(Tree, 120);
	Tree = AVLTree::insert(Tree, 90);
	Tree = AVLTree::insert(Tree, 65);

	AVLTree::inOrder(Tree);
	printf("\n");

	system("pause");
	return 0;
}