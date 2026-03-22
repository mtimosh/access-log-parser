import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddr;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String line) {
        Pattern pattern = Pattern.compile(
                "^(\\S+) - - \\[(.+?)] \"(\\S+) (\\S+) HTTP/\\d\\.\\d\" (\\d{3}) (\\d+|-) \"(.*?)\" \"(.*?)\"$"
        );
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid log line: " + line);
        }

        this.ipAddr = matcher.group(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        this.time = LocalDateTime.parse(matcher.group(2), formatter);

        String methodStr = matcher.group(3);
        HttpMethod m;
        try {
            m = HttpMethod.valueOf(methodStr);
        } catch (IllegalArgumentException e) {
            m = HttpMethod.UNKNOWN;
        }
        this.method = m;

        this.path = matcher.group(4);
        this.responseCode = Integer.parseInt(matcher.group(5));

        String sizeStr = matcher.group(6);
        this.responseSize = sizeStr.equals("-") ? 0 : Integer.parseInt(sizeStr);

        String refererStr = matcher.group(7);
        this.referer = refererStr.equals("-") ? "" : refererStr;

        String userAgentString = matcher.group(8);
        this.userAgent = new UserAgent(userAgentString);
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}