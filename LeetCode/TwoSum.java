package LeetCode;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        int index1 = 0 ,index2 = 0;
        for(int i = 0; i < n ; i++) {
            for(int j = i + 1;j < n ; j++) {
            sum =  nums[i] + nums[j];
            if (sum == target){
               index1 = i;
               index2 = j;
               return new int[]{index1,index2};
            }
            }
        }
        return new int[]{index1,index2};
    }
