package com.robex1305.springsecuritydemo.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.net.ssl.SSLSessionContext;
import javax.websocket.OnError;
import javax.websocket.server.PathParam;

@RestController
public class DemoController {

    private boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication() != null &&
                securityContext.getAuthentication().isAuthenticated() &&
                !(securityContext.getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private String getUsername() {

        if (!isAuthenticated()) {
            return "visitor";
        }

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/any")
    @PreAuthorize("isAuthenticated()")
    public String any() {
        return "<b style='color:blue;'>Hello " + getUsername() + ". Anyone <u>authenticated</u> can access this page</b>" +
                "<br>> <a href='/home'>Return to home page</a> ";

    }

    @GetMapping("/home")

    @PermitAll
    public String home(@PathParam("from") String from) {
        StringBuilder builder = new StringBuilder();
        if(StringUtils.hasText(from)){
            switch (from) {
                case "login":
                    if(isAuthenticated()) {
                        builder.append("<h4 style='color:green;'>Logged in successfully</h4><br>");
                    }
                    break;
                case "logout":
                    if(!isAuthenticated()){
                        builder.append("<h4 style='color:green;'>Logged out successfully</h4><br>");
                    }

                    break;
                default:
                    break;
            }
        }

        builder.append("<b style='color:blue;'>Hello ").append(getUsername()).append(". Anyone can access this page</b>");

        if (!isAuthenticated()) {
            builder.append("<br><br>You can login by clicking <a href='/login'><b><u>here</u></b></a>");
        }
        else {
            builder.append("<br><br>You can logout by clicking <a href='/logout'><b><u>here</u></b></a>");
        }

        builder.append("<br><br>");
        builder.append("<b><u>List of links</u></b>");
        builder.append("<br><br>");
        builder.append("<table>");
        builder.append("<tr><td><a href='/home'> /home</a></td>   <td> (no authentication required)</td></tr>");
        builder.append("<tr><td><a href='/any'>  /any</a></td>   <td> (requires authentification)</td></tr>");
        builder.append("<tr><td><a href='/user'> /user</a></td>   <td> (requires ROLE_USER or ROLE_ADMIN)</td></tr>");
        builder.append("<tr><td><a href='/admin'>/admin</a></td>   <td> (requires ROLE_ADMIN)</td></tr>");
        builder.append("</table>");
        return builder.toString();
    }


    @GetMapping("/user")
    @Secured("ROLE_USER")
    public String user() {
        return "<b style='color:blue;'>Hello " + getUsername() + ". Anyone <u>authenticated</u> and enroled as <u>USER</u> can access this page</b>" +
                "<br>> <a href='/home'>Return to home page</a>";
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String admin() {
        return "<b style='color:blue;'>Hello " + getUsername() + ". Anyone <u>authenticated</u> and enroled as <u>ADMIN</u> can access thi pages</b>" +
                "<br>> <a href='/home'>Return to home page</a>";
    }

    @GetMapping("/unauthorized")
    @PermitAll
    public String unauthorized() {
        return "<b style='color:red;'>Hello " + getUsername() + ". You are not allowed to access this page</b>" +
                "<br>> <a href='/home'>Return to home page</a>";
    }
}
