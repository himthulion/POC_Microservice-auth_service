package com.poc.base.service;

import java.io.Serializable;

import com.poc.base.exception.ValidateHelperException;

public abstract class BaseService implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void returnErrorRequiredFields() throws ValidateHelperException {
		throw new ValidateHelperException();
	}

}
