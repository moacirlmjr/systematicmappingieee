package br.com.ufpb.mapSis;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.ufpb.mapSis.beans.Artigo;

public class JsoupTest {

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
	
	private static final String ARTIGO = "detail";
	private static final String LIST_ARTIGOS = "header";
	private static final String SEARCH_RESULTS = "Results";
	private static final String BUSCADOR_VALUE = "IEEE Xplore";

	public static void main(String[] args) {
		try {
			File input = new File("htmls");
			List<Artigo> artigos = new ArrayList<Artigo>();
			for (String html : input.list()) {
				File fileHtml = new File("htmls\\" + html);
				Document doc = Jsoup.parse(fileHtml, "UTF-8", "localhost");
				for (Element e : doc.select("ul")) {
					if (e.className().equalsIgnoreCase(SEARCH_RESULTS)) {
						for (Element subE : e.getElementsByClass(LIST_ARTIGOS)) {
							Artigo artigo = new Artigo();
							Element detail = subE.getElementsByClass(ARTIGO).first();
							Element H3 = detail.getElementsByTag("h3").first();
							if (H3.getElementsByTag("a").first() != null) {
								artigo.setTitulo(H3.getElementsByTag("a").first().text());

								String textoDetail[] = detail.toString().split("\n");
								for (String texto : textoDetail) {
									if (texto.contains("<h3>")) {
										artigo.setAutores(texto.split("</h3> ")[1]);
									} else if (texto.contains("Publication Year")) {
										artigo.setPubYear(texto.split(": ")[1]);
									} else if (texto.contains("RecentIssue.jsp")) {
										Document doc2 = Jsoup.parseBodyFragment(texto);
										Element link = doc2.body();
										artigo.setOndePub(link.text());
									} else if (texto.contains("stamp.jsp?")) {
										Document doc2 = Jsoup.parseBodyFragment(texto);
										Element linkDownloadElement = doc2.body();
										for (Element link : linkDownloadElement.getElementsByAttribute("href")) {
											if (link.attr("href").contains("stamp.jsp?")) {
												artigo.setLinkDownload(link.attr("href"));
												break;
											}
										}

									}
								}
								artigos.add(artigo);
							}
						}

					}
				}
			}
			for(Artigo a: artigos){
				String data = "";
				try{
					data = URLEncoder.encode(TITULO, "UTF-8") + "="
							+ URLEncoder.encode(a.getTitulo(), "UTF-8") + "&"
							
							+ URLEncoder.encode(AUTORES, "UTF-8") + "="
							+ URLEncoder.encode(a.getAutores(), "UTF-8") + "&"
							
							+ URLEncoder.encode(ANO_PUBLICACAO, "UTF-8") + "="
							+ URLEncoder.encode(a.getPubYear(), "UTF-8") + "&"
							
							+ URLEncoder.encode(CONGRESSO, "UTF-8") + "="
							+ URLEncoder.encode(a.getOndePub(), "UTF-8") + "&"
							
							+ URLEncoder.encode(LINK_DOWNLOAD, "UTF-8") + "="
							+ URLEncoder.encode(a.getLinkDownload(), "UTF-8") + "&"
							
							+ URLEncoder.encode(BUSCADOR, "UTF-8") + "="
							+ URLEncoder.encode(BUSCADOR_VALUE, "UTF-8") + "&";
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


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
