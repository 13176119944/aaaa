package com.lening.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MyFilter implements Filter {
    Set<String> notCheckUrl = new HashSet<String>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(notCheckUrl+"----------");
        String uri = filterConfig.getInitParameter("notCheckUrl");
        String[] split = uri.split(",");
        for (String s : split) {
            notCheckUrl.add(s.trim());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if(notCheckUrl.contains(uri)){
            chain.doFilter(request, response);
        }else{
            Object ub = req.getSession().getAttribute("ub");
            System.out.println(ub);
            if(ub==null){
                req.getRequestDispatcher("/index.html").forward(request,response);
            }else{
                chain.doFilter(request,response);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
