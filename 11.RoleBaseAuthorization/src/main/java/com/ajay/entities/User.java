package com.ajay.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ajay.entities.enums.Permissions;
import com.ajay.entities.enums.Role;
import com.ajay.utils.PermissionMapping;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role >roles;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
    	Set<SimpleGrantedAuthority>authorities=new HashSet<>();
    	
    	roles.forEach(role-> {
    		Set<SimpleGrantedAuthority> Permission= PermissionMapping.getAuthorityRole(role);
    		authorities.addAll(Permission);
    		authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
    	});
    	
    	return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
