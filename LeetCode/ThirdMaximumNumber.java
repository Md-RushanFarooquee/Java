
public class ThirdMaximumNumber {
    public int thirdMax(int[] nums) {
        int n = nums.length;
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        boolean found2 = false;
        boolean found3 = false;

        for(int i = 0; i < n; i++){
            if(nums[i] > max1) max1 = nums[i];
        }
        for(int i = 0; i < n; i++){
            if(nums[i] != max1 && nums[i] > max2){
                max2 = nums[i];
                found2 = true;
            }
        }
        for(int i = 0; i < n; i++){
            if(nums[i] != max1 && nums[i] != max2 && nums[i] >= max3){
                max3 = nums[i];
                found3 = true;
            }
        }
        int thirdMAX;
        if(found3) thirdMAX = max3;
        else thirdMAX = max1;

        return thirdMAX;
    }
}

