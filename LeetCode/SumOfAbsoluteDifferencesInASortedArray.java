public class SumOfAbsoluteDifferencesInASortedArray {
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int ans[] = new int[nums.length];
        int prefix[] = new int[nums.length];
        int suffix[] = new int[nums.length];
        for(int i = 0;i<nums.length - 1;i++){
            prefix[i+1] = prefix[i] + nums[i];
        }
        for(int i = nums.length - 1;i>=1;i--){
            suffix[i-1] = suffix[i] + nums[i];
        }
        for(int i = 0;i<nums.length;i++){
            int leftSum = nums[i] * i - prefix[i];
            int rightSum = suffix[i] - nums[i] * (nums.length - i - 1) ;
            ans[i] = leftSum + rightSum;
        }
        return ans;
    }
}
