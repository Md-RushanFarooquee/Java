public class MissingNumber {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sumArr = 0;
        for(int i = 0; i<n; i++){
            sumArr += nums[i];
        }
        int sum = n*(n+1) / 2;

        int missing = sum - sumArr;

        return missing;
    }
}
