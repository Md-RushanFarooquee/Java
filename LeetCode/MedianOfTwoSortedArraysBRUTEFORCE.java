public class MedianOfTwoSortedArraysBRUTEFORCE {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int c [] = new int[nums1.length + nums2.length];
        merge(c,nums1,nums2);
        double median = 0;
        int n = c.length;
        if(n % 2 == 0) median = (c[n/2 - 1]  + c[n/2]) / 2.0;
        else median = c[(n/2)];     
        return median;
        }
    public static void merge(int c[] , int a[], int b[]){
        int i=0,j=0,k=0;

        while(i < a.length && j < b.length) c[k++] = (a[i] < b[j]) ? a[i++] : b[j++];
        while(i<a.length) c[k++] = a[i++];
        while(j<b.length) c[k++] = b[j++];
    }
}
