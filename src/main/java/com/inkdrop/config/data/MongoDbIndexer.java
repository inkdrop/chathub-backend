package com.inkdrop.config.data;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MongoDbIndexer {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void index(){
		log.info("Generating indexes...");
		// Room
		mongoTemplate.indexOps(Room.class).ensureIndex(new Index().on("uid",Direction.ASC));
		
		// User
		mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("backendAccessToken",Direction.ASC));
		mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("accessToken",Direction.ASC));
		log.info("Done");
	}
	
}
