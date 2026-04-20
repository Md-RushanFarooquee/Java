public class ReverseString {
    public void reverseString(char[] s) {
        int index1 = 0;
        int index2 = s.length-1;
        while(index1 < index2){
            char temp = s[index1];
            s[index1] = s[index2];
            s[index2] = temp;
            index1++;
            index2--;
        }
    }
}
