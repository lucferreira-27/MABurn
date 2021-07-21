package com.lucas.ferreira.maburn.model.documents.xml.form.config;

import com.lucas.ferreira.maburn.model.enums.Category;

public class ConfigForm {

	private GeralConfigForm geralConfigForm = new GeralConfigForm();
	private AnimeConfigForm animeConfig = new AnimeConfigForm();
	private MangaConfigForm mangaConfig = new MangaConfigForm();
	
	public AnimeConfigForm getAnimeConfig() {
		return animeConfig;
	}

	public void setAnimeConfig(AnimeConfigForm animeConfig) {
		this.animeConfig = animeConfig;
	}

	public MangaConfigForm getMangaConfig() {
		return mangaConfig;
	}

	public void setMangaConfig(MangaConfigForm mangaConfig) {
		this.mangaConfig = mangaConfig;
	}

	public GeralConfigForm getGeralConfigForm() {
		return geralConfigForm;
	}

	public void setGeralConfigForm(GeralConfigForm geralConfigForm) {
		this.geralConfigForm = geralConfigForm;
	}

	public TitleConfigForm getTitleConfigFormByCategory(Category category) {
		if (category == Category.ANIME) {
			return animeConfig;
		} else if (category == Category.MANGA) {
			return mangaConfig;
		}
		return null;
	}
}
