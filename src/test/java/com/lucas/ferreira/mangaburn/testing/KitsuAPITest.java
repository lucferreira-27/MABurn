package com.lucas.ferreira.mangaburn.testing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.lucas.ferreira.maburn.model.dao.CollectDatas;
import com.lucas.ferreira.maburn.model.enums.Category;
import com.lucas.ferreira.maburn.model.service.response.KitsuResponseAPI;

public class KitsuAPITest {

	private KitsuResponseAPI kitsuAPI;
	public static final String URL_TEST_1 = "https://kitsu.io/api//edge/anime?filter[text]=Dragon+Ball Super";
	public static final String URL_TEST_2 = "https://kitsu.io/api//edge/anime?filter[text]=Akira";
	public static final String URL_TEST_3 = "https://kitsu.io/api//edge/anime?filter[text]=Naruto";
	public static final String URL_TEST_4 = "https://kitsu.io/api//edge/anime?filter[text]=One Piece";
	public static final String URL_TEST_5 = "https://kitsu.io/api//edge/anime?filter[text]=Berserk";

	@Before
	public void setUp() {

		// kitsuScrapingSearh = new KitsuScraping(URL_SEARCH_TEST,
		// PagesTypes.SEARCH_PAGE);
	}

	@Test
	public void fetchAllTest1() {
		kitsuAPI = new KitsuResponseAPI(URL_TEST_1);
		CollectDatas datas = kitsuAPI.fetchFirst();
		assertThat(datas.getCanonicalTitle(), equalTo("Dragon Ball Super"));
		assertThat(datas.getPosterImageLink("tiny"),
				equalTo("https://media.kitsu.io/anime/poster_images/10879/tiny.jpg?1597697396"));
		assertThat(datas.getPosterImageLink("small"),
				equalTo("https://media.kitsu.io/anime/poster_images/10879/small.jpg?1597697396"));
		assertThat(datas.getPosterImageLink("medium"),
				equalTo("https://media.kitsu.io/anime/poster_images/10879/medium.jpg?1597697396"));
		assertThat(datas.getPosterImageLink("large"),
				equalTo("https://media.kitsu.io/anime/poster_images/10879/large.jpg?1597697396"));
		assertThat(datas.getPosterImageLink("original"),
				equalTo("https://media.kitsu.io/anime/poster_images/10879/original.jpg?1597697396"));
		assertThat(datas.getCategory(), equalTo(Category.ANIME));

		String synopsis = "Set just after the events of the Buu Saga of Dragon Ball Z, a deadly threat awakens once more. "
				+ "People lived in peace without knowing who the true heroes were during the devastating battle against Majin Buu. "
				+ "The powerful Dragon Balls have prevented any permanent damage, and our heroes also continue to live a normal life. "
				+ "In the far reaches of the universe, however, a powerful being awakens early from his slumber, curious about a prophecy "
				+ "of his defeat. Join Gokuu, Piccolo, Vegeta, Gohan, and the rest of the Dragon Ball crew as they tackle the strongest "
				+ "opponent they have ever faced. Beerus, the god of destruction, now sets his curious sights on Earth. Will the heroes "
				+ "save the day and prevent earth's destruction? Or will the whims of a bored god prove too powerful for the Saiyans? "
				+ "Gokuu faces impossible odds once more and fights for the safety of his loved ones and the planet.".trim();

		assertThat(datas.getSynopsis().length(), equalTo(synopsis.length()));
	}

	@Test
	public void fetchAllTest2() {
		kitsuAPI = new KitsuResponseAPI(URL_TEST_2);
		CollectDatas datas = kitsuAPI.fetchFirst();
		assertThat(datas.getCanonicalTitle(), equalTo("Akira"));
		assertThat(datas.getCategory(), equalTo(Category.ANIME));
	}

	@Test
	public void fetchAllTest3() {
		kitsuAPI = new KitsuResponseAPI(URL_TEST_3);
		CollectDatas datas = kitsuAPI.fetchFirst();
		assertThat(datas.getCanonicalTitle(), equalTo("Naruto"));
		assertThat(datas.getCategory(), equalTo(Category.ANIME));
	}

	@Test
	public void fetchAllTest4() {
		kitsuAPI = new KitsuResponseAPI(URL_TEST_4);
		CollectDatas datas = kitsuAPI.fetchFirst();
		assertThat(datas.getCanonicalTitle(), equalTo("One Piece"));
		assertThat(datas.getCategory(), equalTo(Category.ANIME));
	}
}
