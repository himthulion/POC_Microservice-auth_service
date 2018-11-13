package com.poc.base.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExceptionMessageDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean hasMessage = true;
	private List<ExceptionMessage> messageList = new ArrayList<ExceptionMessage>(0);

	public List<ExceptionMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ExceptionMessage> messageList) {
		this.messageList = messageList;
	}

	public boolean isHasMessage() {
		return hasMessage;
	}

	public void setHasMessage(boolean hasMessage) {
		this.hasMessage = hasMessage;
	}

}
