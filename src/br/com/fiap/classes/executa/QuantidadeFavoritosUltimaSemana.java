package br.com.fiap.classes.executa;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;

import br.com.fiap.interfaces.TwitterExecuta;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class QuantidadeFavoritosUltimaSemana implements TwitterExecuta {

	@SuppressWarnings("static-access")
	@Override
	public void twitterExecuta(Twitter credenciais, String mensagem, Thread thread) throws TwitterException, Exception {
		Map<Integer, Integer> executa = new Hashtable<>();

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
					
					contador+= status.getFavoriteCount();
					
					if(numeroSolicitudesResult<50 && numeroSolicitudesResult != -1 ){
						while(true){//getSecondsUntilReset
							thread.sleep(30000); 
							if(result.getRateLimitStatus().getRemaining()>=50 ) break;
						}
					}
					
				}
				
				result = credenciais.search(query);
			}
			executa.put(i, contador);
		}
		
		FileWriter arquivo = new FileWriter(new File(thread.getName()+".txt"));
		PrintWriter out = new PrintWriter(arquivo);
		
		StringBuffer valores = new StringBuffer("3. ");
		for(int i=1; i<=executa.size();i++)
			valores.append(" "+i+"-"+executa.get(i));
			
	    out.print(valores.toString());
		out.close();
		arquivo.close();
		
	}

	
}
