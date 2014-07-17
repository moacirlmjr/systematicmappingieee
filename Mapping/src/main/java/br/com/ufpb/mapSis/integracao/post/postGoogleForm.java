package br.com.ufpb.mapSis.integracao.post;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import br.com.ufpb.mapSis.beans.Artigo;

public class postGoogleForm {
	
	//TESTES
	private static final String URL_FORM = "https://docs.google.com/forms/d/17SMNfbjjR4Ej9vKIzGRWb_btMC2JmKm7tWGMzwKbMyM/formResponse";
	private static final String BUSCADOR = "entry.711462740";
	private static final String LINK_DOWNLOAD = "entry.846156339";
	private static final String CONGRESSO = "entry.1871036549";
	private static final String ANO_PUBLICACAO = "entry.1367161724";
	private static final String AUTORES = "entry.1022653856";
	private static final String TITULO = "entry.1147950154";
	
	//FORM DE ALEXANDRE
	
//	private static final String URL_FORM = "https://docs.google.com/forms/d/1xR8RklZsSGngh1QI6hs4SMCVVtySwIeANS6aS-GrPLE/formResponse";
//	private static final String TITULO = "entry.8648199";
//	private static final String ANO_PUBLICACAO = "entry.158585387";
//	private static final String AUTORES = "entry.1780683021";
//	private static final String CONGRESSO = "entry.2018964305";
//	private static final String BUSCADOR = "entry.1170487382";
//	private static final String LINK_DOWNLOAD = "entry.1657641642";
	
	public static void post(Artigo a, String buscador) throws Exception{
		String data = "";
		
		try{
			data = URLEncoder.encode(TITULO, "UTF-8") + "="
					+ URLEncoder.encode(a.getTitulo(), "UTF-8") + "&"
					
					+ URLEncoder.encode(AUTORES, "UTF-8") + "="
					+ a.getAutores() + "&"
					
					+ URLEncoder.encode(ANO_PUBLICACAO, "UTF-8") + "="
					+ URLEncoder.encode(a.getPubYear(), "UTF-8") + "&"
					
					+ URLEncoder.encode(CONGRESSO, "UTF-8") + "="
					+ URLEncoder.encode(a.getOndePub(), "UTF-8") + "&"
					
					+ URLEncoder.encode(LINK_DOWNLOAD, "UTF-8") + "="
					+ URLEncoder.encode(a.getLinkDownload(), "UTF-8") + "&"
					
					+ URLEncoder.encode(BUSCADOR, "UTF-8") + "="
					+ URLEncoder.encode(buscador, "UTF-8") + "&";
		}catch (Exception e) {
			e.printStackTrace();
		}
		URL url = new URL(URL_FORM);
		URLConnection urlConnection = url.openConnection();
		
		// envia dados
		urlConnection.setDoOutput(true);
		OutputStreamWriter outputWriter = new OutputStreamWriter(
				urlConnection.getOutputStream());
		outputWriter.write(data);
		outputWriter.flush();
		
		// Obtem as respostas
		InputStreamReader inputReader = new InputStreamReader(
				urlConnection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		
		String linha = "";
		while ((linha = bufferedReader.readLine()) != null) {
			if(linha.contains("<title>")){
				System.out.println(linha);
				break;
			}
		}
	}
	
}
