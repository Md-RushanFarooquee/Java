public class anagaramsMETHOD1 {
    public static void main(String[] args) {
        String s1 = "race";
        String s2 = "care";
        boolean flag = true;
        if(s1.length()!=s2.length()) flag = false;
        else{
        int countS[] = count(s1);
        int countT[] = count(s2);
            for(int i =0;i<26;i++){
                if(countS[i] != countT[i]) {flag = false; break;}
            }
        }
        System.out.println(flag);
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
