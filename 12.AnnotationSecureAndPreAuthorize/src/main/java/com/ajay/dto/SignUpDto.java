package com.ajay.dto;

import java.util.Set;

import com.ajay.entities.enums.Permissions;
import com.ajay.entities.enums.Role;

import lombok.Data;

@Data
public class SignUpDto {

	private String name;
	private String email;
	private String password;
	private Set<Role> roles;
	private Set<Permissions>permissions;
	
}
