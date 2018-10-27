package SubStringMatch;

public class KMPMain {
    public static void main(String[] args) {
        String pat = "AACAA";
        String text = "AABRAACADABRAACAADABRADDSACASAACAAAACD";

        KMPALPHA kmpObj1 = new KMPALPHA(pat);
        kmpObj1.search(text);

        System.out.println();

        KMPBETA kmpObj2 = new KMPBETA(pat);
        kmpObj2.search(text);
    }
}

class KMPALPHA {
    private String pat;
    private int[] pi;
    private int m;

    public KMPALPHA(String pat) {
        this.pat = pat;
        this.computePrefixFunc(pat);
        this.m = pat.length();
    }

    private void computePrefixFunc(String pat) {
        int m = pat.length();
        this.pi = new int[m];

        this.pi[0] = -1;
        int k=-1;
        for(int q=1; q<m; q++) {
            while(k>=0 && pat.charAt(k+1)!=pat.charAt(q)) {
                k = this.pi[k];
            }
            if(pat.charAt(k+1) == pat.charAt(q)) {
                k++;
            }
            this.pi[q] = k;
        }
    }

    public void search(String txt) {
        int n=txt.length();
        int q=-1;

        for(int i=0; i<n; i++) {
            while(q>=0 && this.pat.charAt(q+1)!=txt.charAt(i)) {
                q = this.pi[q];
            }
            if(this.pat.charAt(q+1) == txt.charAt(i)) {
                q++;
            }
            if(q == (this.m-1)) {
                System.out.println("Pattern occurs with shift " + (i-this.m+2));
                q = this.pi[q];
            }
        }
    }
}

class KMPBETA {
    private String pat;
    private int[][] dfa;
    private int nextJ;

    public KMPBETA(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        this.dfa = new int[R][M];
        this.dfa[pat.charAt(0)][0] = 1;

        int X = 0;
        for(int j=1; j<M; j++) {
            for(int c=0; c<R; c++) {
                this.dfa[c][j] = dfa[c][X];
            }
            this.dfa[pat.charAt(j)][j] = j + 1;
            X = this.dfa[pat.charAt(j)][X];
        }

        this.nextJ = X;
    }

    public void search(String txt) {
        int i, j, N=txt.length(), M=this.pat.length();

        for(i=0, j=0; i<N && j<M; i++) {
            j = this.dfa[txt.charAt(i)][j];

            if(j == M) {
                System.out.println("Pattern occurs with shift " + (i-M+2));
                j = this.nextJ;
            }
        }
    }

}
