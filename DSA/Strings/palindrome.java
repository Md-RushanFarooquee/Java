public class palindrome {
    public static void main(String[] args) {
        String s = "abba";
        int i  = 0, j = s.length()-1;
        boolean valid = true;
        while(i<=j){
            if(s.charAt(i) != s.charAt(j)) {valid = false;break;}
                i++;
                j--;
        }
        System.out.println(valid);
    }
}
