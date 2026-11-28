import java.util.Scanner;

public class BeautifulMatrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Iposition = -1;
        int Jposition = -1;
        int count = 0;
        int matrix [][] = new int[5][5];
        for(int i = 0; i<5;i++){
            for(int j = 0;j<5;j++){
                matrix [i][j] = sc.nextInt();
                if(matrix[i][j] == 1){
                    Iposition = i;
                    Jposition = j;
                }
            }
        }

        while(Iposition < 2){Iposition++; count++;}
        while(Iposition > 2){Iposition--; count++;}
        while(Jposition < 2){Jposition++; count++;}
        while(Jposition > 2){Jposition--; count++;}

        System.out.println(count);
    }
    
}
