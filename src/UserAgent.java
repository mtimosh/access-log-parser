public class UserAgent {
    private String userAgentString;

    public UserAgent(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public boolean isBot() {
        if (userAgentString == null) {
            return false;
        }
        String lowerCase = userAgentString.toLowerCase();
        return lowerCase.contains("bot");
    }
}