package com.inkdrop.config.data;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
//@Slf4j
public class MongoDbIndexer {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void index(){
//		log.info("Generating indexes...");
//		// Room
//		mongoTemplate.indexOps(Room.class).ensureIndex(new Index().on("uid",Direction.ASC));
//		
//		// User
//		mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("backendAccessToken",Direction.ASC));
//		mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("accessToken",Direction.ASC));
//		log.info("Done");
	}
	
}
