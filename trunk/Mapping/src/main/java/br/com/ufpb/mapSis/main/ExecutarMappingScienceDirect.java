package br.com.ufpb.mapSis.main;

import java.io.File;
import java.util.List;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.integracao.post.postGoogleForm;
import br.com.ufpb.mapSis.parser.ParserHtmlACM;
import br.com.ufpb.mapSis.parser.ParserHtmlScienceDirect;

public class ExecutarMappingScienceDirect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File input = new File("scienceHtml");
		try {
			List<Artigo> artigos = ParserHtmlScienceDirect.realizarParserHtml(input);

			for (Artigo a : artigos) {
				postGoogleForm.post(a,"Science Direct");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
