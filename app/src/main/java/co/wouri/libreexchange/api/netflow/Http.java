package co.wouri.libreexchange.api.netflow;

/**
 * Http request creator.
 * Malformed URL will throw runtime exception.
 * <p/>
 * (c) Artur Sharipov
 */
public interface Http {
    HttpRequest get(String url);

    HttpRequest post(String url);

    HttpRequest put(String url);

    HttpRequest delete(String url);

    HttpRequest request(String url, String method);
}

