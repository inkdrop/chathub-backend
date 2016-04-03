package com.inkdrop.app.domain.formatter.jsonModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.inkdrop.app.domain.models.Message;

public class MessagesPageJson implements Serializable {

	private static final long serialVersionUID = -4790647546007295343L;

	private Integer page;
	private Integer total;
	private List<MessageJson> messages;
	
	public MessagesPageJson(Page<Message> pageResult) {
		this.page = pageResult.getNumber();
		this.total = pageResult.getTotalPages();
		this.messages = formatMessages(pageResult.getContent());
	}

	private List<MessageJson> formatMessages(List<Message> messages) {
		List<MessageJson> m = new ArrayList<>();
		for (Message message : messages)
			m.add(new MessageJson(message));

		return m;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<MessageJson> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageJson> messages) {
		this.messages = messages;
	}
}
