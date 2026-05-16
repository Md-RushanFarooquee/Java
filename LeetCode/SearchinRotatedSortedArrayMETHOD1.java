public class SearchinRotatedSortedArrayMETHOD1 {
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length-1;
        while(lo<hi){
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] > nums[hi]) lo = mid + 1;
            else{
                hi = mid;
            } 
        }
        int min = nums[lo];
        int mindx = lo;
        if(target >= min && target <= nums[nums.length-1]){
            lo = mindx; hi = nums.length-1;
            while(lo<=hi){
                int mid = lo + (hi - lo) / 2;
                if(nums[mid] == target) return mid;
                else if (nums[mid] > target) hi = mid - 1;
                else lo = mid + 1;
            }
        }
        else {
            lo = 0; hi = mindx-1;
            while(lo<=hi){
                int mid = lo + (hi - lo) / 2;
                if(nums[mid] == target) return mid;
                else if (nums[mid] > target) hi = mid - 1;
                else lo = mid + 1;
            }
        }
        return -1;
    }
}
