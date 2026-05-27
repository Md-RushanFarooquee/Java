public class SumOfGoodNumbers {
    public int sumOfGoodNumbers(int[] nums, int k) {
        int sum = 0;
        for(int i = 0;i<nums.length;i++){
            boolean leftGood = true, rightGood = true;
            if(i+k < nums.length){
                if(nums[i] <= nums[i+k]) rightGood = false;
            }
            if(i-k >= 0){
                if(nums[i] <= nums[i-k]) leftGood = false;
            }
            if(leftGood && rightGood) sum +=nums[i];
        }
        return sum;
    }
}
