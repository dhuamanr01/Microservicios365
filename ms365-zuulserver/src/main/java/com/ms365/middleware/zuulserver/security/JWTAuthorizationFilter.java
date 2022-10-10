package com.ms365.middleware.zuulserver.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ms365.middleware.zuulserver.common.constants.Constants;
import com.ms365.middleware.zuulserver.common.constants.Constants.Security;
import com.ms365.middleware.zuulserver.dto.RoleDTO;
import com.ms365.middleware.zuulserver.dto.ServicioDTO;
import com.ms365.middleware.zuulserver.exception.GenericException;
import com.ms365.middleware.zuulserver.service.AuthenticatorService;
import com.ms365.middleware.zuulserver.service.ServicioService;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

  @Autowired
  private AuthenticatorService authenticatorService;

  @Autowired
  private ServicioService servicioService;

  @Override
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    logger.info(Constants.Logger.Method.INITIALIZE);
    logger.info("----------------------------------------------------------------------");

    logger.info(" > HTTP Method : " + req.getMethod());
    logger.info(" > ContextPath : " + req.getContextPath());
    logger.info(" > ServletPath : " + req.getServletPath());
    logger.info(" > QueryString : " + req.getQueryString());
    logger.info(" > RequestURI  : " + req.getRequestURI());
    logger.info(" > RequestURL  : " + req.getRequestURL());
    logger.info(" > AuthType    : " + req.getAuthType());
    logger.info(" > LocalAddr   : " + req.getLocalAddr());
    logger.info(" > LocalName   : " + req.getLocalName());
    logger.info(" > LocalPort   : " + req.getLocalPort());
    logger.info(" > RemoteAddr  : " + req.getRemoteAddr());
    logger.info(" > RemteHost   : " + req.getRemoteHost());
    logger.info(" > RemotePort  : " + req.getRemotePort());

    String authorizationHeader = req.getHeader(Security.Headers.AUTHORIZATION);
    logger.info(" > token header: " + authorizationHeader);

    if (authorizationHeader == null || !authorizationHeader.startsWith(Security.Token.BEARER_PREFIX)) {
      //chain.doFilter(req, res);
      //res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //return;
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      PrintWriter out = res.getWriter();
      out.print("{\"code\" : " + res.getStatus() + ", " +
                "\"message\" : \"no existe token\"}");
      out.flush();
      return;
    }

    String token = authorizationHeader.replace(Security.Token.BEARER_PREFIX + " ", "");
    logger.info(" > token       : " + token);

    String userName = null;
    try {
      userName = TokenProvider.getUserName(token);
    }
    catch (GenericException ex) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.setStatus(ex.getStatus());
      PrintWriter out = res.getWriter();
      out.print("{\"code\" : " + res.getStatus() + ", " +
                "\"message\" : \"" + ex.getMessage() + "\"}");
      out.flush();
      return;
    }

    UserDetails user = this.authenticatorService.loadUserByUsername(userName);
    logger.info(" > userName    : " + userName);
    logger.info(" > UserDetails : " + user);

    boolean authorized = false;
    List<ServicioDTO> list = null;
    try{
      list = this.servicioService.findByMetodo(req.getMethod(), Sort.by(Sort.Direction.ASC, "orden"));
    }
    catch(Exception ex) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      PrintWriter out = res.getWriter();
      out.print("{\"code\" : " + res.getStatus() + ", " +
                "\"message\" : \"" + ex.getMessage() + "\"}");
      out.flush();
      return;
    }

    for (ServicioDTO servicioDTO : list) {
      logger.info(" > servicioDTO : " + servicioDTO);

      String uriService = servicioDTO.getUrl();
      String uri               = req.getRequestURI().substring(req.getContextPath().length());
      String regExpUriPattern  = uriService.replace("*", Security.URI_PATH_REG_EXP_PATTERN);
      boolean matchWithURI    = uri.matches(regExpUriPattern);
      boolean containsWithURI = req.getRequestURI().contains(uriService);

      logger.info("  >\t      uriService: " + uriService);
      logger.info("  >\t             uri: " + uri);
      logger.info("  >\tregExpUriPattern: " + regExpUriPattern);
      logger.info("  >\t         matches: " + matchWithURI);
      logger.info("  >\t        containt: " + containsWithURI);

      if (matchWithURI || containsWithURI){
        logger.info("   > roles : " + servicioDTO.getRoles().size());

        if (servicioDTO.getRoles().size() == 0) {
          authorized = false;
          break;
        }
        else {
          Map<String, String> roles = new HashMap<String, String>();

          for (RoleDTO roleDTO : servicioDTO.getRoles()) {
            logger.info("    >  roleDTO : " + roleDTO.getAuth());
            roles.put(roleDTO.getAuth(), roleDTO.getAuth());
          }
          logger.info("   > roles : " + roles);

          for (GrantedAuthority grantedAuthority : user.getAuthorities()){
            logger.info("    >  grantedAuthority : " + grantedAuthority.getAuthority() + ", " +
                                                       roles.containsKey(grantedAuthority.getAuthority()));

            if (roles.containsKey(grantedAuthority.getAuthority())) {
              authorized = true;
            }
          }
        }
      }

      if (authorized) break;
    }
    logger.info(" > authorized  : " + authorized);

    if (!authorized) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.setStatus(HttpServletResponse.SC_FORBIDDEN);

      PrintWriter out = res.getWriter();
      out.print("{\"code\" : " + res.getStatus() + ", " +
                "\"message\" : \"Usuario no tiene rol necesario para esta operacion\"}");
      out.flush();
      return;
    }

    UsernamePasswordAuthenticationToken authenticationToken = null;
    try {
      authenticationToken = TokenProvider.getAuthentication(token, user);
    }
    catch (GenericException ex) {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.setStatus(ex.getStatus());
      PrintWriter out = res.getWriter();
      out.print("{\"code\" : " + res.getStatus() + ", " +
                "\"message\" : \"" + ex.getMessage() + "\"}");
      out.flush();
      return;
    }

    logger.info(Constants.Logger.Method.FINALIZE);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    chain.doFilter(req, res);
  }
}
