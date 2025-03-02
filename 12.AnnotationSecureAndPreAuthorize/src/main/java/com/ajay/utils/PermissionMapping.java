package com.ajay.utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ajay.entities.enums.Permissions;
import com.ajay.entities.enums.Role;

public class PermissionMapping {

	public static Map<Role, Set<Permissions>> map = Map.of(
			Role.USER, Set.of(Permissions.VIEW, Permissions.CREATE),
			Role.CREATER, Set.of(Permissions.VIEW, Permissions.CREATE, Permissions.UPDATE),
			Role.ADMIN,   Set.of(Permissions.VIEW, Permissions.UPDATE, Permissions.DELETE));

	public static  Set<SimpleGrantedAuthority> getAuthorityRole(Role role) {
		return map.get(role).stream().map(permission-> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
	}

}
