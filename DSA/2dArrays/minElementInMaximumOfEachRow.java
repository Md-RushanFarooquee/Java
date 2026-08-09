// find minimum element out of all the maximum elements of each row
// eg - max in each row - 8,7,5,8 - min in these = 5 ans;
public class minElementInMaximumOfEachRow {
    public static void main(String[] args) {
        int[][] arr = {{2, 8, 3, 4, 7},{7, 2, 1, 6, 3},{5, 5, 4, 1, 4},{3, 1, 8, 2, 6}};
        int min = Integer.MAX_VALUE;
        for(int i = 0;i<arr.length;i++){
            int maxRow = Integer.MIN_VALUE;
            for(int j = 0;j<arr[0].length;j++){
                if(arr[i][j] > maxRow) maxRow = arr[i][j];
            }
            if(maxRow < min) min = maxRow;
        }
        System.out.println(min);
    }
}
