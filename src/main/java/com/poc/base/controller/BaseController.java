package com.poc.base.controller;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.poc.base.dto.ExceptionMessage;
import com.poc.base.dto.ExceptionMessageDto;
import com.poc.base.exception.ValidateHelperException;
import com.poc.base.utils.AppUtil;

public abstract class BaseController implements Serializable {
	private static final long serialVersionUID = 1L;

	protected ResponseEntity<?> ResponseEntitybyException(Exception ex) {
		ExceptionMessageDto body = new ExceptionMessageDto();
		if (ex instanceof ValidateHelperException) {
			ValidateHelperException vex = (ValidateHelperException) ex;
			if (vex.getValidateMessageList().size() > 0) {
				body.setMessageList(vex.getValidateMessageList());
			} else {
				ReloadableResourceBundleMessageSource messageBundle = AppUtil.getApplicationContext()
						.getBean(ReloadableResourceBundleMessageSource.class);
				ExceptionMessage message = new ExceptionMessage();
				message.setCode("base.fields.required");
				message.setMessageTh(messageBundle.getMessage("base.fields.required", null, new Locale("th")));
				message.setMessageEn(messageBundle.getMessage("base.fields.required", null, new Locale("en")));
				body.getMessageList().add(message);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		} else {
			ExceptionMessage message = new ExceptionMessage();
			message.setMessageTh(ex.toString());
			message.setMessageEn(ex.toString());
			body.getMessageList().add(message);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
		}
	}
}
