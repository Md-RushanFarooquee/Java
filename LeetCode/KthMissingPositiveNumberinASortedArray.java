public class KthMissingPositiveNumberinASortedArray {
    public int findKthPositive(int[] arr, int k) {
        int lo = 0, hi = arr.length - 1;
        while(lo <= hi){
            int mid = lo + (hi -lo) / 2;
            int correctNO = mid + 1;
            int missing = arr[mid] - correctNO;
            if(k <= missing) hi = mid - 1;
            else lo = mid + 1;
        }
        return lo + k;
    }
}
