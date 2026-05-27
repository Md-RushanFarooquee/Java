import java.util.Arrays;

public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        int num = 1;
        for(int i = 0; i<nums.length;i++){
            if(nums[i] > 0){
                if(nums[i] == num) num++;
            }
        }
        return num;
    }
}
