package br.com.ufpb.mapSis.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.ufpb.mapSis.beans.Artigo;

public class ParserHtmlIEEE {

	private static final String ARTIGO = "detail";
	private static final String LIST_ARTIGOS = "header";
	private static final String SEARCH_RESULTS = "Results";

	public static List<Artigo> realizarParserHtml(File diretorio)
			throws Exception {

		List<Artigo> artigos = new ArrayList<Artigo>();
		if (diretorio.isDirectory()) {
			for (File html : diretorio.listFiles()) {
				if (html.getName().contains("html")) {
					Document doc = Jsoup.parse(html, "UTF-8", "localhost");
					for (Element e : doc.select("ul")) {
						if (e.className().equalsIgnoreCase(SEARCH_RESULTS)) {
							for (Element subE : e
									.getElementsByClass(LIST_ARTIGOS)) {
								Boolean test = new Boolean(false);
								Artigo artigo = new Artigo();
								Element detail = subE
										.getElementsByClass(ARTIGO).first();
								Element H3 = detail.getElementsByTag("h3")
										.first();
								
								if (H3.getElementsByTag("a").first() != null) {
									artigo.setTitulo(H3.getElementsByTag("a")
											.first().text());
								} else {
									artigo.setTitulo(H3.text());
								}
								String textoDetail[] = detail.toString().split(
										"\n");
								Elements aAuthors = detail.getElementsByClass("prefNameLink");
								artigo.setAutores("");
								for(Element author: aAuthors){
									artigo.setAutores(artigo.getAutores() + " " + author.text());
									test = true;
								}
								
								for (String texto : textoDetail) {
									if (texto
											.contains("Publication Year")) {
										artigo.setPubYear(texto.split(": ")[1]);
									} else if (texto
											.contains("RecentIssue.jsp")) {
										Document doc2 = Jsoup
												.parseBodyFragment(texto);
										Element link = doc2.body();
										artigo.setOndePub(link.text());
									} else if (texto.contains("stamp.jsp?")) {
										Document doc2 = Jsoup
												.parseBodyFragment(texto);
										Element linkDownloadElement = doc2
												.body();
										for (Element link : linkDownloadElement
												.getElementsByAttribute("href")) {
											if (link.attr("href").contains(
													"stamp.jsp?")) {
												artigo.setLinkDownload(link
														.attr("href"));
												break;
											}
										}

									}
								}
								if(test){
									artigos.add(artigo);
								}
							}

						}
					}
				}
			}
		}

		return artigos;
	}

}
