package Arrays;

import java.util.ArrayList;

public class AddOne {
    public static void main(String[] args) {

        int nums[] = {9,9,9};
        ArrayList<Integer> arr = new ArrayList<>();

        for(int num : nums){
            arr.add(num);
        }
        int n = arr.size();
        for(int i = n-1; i>=0; i--){
            if(arr.get(i) != 9) {
                arr.set(i, arr.get(i) + 1);
                break;
            }
            if(arr.get(i) == 9) {
                arr.set(i,0);
            }
            
        }
        if(arr.get(0) == 0){
            arr.add(0, 1);
        }

        System.out.println(arr);
    }
}
