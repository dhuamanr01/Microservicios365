package com.ms365.middleware.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;

public class ErrorFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return "error";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    return null;
  }
}
