package br.com.fiap.aplicacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.fiap.bean.Credenciais;
import br.com.fiap.bean.TwitterClass;
import br.com.fiap.classes.executa.OrdenarNomeAutor;
import br.com.fiap.classes.executa.OrdenarData;
import br.com.fiap.classes.executa.QuantidadeFavoritosUltimaSemana;
import br.com.fiap.classes.executa.QuantidadeReTweetsUltimaSemana;
import br.com.fiap.classes.executa.QuantidadeTweetsUltimaSemana;
import twitter4j.Twitter;

public class MainEstatisticoTwitter {
	public static void main(String[] args)  {

		
		try {
			
			String palavraChave = "#java8";
			//Generico
			Twitter credenciais = new Credenciais("bWpg95EUYI3Wah4YAtEOxTzwh",
												  "luaCmtCrorKMTD92CpFpLL1YjNwp2ozZqbvROFz2RxPtYG4SLc",
												  "736572641339953152-vFsPTpkkKcx3Vi289o3Qwl4HElqk75y",
												  "SQIekM3OC98CeuEg3rZatu56WClgIThp7SwMlQAuEFfTx").criaAcesso();
			
			//Quantidade de tweets
			QuantidadeTweetsUltimaSemana quantidadePorDia = new QuantidadeTweetsUltimaSemana();
			TwitterAcoes<Map<Integer, Integer>> twitterAcoesMapTweet = new TwitterAcoes<Map<Integer, Integer>>(credenciais,quantidadePorDia,palavraChave);
			Thread threadTweets=new Thread(twitterAcoesMapTweet);
			threadTweets.setName(quantidadePorDia.getClass().getSimpleName());
			threadTweets.start();
						
			//Quantidade de Retweetss
			QuantidadeReTweetsUltimaSemana quantidadeRePorDia = new QuantidadeReTweetsUltimaSemana();
			TwitterAcoes<Map<Integer, Integer>> twitterAcoesMapRe = new TwitterAcoes<Map<Integer, Integer>>(credenciais,quantidadeRePorDia,palavraChave);
			Thread threadReTweets=new Thread(twitterAcoesMapRe);
			threadReTweets.setName(quantidadeRePorDia.getClass().getSimpleName());
			threadReTweets.start();
			
			//Quantidade de Favoritos
			QuantidadeFavoritosUltimaSemana quantidadeFavoritosPorDia = new QuantidadeFavoritosUltimaSemana();
			TwitterAcoes<Map<Integer, Integer>> twitterAcoesMapFav = new TwitterAcoes<Map<Integer, Integer>>(credenciais,quantidadeFavoritosPorDia,palavraChave);
			Thread threadFavoritosTweets=new Thread(twitterAcoesMapFav);
			threadFavoritosTweets.setName(quantidadeFavoritosPorDia.getClass().getSimpleName());
			threadFavoritosTweets.start();
			
			//Ordenar Lista ordenada por Nome do autor
			OrdenarNomeAutor ordenarNomeAutor = new OrdenarNomeAutor();
			TwitterAcoes<List<TwitterClass>> twitterAcoesListNomeAutor = new TwitterAcoes<List<TwitterClass>>(credenciais,ordenarNomeAutor,palavraChave);
			Thread threadOrdenarPorNome=new Thread(twitterAcoesListNomeAutor);
			threadOrdenarPorNome.setName(ordenarNomeAutor.getClass().getSimpleName());
			threadOrdenarPorNome.start();
			
			//Ordenar Lista ordenada por Data
			OrdenarData ordenarData = new OrdenarData();
			TwitterAcoes<List<TwitterClass>> twitterAcoesListData = new TwitterAcoes<List<TwitterClass>>(credenciais,ordenarData,palavraChave);
			Thread threadListaOrdenadaData=new Thread(twitterAcoesListData);
			threadListaOrdenadaData.setName(ordenarData.getClass().getSimpleName());
			threadListaOrdenadaData.start();
			
			List<Thread> threadMonitora = new ArrayList<>();
			threadMonitora.add(threadTweets);
			threadMonitora.add(threadReTweets);
			threadMonitora.add(threadFavoritosTweets);
			threadMonitora.add(threadOrdenarPorNome);
			threadMonitora.add(threadListaOrdenadaData);
			
			//Publica Mensagem
			MainMonitoraThreadsEnviaTweet monitoraThreads = new MainMonitoraThreadsEnviaTweet(threadMonitora,credenciais);
			monitoraThreads.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
