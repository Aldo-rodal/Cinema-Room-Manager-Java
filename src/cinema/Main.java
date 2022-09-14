package cinema;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    static void printCinema(int row, int seat, char[][] cinemaGrid) {

        System.out.println("Cinema:");

        System.out.print(" ");
        for (int i = 1; i <= seat; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < row; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seat; j++) {
                System.out.print(cinemaGrid[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static int calculatePrice(int row, int seat, int rowClient) {
        int c = row * seat;
        int price1 = 10;
        int price2 = 8;
        int price = 0; // Array of 2 elements: the first one  would be the price
                                             // and the second the total income.
        if(c <= 60) {
            price = price1;
        } else if (row % 2 == 0 && rowClient <= row / 2) {
            price = price1;
        } else if (row % 2 == 0 && rowClient > row / 2) {
            price = price2;
        } else if (row % 2 != 0 && rowClient <= row / 2) {
            price = price1;
        } else if (row % 2 != 0 && rowClient > row / 2) {
            price = price2;
        }
        return price;
    }
    public static int calculateTotalIncome(int row, int seat) {
        int c = row * seat;
        int price1 = 10;
        int price2 = 8;
        int totalIncome = 0;
        int priceFirstHalf = 0;
        int priceSecondHalf = 0;

        if(c <= 60) {
            totalIncome = price1 * c;
            return totalIncome;
        } else if (row % 2 == 0) {
            priceFirstHalf = seat * (row / 2) * price1;
            priceSecondHalf = seat * (row / 2) * price2;
        } else {
            priceFirstHalf = seat * (row / 2) * price1;
            priceSecondHalf = seat * ((row / 2) + 1) * price2;
        }
        totalIncome = priceFirstHalf + priceSecondHalf;
        return totalIncome;
    }

    public static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static int scanMenuOption() {
        int option;
        for (; ; ) {
            option = scanner.nextInt();
            if (option >= 0 && option <= 3) {
                break;
            } else {
                System.out.println("Wrong input!");
            }
        }
        return option;
    }

    public static int buyATicket(int row, int seat, char[][] cinemaGrid) {
        int rowClient;
        int seatClient;

        for (;;) {
            System.out.println("Enter a row number: ");
            rowClient = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            seatClient = scanner.nextInt();

            if (rowClient > 0 && rowClient < 10 && seatClient > 0 && seatClient < 10) {
                if (cinemaGrid[rowClient - 1][seatClient - 1] == 'B') {
                    System.out.println("That ticket has already been purchased!");
                } else {
                    break;
                }
            } else {
                System.out.println("Wrong input!");
            }
        }

        System.out.println("Ticket price: ");
        int price = calculatePrice(row, seat, rowClient);;

        if (cinemaGrid[rowClient - 1][seatClient - 1] == 'B') {
            price = 0;
        } else {
            System.out.println("$" + price);
        }

        cinemaGrid[rowClient - 1][seatClient - 1] = 'B';

        return price;
    }

    public static void statistics(int countTicketsSold, int row, int seat, int income, int totalIncome) {
        double percentage = countTicketsSold * 100.0 / (row * seat);
        System.out.printf("Number of purchased tickets: %d\n", countTicketsSold);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.printf("Current income: $%d\n", income);
        System.out.printf("Total income: $%d\n", totalIncome);
        System.out.println();
    }
    public static void main(String[] args) {
        int row;
        int seat;
        for (;;) {
            System.out.println("Enter the number of rows: ");
            row = scanner.nextInt();
            if (row > 0 && row < 10) {
                break;
            } else {
                System.out.println("Wrong input!");
            }
        }
        for (;;) {
            System.out.println("Enter the number of seats in each row: ");
            seat = scanner.nextInt();
            if (seat > 0 && seat < 10) {
                break;
            } else {
                System.out.println("Wrong input!");
            }
        }
        char[][] cinemaGrid = new char[row][seat];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < seat; j++) {
                cinemaGrid[i][j] = 'S';
            }
        }

        int option;
        int countTicketsSold = 0;
        int totalIncome = calculateTotalIncome(row, seat);
        int income = 0;
        int price = 0;

        for (;;) {
            printMenu();
            option = scanMenuOption();

            if (option == 0) {
                break;
            }
            switch (option) {
                case 1:
                    printCinema(row, seat, cinemaGrid);
                    break;
                case 2:
                    price = buyATicket(row, seat, cinemaGrid);
                    countTicketsSold++;
                    if(price == 0) {
                        countTicketsSold--;
                    }
                    income += price;
                    break;
                case 3:
                    statistics(countTicketsSold, row, seat, income, totalIncome);
                    break;
            }
        }
    }
}
