package com.ms365.middleware.zuulserver.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ms365.middleware.zuulserver.common.constants.Constants.Security;
import com.ms365.middleware.zuulserver.exception.GenericException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenProvider {
  private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private TokenProvider() {}

	public static String generateToken(Authentication authentication, Date expiration) {
		String authorities = authentication.getAuthorities().stream()
				                 .map(GrantedAuthority::getAuthority)
				                 .collect(Collectors.joining(","));

    String authName = authentication.getName();
    logger.info(" > authName   : " + authName);
    logger.info(" > Authorities: " + authorities);

    String token = Jwts.builder()
                   .setIssuedAt(new Date())
                   .setIssuer(Security.ISSUER_INFO)
                   //.setSubject(((User) auth.getPrincipal()).getUsername())
                   .setSubject(authName)
                   .claim(Security.Keys.AUTHORITIES, authorities)
                   //.signWith(SignatureAlgorithm.HS512, Security.Keys.Signing)
                   .signWith(SignatureAlgorithm.HS256, Security.Keys.SIGNING)
                   .setExpiration(expiration)
                   .compact();

    logger.info(" > token      : " + token);
    return token;
	}

	public static UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) throws GenericException {
	  Claims claims = null;
    GenericException exception = null;

	  try {
		  claims = Jwts.parser()
		           .setSigningKey(Security.Keys.SIGNING)
               .parseClaimsJws(token)
               .getBody();
	  }
    catch (ExpiredJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_NOT_ACCEPTABLE, ex.getMessage());
    }
    catch (UnsupportedJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_CONFLICT, ex.getMessage());
    }
    catch (MalformedJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
    catch (SignatureException ex) {
      exception = new GenericException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
    catch (IllegalArgumentException ex) {
      exception = new GenericException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    finally {
      if (exception != null) {
        logger.error(" * " + exception.getStatus() + " : " + exception.getMessage());
        throw exception;
      }
    }

		final Collection<SimpleGrantedAuthority> authorities =
				    Arrays.stream(claims.get(Security.Keys.AUTHORITIES).toString().split(","))
						  .map(SimpleGrantedAuthority::new)
						  .collect(Collectors.toList());

		logger.info(" > userDetails: " + userDetails);
		logger.info(" > authorities: " + authorities);

		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public static String getUserName(final String token) throws GenericException {
	  String user = null;
	  GenericException exception = null;

	  try {
	    user = Jwts.parser()
	           .setSigningKey(Security.Keys.SIGNING)
             .parseClaimsJws(token)
             .getBody().getSubject();
    }
    catch (ExpiredJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_NOT_ACCEPTABLE, ex.getMessage());
    }
    catch (UnsupportedJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_CONFLICT, ex.getMessage());
    }
    catch (MalformedJwtException ex) {
      exception = new GenericException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
    catch (SignatureException ex) {
      exception = new GenericException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
    catch (IllegalArgumentException ex) {
      exception = new GenericException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
	  finally {
	    if (exception != null) {
	      logger.error(" * " + exception.getStatus() + " : " + exception.getMessage());
	      throw exception;
	    }
	  }

		logger.info(" > user: " + user);
		return user;
	}

}
