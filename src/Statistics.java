import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private HashSet<String> pagesSet = new HashSet<>();
    private HashMap<String, Integer> operatingSystemCounts = new HashMap<>();
    private HashMap<String, Boolean> notFoundPagesMap = new HashMap<>();
    private HashMap<String, Integer> browserCounts = new HashMap<>();
    private long totalVisitsByRealUsers = 0;
    private long totalErrorRequests = 0;
    private long minTimestamp = Long.MAX_VALUE;
    private long maxTimestamp = Long.MIN_VALUE;
    private HashSet<String> uniqueRealUserIps = new HashSet<>();

    public void addEntry(String page, int responseCode, String operatingSystem, String browser,
                         long timestamp, String ip, String userAgent) {
        if (responseCode == 200) {
            pagesSet.add(page);
        } else if (responseCode == 404) {
            notFoundPagesMap.put(page, true);
        }

        Integer osCount = operatingSystemCounts.get(operatingSystem);
        if (osCount == null) {
            operatingSystemCounts.put(operatingSystem, 1);
        } else {
            operatingSystemCounts.put(operatingSystem, osCount + 1);
        }

        Integer browserCount = browserCounts.get(browser);
        if (browserCount == null) {
            browserCounts.put(browser, 1);
        } else {
            browserCounts.put(browser, browserCount + 1);
        }

        if (timestamp < minTimestamp) {
            minTimestamp = timestamp;
        }
        if (timestamp > maxTimestamp) {
            maxTimestamp = timestamp;
        }

        UserAgent ua = new UserAgent(userAgent);
        if (!ua.isBot()) {
            totalVisitsByRealUsers++;
            uniqueRealUserIps.add(ip);
        }

        if (responseCode >= 400 && responseCode < 600) {
            totalErrorRequests++;
        }
    }

    public Set<String> getPagesSet() {
        return pagesSet;
    }

    public Set<String> getNonExistingPagesSet() {
        return notFoundPagesMap.keySet();
    }

    public Map<String, Double> getOperatingSystemShares() {
        int total = 0;
        for (int value : operatingSystemCounts.values()) {
            total += value;
        }

        Map<String, Double> shares = new HashMap<>();
        if (total == 0) {
            return shares;
        }

        for (Map.Entry<String, Integer> entry : operatingSystemCounts.entrySet()) {
            double share = (double) entry.getValue() / total;
            shares.put(entry.getKey(), share);
        }
        return shares;
    }

    public Map<String, Double> getBrowserShares() {
        int total = 0;
        for (int value : browserCounts.values()) {
            total += value;
        }

        Map<String, Double> shares = new HashMap<>();
        if (total == 0) {
            return shares;
        }

        for (Map.Entry<String, Integer> entry : browserCounts.entrySet()) {
            double share = (double) entry.getValue() / total;
            shares.put(entry.getKey(), share);
        }
        return shares;
    }

    public double getAverageVisitsPerHour() {
        if (minTimestamp == Long.MAX_VALUE || maxTimestamp == Long.MIN_VALUE) {
            return 0.0;
        }
        long diffMillis = maxTimestamp - minTimestamp;
        double diffHours = diffMillis / (1000.0 * 60 * 60);
        if (diffHours < 0.0001) {
            return totalVisitsByRealUsers;
        }
        return totalVisitsByRealUsers / diffHours;
    }

    public double getAverageErrorsPerHour() {
        if (minTimestamp == Long.MAX_VALUE || maxTimestamp == Long.MIN_VALUE) {
            return 0.0;
        }
        long diffMillis = maxTimestamp - minTimestamp;
        double diffHours = diffMillis / (1000.0 * 60 * 60);
        if (diffHours < 0.0001) {
            return totalErrorRequests;
        }
        return totalErrorRequests / diffHours;
    }

    public double getAverageVisitsPerUser() {
        if (uniqueRealUserIps.isEmpty()) {
            return 0.0;
        }
        return (double) totalVisitsByRealUsers / uniqueRealUserIps.size();
    }
}