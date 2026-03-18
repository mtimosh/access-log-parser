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
        stats.addEntry("/index.html", 200, "Windows 10", "Chrome");
        stats.addEntry("/about.html", 200, "Windows 10", "Chrome");
        stats.addEntry("/products.html", 200, "macOS 12", "Safari");
        stats.addEntry("/contact.html", 200, "Linux", "Firefox");
        stats.addEntry("/old-page.html", 404, "Windows 10", "Chrome");
        stats.addEntry("/broken-link.html", 404, "macOS 12", "Safari");
        stats.addEntry("/temp.html", 404, "Linux", "Firefox");
        stats.addEntry("/index.html", 200, "Windows 10", "Chrome");
        System.out.println("Существующие страницы (200):");
        for (String page : stats.getPagesSet()) {
            System.out.println("  " + page);
        }
        System.out.println("\nНесуществующие страницы (404):");
        for (String page : stats.getNonExistingPagesSet()) {
            System.out.println("  " + page);
        }
        System.out.println("\nДоли операционных систем:");
        var osShares = stats.getOperatingSystemShares();
        double osTotal = 0.0;
        for (var entry : osShares.entrySet()) {
            System.out.printf("  %s: %.2f%n", entry.getKey(), entry.getValue());
            osTotal += entry.getValue();
        }
        System.out.printf("Сумма долей ОС: %.2f (должна быть 1.0)%n", osTotal);
        System.out.println("\nДоли браузеров:");
        var browserShares = stats.getBrowserShares();
        double browserTotal = 0.0;
        for (var entry : browserShares.entrySet()) {
            System.out.printf("  %s: %.2f%n", entry.getKey(), entry.getValue());
            browserTotal += entry.getValue();
        }
        System.out.printf("Сумма долей браузеров: %.2f (должна быть 1.0)%n", browserTotal);
    }
}