public class MaximumTotalSubarrayValueI {
    public long maxTotalValue(int[] nums, int k) {
        long max = Integer.MIN_VALUE;
        long min = Integer.MAX_VALUE;
        for(long num : nums){
            if(num > max) max = num;
            if(num < min) min = num;
        }
        return (max - min) * k;
    }
}
