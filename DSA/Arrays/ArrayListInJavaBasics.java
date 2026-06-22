package Arrays;

import java.util.ArrayList;
import java.util.Collections;

// ArrayList = Array with dynamic size

public class ArrayListInJavaBasics {
    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>(); 
        arr.add(25);
        arr.add(21);
        arr.add(18);
        arr.add(5);
        arr.add(10);

        System.out.println(arr.get(2));  // corresponding to arr[2]
        arr.set(3, 50); // arr[3] = 50 , arr.set(index, element)

        System.out.println(arr);

        int n = arr.size();
        for(int i = 0; i< n; i++){
            System.out.print(arr.get(i) + " ");
        }
        System.out.println();

        arr.add(78); // add new element at the last
        arr.add(1,100); // add new element after the index and shift other elements

        System.out.println(arr);
        n = arr.size();
        arr.remove(n-1);
        System.out.println(arr);
        Collections.reverse(arr);
        System.out.println(arr);
    }
}
