package br.com.fiap.aplicacao;


import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterAcoes<E> extends Thread {

	private Twitter credenciais;
	private TwitterExecuta twitterExecuta;
	private String mensagem;
	private E retorno;
	
	public E retorno(){
		return retorno;
	}
	
	public TwitterAcoes(Twitter credenciais, TwitterExecuta twitterExecuta, String mensagem) {
		this.credenciais = credenciais;
		this.twitterExecuta = twitterExecuta;
		this.mensagem = mensagem;
	}

	@Override
	public void run() {
		try {
			executa(Thread.currentThread());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void executa(Thread thread) throws TwitterException, Exception{
		twitterExecuta.twitterExecuta(credenciais,mensagem,thread);
	}
	
}
