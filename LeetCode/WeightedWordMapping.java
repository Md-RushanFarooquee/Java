public class WeightedWordMapping {
    public String mapWordWeights(String[] words, int[] weights) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            int sum = 0;
            for (int j = 0; j < word.length(); j++) {
                char c = word.charAt(j);
                sum += weights[c - 97];
            }
            sum = sum % 26;
            char map = (char) (122 - sum);
            sb.append(map);
        } 
        return sb.toString();       
    }
}
