public class LogStatistics {
    private int totalLines = 0;
    private int longestLine = 0;
    private int shortestLine = Integer.MAX_VALUE;

    public void processLine(String line) {
        int length = line.length();
        totalLines++;
        if (length > 1024) {
            throw new LineTooLongException("Line length " + length + " exceeds 1024 characters at line " + totalLines);
        }
        if (length > longestLine) {
            longestLine = length;
        }
        if (length < shortestLine) {
            shortestLine = length;
        }
    }

    public void printStatistics() {
        if (totalLines == 0) {
            shortestLine = 0;
        }
        System.out.println("Total lines: " + totalLines);
        System.out.println("Longest line length: " + longestLine);
        System.out.println("Shortest line length: " + shortestLine);
    }
}

class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}