public class MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int max[] = new int[n];
        int maxIndex = 0;
        int count = 0;
        for(int i = 0 ;i<n;i++){
            if(nums[i] != 0) count++;
            else{
                max[maxIndex++] = count;
                count = 0;
            }
        }
        if(maxIndex < n)  max[maxIndex++] = count;
        int consecutive = 0;
        for(int i =0; i<maxIndex;i++){
            if(max[i] > consecutive ) consecutive = max[i];
        }
        return consecutive;
    }
}
