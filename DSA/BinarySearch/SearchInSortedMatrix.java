public class SearchInSortedMatrix {
    public static void main(String[] args) {
        int [][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        int target = 3;
        boolean found = false;
        int rows = matrix.length, cols = matrix[0].length;
        int lo = 0, hi = rows * cols - 1;
        while(lo<=hi){
            int mid = (lo + hi) / 2;
            int midRow = mid / cols, midCol = mid % cols; 
            if(matrix[midRow][midCol] == target) {found = true; break;}
            else if (matrix[midRow][midCol] > target) hi = mid - 1;
            else lo = mid + 1;
            
        }
        System.out.println(found);
    }
}
