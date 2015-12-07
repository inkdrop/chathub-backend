package com.inkdrop.controllers.api.models;

import java.io.Serializable;

/**
 * Class used to receive params from requests (@RequestBody)
 * @author luizkowalski
 *
 */
public class Params implements Serializable {
	private static final long serialVersionUID = 6961393145500932303L;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
