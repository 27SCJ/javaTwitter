package br.com.fiap.interfaces;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public interface TwitterExecuta {

	public void twitterExecuta(Twitter credenciais,String mensagem,Thread thread) throws TwitterException,Exception;
	
}
