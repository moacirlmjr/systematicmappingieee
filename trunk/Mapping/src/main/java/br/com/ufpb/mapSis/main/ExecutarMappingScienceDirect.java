package br.com.ufpb.mapSis.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.ufpb.mapSis.beans.Artigo;
import br.com.ufpb.mapSis.integracao.post.postGoogleForm;

public class ExecutarMappingScienceDirect {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		try {
			File input = new File("scienceDirect.xlsx");
			InputStream fileStream = new FileInputStream(input);
			Workbook wb = new XSSFWorkbook(fileStream);
			Sheet sheet1 = wb.getSheetAt(0);
			List<Artigo> artigos = new LinkedList<Artigo>();
			for (Row row : sheet1) {
				Artigo artigo = new Artigo();
				if (row.getRowNum() != 0) {
					for (Cell cell : row) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							if (cell.getColumnIndex() == 0) {
								artigo.setTitulo(cell.getStringCellValue());
							} else if (cell.getColumnIndex() == 1) {
								artigo.setAutores(ExecutarMappingScienceDirect
										.replaceAcutesHTML(cell
												.getStringCellValue()));
							} else if (cell.getColumnIndex() == 2) {
								artigo.setOndePub(cell.getStringCellValue());
							} else if (cell.getColumnIndex() == 4) {
								artigo.setLinkDownload(cell
										.getStringCellValue());
							}
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (cell.getColumnIndex() == 3) {
								artigo.setPubYear((cell.getNumericCellValue() + "")
										.replace(".0", ""));
							}
							break;
						default:
							System.out.println();
						}
					}
					artigos.add(artigo);
				}
			}
			// List<Artigo> artigos =
			// ParserHtmlScienceDirect.realizarParserHtml(input);
			// FileUtil.criaArquivo("arquivo.csv", false);
			 for (Artigo a : artigos) {
			// FileUtil.escreveNoArquivo(a.getTitulo());
			// FileUtil.quebra();
			//
			// FileUtil.escreveNoArquivo(a.getAutores());
			// FileUtil.quebra();
			//
			// FileUtil.escreveNoArquivo(a.getOndePub());
			// FileUtil.quebra();
			//
			// FileUtil.escreveNoArquivo(a.getPubYear());
			// FileUtil.quebra();
			//
			// FileUtil.escreveNoArquivo(a.getLinkDownload());
			// FileUtil.quebra();
			//
			// FileUtil.escreveNoArquivo(a.getOrigem());
			// FileUtil.quebraLinha(1);
			// FileUtil.refresh();
				postGoogleForm.post(a, "Science Direct");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceAcutesHTML(String str) {

		str = str.replaceAll("&aacute;", "á");
		str = str.replaceAll("&eacute;", "é");
		str = str.replaceAll("&iacute;", "í");
		str = str.replaceAll("&oacute;", "ó");
		str = str.replaceAll("&uacute;", "ú");
		str = str.replaceAll("&Aacute;", "Á");
		str = str.replaceAll("&Eacute;", "É");
		str = str.replaceAll("&Iacute;", "Í");
		str = str.replaceAll("&Oacute;", "Ó");
		str = str.replaceAll("&Uacute;", "Ú");
		str = str.replaceAll("&ntilde;", "ñ");
		str = str.replaceAll("&Ntilde;", "Ñ");

		return str;
	}

}
