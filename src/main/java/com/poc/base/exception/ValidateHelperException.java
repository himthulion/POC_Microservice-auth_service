package com.poc.base.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.poc.base.dto.ExceptionMessage;
import com.poc.base.utils.AppUtil;

public class ValidateHelperException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ExceptionMessage> validateMessageList = new ArrayList<ExceptionMessage>(0);

	public ValidateHelperException() {
	}

	public void addErrorMessage(String msgCode) throws Exception {
		ReloadableResourceBundleMessageSource authMessage = AppUtil.getApplicationContext()
				.getBean(ReloadableResourceBundleMessageSource.class);
		ExceptionMessage message = new ExceptionMessage();
		message.setCode(msgCode);
		message.setMessageTh(authMessage.getMessage(msgCode, null, new Locale("th")));
		message.setMessageEn(authMessage.getMessage(msgCode, null, new Locale("en")));
		validateMessageList.add(message);
	}

	public void beginValidate() {
		validateMessageList = new ArrayList<ExceptionMessage>(0);
	}

	public void endValidate() throws ValidateHelperException {
		if (validateMessageList.size() > 0) {
			throw this;
		}
	}

	public List<ExceptionMessage> getValidateMessageList() {
		return validateMessageList;
	}

	public void setValidateMessageList(List<ExceptionMessage> validateMessageList) {
		this.validateMessageList = validateMessageList;
	}

}
