package br.com.ufpb.mapSis.main;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.parser.ParserXlsxRespostas;
import br.com.ufpb.mapSis.util.FileUtil;

public class ExecutarRemocaoRepetidos {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File input = new File("respostas.xlsx");
		try {
			List<Artigo> artigos = ParserXlsxRespostas
					.realizarParserXlsx(input);
			System.out.println("");

			Map<String, Artigo> artigosMap = new LinkedHashMap<String, Artigo>();

			for (Artigo a : artigos) {
				if (!a.getOndePub().contains(a.getTitulo())) {
					if (!artigosMap.containsKey(a.getTitulo().toLowerCase())) {
						artigosMap.put(a.getTitulo().toLowerCase(), a);
					} else {
						Artigo art = artigosMap
								.get(a.getTitulo().toLowerCase());
						if (art.getLinkDownload().contains("Sem")) {
							artigosMap.put(a.getTitulo().toLowerCase(), a);
						}
					}
				}
			}

			FileUtil.criaArquivo("respostas.csv", false);
			for (String titulo : artigosMap.keySet()) {
				Artigo a = artigosMap.get(titulo);
				FileUtil.escreveNoArquivo(a.getTitulo() + "");
				FileUtil.quebra();

				FileUtil.escreveNoArquivo(a.getAutores() + "");
				FileUtil.quebra();

				FileUtil.escreveNoArquivo(a.getOndePub() + "");
				FileUtil.quebra();

				FileUtil.escreveNoArquivo(a.getPubYear() + "");
				FileUtil.quebra();

				FileUtil.escreveNoArquivo(a.getLinkDownload() + "");
				FileUtil.quebra();

				FileUtil.quebraLinha(1);
				FileUtil.refresh();
			}

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
