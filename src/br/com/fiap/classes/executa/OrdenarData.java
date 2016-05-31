package br.com.fiap.classes.executa;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.bean.TwitterClass;
import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class OrdenarData implements TwitterExecuta {

	@SuppressWarnings("static-access")
	@Override
	public void twitterExecuta(Twitter credenciais, String mensagem, Thread thread) throws TwitterException, Exception {
		List<TwitterClass> executa = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime data = LocalDateTime.now();
		int numeroSolicitudesResult=-1;
		
		for(int i=1;i<8;i++){
			Query query = new Query(mensagem);
			
			String dataFormatadaDesde = data.format(formatter);
			String dataFormatadaAte = data.plusDays(1).format(formatter);
			data = data.plusDays(-1);
			query.setSince(dataFormatadaDesde);
			query.setUntil(dataFormatadaAte);
			QueryResult result;
			
			result = credenciais.search(query);
			
			while (result.hasNext()) {
				query = result.nextQuery();
				for (Status status : result.getTweets()) {
					
					numeroSolicitudesResult = result.getRateLimitStatus().getRemaining();
					//System.out.println(numeroSolicitudesResult + " - " + result.getTweets().size());
					
					TwitterClass twitterClass= new TwitterClass(status.getUser().getName(),Instant.ofEpochMilli(status.getCreatedAt().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() ,status.getText());
					executa.add(twitterClass);
					
					if(numeroSolicitudesResult<50 && numeroSolicitudesResult != -1 ){
						while(true){//getSecondsUntilReset
							thread.sleep(30000); 
							if(result.getRateLimitStatus().getRemaining()>=50 ) break;
						}
					}
					
				}
				result = credenciais.search(query);
			}
			
		}
		
		FileWriter arquivo = new FileWriter(new File(thread.getName()+".txt"));
		PrintWriter out = new PrintWriter(arquivo);
		//out.println("5.1 " +executa.get(0).getDataPublicacao());
		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		LocalDateTime data1 = executa.get(0).getDataPublicacao();
		String datanova1 = data1.format(formatter1);		
		out.println("5.1- " + datanova1);
		
		//out.print("5.2 " +executa.get(executa.size()-1).getDataPublicacao());
		
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss a");
		LocalDateTime data2 = executa.get(executa.size()-1).getDataPublicacao();
		String datanova2 = data2.format(formatter2);		
		out.println("5.2- " + datanova2);
		
		out.close();
		arquivo.close();

	}
}
