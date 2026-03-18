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
                now - 3000, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124", "https://google.com/search");
        stats.addEntry("/index.html", 200, "Windows 10", "Chrome",
                now - 2000, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124", "https://google.com/search");
        stats.addEntry("/index.html", 200, "Windows 10", "Chrome",
                now - 1000, "192.168.1.1", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124", "https://google.com/search");

        stats.addEntry("/products.html", 200, "macOS 12", "Safari",
                now - 2000, "192.168.1.2", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 Safari/605.1.15", "https://yahoo.com");
        stats.addEntry("/products.html", 200, "macOS 12", "Safari",
                now - 1000, "192.168.1.2", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 Safari/605.1.15", "https://yahoo.com");

        stats.addEntry("/about.html", 200, "Linux", "Firefox",
                now - 1000, "192.168.1.3", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0", "https://stackoverflow.com");

        stats.addEntry("/bot-page.html", 200, "Windows 10", "Googlebot",
                now - 500, "192.168.1.4", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "https://google.com");

        stats.addEntry("/error-page", 404, "Windows 10", "Chrome",
                now - 400, "192.168.1.5", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0.4472.124", null);

        System.out.println("=== Проверка новых методов ===");
        System.out.printf("Пиковая посещаемость в секунду: %d%n", stats.getPeakVisitsPerSecond());
        System.out.printf("Максимальная посещаемость одним пользователем: %d%n", stats.getMaxVisitsPerUser());
        System.out.println("Список сайтов (referer domains):");
        for (String domain : stats.getRefererDomains()) {
            System.out.println("  " + domain);
        }

        System.out.println("\n--- Пояснение ---");
        System.out.println("Записи (секунды):");
        System.out.println("  Секунда " + ((now - 3000)/1000) + ": 1 посещение (IP 192.168.1.1)");
        System.out.println("  Секунда " + ((now - 2000)/1000) + ": 2 посещения (IP 192.168.1.1 и 192.168.1.2)");
        System.out.println("  Секунда " + ((now - 1000)/1000) + ": 3 посещения (IP 192.168.1.1, 192.168.1.2, 192.168.1.3)");
        System.out.println("  Секунда " + ((now - 500)/1000) + ": 1 посещение (бот, не учитывается)");
        System.out.println("  Секунда " + ((now - 400)/1000) + ": 1 посещение (IP 192.168.1.5)");
        System.out.println("Пик должен быть 3 (реальные пользователи на 3-й секунде).");
        System.out.println("Максимум посещений одним пользователем: IP 192.168.1.1 посетил 3 раза (индекс 0,1,2).");
        System.out.println("Referer domains: google.com, yahoo.com, stackoverflow.com (бот тоже дал google.com).");
    }
}