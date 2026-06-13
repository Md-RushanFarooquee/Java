public class PartitionArrayAccordingToGivenPivot {
    public static int[] pivotArray(int[] nums, int pivot) {
        int ans[] = new int[nums.length];
        int k = 0;
        for(int num : nums) if(num < pivot) ans[k++] = num;
        for(int num : nums) if(num == pivot) ans[k++] = num;
        for(int num : nums) if(num > pivot) ans[k++] = num;
        return ans;
    } 
}
