public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        String palindrome = s.substring(0,1);
        int maxLen = 1;
        for(int i = 0;i<s.length()-1;i++){
            for(int j = i+1;j<=s.length();j++){
                if(check(s.substring(i,j)) && s.substring(i,j).length() > maxLen){
                    palindrome = s.substring(i,j);
                    maxLen = Math.max(maxLen,s.substring(i,j).length());
                } 
            }
        }
        return palindrome;
    }
    private boolean check(String s){
        int i = 0;
        int j = s.length() - 1;
        while(i<=j){
            if(s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
