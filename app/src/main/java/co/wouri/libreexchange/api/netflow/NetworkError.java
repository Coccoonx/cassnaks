package co.wouri.libreexchange.api.netflow;

/**
 * Network errors
 * <p/>
 * (c) Artur Sharipov
 */
public enum NetworkError {
    Offline,
    /**
     * Network authentication requested (web-form)
     */
    AuthenticationRequired,
    /**
     * Unsupported HTTP method requested
     */
    UnsupportedMethod,
    /**
     * Request timeout
     */
    Timeout, /**
     * Unknown error, post an issue with logs if you encounter it.
     */
    Unknown
}
