
import java.util.ArrayList;
import java.util.Collections;

public class PlusOne {
    public int[] plusOne(int[] digits) {
        ArrayList<Integer> ans = new ArrayList<>();
        
         int n = digits.length;
         int carry = 1;
         for(int i = n-1; i>=0; i--){
            if(digits[i] + carry <= 9){
                ans.add(digits[i] + carry);
                carry = 0;
            }
            else{
                ans.add(0);
                carry = 1;
            }
         }
         if(carry == 1){
            ans.add(1);
         }
         n = ans.size();
         int arr[] = new int[n];
         Collections.reverse(ans);

         for(int i = 0; i<n; i++){
            arr[i] = ans.get(i);
         }
         return arr;
    }
}
