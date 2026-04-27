import java.util.ArrayList;

public class FirstLettertoAppearTwice {
    public char repeatedCharacter(String s) {
        ArrayList <Character> Present = new ArrayList<>();
        int n = s.length();
        for(int i =0; i<n;i++){
            char ch = s.charAt(i);
            if(Present.contains(ch)) return ch;
            else Present.add(ch);
        }
        return ' ';
    }
}
