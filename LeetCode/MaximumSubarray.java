public class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        int MAX = nums[0];
        int sum = 0;
        for(int i =0;i<nums.length;i++){
            sum+=nums[i];
            MAX = Math.max(sum,MAX);
            if(sum<0) sum = 0;
        }
        return MAX;
    }
}
