import java.util.ArrayList;
import java.util.Arrays;

public class CommonElements {
    public static void main(String[] args) {
        int a [] = {3, 4, 2, 2, 4};
        int b [] = {3, 2, 2, 7};
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        ArrayList<Integer> commonElements = new ArrayList<>();

        while(i<a.length && j<b.length) { 
            if(a[i] == b[j]) {  commonElements.add(a[i]) ; i++ ; j++; } 
            else if(a[i] < b[j]) i++;
            else if(a[i] > b[j]) j++;
        }

        System.out.println(commonElements);
    }
}
