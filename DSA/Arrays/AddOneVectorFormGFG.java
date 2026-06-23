package Arrays;

import java.util.Vector;

public class AddOneVectorFormGFG {
    Vector<Integer> addOne(int[] arr) {
       int n = arr.length;
       
       Vector<Integer> answer = new Vector<>();
       
       for(int i = n - 1; i >= 0; i--) {
            if (arr[i] != 9){
                arr[i]++;   
                break;
            }   
            if (arr[i]== 9){
                arr[i] = 0;
            } 
       }
        if (arr[0] == 0)
        {
            answer.add(0, 1);
        }
        
         for (int num : arr) {
             answer.add(num);
        }
        return answer;
    }
}

