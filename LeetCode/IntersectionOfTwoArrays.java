import java.util.ArrayList;
import java.util.Arrays;

public class IntersectionOfTwoArrays {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0;
        int j = 0;
        ArrayList<Integer> intersection = new ArrayList<>();

        while(i < nums1.length && j < nums2.length ){

            if(nums1[i] == nums2[j] && intersection.isEmpty()){
                 intersection.add(nums1[i]);
                 i++;
                 j++;
            }
            else if(nums1[i] == nums2[j] && intersection.get(intersection.size()-1) != nums1[i]){
                 intersection.add(nums1[i]);
                 i++;
                 j++;
            }
            else if(nums1[i] < nums2[j]) i++;
            else j++;
        }
        int arr[] = new int[intersection.size()];
        for(int k =0 ; k<intersection.size();k++){
            arr[k] = intersection.get(k);
        }
        return arr;
    }
}
