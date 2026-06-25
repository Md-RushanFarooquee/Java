package Arrays;
//same question in reverse order
import static Arrays.printArray.print;

public class Merge2SortedArrays {
    public static void main(String[] args) {
        int a [] = {2,5,6,9,2,0};
        int b [] = {1,3,4,5,7,8};
        int c [] = new int[a.length + b.length];
        // c = {1,2,3,4,5,5,6,7,8,9}
        
        print(c);
        merge(c,a,b);
        print(c);
    }

    public static void merge(int c[] , int a[], int b[]){
        int i=0,j=0,k=0;

        while(i < a.length && j < b.length) c[k++] = (a[i] < b[j]) ? a[i++] : b[j++];
        while(i<a.length) c[k++] = a[i++];
        while(j<b.length) c[k++] = b[j++];
    }
}
