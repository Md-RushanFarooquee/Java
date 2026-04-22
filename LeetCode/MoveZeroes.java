public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int numPosition = 0;

        for(int i = 0;i<n;i++){
            if(nums[i] != 0){
                nums[numPosition] = nums[i];
                numPosition++;
            }
        }
        for(int i = numPosition;i<n;i++){
            nums[i] = 0;
        }
    }
}
