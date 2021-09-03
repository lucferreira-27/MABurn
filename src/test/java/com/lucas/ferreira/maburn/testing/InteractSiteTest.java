package com.lucas.ferreira.maburn.testing;

import com.lucas.ferreira.maburn.model.InitializeModel;
import com.lucas.ferreira.maburn.model.browser.PlaywrightSettings;
import com.lucas.ferreira.maburn.model.sites.*;
import com.lucas.ferreira.maburn.webserver.LocalServer;
import com.lucas.ferreira.maburn.webserver.WebServer;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.impl.Driver;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InteractSiteTest {
    private Path driver;
    LocalServer localServer = new LocalServer();

    @BeforeEach
    public void setup() throws IOException {

        driver = Driver.ensureDriverInstalled(PlaywrightSettings.getEnv());
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }
    }

    @After
    public void after() throws IOException {
        if (driver != null)
            Files.delete(driver);

    }

    @Test
    public void testGetInSaikoAnimesWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://saikoanimes.net/anime/edens-zero/";

        withTarget(withTargeturl, "Saiko", "[Saiko-Animes]_Edens_Zero_-_19_[WEB-1080p]_[AnimesHD].mp4", 1);

    }

    @Test
    public void testGetInSaikoAnimesWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://saikoanimes.net/anime/edens-zero/";
        withoutTarget(withoutTargeturl, "SAIKO_ANIMES", 1);

    }

    @Test
    public void testGetInBetterAnimeWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://betteranime.net/anime/legendado/one-piece";
        withoutTarget(withoutTargeturl, "BETTER_ANIME", 1);

    }

    @Test
    public void testGetInBetterAnimeWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://betteranime.net/anime/legendado/one-piece";
        withoutTarget(withoutTargeturl, "BETTER_ANIME", 1);

    }

    @Test
    public void testGetInFenixFansubWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://fenixfansub.com/animes/fumetsu-no-anata-e/";
        withoutTarget(withoutTargeturl, "FENIX_FANSUB", 1);

    }

    @Test
    public void testGetInFenixFansubWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://fenixfansub.com/beta/download/713aWWXF";
        withTarget(withTargeturl, "FENIX_FANSUB", "Episódio 01v1", 1);

    }

    @Test
    public void testGetInMangaLivreWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://mangalivre.net/manga/dandadan/12524";
        withoutTarget(withoutTargeturl, "MANGA_LIVRE", 1);

    }

    @Test
    public void testGetInMangaLivreWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }
        String withTargeturl = "https://mangalivre.net/ler/one-piece/online/181297/capitulo-940";
        SiteResult siteResult = withTarget(withTargeturl, "MANGA_LIVRE", "Ler Capítulo 1", 1);
        assertTrue(siteResult.getItemsValues().get(0).getUrls().size() > 1);

    }

    @Test
    public void testGetInMangaHostWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://mangahost4.com/manga/one-piece-br-mh27284";
        withoutTarget(withoutTargeturl, "MANGA_HOST", 1);

    }

    @Test
    public void testGetInMangaHostWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://mangahost4.com/manga/one-piece-br-mh27284/1021";
        SiteResult siteResult = withTarget(withTargeturl, "MANGA_HOST", "Ler Capítulo 1", 1);
        assertTrue(siteResult.getItemsValues().get(0).getUrls().size() > 1);

    }

    @Test
    public void testGetInMangaYabuWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://mangayabu.top/manga/again-my-life/";
        withoutTarget(withoutTargeturl, "MANGA_YABU", 1);

    }

    @Test
    public void testGetInMangaYabuWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://mangayabu.top/ler/again-my-life-capitulo-59-my672451/";
        SiteResult siteResult = withTarget(withTargeturl, "MANGA_YABU", "Again My Life – Capítulo 58", 1);
        assertTrue(siteResult.getItemsValues().get(0).getUrls().size() > 1);

    }


    @Test
    public void testGetInAnimeFrenzyWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://animefrenzy.org/anime/yuukoku-no-moriarty-dubbed";
        withoutTarget(withoutTargeturl, "ANIME_FRENZY", 1);

    }

    @Test
    public void testGetInAnimeFrenzyWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://animefrenzy.org/stream/yuukoku-no-moriarty-dubbed-episode-1";
        withTarget(withTargeturl, "ANIME_FRENZY", "Episode 1", 1);

    }

    @Test
    public void testGetInAnimePlanetENWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://www.anime-planet.com/anime/that-time-i-got-reincarnated-as-a-slime/videos";
        withoutTarget(withoutTargeturl, "ANIME_PLANET_[EN]", 1);

    }

    @Test
    public void testGetInAnimePlanetPTBRWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://www.anime-planet.com/anime/that-time-i-got-reincarnated-as-a-slime/videos";
        withoutTarget(withoutTargeturl, "ANIME_PLANET_[PT_BR]", 1);

    }

    @Test
    public void testGetInAnimePlanetENWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://www.anime-planet.com/anime/that-time-i-got-reincarnated-as-a-slime/videos/230572";
        withTarget(withTargeturl, "ANIME_PLANET_[EN]", "Episode 23 Saved Souls", 1);

    }

    @Test
    public void testGetInAnimePlanePTBRtWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://www.anime-planet.com/anime/that-time-i-got-reincarnated-as-a-slime/videos/230572";
        withTarget(withTargeturl, "ANIME_PLANET_[PT_BR]", "Episode 23 Saved Souls", 1);

    }

    @Test
    public void testGetInKissAnimeWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "https://kissanime.com.ru/Anime/One-Piece.79566/";
        withoutTarget(withoutTargeturl, "KISS_ANIME", 1);

    }

    @Test
    public void testGetInKissAnimeWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "https://kissanime.com.ru/Anime/One-Piece.79566/Episode-987?id=172108";
        withTarget(withTargeturl, "KISS_ANIME", "One Piece - Episode 987", 1);

    }

    @Test
    public void testGetInMangaPandauWithoutTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withoutTargeturl = "http://manga-panda.today/manga/the-last-golden-child";
        withoutTarget(withoutTargeturl, "MANGA_PANDA", 1);

    }

    @Test
    public void testGetInMangaPandaWithTarget() throws Exception {
        if (LocalServer.getWebServer() == null) {
            WebServer webServer = localServer.create();
            webServer.start();
        }

        String withTargeturl = "http://manga-panda.today/the-last-golden-child-chapter-15";
        SiteResult siteResult = withTarget(withTargeturl, "MANGA_PANDA", "The Last Golden Child Chapter 15", 1);
        assertTrue(siteResult.getItemsValues().get(0).getUrls().size() > 1);

    }

    @Test
    public void testGetInMangaYehWithoutTarget() throws Exception {


        String withoutTargeturl = "https://mangayeh.com/manga/shingeki-no-kyojin";
       SiteResult siteResult = withoutTarget(withoutTargeturl, "MANGA_YEH", 1);

    }

    @Test
    public void testGetInMangaYehWithTarget() throws Exception {


        String withTargeturl = "https://mangayeh.com/chapter/shingeki-no-kyojin/chapter-139-final-chapter-facing-that-tree-on-the-hill";
        SiteResult siteResult = withTarget(withTargeturl, "MANGA_YEH", "Chapter 139 - Final Chapter: Facing that tree on the hill", 1);
        assertTrue(siteResult.getItemsValues().get(0).getUrls().size() > 1);
    }
    private SiteResult withoutTarget(String withoutTargeturl, String site, int repeat)
            throws Exception {
        InitializeModel initializeModel = new InitializeModel();
        initializeModel.boot();
        SiteResult siteResult = null;
        for (int i = 0; i < repeat; i++) {

            siteResult = interactWithoutTarget(withoutTargeturl, site);

        }
        assertNotNull(siteResult);
        assertTrue(siteResult.getItemsValues().size() > 0);
        assertNotNull(siteResult.getPageInfo().getImageSmall());
        assertTrue(siteResult.getPageInfo().getImageSmall().getWidth() > 0);
         return  siteResult;
    }

    private SiteResult withTarget(String withoutTargeturl, String site, String target, int repeat)
            throws Exception {

        InitializeModel initializeModel = new InitializeModel();
        initializeModel.boot();
        SiteResult siteResult = null;
        for (int i = 0; i < repeat; i++) {

            siteResult = interactWithTarget(withoutTargeturl, site, target);

        }
        assertNotNull(siteResult);
        assertEquals(siteResult.getItemsValues().get(0).getName(), target);
        assertTrue(siteResult.getItemsValues().get(0).getUrls() != null
                && !siteResult.getItemsValues().get(0).getUrls().isEmpty());

        return siteResult;
    }

    private SiteResult interactWithoutTarget(String url, String sitename)
            throws Exception {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.firefox().launch(new LaunchOptions().setHeadless(false));

        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        RecoverSites recoverSites = new RecoverSites();
        RegisteredSite site = recoverSites.recoverAll().stream()
                .filter(fsite -> fsite.getSiteConfig().getSlug().contains(sitename))
                .collect(Collectors.toList()).get(0);

        InteractSite interactSite = new InteractSite(page);
        SiteValues siteValues = new SiteValues();
        System.out.println("Execute: [" + site.getSiteConfig().getScriptPath() + "]");
        System.out.println("URL: [" + url + "]");
        siteValues.setRegisteredSite(site);
        siteValues.setUrl(url);
        interactSite.get(siteValues);
        //	browser.close();
        return interactSite.getSiteResult();
    }

    private SiteResult interactWithTarget(String url, String sitename, String target)
            throws Exception {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.firefox().launch(new LaunchOptions().setHeadless(false));

        BrowserContext context = browser.newContext();
        Page page = context.newPage();


        RecoverSites recoverSites = new RecoverSites();

        RegisteredSite site = recoverSites.recoverAll().stream()
                .filter(fsite -> fsite.getSiteConfig().getSlug().contains(sitename))
                .collect(Collectors.toList()).get(0);

        System.out.println("Execute: [" + site.getSiteConfig().getScriptPath() + "]");
        System.out.println("URL: [" + url + "]");
        System.out.println("Target: [" + target + "]");
        InteractSite interactSite = new InteractSite(page);
        SiteValues siteValues = new SiteValues();
        siteValues.setRegisteredSite(site);
        siteValues.setUrl(url);
        siteValues.setTarget(target);
        interactSite.get(siteValues);
        return interactSite.getSiteResult();
    }

}
