public class ArrangingCoinsBINARYSEARCH {
    public static void main(String[] args) {
        int n = 3;
        long lo = 0, hi = n, ans = 0;
        while(lo<=hi){
            long m = lo + (hi - lo) / 2;
            long k = m *(m + 1) /  2;
            if(k == n) {ans = m; break;}
            else if(k > n) hi = m - 1;
            else{
                ans = m;
                lo = m + 1;
            }
        }
        System.out.println((int) ans);
    }
}
