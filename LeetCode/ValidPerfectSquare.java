public class ValidPerfectSquare {
    public boolean isPerfectSquare(int num) {
        if(num == 0) return true;
        int lo = 1;
        int hi = num;
        while(lo<=hi){
            int mid = lo + (hi - lo) / 2;
            if(mid == num / mid && num % mid == 0) return true;
            else if(mid > num / mid) hi = mid-1;
            else lo = mid + 1;
        }
        return false;
    }
}
