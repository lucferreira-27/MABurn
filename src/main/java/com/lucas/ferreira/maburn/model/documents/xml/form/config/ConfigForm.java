package com.lucas.ferreira.maburn.model.documents.xml.form.config;

public class ConfigForm {

	private GeralConfigForm geralConfigForm = new GeralConfigForm();
	private AnimeConfigForm mangaConfig = new AnimeConfigForm();
	private MangaConfigForm animeConfig = new MangaConfigForm();

	public MangaConfigForm getAnimeConfig() {
		return animeConfig;
	}

	public void setAnimeConfig(MangaConfigForm animeConfig) {
		this.animeConfig = animeConfig;
	}

	public AnimeConfigForm getMangaConfig() {
		return mangaConfig;
	}

	public void setMangaConfig(AnimeConfigForm mangaConfig) {
		this.mangaConfig = mangaConfig;
	}

	public GeralConfigForm getGeralConfigForm() {
		return geralConfigForm;
	}

	public void setGeralConfigForm(GeralConfigForm geralConfigForm) {
		this.geralConfigForm = geralConfigForm;
	}
}
