package com.ms365.middleware.zuulserver.filters;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class RouteFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return FilterConstants.ROUTE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    return RequestContext.getCurrentContext().getRouteHost() != null &&
           RequestContext.getCurrentContext().sendZuulResponse();
  }

  @Override
  public Object run() {
    return null;
  }
}