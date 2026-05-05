public class MaximumCountofPositiveIntegerAndNegativeInteger {
    public int maximumCount(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        int posIndex = -1;
        int negIndex = -1;
        while(low <= high){
            int mid = (low+high) / 2;
            if(nums[mid] > 0) {
                posIndex = mid;
                high = mid - 1;
            }
            else low = mid + 1;
        }
        
        int pos;
        if(posIndex == -1) pos = 0;
        else pos = nums.length - posIndex;

        low = 0;
        high = nums.length-1;
        while(low <= high){
            int mid = (low+high) / 2;
            if(nums[mid] < 0) {
                negIndex = mid;
                low = mid + 1;
            }
            else high = mid - 1;
        }
        int neg = negIndex + 1;
        if(neg > pos) return neg;
        else return pos;
    }
}
