package Arrays;

//same question in reverse order

import static Arrays.printArray.print;


public class Merge2SortedArraysMethod2 {
    public static void main(String[] args) {
        int a [] = {2,5,6,9,20};
        int b [] = {1,3,4,5,7,8};
        int c [] = new int[a.length + b.length];

        print(c);
        merge(c, a, b);
        print(c);
    }
    public static void merge(int c[], int a[], int b[]){
        int i =a.length - 1,j=b.length - 1,k=c.length- 1;

        while(i>=0 && j >=0) c[k--] = (a[i] > b[j]) ? a[i--] : b[j--];
        while(i>=0) c[k--] = a[i--];
        while(j>=0) c[k--] = b[j--];
    }
}
