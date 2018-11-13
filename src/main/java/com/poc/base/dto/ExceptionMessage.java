package com.poc.base.dto;

import java.io.Serializable;

public class ExceptionMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String messageTh;
	private String messageEn;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageTh() {
		return messageTh;
	}

	public void setMessageTh(String messageTh) {
		this.messageTh = messageTh;
	}

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

}
