package br.com.ufpb.mapSis.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class Brasnam {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File f = new File("001.pdf");
		FileInputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (IOException e) {
			System.out.println("ERRO: " + e.getMessage());
		}

		PDDocument pdfDocument = null;
		try {
			PDFParser parser = new PDFParser(is);
			parser.parse();
			pdfDocument = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			System.out.println(stripper.getText(pdfDocument));
		} catch (IOException e) {
			System.out.println("ERRO: Não é possível abrir a stream" + e);
		} catch (Throwable e) {
			// Fazemos um catch, uma vez que precisamos de fechar o recurso
			System.out.println("ERRO: Um erro ocorreu enquanto tentava obter o conteúdo do PDF" + e);
		} finally {
			if (pdfDocument != null) {
				try {
					pdfDocument.close();
				} catch (IOException e) {
				  System.out.println("ERRO: Não foi possível fechar o PDF." + e);
				}
			}
		}

	}
}
