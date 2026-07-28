// check in a single binary search
public class SearchInRotatedSortedArrayMethod2 {
    public static void main(String[] args) {
        int nums[] = {5, 6, 7, 8, 9, 10,0,1, 2, 3};
        int target = 8;
        int ans = -1;
        int lo = 0, hi = nums.length-1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] == target) {ans = mid; break;}
            else if(nums[mid] >= nums[lo]){
                if(nums[mid] > target && nums[lo] <= target) hi = mid - 1;
                else lo = mid + 1;
            }
            else{ // mid to high is sorted
                if(nums[mid] < target && nums[hi] >= target) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        System.out.println(ans);
    }
}
