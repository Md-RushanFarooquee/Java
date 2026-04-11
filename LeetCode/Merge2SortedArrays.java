public class Merge2SortedArrays {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
       int i = 0,j = 0,k = 0;
       int c [] = new int[m+n];

       while(i<m && j<n) c[k++] = (nums1[i] < nums2[j]) ? nums1[i++] : nums2[j++];
       while(i<m) c[k++] = nums1[i++];
       while(j<n) c[k++] = nums2[j++];

       for(i = 0; i<m+n; i++){
        nums1[i] = c[i];
       }
    }
}
