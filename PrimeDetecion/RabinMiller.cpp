#include <iostream>
#include <cmath>
#include <algorithm>
#include <ctime>
#include <random>

using namespace std;

typedef long long ll;

ll mod_mul(ll a, ll b, ll n) {
	ll res = 0;
	while (b) {
		if (b & 1) {
			res = (res + a) % n;
		}
		a = (a + a) % n;
		b >>= 1;
	}
	return res;
}

ll mod_pow(ll a, ll b, ll n) {
	ll res = 1;
	while (b) {
		if (b & 1) {
			res = (res*a) % n;
		}
		a = (a*a) % n;
		b >>= 1;
	}
	return res;
}

//if n is a composite then return true
bool witness(ll a, ll n) {
	//special case for n=0,1,2
	if (n == 0) {
		return true;
	}
	if (n == 1 || n == 2) {
		return false;
	}
	//evaluate t and u, which are equivalute to u*2^t=n-1
	ll t = 0;
	ll u = n-1;
	while (!(u & 1)) {
		t++;
		u >>= 1;
	}

	ll x = mod_pow(a, u, n);
	ll x_pre = x;
	for (int i = 0; i < t; i++) {
		x = mod_pow(x_pre, 2, n);
		//non-trivial square root detection, if it is then n must be a composite
		if (x == 1 && x_pre != 1 && x_pre != (n - 1)) {
			return true;
		}
		x_pre = x;
	}
	return (x != 1);
}

//just for s detections
//if n is a prime then return true
bool rabinMiller(ll n, int s) {
	ll a;
	while (s--) {
		a = (rand() % (n - 1)) + 1; // 1 <= a <= n-1
		if (witness(a, n)) {
			return false;
		}
	}
	return true;
}

int main() {

	srand(time(0));

	ll n;
	int s = 20;
	for (int i = 0; i < 30; i++) {
		n = rand();
		if (rabinMiller(n, s)) {
			printf("%lld is a prime\n", n);
		}
		else {
			printf("%d is a composite\n", n);
		}
	}

	system("pause");
	return 0;
}
