// Source code is decompiled from a .class file using FernFlower decompiler.
package filters;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@filters.Secured
@Provider
@Priority(1000)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    ContainerRequestContext requestContext;

    public AuthenticationFilter() {
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("request filter invoked...");
        String authorizationHeader = requestContext.getHeaderString("Authorization");
        System.out.println("authorizationHeader: " + authorizationHeader);
        if (!this.isTokenBasedAuthentication(authorizationHeader)) {
            this.abortWithUnauthorized(requestContext);
        } else {
            String token = authorizationHeader.substring("Bearer".length()).trim();

            try {
                this.validateToken(token);
            } catch (Exception var5) {
                requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            }

        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("Bearer".toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Status.UNAUTHORIZED).header("WWW-Authenticate", "Bearer").build());
    }

    private void validateToken(String token) {
        try {
            String keyString = "simplekey";
            Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
            System.out.println("the key is : " + key);
            System.out.println("test:" + Jwts.parser().setSigningKey(key).parseClaimsJws(token));
            System.out.println("#### valid token : " + token);
        } catch (Exception var4) {
            System.out.println("#### invalid token : " + token);
            this.requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }

    }
}
