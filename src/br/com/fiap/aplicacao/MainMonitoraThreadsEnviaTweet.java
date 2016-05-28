package br.com.fiap.aplicacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import br.com.fiap.classes.executa.PublicarTwitter;
import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MainMonitoraThreadsEnviaTweet extends Thread {

	private List<Thread> threads;
	private Twitter credenciais;
	
	public MainMonitoraThreadsEnviaTweet(List<Thread> threads,Twitter credenciais) {
		this.threads = threads;
		this.credenciais = credenciais;
	}

	public void publica(String mensagem){
		//Aguarda 20 segundos para só prevenir o limite de acessos que permite o twitter
		try {
			Thread.sleep(20000);
			TwitterExecuta twitterAcoesString = new PublicarTwitter();
			twitterAcoesString.twitterExecuta(credenciais, mensagem, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		try {
			StringBuffer mensagem = new StringBuffer();
			
			for(int i =0; i< threads.size(); i++){
				while(true){
					if(threads.get(i).getState() == State.TERMINATED ){
						break;
					}else{
						Thread.sleep(10000);
					}
				}
			}
			
			int validaEnviar2Tweets=0;
			for(int i =0; i< threads.size(); i++){
				FileReader fr = new FileReader(threads.get(i).getName()+".txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while ((line = br.readLine()) != null) {
				    if (line.isEmpty()) {
				        break;
				    }
				    mensagem.append(line+" \n");
				}
				
				if(i==2){
					publica(mensagem.toString());
					mensagem.delete(0, mensagem.length());
				}
				
				if(i+1==threads.size()){
					publica(mensagem.toString());
					mensagem.delete(0, mensagem.length());
				}
				
				fr.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}
