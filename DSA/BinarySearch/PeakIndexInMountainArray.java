// from leetcode
// 3 <= arr.length <= 105
// arr is guaranteed to be a mountain array.

public class PeakIndexInMountainArray {
        public int peakIndexInMountainArray(int[] arr) {
        int lo = 1;
        int hi = arr.length-2;
        while(lo<=hi){
            int mid = (lo + hi) / 2;
            if(arr[mid] > arr[mid+1] && arr[mid] > arr[mid-1]) return mid;
            else if(arr[mid] > arr[mid-1] && arr[mid] < arr[mid+1]) lo = mid+1;
            else hi = mid - 1;
        }
        return lo;
    }
}
