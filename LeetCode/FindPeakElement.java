public class FindPeakElement {
    public int findPeakElement(int[] nums) {
        int low = 0;
        int high = nums.length -1;
        int idx = -1;
        while(low < high){
            int mid = (low + high) / 2;
            if(nums[mid] > nums[mid+1]) high = mid;
            else{
                idx = low;
                low = mid + 1;
            }
        }
        return low;
    }
}
