public class SortColors {
    public void sortColors(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        int mid = 0;
        while(mid<=hi){
            if(nums[mid] == 0){
                swap(nums,lo,mid);
                lo++;
                mid++;
            }
            else if(nums[mid] == 2){
                swap(nums,mid,hi);
                hi--;
            }
            else{
                mid++;
            }
        }
    }
    public void swap(int arr[],int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
