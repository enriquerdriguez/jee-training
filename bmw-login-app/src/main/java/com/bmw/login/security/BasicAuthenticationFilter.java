package com.bmw.login.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.eclipse.persistence.internal.oxm.conversion.Base64;

import com.bmw.login.boundaries.LoginService;
import com.bmw.login.entity.User;

@Provider
@BasicAuthentication
public class BasicAuthenticationFilter implements ContainerRequestFilter {
    private static final Logger LOG = Logger.getLogger(BasicAuthenticationFilter.class.getName());

    @Inject
    private LoginService loginService;

    @Context
    private HttpServletRequest servletRequest;

    @Context
    private HttpHeaders headers;

    @Context
    private ResourceInfo resourceInfo;


    public static User getHttpBasicAuthorization(HttpHeaders headers) {
        List<String> header = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || header.isEmpty())
            return null;

        String authorization = header.get(0);
        authorization = authorization.substring("Basic ".length());

        final String usernameAndPassword = new String(Base64.base64Decode(authorization.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

        if(tokenizer.countTokens() < 2)
            return null;
        User user = new User();
        user.setEmail(tokenizer.nextToken());
        user.setPassword(tokenizer.nextToken());
        return user;
    }

    private boolean isHttpBasicAuthorized(HttpHeaders headers, BasicAuthentication basicAuthentication){
        User user = getHttpBasicAuthorization(headers);
        if(user == null){
            return false;
        }

        User userValidated = null;
        try {
            userValidated = loginService.validate(user);
        }catch (NoResultException ex){
        }
        return userValidated != null;
    }

    private boolean isAuthorized(){
        HttpSession session = servletRequest.getSession();

        Method resourceMethod = resourceInfo.getResourceMethod();
        BasicAuthentication basicAuthentication = resourceMethod.getDeclaredAnnotation(BasicAuthentication.class);

        if(basicAuthentication == null){
            Class<?> resourceClass = resourceInfo.getResourceClass();
            basicAuthentication = resourceClass.getDeclaredAnnotation(BasicAuthentication.class);
        }
        //If no session detected the next step is check if the request has Httpbasic security
        return isHttpBasicAuthorized(headers, basicAuthentication);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final Response errResponse = Response.status(Response.Status.UNAUTHORIZED).build();

        if(!isAuthorized())
            containerRequestContext.abortWith(errResponse);
    }

}