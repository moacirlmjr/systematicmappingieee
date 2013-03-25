package br.com.ufpb.mapSis.main;

import java.io.File;
import java.util.List;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.integracao.post.postGoogleForm;
import br.com.ufpb.mapSis.parser.ParserXlsxScopus;

public class ExecutarMappingScopus {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File input = new File("ResultadosScopus.xlsx");
		try {
			List<Artigo> artigos = ParserXlsxScopus.realizarParserXlsx(input);
			
			for(Artigo a: artigos){
				postGoogleForm.post(a, "Scopus");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
