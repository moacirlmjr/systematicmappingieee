package br.com.ufpb.mapSis.main;

import java.io.File;
import java.util.List;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.integracao.post.postGoogleForm;
import br.com.ufpb.mapSis.parser.ParserHtmlSpringer;

public class ExecutarMappingSpringer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File input = new File("htmlSpringer");
		try {
			List<Artigo> artigos = ParserHtmlSpringer.realizarParserHtml(input);
			
			for(Artigo a: artigos){
				postGoogleForm.post(a, "Springer Link");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
