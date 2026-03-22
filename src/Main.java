import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to access.log: ");
        String pathStr = scanner.nextLine();
        Path path = Paths.get(pathStr);

        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            return;
        }
        if (!Files.isRegularFile(path)) {
            System.out.println("Path is not a file.");
            return;
        }

        Statistics stats = new Statistics();
        int totalLines = 0;
        int skipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                if (line.length() > 1024) {
                    throw new RuntimeException("Line longer than 1024 characters: " + line.length());
                }

                try {
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);
                } catch (IllegalArgumentException e) {
                    skipped++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Total lines: " + totalLines);
        System.out.println("Skipped invalid lines: " + skipped);
        System.out.printf("Traffic rate: %.2f bytes/hour\n", stats.getTrafficRate());

        System.out.println("Existing pages count: " + stats.getExistingPages().size());
        System.out.println("OS shares: " + stats.getOsShares());
        System.out.println("404 pages: " + stats.getNotFoundPages());
        System.out.println("Browser shares: " + stats.getBrowserShares());

        System.out.printf("Average visits per hour (non-bot): %.2f\n", stats.getAverageVisitsPerHour());
        System.out.printf("Average errors per hour: %.2f\n", stats.getAverageErrorsPerHour());
        System.out.printf("Average visits per user (non-bot): %.2f\n", stats.getAverageVisitsPerUser());

        System.out.println("Peak visits per second: " + stats.getPeakVisitsPerSecond());
        System.out.println("Referer domains: " + stats.getRefererDomains());
        System.out.println("Max visits per user: " + stats.getMaxVisitsPerUser());
    }
}