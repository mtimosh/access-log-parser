public class UserAgent {
    private final String os;
    private final String browser;
    private final boolean isBot;

    public UserAgent(String userAgentString) {
        String lower = userAgentString.toLowerCase();

        if (lower.contains("windows")) {
            this.os = "Windows";
        } else if (lower.contains("mac")) {
            this.os = "macOS";
        } else if (lower.contains("linux")) {
            this.os = "Linux";
        } else {
            this.os = "Other";
        }

        if (lower.contains("edg")) {
            this.browser = "Edge";
        } else if (lower.contains("firefox")) {
            this.browser = "Firefox";
        } else if (lower.contains("chrome")) {
            this.browser = "Chrome";
        } else if (lower.contains("opera")) {
            this.browser = "Opera";
        } else {
            this.browser = "Other";
        }

        this.isBot = lower.contains("bot");
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isBot() {
        return isBot;
    }
}