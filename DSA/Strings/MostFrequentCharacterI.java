// my method by making frequency array
public class MostFrequentCharacterI {
    public static void main(String[] args) {
        String s = "testsample";
        System.out.println(getMaxOccuringChar(s));
    }
    public static char getMaxOccuringChar(String s) {
        int arr[] = count(s);
        char ch = s.charAt(0);
        int max = 0;
        for(int i = 0; i<26;i++){
            if(arr[i] > max){
                max = arr[i];
                ch = (char) (i + 97);
            } 
        }
        return ch;
    }
    public static int [] count(String s) {
        int count [] = new int[26];
        for(int i =0; i<s.length();i++){
                int n = 97;
                n = s.charAt(i) - n;
                count[n]++;
        }
            return count;
    }
}
