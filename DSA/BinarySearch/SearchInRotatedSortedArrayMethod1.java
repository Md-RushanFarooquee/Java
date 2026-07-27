// find peak element and then apply binary search
public class SearchInRotatedSortedArrayMethod1 {
    public static void main(String[] args) {
        int nums[] = {5, 6, 7, 8, 9, 10,0,1, 2, 3};
        int target = 8;
        int ans = -1;
        int lo = 0, hi = nums.length-1;
        while(lo<hi){
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] > nums[hi]) lo = mid + 1;
            else hi = mid;
        }
        int min = nums[lo];
        int mindx = lo;
        if(target >= min && target <= nums[nums.length-1]){
            lo = mindx; hi = nums.length-1;
            while(lo<=hi){
                int mid = lo + (hi - lo) / 2;
                if(nums[mid] == target) {ans = mid; break;}
                else if (nums[mid] > target) hi = mid - 1;
                else lo = mid + 1;
            }
        }
        else {
            lo = 0; hi = mindx-1;
            while(lo<=hi){
                int mid = lo + (hi - lo) / 2;
                if(nums[mid] == target) {ans = mid; break;}
                else if (nums[mid] > target) hi = mid - 1;
                else lo = mid + 1;
            }
        }
        System.out.println(ans);

    }
}
