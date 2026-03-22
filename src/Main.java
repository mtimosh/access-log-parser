import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern logPattern = Pattern.compile(
                "^(\\S+) - - \\[(.+?)] \"(\\S+) (\\S+) HTTP/\\d\\.\\d\" (\\d{3}) (\\d+|-) \"(.*?)\" \"(.*?)\"$"
        );
        int totalLines = 0;
        int yandexCount = 0;
        int googleCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;
                if (line.length() > 1024) {
                    throw new RuntimeException("Line longer than 1024 characters: " + line.length());
                }
                Matcher matcher = logPattern.matcher(line);
                if (matcher.find()) {
                    String userAgent = matcher.group(8);
                    int firstOpen = userAgent.indexOf('(');
                    int firstClose = userAgent.indexOf(')');
                    if (firstOpen != -1 && firstClose != -1 && firstClose > firstOpen) {
                        String inside = userAgent.substring(firstOpen + 1, firstClose);
                        String[] parts = inside.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            int slashIndex = fragment.indexOf('/');
                            if (slashIndex != -1) {
                                fragment = fragment.substring(0, slashIndex);
                            }
                            if (fragment.equals("YandexBot")) {
                                yandexCount++;
                            } else if (fragment.equals("Googlebot")) {
                                googleCount++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Total lines: " + totalLines);
        if (totalLines > 0) {
            double yandexShare = (double) yandexCount / totalLines * 100;
            double googleShare = (double) googleCount / totalLines * 100;
            System.out.printf("YandexBot share: %.2f%%\n", yandexShare);
            System.out.printf("Googlebot share: %.2f%%\n", googleShare);
        } else {
            System.out.println("File is empty.");
        }
    }
}