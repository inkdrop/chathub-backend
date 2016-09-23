package com.inkdrop.config.data;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.BasePersistable;

@Component
public class MongoDbEventListener extends AbstractMongoEventListener<BasePersistable>{

	@Override
	public void onBeforeConvert(BeforeConvertEvent<BasePersistable> event) {
		event.getSource().onCreate();
		event.getSource().onUpdate();
	}
}
