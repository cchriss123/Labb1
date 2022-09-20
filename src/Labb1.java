import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Labb1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

        Time[] times = new Time[24];

        times[0] = new Time("00-01",91);
        times[1] = new Time("01-02",83);
        times[2] = new Time("02-03",86);
        times[3] = new Time("03-04",84);
        times[4] = new Time("04-05",86);
        times[5] = new Time("05-06",85);
        times[6] = new Time("06-07",107);
        times[7] = new Time("07-08",407);
        times[8] = new Time("08-09",419);
        times[9] = new Time("09-10",392);
        times[10] = new Time("10-11",368);
        times[11] = new Time("11-12",344);
        times[12] = new Time("12-13",317);
        times[13] = new Time("13-14",298);
        times[14] = new Time("14-15",267);
        times[15] = new Time("15-16",276);
        times[16] = new Time("16-17",281);
        times[17] = new Time("17-18",337);
        times[18] = new Time("18-19",449);
        times[19] = new Time("19-20",481);
        times[20] = new Time("20-21",416);
        times[21] = new Time("21-22",321);
        times[22] = new Time("22-23",172);
        times[23] = new Time("23-24",87);

        boolean isShowMenu = true;
        while (isShowMenu) {
            System.out.println("Elpriser");
            System.out.println("========");
            System.out.println("1. Inmatning");
            System.out.println("2. Min, Max och Medel");
            System.out.println("3. Sortera");
            System.out.println("4. Bästa Laddningstid (4h)");
            System.out.println("5. Visualisering");
            System.out.println("e. Avsluta");

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> input(times, sc);
                case "2" -> printMinMaxAvg(times, minCost(times), maxCost(times), sc);
                case "3" -> sorting(times, sc);
                case "4" -> bestTime(times, sc);
                case "5" -> visualising(times, minCost(times), maxCost(times),sc);
                case "e", "E" -> {
                    isShowMenu = false;
                    System.out.println("Avslutar");
                }
                default -> {
                        System.out.println("Felaktigt val");
                        pressEnter(sc);
                }
            }
        }
    }

    public static int minCost(Time[] times){
        int minCost = times[0].cost;
        for (int i = 0; i < times.length; i++) {
            if (minCost>times[i].cost)
                minCost = times[i].cost;
        }
        return minCost;
    }

    public static int maxCost(Time[] times){
        int maxCost = times[0].cost;
        for (int i = 0; i < times.length; i++) {
            if (maxCost<times[i].cost)
                maxCost = times[i].cost;
        }
        return maxCost;
    }

    public static void pressEnter(Scanner sc) {
        System.out.println("\nPress \"ENTER\" to continue...");
        sc.nextLine();
    }

    public static void input(Time[] times, Scanner sc) {
        for (int i = 0; i < times.length; i++) {
            while (true) {
                System.out.println("Skriv in elpriset i ören som gäller " + times[i].hour + ".");
                String input = sc.nextLine();
                try {
                    times[i].cost = Integer.parseInt(input);
                    break;
                }
                catch (Exception e) {
                    System.out.println("Var vänlig skriv in ett heltal.");
                }
            }
        }
    }

    public static void printMinMaxAvg(Time[] times, int minCost, int maxCost, Scanner sc) {

        int totalCost = 0;
        String minTime = times[0].hour;
        String maxTime = times[0].hour;

        for (int i = 0; i < times.length; i++) {
            if (times[i].cost == minCost)
                minTime = times[i].hour;
            if (times[i].cost == maxCost)
                maxTime = times[i].hour;

            totalCost += times[i].cost;
        }

        System.out.println("Lägsta priset är " + minCost + " öre och det sker klockan " + minTime + ".");
        System.out.println("Högsta priset är " + maxCost + " öre och det sker klockan " + maxTime + ".");
        System.out.println("Genomsnittliga priset är " + (totalCost / times.length) + " öre.");

        pressEnter(sc);
    }

    public static void sorting(Time[] times, Scanner sc) {
        Time[] timesCopy = Arrays.copyOf(times, times.length);

        boolean isSwaped = true;
        while (isSwaped) {
            isSwaped = false;
            for (int i = 0; i < timesCopy.length - 1; i++) {
                if (timesCopy[i].cost > timesCopy[i + 1].cost) {
                    Time temp = timesCopy[i + 1];
                    timesCopy[i + 1] = timesCopy[i];
                    timesCopy[i] = temp;
                    isSwaped = true;
                }
            }
        }

        for (int i = 0; i < timesCopy.length; i++)
            System.out.println(timesCopy[i].hour + " " + timesCopy[i].cost + " öre.");

        pressEnter(sc);
    }

    public static void bestTime(Time[] times, Scanner sc) {

        for (int i = 0; i < times.length - 3; i++) {
            times[i].fourHourAvg = (times[i].cost + times[i+1].cost + times[i+2].cost + times[i+3].cost) / 4.0;
        }
        double minFourHourAvg = times[0].fourHourAvg;
        int minSpot = 0;
        for (int i = 0; i < times.length - 3; i++) {
            if (times[i].fourHourAvg < minFourHourAvg) {
                minFourHourAvg = times[i].fourHourAvg;
                minSpot = i;
            }
        }
        System.out.println("De 4 billigaste tiderna som ligger irad är:");
        for (int i = minSpot; i < minSpot + 4; i++)
            System.out.println(times[i].hour + " " + times[i].cost + " öre.");

        System.out.println("Billigast laddtid får du om du börjar ladda klockan " + minSpot + ".");
        System.out.println("Medelpriset för 4h blir då " + minFourHourAvg + " öre.");
        pressEnter(sc);
    }

    public static void visualising(Time[] times, int minCost, int maxCost, Scanner sc) {

        String tempMinCost = "" + minCost;
        String tempMaxCost = "" + maxCost;
        String space = " ";
        double numbScaler = 1;

        for (int row = 0; row < 11; row++) {
            double maxNumb = maxCost;
            maxNumb *= numbScaler;
            for (int col = 0 ; col < times.length+1; col++) {
                if (row == 0 && col == 0)
                    System.out.print(tempMaxCost + "|");
                else if (col == 0 && row == 10)
                    System.out.print(space.repeat(tempMaxCost.length()-tempMinCost.length())+tempMinCost+"|");
                else if (col == 0)
                    System.out.print(space.repeat(tempMaxCost.length())+"|");
                else if (times[col-1].cost >= maxNumb)
                    System.out.print(" * ");
                else
                    System.out.print("   ");
            }
            numbScaler -= 0.1;
            System.out.println();
        }

        System.out.println(space.repeat(tempMaxCost.length()) +
                "|-----------------------------------------------------------------------");
        System.out.println(space.repeat(tempMaxCost.length()) +
                "|00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23");

        pressEnter(sc);
    }
}
class Time {
    String hour;
    int cost;
    double fourHourAvg = 0;

    public Time(String hour, int cost){
        this.hour = hour;
        this.cost = cost;
    }
}
