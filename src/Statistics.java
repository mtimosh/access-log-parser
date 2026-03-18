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
    private HashMap<Long, Integer> visitsPerSecond = new HashMap<>();
    private HashSet<String> refererDomains = new HashSet<>();
    private HashMap<String, Integer> visitsPerUser = new HashMap<>();

    public void addEntry(String page, int responseCode, String operatingSystem, String browser,
                         long timestamp, String ip, String userAgent, String referer) {
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
        boolean isBot = ua.isBot();

        if (!isBot) {
            totalVisitsByRealUsers++;
            uniqueRealUserIps.add(ip);

            long second = timestamp / 1000;
            Integer secondCount = visitsPerSecond.get(second);
            if (secondCount == null) {
                visitsPerSecond.put(second, 1);
            } else {
                visitsPerSecond.put(second, secondCount + 1);
            }

            Integer userCount = visitsPerUser.get(ip);
            if (userCount == null) {
                visitsPerUser.put(ip, 1);
            } else {
                visitsPerUser.put(ip, userCount + 1);
            }
        }

        if (responseCode >= 400 && responseCode < 600) {
            totalErrorRequests++;
        }

        if (referer != null && !referer.isEmpty()) {
            String domain = extractDomain(referer);
            if (domain != null) {
                refererDomains.add(domain);
            }
        }
    }

    private String extractDomain(String url) {
        try {
            String lower = url.toLowerCase();
            int start = 0;
            if (lower.startsWith("http://")) {
                start = 7;
            } else if (lower.startsWith("https://")) {
                start = 8;
            } else {
                return null;
            }
            int end = url.indexOf('/', start);
            if (end == -1) {
                end = url.length();
            }
            String host = url.substring(start, end);
            if (host.startsWith("www.")) {
                host = host.substring(4);
            }
            return host;
        } catch (Exception e) {
            return null;
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

    public int getPeakVisitsPerSecond() {
        int max = 0;
        for (int count : visitsPerSecond.values()) {
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    public Set<String> getRefererDomains() {
        return refererDomains;
    }

    public int getMaxVisitsPerUser() {
        int max = 0;
        for (int count : visitsPerUser.values()) {
            if (count > max) {
                max = count;
            }
        }
        return max;
    }
}