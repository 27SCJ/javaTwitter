package br.com.fiap.bean;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Credenciais {

	private String oAuthConsumerKey;
	private String oAuthConsumerSecret;
	private String token;
	private String tokenSecret;
	
	
	public Credenciais(String oAuthConsumerKey, String oAuthConsumerSecret, String token,
			String tokenSecret) {
		this.oAuthConsumerKey = oAuthConsumerKey;
		this.oAuthConsumerSecret = oAuthConsumerSecret;
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	
	private static AccessToken loadAccessToken(String token,String tokenSecret){
		return new AccessToken(token, tokenSecret);
	}
	
	public Twitter criaAcesso() {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(oAuthConsumerKey);
		builder.setOAuthConsumerSecret(oAuthConsumerSecret);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		AccessToken accessToken = loadAccessToken(token,tokenSecret);
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}
	
}
