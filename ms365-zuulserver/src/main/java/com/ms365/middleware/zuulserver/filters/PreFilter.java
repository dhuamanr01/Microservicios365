package com.ms365.middleware.zuulserver.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.util.UrlPathHelper;

import com.ms365.middleware.zuulserver.common.constants.Constants;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {
  private static Logger logger = LoggerFactory.getLogger(PreFilter.class);

  private UrlPathHelper urlPathHelper = new UrlPathHelper();
  private RouteLocator routeLocator;

  @Autowired(required = true)
  private EurekaClient discoveryClient;

  @Autowired
  public void setLocationHeaderRewritingFilter(RouteLocator routeLocator) {
    this.routeLocator = routeLocator;
  }

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext ctx = RequestContext.getCurrentContext();
    return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) && // a filter has already forwarded
           !ctx.containsKey(FilterConstants.SERVICE_ID_KEY);   // a filter has already determined serviceId
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();

    logger.trace(Constants.Logger.Method.INITIALIZE);
    logger.trace("----------------------------------------------------------------------");

    HttpServletRequest request = ctx.getRequest();
    logger.info(" > Request Method : " + request.getMethod());
    logger.info("  > Request URL : " + request.getRequestURL().toString());
    logger.info("  > Request URI : " + request.getRequestURI().toString());

    String requestURI = this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
    Route route = routeLocator.getMatchingRoute(requestURI);
    if (route != null) {
      logger.info(" > Route: " + route.getId());
      logger.info("  > Prefix     : " + route.getPrefix());
      logger.info("  > FullPath   : " + route.getFullPath());
      logger.info("  > Location   : " + route.getLocation());
      logger.info("  > Path       : " + route.getPath());
      logger.info("  > stripPrefix: " + route.isPrefixStripped());

      try {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(route.getLocation(), false);
        logger.info("  > InstanceInfo: " + instance.getInstanceId());
      }
      catch (Exception ex) {}
    }

    logger.trace(Constants.Logger.Method.FINALIZE);
    return null;
  }
}
