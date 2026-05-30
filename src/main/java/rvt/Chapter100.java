package rvt;

import java.util.*;

public class Chapter100 {

    public static void main(String[] args) {

        String numerator = "";
        int numeratorInt = 0;
        int divisor = 0;
        boolean goodData = true;
        while (goodData) {
            System.out.print("Enter the numerator: ");
            Scanner scanner = new Scanner(System.in);
            numerator = scanner.nextLine();
            try {
                if (numerator.charAt(0) == 'q' || numerator.charAt(0) == 'Q') {
                    scanner.close();
                    break;
                } else {
                    numeratorInt = Integer.parseInt(numerator);
                    System.out.print("Enter the divisor: ");
                    divisor = scanner.nextInt();
                    System.out.println(numeratorInt + " / " + divisor + " is " + (numeratorInt / divisor));
                    System.out.println(numerator);
                }
            } catch (ArithmeticException ex) {
                System.out.println("You can't divide " + numeratorInt + "by " + divisor);
            } catch (InputMismatchException ex) {
                System.out.println("You entered bad data.");
                System.out.println("Please try again.");
                scanner.nextLine();
            }
        }
    }
}