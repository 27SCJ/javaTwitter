package br.com.fiap.classes.executa;

import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PublicarTwitter implements TwitterExecuta {

	@Override
	public void twitterExecuta(Twitter credenciais, String mensagem, Thread thread) throws TwitterException,Exception {
		credenciais.updateStatus(mensagem + " @michelpf" );
	}

}
