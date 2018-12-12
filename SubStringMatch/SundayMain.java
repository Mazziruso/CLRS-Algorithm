package SubStringMatch;

import java.util.Arrays;

public class SundayMain {

    public static void main(String[] args) {
        SundayMatch s = new SundayMatch();
        String pat = "AACAA";
        String text = "AABRAACADABRAACAADABRADDSACASAACAAAACD";
        int cnt = s.matching(text, pat);
        System.out.println(cnt + " times");
    }

}

class SundayMatch {
    private int[] shift;
    private void getShift(String pattern) {
        int m = pattern.length();
        this.shift = new int[256];
        Arrays.fill(this.shift,m+1);
        for(int i=0; i<m; i++) {
            this.shift[pattern.charAt(i)] = m - i;
        }
    }
    public int matching(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();
        getShift(pattern);
        int s = 0;
        int j = 0;
        int cnt = 0;
        while(s<=n-m) {
            j = 0;
            while (j < m && text.charAt(s + j) == pattern.charAt(j)) {
                j++;
            }
            if (j >= m) {
                System.out.println("Pattern occurs with shift " + (s + 1));
                cnt++;
            }
            s += this.shift[text.charAt(s + m)];
        }
        return cnt;
    }
}