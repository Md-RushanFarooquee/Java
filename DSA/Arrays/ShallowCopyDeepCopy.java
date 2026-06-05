package Arrays;

import java.util.Arrays;
public class ShallowCopyDeepCopy {

    public static void main(String[] args) {

    int arr[] = {10,20,30,40};
    int x []= arr;  //shallow copy
    x[0] = 100;      // changes in x = changes in original arr
    System.out.println(arr[0]);

    int y[] = Arrays.copyOf(arr,arr.length);  //deep copy
    y[1] = 100;
    System.out.println(arr[1]);   // no change in original
    }
}
