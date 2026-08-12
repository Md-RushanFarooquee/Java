import java.util.Arrays;

public class anagramsMETHOD2 {
    public static void main(String[] args) {
        String s1 = "race";
        String s2 = "care";
        boolean flag = true;
        if(s1.length() != s2.length()) flag = false;
        else {
        char arr1[] = s1.toCharArray();
        char arr2[] = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        for(int i =0;i<arr1.length;i++){
            if(arr1[i] != arr2[i]) {flag = false; break;}
            }
        }
        System.out.println(flag);
    }
}
