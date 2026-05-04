public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        s = s.toLowerCase();
        s = s.replaceAll("[^a-z0-9]", "");
        String result = "";
        for(int i=s.length() - 1;i>=0;i--){
            result += s.charAt(i);
        }
        return result.equals(s);
    }
}
