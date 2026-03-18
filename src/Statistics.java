
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private HashSet<String> pagesSet = new HashSet<>();
    private HashMap<String, Integer> operatingSystemCounts = new HashMap<>();

    public void addEntry(String page, int responseCode, String operatingSystem) {
        if (responseCode == 200) {
            pagesSet.add(page);
        }
        Integer count = operatingSystemCounts.get(operatingSystem);
        if (count == null) {
            operatingSystemCounts.put(operatingSystem, 1);
        } else {
            operatingSystemCounts.put(operatingSystem, count + 1);
        }
    }

    public Set<String> getPagesSet() {
        return pagesSet;
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
}