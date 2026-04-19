public class SingleNumber {
    public int singleNumber(int[] nums) {
        int target = nums[0];
        for(int i = 0; i<nums.length;i++){
            int count = 0;
            for(int j = 0;j<nums.length;j++){
                if(nums[i] == nums[j]) count++;
            }
            if(count == 1){
                target = nums[i];
                return target;
            }
        }
        return target;
    }
}
