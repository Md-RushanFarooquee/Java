public class ArrangingCoinsBINARYSEARCH {
    public int arrangeCoins(int n) {
        long lo = 0, hi = n, ans = 0;
        while(lo<=hi){
            long m = lo + (hi - lo) / 2;
            long k = m *(m + 1) /  2;
            if(k == n) return (int)m;
            else if(k > n) hi = m - 1;
            else{
                ans = m;
                lo = m + 1;
            }
        }
        return (int)ans;
    }
}
