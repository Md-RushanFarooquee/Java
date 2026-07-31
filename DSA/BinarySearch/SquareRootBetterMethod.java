// T.C = O(logn) by Binary Search
public class SquareRootBetterMethod {
    public static void main(String[] args) {
        int x = 20;
        int low = 1;
        int high = x;
        int ans = 0;
        if(x == 0) System.out.println(x);
        else{
        while(low<=high){
            int mid = low + (high - low) / 2;
            if(mid == x / mid) {ans = mid; break;}
            else if(mid > x / mid) high = mid - 1;
            else low = mid + 1;
            }
        }
        if(ans!=0) System.out.println(ans);
        else System.out.println(high);
    }
}
