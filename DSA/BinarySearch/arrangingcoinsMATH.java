public class arrangingcoinsMATH {
    public static int sqrt(long x) {
        if(x == 0) return 0;
        long low = 1,high = x;
        while(low<=high){
            long mid = low + (high - low)/2;
            if(mid == x/mid) return (int)mid;
            else if(mid > x / mid) high = mid - 1;
            else low = mid + 1;
        }
        return (int)high;
    }

    public static void main(String args[]) {
        int n = 13;
        long m = (long)n;
        int k =  (sqrt(8*m+1) - 1) / 2;
        System.out.println(k);
    }
}
