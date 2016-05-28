package br.com.fiap.bean;

import java.time.LocalDateTime;


public class TwitterClass implements Comparable<TwitterClass>{

	private String nomeAutor;
	private LocalDateTime dataPublicacao;
	private String mensagem;
	
	
	public TwitterClass(String nomeAutor, LocalDateTime dataPublicacao, String mensagem) {
		this.nomeAutor = nomeAutor;
		this.dataPublicacao = dataPublicacao;
		this.mensagem = mensagem;
	}
	
	
	public String getNomeAutor() {
		return nomeAutor;
	}



	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
	}

	public String getMensagem() {
		return mensagem;
	}


	@Override
	public int compareTo(TwitterClass o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}
