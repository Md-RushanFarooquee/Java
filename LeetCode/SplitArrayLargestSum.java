public class SplitArrayLargestSum {
    private boolean canSplit(int nums[],int k,int mid){
        int subArrayCount = 1;
        int currentSum = 0;
        for(int num : nums){
            if(currentSum + num <= mid) currentSum += num;
            else{
                currentSum = num;
                subArrayCount++;
            }
            if(subArrayCount > k) return false;
        }
        return true;
    }
    public int splitArray(int[] nums, int k) {
        int totalSum = 0;
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            max = Math.max(num,max);
            totalSum += num;
        }
        int lo = max;
        int hi = totalSum;       
        while(lo<=hi){
            int mid = lo + (hi-lo) / 2;
            if(canSplit(nums,k,mid)) hi = mid - 1;
            else lo = mid + 1;
        }
        return lo;
    }
}
