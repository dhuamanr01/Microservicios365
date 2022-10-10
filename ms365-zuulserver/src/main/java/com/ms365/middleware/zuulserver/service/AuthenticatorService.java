package com.ms365.middleware.zuulserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms365.middleware.zuulserver.dto.UsuarioDTO;

@Service
public class AuthenticatorService implements UserDetailsService {
  private static Logger logger = LoggerFactory.getLogger(AuthenticatorService.class);

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public AuthenticatorService() {
    super();
  }

  public void finalize(){}

  @Override
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioDTO usuarioDTO = null;

    logger.info(" > Trying to authenticate to {}", username);

   // if (username.matches("^\\d{1,}$")) {
    // logger.info("  > Trying findById");
      logger.info("  > Trying findByUserName");
      try {
     //   usuarioDTO = this.usuarioService.findById(new Integer(username));
        usuarioDTO = this.usuarioService.findByUserName(username);//daniel
        logger.info(" usuarioDTO - "+usuarioDTO);
      }
      catch(Exception ex) {
        throw new UsernameNotFoundException(ex.getMessage(), ex);
      }
    //}

    if (usuarioDTO == null) {
      logger.info(" > NOT FOUND");
      throw new UsernameNotFoundException("Username " + username + " not found");
    }

    logger.info(" > User Enabled " + usuarioDTO.getEstado());

    if (!usuarioDTO.getEstado().booleanValue()) {
      throw new UsernameNotFoundException("Username " + username + " not enabled");
    }

    logger.info(" > Role " + usuarioDTO.getRole().getAuth());
    Collection<? extends GrantedAuthority> authorities = this.getGrantedAuthorities(usuarioDTO);

    return new User(username, this.passwordEncoder.encode(usuarioDTO.getPassword()), authorities);
  }

  private Collection<? extends GrantedAuthority> getGrantedAuthorities(UsuarioDTO usuarioDTO) {
    List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

    logger.info(" > Role {}", usuarioDTO.getRole().getAuth());

    list.add(new GrantedAuthority() {
               private static final long serialVersionUID = 2409931876244987359L;

               @Override
               public String getAuthority() {
                 return usuarioDTO.getRole().getAuth();
               }
             }
            );

    return list;
  }
}
