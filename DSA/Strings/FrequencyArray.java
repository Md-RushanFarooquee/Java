public class FrequencyArray {
    public static void main(String[] args) {
        String s = "testsample";
        System.out.println(getMaxOccuringChar(s));
    }
    public static char getMaxOccuringChar(String s) {
        int n = s.length();
        int freq[]= new int[26];
        for(int i = 0;i<n;i++){
            char ch = s.charAt(i);
            int idx = ch - 97;
            freq[idx]++;
        }
        int maxfreq = 0;
        char ans = s.charAt(0);
        for(int i = 0; i<26;i++){
            if(freq[i]> maxfreq){
                maxfreq = freq[i];
                ans = (char) (i + 97);
            } 
        }
        return ans;
    }
}
