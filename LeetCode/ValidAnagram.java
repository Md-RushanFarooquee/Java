public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if(s.length()!=t.length()) return false;
        int countS[] = count(s);
        int countT[] = count(t);
            for(int i =0;i<26;i++){
                if(countS[i] != countT[i]) return false;
            }
        
        return true;
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
