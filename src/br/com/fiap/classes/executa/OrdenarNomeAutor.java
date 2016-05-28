package br.com.fiap.classes.executa;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fiap.bean.TwitterClass;
import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class OrdenarNomeAutor implements TwitterExecuta {

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
			int contador=0;
			
			result = credenciais.search(query);
			
			while (result.hasNext()) {
				query = result.nextQuery();
				for (Status status : result.getTweets()) {
					
					numeroSolicitudesResult = result.getRateLimitStatus().getRemaining();
					System.out.println(numeroSolicitudesResult + " - " + result.getTweets().size());
					
					//TwitterClass twitterClass= new TwitterClass(status.getUser().getName(),status.getUser().getScreenName(),LocalDateTime.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(status.getCreatedAt())) ,status.getText());
					TwitterClass twitterClass= new TwitterClass(status.getUser().getName(),Instant.ofEpochMilli(status.getCreatedAt().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() ,status.getText());
					executa.add(twitterClass);
					
					if(numeroSolicitudesResult<50 && numeroSolicitudesResult != -1 ){
						while(true){//getSecondsUntilReset
							thread.sleep(30000); 
							if(result.getRateLimitStatus().getRemaining()>=50 ) break;
						}
					}
					
					//System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
				}
				result = credenciais.search(query);
			}
			
		}
		
		Collections.sort(executa,(p1,p2) -> p1.getNomeAutor().toUpperCase().trim().compareTo(p2.getNomeAutor().toUpperCase().trim()));
		
		FileWriter arquivo = new FileWriter(new File(thread.getName()+".txt"));
		PrintWriter out = new PrintWriter(arquivo);
		out.println("4.1 "+executa.get(0).getNomeAutor());
		out.print("4.2 " +executa.get(executa.size()-1).getNomeAutor());
		out.close();
		arquivo.close();
		
	}


}
