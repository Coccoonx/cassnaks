package co.wouri.libreexchange.api.netflow;

/**
 * (c) Artur Sharipov
 */
public class NetworkAuthenticationException extends HttpzoidException {
    public NetworkAuthenticationException() {
        super("Network authentication required", NetworkError.AuthenticationRequired);
    }
}
