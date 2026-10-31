package lld.urlShortner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class URLShortner {
    private final AtomicLong counter = new AtomicLong(100000);
    private static String BASE = "http://short.ly/";
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final ConcurrentHashMap<String , String> codeToUrl = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String , String> urltoCode = new ConcurrentHashMap<>();

    public String getShortURL(String longUrl) {
        String code = urltoCode.computeIfAbsent(longUrl , u -> {
            String c = encoder(counter.getAndIncrement());
            codeToUrl.put(c, u);
            return c;
        });
        return BASE + code;
    }

    public static String expandShortUrl(String url) {
        String code = url.substring(BASE.length());
        return codeToUrl.get(code);
    }

    private String encoder(long id) {
        StringBuilder sb = new StringBuilder();
        while(id > 0) {
            sb.append(CHARS.charAt((int)id%62));
            id = id/62;
        }
        return sb.reverse().toString();
    }


}
