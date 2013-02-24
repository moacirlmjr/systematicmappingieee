package br.com.ufpb.mapSis.beans;

public class Artigo {
	private String titulo;
	private String autores;
	private String pubYear;
	private String ondePub;
	private String linkDownload;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public String getOndePub() {
		return ondePub;
	}

	public void setOndePub(String ondePub) {
		this.ondePub = ondePub;
	}

	public String getLinkDownload() {
		return linkDownload;
	}

	public void setLinkDownload(String linkDownload) {
		this.linkDownload = linkDownload;
	}

}
