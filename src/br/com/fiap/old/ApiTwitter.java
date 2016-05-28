package br.com.fiap.old;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ApiTwitter {


	/**
	 * Funcao para pegaro accessToken do twitter
	 * @return
	 */
	private static AccessToken loadAccessToken(){
		String token = "730883420855930880-xMQHnyDrFsckC1tLH7DoQa5Upr7d4b1";
		String tokenSecret ="CZflb4Clto1YgfgOvHlXwoO2jVSi3uaApPtbgXmCnPp2B";
		return new AccessToken(token, tokenSecret);
	}
	
	/**
	 * Funcao para Postar Uma Mensagem
	 * @param Mensagem
	 * @return
	 */
	public String TwittaMensagem(String Mensagem) {
		String resposta = "";
		try {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey("Rp78Sw0fFNCJJV9p5yPIhwL8b");
			builder.setOAuthConsumerSecret("NgYHU2LhuATip1Xkf3xWe6QLuedcChsEWG39XhghjExlOuaJn8");
			Configuration configuration = builder.build();
			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			AccessToken accessToken = loadAccessToken();
			twitter.setOAuthAccessToken(accessToken);
			Status status = twitter.updateStatus(Mensagem);
			resposta = "Tweet postado com sucesso! [" +	status.getText() + "].";
		} catch (Exception e) {
			//e.printStackTrace();
			resposta = e.getMessage();
			resposta = "Erro ao postar Tweet.";
		}
		return resposta;
	}

	
	/**
	 * Quantidade x Dia Tweets UltimaSemana
	 * @param Mensagem
	 * @return
	 */
	public String QuantidadexDiaTweetsUltimaSemana(String mensagem) {
		String resposta = "";
		try {
			/*Funcao Generica para pegar Acesso*/
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey("Rp78Sw0fFNCJJV9p5yPIhwL8b");
			builder.setOAuthConsumerSecret("NgYHU2LhuATip1Xkf3xWe6QLuedcChsEWG39XhghjExlOuaJn8");
			Configuration configuration = builder.build();
			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			AccessToken accessToken = loadAccessToken();
			twitter.setOAuthAccessToken(accessToken);
			/*----*/
			
			Query query = new Query(mensagem);
			//query.setResultType(ResultType.popular);
            QueryResult result;
            /*do {*/
                result = twitter.search(query);
   
                System.out.println("Entro aqui  "+ mensagem);
                List<Status> tweets = result.getTweets();
                System.out.println("Quantidade x dia: "+ tweets.size());
                for (Status tweet : tweets) {
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            /*} while ((query = result.nextQuery()) != null);
            System.exit(0);*/
			
		} catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }catch (Exception e) {
			//e.printStackTrace();
			resposta = e.getMessage();
			resposta = "Erro ao postar Tweet.";
		}
		return resposta;
	}


	// Getting Timeline - Twitter.get**** Timeline() returns a List of latest tweets from user's home timeline.
	public String TimelineTwitter() {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			// The factory instance is re-useable and thread safe.			
			//Twitter twitter = TwitterFactory.getSingleton();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing home timeline.");
			int contador = 0;
			for (Status status : statuses) {
				contador = contador + 1;
				System.out.println("@ " + status.getUser().getName() + ":" + status.getCreatedAt() + " - " +  status.getText() + "Retweets: " + status.getRetweetCount());
			}
			System.out.println(contador + " registros encontrados.");
			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}

	// Search for Tweets - You can search for Tweets using Query class and Twitter.search(twitter4j.Query) method.
	public String BuscaTweets(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumeKey#####")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			int contador = 0;
			Query query = new Query(texto);
			query.setCount(100);
			QueryResult result = twitter.search(query);

			for (Status status : result.getTweets()) {
				contador = contador + 1;
				System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			}
			System.out.println(contador + " registros encontrados.");
			System.out.println();

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}

	// 1. Quantidade por dia de tweets da última semana.
	public String BuscaTweetsFullporDia(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			/*
			 * 

				int contador = 0;
				Query query = new Query(texto);
				query.setCount(1000);
			    QueryResult result = twitter.search(query);

			    for (Status status : result.getTweets()) {
				 	contador = contador + 1;
					System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			    }
			    System.out.println(contador + " registros encontrados.");
				System.out.println();
			 */
			// Contar a quantidade de Mensagens 
			//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(texto);
			int numberOfTweets = 20000;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			System.out.println("Contando tweets: " + tweets.size());

			
			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered: " + tweets.size() + " tweets");
					for (Status t: tweets) 
						if(t.getId() < lastID) lastID = t.getId();
				}
				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}; 
				query.setMaxId(lastID-1);
			}
			System.out.println("");

			System.out.println("Exibindo Tweets");
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				GeoLocation loc = t.getGeoLocation();
				String user = t.getUser().getScreenName();
				String msg = t.getText();
				Date time = t.getCreatedAt();
				int j = i + 1;
				if (loc!=null) {
					Double lat = t.getGeoLocation().getLatitude();
					Double lon = t.getGeoLocation().getLongitude();
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg + " located at " + lat + ", " + lon);
				} 
				else 
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg);
			}

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}	

	// Usar estas função abaixo para o trabalho	
	// 2. Quantidade por dia de retweets da última semana.
	public String BuscaReTweetsFullporDia(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			/*
			 * 

				int contador = 0;
				Query query = new Query(texto);
				query.setCount(1000);
			    QueryResult result = twitter.search(query);

			    for (Status status : result.getTweets()) {
				 	contador = contador + 1;
					System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			    }
			    System.out.println(contador + " registros encontrados.");
				System.out.println();
			 */

			//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(texto);
			int numberOfTweets = 20000;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			System.out.println("Contando tweets: " + tweets.size());

			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered: " + tweets.size() + " tweets");
					for (Status t: tweets) 
						if(t.getId() < lastID) lastID = t.getId();
				}
				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}; 
				query.setMaxId(lastID-1);
			}
			System.out.println("");

			System.out.println("Exibindo Tweets");
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				GeoLocation loc = t.getGeoLocation();
				String user = t.getUser().getScreenName();
				String msg = t.getText();
				Date time = t.getCreatedAt();
				int j = i + 1;
				if (loc!=null) {
					Double lat = t.getGeoLocation().getLatitude();
					Double lon = t.getGeoLocation().getLongitude();
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg + " located at " + lat + ", " + lon);
				} 
				else 
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg);
			}

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}	

	// Usar estas função abaixo para o trabalho	
	//3. Quantidade por dia de favoritações da última semana.
	public String BuscaFavoritosFullnaSemana(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			/*
			 * 

				int contador = 0;
				Query query = new Query(texto);
				query.setCount(1000);
			    QueryResult result = twitter.search(query);

			    for (Status status : result.getTweets()) {
				 	contador = contador + 1;
					System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			    }
			    System.out.println(contador + " registros encontrados.");
				System.out.println();
			 */

			//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(texto);
			int numberOfTweets = 20000;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			System.out.println("Contando tweets: " + tweets.size());

			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered: " + tweets.size() + " tweets");
					for (Status t: tweets) 
						if(t.getId() < lastID) lastID = t.getId();
				}
				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}; 
				query.setMaxId(lastID-1);
			}
			System.out.println("");

			System.out.println("Exibindo Tweets");
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				GeoLocation loc = t.getGeoLocation();
				String user = t.getUser().getScreenName();
				String msg = t.getText();
				Date time = t.getCreatedAt();
				//t.getRetweetedStatus();
				//t.getFavoriteCount()
				int j = i + 1;
				if (loc!=null) {
					Double lat = t.getGeoLocation().getLatitude();
					Double lon = t.getGeoLocation().getLongitude();
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg + " located at " + lat + ", " + lon);
				} 
				else 
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg);
			}

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}	

	// Usar estas função abaixo para o trabalho	
	// 4. Ordenar os tweets pelo nome do autor, e exibir o primeiro nome e o último nome.
	public String BuscaTweetsFullPorNome(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	

			/*
			 *
				int contador = 0;
				Query query = new Query(texto);
				query.setCount(1000);
			    QueryResult result = twitter.search(query);

			    for (Status status : result.getTweets()) {
				 	contador = contador + 1;
					System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			    }
			    System.out.println(contador + " registros encontrados.");
				System.out.println();
			 */

			//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(texto);
			int numberOfTweets = 20000;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			System.out.println("Contando tweets: " + tweets.size());

			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered: " + tweets.size() + " tweets");
					for (Status t: tweets) 
						if(t.getId() < lastID) lastID = t.getId();
				}
				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}; 
				query.setMaxId(lastID-1);
			}
			System.out.println("");

			System.out.println("Exibindo Tweets");
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				GeoLocation loc = t.getGeoLocation();
				String user = t.getUser().getScreenName();
				String msg = t.getText();
				Date time = t.getCreatedAt();
				int j = i + 1;
				if (loc!=null) {
					Double lat = t.getGeoLocation().getLatitude();
					Double lon = t.getGeoLocation().getLongitude();
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg + " located at " + lat + ", " + lon);
				} 
				else 
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg);
			}

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}	
	// Usar estas função abaixo para o trabalho	
	
	// 5. Ordenar os tweets por data, e exibir a data mais recente e a menos recente.
	public String BuscaTweetsFullporData(String texto) {
		String resposta = "";
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("ConsumerKey###")
			.setOAuthConsumerSecret("ConsumerSecret###")
			.setOAuthAccessToken("AccessToken###")
			.setOAuthAccessTokenSecret("TokenSecret###");
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();	
			/*
			 * 	int contador = 0;
				Query query = new Query(texto);
				query.setCount(1000);
			    QueryResult result = twitter.search(query);

			    for (Status status : result.getTweets()) {
				 	contador = contador + 1;
					System.out.println(status.getId() + " @" + status.getUser().getScreenName() + ":"  + status.getCreatedAt() + " - " + status.getText()+ " Retweets: " + status.getRetweetCount() + " Favoritos: " + status.getFavoriteCount());
			    }
			    System.out.println(contador + " registros encontrados.");
				System.out.println();
			 */

			//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Query query = new Query(texto);
			int numberOfTweets = 20000;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();
			System.out.println("Contando tweets: " + tweets.size());

			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else 
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered: " + tweets.size() + " tweets");
					for (Status t: tweets) 
						if(t.getId() < lastID) lastID = t.getId();
				}
				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}; 
				query.setMaxId(lastID-1);
			}
			System.out.println("");

			System.out.println("Exibindo Tweets");
			for (int i = 0; i < tweets.size(); i++) {
				Status t = (Status) tweets.get(i);
				GeoLocation loc = t.getGeoLocation();
				String user = t.getUser().getScreenName();
				String msg = t.getText();
				Date time = t.getCreatedAt();
				int j = i + 1;
				if (loc!=null) {
					Double lat = t.getGeoLocation().getLatitude();
					Double lon = t.getGeoLocation().getLongitude();
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg + " located at " + lat + ", " + lon);
				} 
				else 
					System.out.println(j + " USER: " + user + "Date: " + time +  " wrote: " + msg);
			}

			resposta = "Timeline do Tweet.";

		} catch (Exception e) {
			e.printStackTrace();
			resposta = "Erro ao listar Timeline.";
		}
		return resposta;
	}	

}