public class MaximumCountofPositiveNegativeInteger {
    public static void main(String[] args) {
        int nums[] = {-2,-1,-1,0,0,1,2};
        int low = 0;
        int high = nums.length-1;
        int posIndex = -1;
        int negIndex = -1;
        // first positive number index :
        while(low <= high){
            int mid = (low+high) / 2;
            if(nums[mid] > 0) {
                posIndex = mid;
                high = mid - 1;
            }
            else low = mid + 1;
        }
        
        int pos;
        if(posIndex == -1) pos = 0;
        else pos = nums.length - posIndex;

        // first negative number index
        low = 0;
        high = nums.length-1;
        while(low <= high){
            int mid = (low+high) / 2;
            if(nums[mid] < 0) {
                negIndex = mid;
                low = mid + 1;
            }
            else high = mid - 1;
        }
        int neg = negIndex + 1;
        System.out.println("Negative : " + neg);
        System.out.println("Positive : " + pos);
    }
}
