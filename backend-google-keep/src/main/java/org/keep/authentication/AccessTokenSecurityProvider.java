package org.keep.authentication;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AccessTokenSecurityProvider<T> implements InjectableProvider<Auth, Parameter> {
    public final static String AUTHENTICATION_HEADER = "x-access-token";
    private final Authenticator<AccessTokenCredentials, T> authenticator;

    public AccessTokenSecurityProvider(Authenticator<AccessTokenCredentials, T> authenticator) {
        this.authenticator = authenticator;
    }

    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    public Injectable getInjectable(ComponentContext componentContext, Auth auth, Parameter parameter) {
        return new ExampleSecurityInjectable<T>(authenticator, auth.required());
    }

    private static class ExampleSecurityInjectable<T> extends AbstractHttpContextInjectable<T> {

        private final Authenticator<AccessTokenCredentials, T> authenticator;
        private final boolean required;

        private ExampleSecurityInjectable(Authenticator<AccessTokenCredentials, T> authenticator, boolean required) {
            this.authenticator = authenticator;
            this.required = required;
        }

        @Override
        public T getValue(HttpContext c) {
            // This is where the credentials are extracted from the request
            final String header = c.getRequest().getHeaderValue(AUTHENTICATION_HEADER);
            try {
                if (header != null) {
                    final Optional<T> result = authenticator.authenticate(new AccessTokenCredentials(header));
                    if (result.isPresent()) {
                        return result.get();
                    }
                }
            } catch (AuthenticationException e) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            if (required) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            return null;
        }
    }
}
