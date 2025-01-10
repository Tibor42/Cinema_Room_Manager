package cinema;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        int menuSelection = 1;

        Scanner scanner;
        scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeats = scanner.nextInt();


        char[][] seats = initCinema(numRows,numSeats);// new char[numRows][numSeats];

        boolean runLoop = true;
        while (runLoop) {
            showMenu();
            menuSelection = scanner.nextInt();
            switch (menuSelection) {
                case 0:
                    runLoop = false;
                    break;
                case 1:
                    drawCinema(seats);
                    break;
                case 2:
                    buyTickets(seats,scanner);
                    break;
                case 3:
                    showStatistics(seats);
                    break;
                default:
                    System.out.println("Unrecognized Command");
                    break;
            }

        }
        return;
    }

    public static boolean buyTickets(char[][] seats, Scanner scanner) {

        boolean ticketSold = false;
        int row =0 , seat = 0;
        while (!ticketSold) {
            System.out.println("Enter a row number:");
            try {
                row = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not a valid integer!");
            }
            System.out.println("Enter a seat number in that row:");
            try {
                seat = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not a valid integer!");
            }

            if ((row ==0 || seat == 0) || ( row > seats.length || seat> seats[0].length )) {
                System.out.println("Wrong input!");

            } else {
                if (seats[row-1][seat-1] == 'B') {
                    System.out.println("That ticket has already been purchased!");

                } else {

                    int price = calcSeatPrice(seats, row);

                    System.out.println("Ticket price: $" + price);
                    System.out.println();

                    seats[row - 1][seat - 1] = 'B';
                    ticketSold = true;
                }
            }
        }
        return true;
    }


    public static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");

        System.out.println("0. Exit ");

    }

    public static void showStatistics(char[][] seats){

        int numRows = seats.length;
        int numSeats = seats[0].length;

        int maxSeats = numRows * numSeats;
        int numTicketsSold = numTicketsSold(seats);

        double numTicketsPercent = (double) numTicketsSold / maxSeats;

        int income = calcCurrentIncome(seats);
        int maxIncome = calcMaxIncome(numRows, numSeats);

        DecimalFormat df = new DecimalFormat("#0.00%");

        System.out.println("Number of purchased tickets:" + numTicketsSold);
        System.out.println("Percentage: " + df.format(numTicketsPercent) );
        System.out.println("Current income: $" + income );
        System.out.println("Total income: $" + maxIncome );
    }

    public static int numTicketsSold( char[][] seats) {
        int r = seats.length;
        int s = seats[0].length;

        int soldTicketsCount = 0;

        for (int i = 0; i<r; i++) {
            for (int j = 0; j<s; j++ ) {
                if ( seats[i][j]== 'B' ) soldTicketsCount++;
            }
        }
        return soldTicketsCount;
    }

    public static int calcCurrentIncome(char[][] seats) {
        int numRows = seats.length;
        int numSeats = seats[0].length;
        int sum = 0;

        int countTen = 0;
        int countEight = 0;
        int price = 0;

        for (int i = 0; i<numRows; i++) {
            for (int j = 0; j<numSeats; j++ ) {
                if ( seats[i][j] == 'B' ) {
                    price = calcSeatPrice(seats, (i +1));
                    if (price == 10) countTen++;
                    else countEight++;

                    System.out.println("Price: " + price + " countTen:" + countTen + " count8:" +  countEight);
                    sum += price;
                }
            }
        }

        return sum;
    }

    public static int calcSeatPrice(char[][] seats, int row) {

        int numRows = seats.length;
        int numSeats = seats[0].length;
        int totalNumberOfSeats = numRows * numSeats;
        int result = 0;
        int firstHalf = numRows / 2;

        if ( totalNumberOfSeats > 60) {
            if (row <= firstHalf) {
                result = 10;
            } else {
                result = 8;
            }
        } else {
            result = 10;
        }
        return result;

    }

    public static int calcMaxIncome(int rows, int seats) {

        int totalNumberOfSeats = rows * seats;
        int result = 0;
        int firstHalf = rows / 2;

        if ( totalNumberOfSeats > 60) {
            // 9 rows => 4 rows = first half,10 // 8 second half
            if (rows % 2 ==0 ) {
                result = firstHalf * seats * 10 + firstHalf * seats * 8;
            } else {
                result = firstHalf * seats * 10 + (firstHalf + 1 ) * seats * 8;

            }
        } else {
            result = totalNumberOfSeats * 10;
        }
        return result;

    }

    public static char[][] initCinema(int rows, int seats) {

        char[][] result = new char[rows][seats];
        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<seats; j++ ) {
                result[i][j] = 'S';
            }
        }
        return result;
    }

    public static void drawCinema(char[][] seats) {
        System.out.println("Cinema:");
        int r = seats.length;
        int s = seats[0].length;

        System.out.print("  ");
        for (int j = 0; j<s;j++) {
            System.out.print((j+1) + " ");
        }
        System.out.println();

        for (int i = 0; i<r; i++) {
            System.out.print( i + 1 );
            for (int j = 0; j<s; j++ ) {
                System.out.print( " " + seats[i][j]  );
            }
            System.out.println(" ");
        }
    }
}