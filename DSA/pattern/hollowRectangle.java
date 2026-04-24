
import java.util.Scanner;

public class hollowRectangle {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter length of rectangle: ");
        int rows = sc.nextInt();
        System.out.print("Enter the breadth of rectanlge : ");
        int coln = sc.nextInt();

        for(int i = 1; i <=coln;i++){
            if (i == 1 || i == coln){
                for(int j = 1; j<= rows;j++){
                    System.out.print("* ");
                }
            } 
            else {
                    System.out.print("* ");
                for(int k = 1; k <= rows-2; k++){
                    System.out.print("  ");
                }
                    System.out.print("* ");
            }
            
            System.out.println();


        }



    }
}
