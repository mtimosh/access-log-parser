
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private HashSet<String> pagesSet = new HashSet<>();
    private HashMap<String, Integer> operatingSystemCounts = new HashMap<>();
    private HashMap<String, Boolean> notFoundPagesMap = new HashMap<>();
    private HashMap<String, Integer> browserCounts = new HashMap<>();

    public void addEntry(String page, int responseCode, String operatingSystem, String browser) {
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
}