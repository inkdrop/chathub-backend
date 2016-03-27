package com.inkdrop.app.controllers.api.v1;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class BasicController {

	static class Params implements Serializable {
		private static final long serialVersionUID = 6961393145500932303L;

		
		public Params() {}
		
		@JsonCreator
		public Params(@JsonProperty("content")String content) {
			super();
			this.content = content;
		}

		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
