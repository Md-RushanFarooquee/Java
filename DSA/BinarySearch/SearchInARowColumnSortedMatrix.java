public class SearchInARowColumnSortedMatrix {
    public static void main(String[] args) {
        int mat[][] =  {{3, 30, 38},
                        {20, 52, 54},
                        {35, 60, 69}};
        int target = 35;
        int found = -1;
        int row = mat.length, col = mat[0].length-1;
        int i = 0,j = col;
        while(i<row && j >= 0){
            if(mat[i][j] == target) {found = 1; break;}
            if(mat[i][j] < target) i++;
            else j--;
        }
        System.out.println(found);
    }
}
