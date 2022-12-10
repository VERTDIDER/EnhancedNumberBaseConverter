import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] input = scanner.nextLine().split("\\s");
            if (input[0].equals("/exit")) break;
            int sourceBase = Integer.parseInt(input[0]);
            int targetBase = Integer.parseInt(input[1]);

            while (true) {

                System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);
                String sourceNumber = scanner.nextLine();
                if (sourceNumber.equals("/back")) break;

                Convertation convertation = new Convertation(sourceBase, targetBase);
                System.out.println("Conversion result: " + convertation.getTarget(sourceNumber) + "\n");

            }

        }
    }
}
