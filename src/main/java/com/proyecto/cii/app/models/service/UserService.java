package com.proyecto.cii.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;



import com.proyecto.cii.app.models.entity.User;
import com.proyecto.cii.app.models.dao.IUserDao;
import com.proyecto.cii.app.models.entity.Role;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private IUserDao dao;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User usuario = dao.findByUsername(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario " + username + " no encontrado");
		}
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for(Role rol: usuario.getRoles()) {
			roles.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		if(roles.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + username + " no tiene roles asignados");
		}
			
		return (UserDetails) new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, roles);
	}
	
	@Transactional
	public void save(User usuario) {
		dao.save(usuario);		
	} 
	
}









