import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите первое число:");
//        int firstNumber = scanner.nextInt();
//        System.out.println("Введите второе число:");
//        int secondNumber = scanner.nextInt();
//        int sum = firstNumber + secondNumber;
//        int difference = firstNumber - secondNumber;
//        int product = firstNumber * secondNumber;
//        double quotient = (double) firstNumber / secondNumber;
//        System.out.println("Сумма: " + sum);
//        System.out.println("Разность: " + difference);
//        System.out.println("Произведение: " + product);
//        System.out.println("Частное: " + quotient);

        Statistics stats = new Statistics();
//        stats.addEntry("/index.html", 200, "Windows 10", "Chrome");
//        stats.addEntry("/about.html", 200, "Windows 10", "Chrome");
//        stats.addEntry("/products.html", 200, "macOS 12", "Safari");
//        stats.addEntry("/contact.html", 200, "Linux", "Firefox");
//        stats.addEntry("/old-page.html", 404, "Windows 10", "Chrome");
//        stats.addEntry("/broken-link.html", 404, "macOS 12", "Safari");
//        stats.addEntry("/temp.html", 404, "Linux", "Firefox");
//        stats.addEntry("/index.html", 200, "Windows 10", "Chrome");
//        System.out.println("Существующие страницы (200):");
//        for (String page : stats.getPagesSet()) {
//            System.out.println("  " + page);
//        }
//        System.out.println("\nНесуществующие страницы (404):");
//        for (String page : stats.getNonExistingPagesSet()) {
//            System.out.println("  " + page);
//        }
//        System.out.println("\nДоли операционных систем:");
//        var osShares = stats.getOperatingSystemShares();
//        double osTotal = 0.0;
//        for (var entry : osShares.entrySet()) {
//            System.out.printf("  %s: %.2f%n", entry.getKey(), entry.getValue());
//            osTotal += entry.getValue();
//        }
//        System.out.printf("Сумма долей ОС: %.2f (должна быть 1.0)%n", osTotal);
//        System.out.println("\nДоли браузеров:");
//        var browserShares = stats.getBrowserShares();
//        double browserTotal = 0.0;
//        for (var entry : browserShares.entrySet()) {
//            System.out.printf("  %s: %.2f%n", entry.getKey(), entry.getValue());
//            browserTotal += entry.getValue();
//        }
//        System.out.printf("Сумма долей браузеров: %.2f (должна быть 1.0)%n", browserTotal);
//
        long now = System.currentTimeMillis();

        stats.addEntry("/index.html", 200, "Windows 10", "Chrome",
                now - 7200000, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124");
        stats.addEntry("/products.html", 200, "Windows 10", "Googlebot",
                now - 5400000, "192.168.1.2", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        stats.addEntry("/old-page.html", 404, "macOS 12", "Safari",
                now - 3600000, "192.168.1.3", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 Safari/605.1.15");
        stats.addEntry("/broken", 500, "Linux", "Firefox",
                now - 1800000, "192.168.1.4", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0");
        stats.addEntry("/index.html", 200, "Windows 10", "Chrome",
                now, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124");

        System.out.printf("Среднее количество посещений за час: %.2f%n", stats.getAverageVisitsPerHour());
        System.out.printf("Среднее количество ошибочных запросов за час: %.2f%n", stats.getAverageErrorsPerHour());
        System.out.printf("Средняя посещаемость одним пользователем: %.2f%n", stats.getAverageVisitsPerUser());
    }
}