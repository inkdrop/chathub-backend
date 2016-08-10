package com.inkdrop.app.feignInterfaces;


import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Accepet: application/json")
public interface GithubAccessToken {
	@RequestLine(value="POST /login/oauth/access_token?client_id={cid}&client_secret={secret}&code={code}")
	String accessToken(@Param("cid") String cid, 
			@Param("secret") String secret, 
			@Param("code") String code);
	
	static class Token {
		  String access_token;
		  String scope;
		  String token_type;
		  
		  public String getAccess_token() {
			return access_token;
		  }
		}
}
