package com.ajay.dto;

import java.util.Set;

import com.ajay.entities.enums.Permissions;
import com.ajay.entities.enums.Role;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String name;
	private String email;
	private Set<Role> roles;
	private Set<Permissions>permissions;
}
