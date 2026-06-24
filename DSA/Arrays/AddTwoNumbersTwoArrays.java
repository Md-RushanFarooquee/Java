package Arrays;

import java.util.ArrayList;
import java.util.Collections;

public class AddTwoNumbersTwoArrays {
    public static void main(String[] args) {
        int arr1[] = {3,9,2,0,7};
        int arr2[] = {9,2,1};

        ArrayList<Integer> ans = new ArrayList<>();

        int n1 = arr1.length;
        int n2 = arr2.length;
        int lenDiff;
        if(n1>n2) lenDiff = n1 - n2;
        else lenDiff = n2-n1;
        int index1 = n1-1;
        int index2 = n2-1;
        int carry = 0;
        if(n1 > n2){
            for(int i = n1-1; i>=lenDiff;i--){
                if(arr1[index1] + arr2[index2] + carry <= 9){
                    ans.add(arr1[index1] + arr2[index2] + carry);
                    carry = 0;
                }
                else if(arr1[index1] + arr2[index2] + carry > 9){
                    ans.add(arr1[index1] + arr2[index2] + carry - 10);
                    carry = 1;
                }
                index1--;
                index2--;
            }
            if(index2 == -1 && carry == 1){
                while(arr1[index1] == 9 && index1 != 0){
                    ans.add(arr1[index1] + 1 - 10);
                    index1--;
                    carry = 0;
                }
            }
            if(arr1[0] == 9){
                ans.add(arr1[0] + 1 - 10);
                ans.add(1);
                carry = 0;
            }
            while(index1>=0){
                ans.add(arr1[index1]);
                index1--;
            }
            
            if(carry == 1){
                ans.add(1);
            }
        }
        else{
            for(int i = n2-1; i>=lenDiff;i--){
                if(arr1[index1] + arr2[index2] + carry <= 9){
                    ans.add(arr1[index1] + arr2[index2] + carry);
                    carry = 0;
                }
                else if(arr1[index1] + arr2[index2] + carry > 9){
                    ans.add(arr1[index1] + arr2[index2] + carry - 10);
                    carry = 1;
                }
                index1--;
                index2--;
            }
            if(index1 == -1 && carry == 1){
                while(arr2[index2] == 9 && index2 != 0){
                    ans.add(arr2[index2] + 1 - 10);
                    index2--;
                    carry = 0;
                }
            }
            if(arr2[0] == 9){
                ans.add(arr2[0] + 1 - 10);
                ans.add(1);
                carry = 0;
            }
            while(index2>=0){
                ans.add(arr2[index2]);
                index2--;
            }
            if(carry == 1){
                ans.add(1);
            }
        }

        Collections.reverse(ans);
        
        System.out.println(ans);
       
    }
}


