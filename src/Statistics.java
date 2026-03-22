import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private Map<String, Integer> pageCounts = new HashMap<>();
    private Map<String, Integer> osCounts = new HashMap<>();
    private Map<String, Integer> browserCounts = new HashMap<>();
    private Set<String> notFoundPages = new HashSet<>();
    private Set<String> refererDomains = new HashSet<>();

    private int nonBotVisits;
    private int errorVisits;
    private Set<String> uniqueNonBotIps = new HashSet<>();
    private Map<LocalDateTime, Integer> visitsPerSecond = new HashMap<>();
    private Map<String, Integer> visitsPerUser = new HashMap<>();

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getResponseSize();
        LocalDateTime time = entry.getTime();
        if (minTime == null || time.isBefore(minTime)) {
            minTime = time;
        }
        if (maxTime == null || time.isAfter(maxTime)) {
            maxTime = time;
        }

        if (entry.getResponseCode() == 200) {
            String path = entry.getPath();
            pageCounts.put(path, pageCounts.getOrDefault(path, 0) + 1);
        }

        UserAgent ua = entry.getUserAgent();
        String os = ua.getOs();
        osCounts.put(os, osCounts.getOrDefault(os, 0) + 1);

        String browser = ua.getBrowser();
        browserCounts.put(browser, browserCounts.getOrDefault(browser, 0) + 1);

        if (entry.getResponseCode() == 404) {
            notFoundPages.add(entry.getPath());
        }

        boolean isBot = ua.isBot();
        if (!isBot) {
            nonBotVisits++;
            uniqueNonBotIps.add(entry.getIpAddr());

            LocalDateTime second = time.truncatedTo(ChronoUnit.SECONDS);
            visitsPerSecond.put(second, visitsPerSecond.getOrDefault(second, 0) + 1);

            String ip = entry.getIpAddr();
            visitsPerUser.put(ip, visitsPerUser.getOrDefault(ip, 0) + 1);
        }

        int code = entry.getResponseCode();
        if (code >= 400 && code <= 599) {
            errorVisits++;
        }

        String referer = entry.getReferer();
        if (referer != null && !referer.isEmpty()) {
            String domain = extractDomain(referer);
            if (domain != null) {
                refererDomains.add(domain);
            }
        }
    }

    private String extractDomain(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return null;
        }
        int start = url.indexOf("://") + 3;
        int end = url.indexOf('/', start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return (double) totalTraffic / hours;
    }

    public Set<String> getExistingPages() {
        return pageCounts.keySet();
    }

    public Map<String, Double> getOsShares() {
        int total = osCounts.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> shares = new HashMap<>();
        if (total == 0) {
            return shares;
        }
        for (Map.Entry<String, Integer> entry : osCounts.entrySet()) {
            shares.put(entry.getKey(), (double) entry.getValue() / total);
        }
        return shares;
    }

    public Set<String> getNotFoundPages() {
        return notFoundPages;
    }

    public Map<String, Double> getBrowserShares() {
        int total = browserCounts.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> shares = new HashMap<>();
        if (total == 0) {
            return shares;
        }
        for (Map.Entry<String, Integer> entry : browserCounts.entrySet()) {
            shares.put(entry.getKey(), (double) entry.getValue() / total);
        }
        return shares;
    }

    public double getAverageVisitsPerHour() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return (double) nonBotVisits / hours;
    }

    public double getAverageErrorsPerHour() {
        if (minTime == null || maxTime == null) {
            return 0;
        }
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        if (hours == 0) {
            hours = 1;
        }
        return (double) errorVisits / hours;
    }

    public double getAverageVisitsPerUser() {
        if (uniqueNonBotIps.isEmpty()) {
            return 0;
        }
        return (double) nonBotVisits / uniqueNonBotIps.size();
    }

    public int getPeakVisitsPerSecond() {
        return visitsPerSecond.values().stream().max(Integer::compareTo).orElse(0);
    }

    public Set<String> getRefererDomains() {
        return refererDomains;
    }

    public int getMaxVisitsPerUser() {
        return visitsPerUser.values().stream().max(Integer::compareTo).orElse(0);
    }
}