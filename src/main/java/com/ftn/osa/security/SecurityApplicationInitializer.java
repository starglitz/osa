//package com.ftn.osa.security;
//
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
//import org.springframework.web.multipart.support.MultipartFilter;
//
//import javax.servlet.ServletContext;
//
//public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
//
//    @Override
//    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
//        insertFilters(servletContext, new MultipartFilter());
//    }
//}