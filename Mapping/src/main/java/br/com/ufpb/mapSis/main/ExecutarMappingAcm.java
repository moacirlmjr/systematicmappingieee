package br.com.ufpb.mapSis.main;

import java.io.File;
import java.util.List;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.integracao.post.postGoogleForm;
import br.com.ufpb.mapSis.parser.ParserHtmlACM;

public class ExecutarMappingAcm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File input = new File("acmHtml");
		try {
			List<Artigo> artigos = ParserHtmlACM.realizarParserHtml(input);

			for (Artigo a : artigos) {
				postGoogleForm.post(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
