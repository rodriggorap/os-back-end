package com.rodrigo.os.resources.exceptions;

import java.util.ArrayList;
import java.util.List;


public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessade> errors = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Long timestamp, Integer status, String erros) {
		super(timestamp, status, erros);
	}

	public List<FieldMessade> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessade(fieldName, message));
	}
	
	

}
