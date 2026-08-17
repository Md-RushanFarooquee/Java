public class NonRepeatingCharacterI {
    public static void main(String[] args) {
        String s = "geeksforgeeks";
        System.out.println(nonRepeatingChar(s));
    }
    public static char nonRepeatingChar(String s) {
        char ans = '$';
        int index = s.lastIndexOf(s.charAt(s.length()-1));
        int freq []= new int[26];
        for(int i = 0; i<s.length();i++){
            freq[s.charAt(i) - 'a']++;
        }
        for(int i = 0; i<26;i++){
            if(freq[i] == 1 && s.indexOf((char) (i + 97)) <= index){
                index = s.indexOf((char) (i + 97));
                ans = (char) (i + 97);
            }
        }
        return ans;
    }
}
