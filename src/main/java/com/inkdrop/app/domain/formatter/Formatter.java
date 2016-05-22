package com.inkdrop.app.domain.formatter;

@FunctionalInterface
public interface Formatter {
	String toJson(Object object);
}
