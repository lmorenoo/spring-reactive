package com.mitocode.springreactive.security;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AuthRequest {

	private String username;
	private String password;

}
