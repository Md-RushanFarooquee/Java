public class ApplyOperationstoanArray {
    public int[] applyOperations(int[] nums) {
        int n = nums.length;
        int numPosition = 0;
        for(int i = 0; i<n-1;i++){
                if(nums[i] == nums[i+1]){
                    nums[i] *=2 ;
                    nums[i+1] =0; 
                }
        }
        for(int i = 0; i<n;i++){
            if(nums[i] !=0){
                nums[numPosition] = nums[i];
                numPosition++;
            }
        }
        for(int i = numPosition;i<n;i++){
            nums[i] = 0;
        }
        return nums;
    }
}
