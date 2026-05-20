public class StringCompression {
    public int compress(char[] chars) {
        if(chars.length == 1) return 1;
        int i = 0,j = 0,k = 0;
        while(j<chars.length){
            if(chars[i] == chars[j]) j++;
            else{
                int freq = j - i;
                if(freq == 1) chars[k++] = chars[i];
                else{
                    chars[k++] = chars[i];
                    String s = Integer.toString(freq);
                    for(char ch : s.toCharArray()){
                        chars[k++] = ch;
                    }
                }
                i = j;
            }
        }
        int freq = j - i;
        if(freq == 1) chars[k++] = chars[i];
        else{
            chars[k++] = chars[i];
            String s = Integer.toString(freq);
                    for(char ch : s.toCharArray()){
                        chars[k++] = ch;
                    }
            }
        return k;
    }
}
