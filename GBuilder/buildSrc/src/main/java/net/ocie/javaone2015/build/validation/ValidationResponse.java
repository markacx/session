package net.ocie.javaone2015.build.validation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ocJLFoster
 */
public class ValidationResponse {
	private final boolean valid;
	private final String password;

	private ValidationResponse(boolean valid, String password) {
		this.valid = valid;
		this.password = password;
	}
	public boolean isValid() {
		return valid;
	}
	public String getPassword() {
		return password;
	}
	
	
	public static ValidationResponse createValid(String password) {
		return new ValidationResponse(true, password);
	}
	public static ValidationResponse createInvalid() {
		return new ValidationResponse(false, null);
	}
	
	
}
