import java.util.Scanner;

public class AntonAndPolyhedrons {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            String s = sc.next();

            switch (s) {
                case "Tetrahedron" -> sum += 4;
                case "Cube" -> sum += 6;
                case "Octahedron" -> sum += 8;
                case "Dodecahedron" -> sum += 12;
                case "Icosahedron" -> sum += 20;
            }
        }

        System.out.println(sum);
    }
}
