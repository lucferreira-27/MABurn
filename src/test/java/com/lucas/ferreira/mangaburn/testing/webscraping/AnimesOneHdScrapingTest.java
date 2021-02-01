package com.lucas.ferreira.mangaburn.testing.webscraping;

import java.util.Scanner;

import org.junit.Test;

import com.lucas.ferreira.maburn.exceptions.WebScrapingException;
import com.lucas.ferreira.maburn.model.webscraping.ScrapeEngine;

public class AnimesOneHdScrapingTest {
	@Test
	public void fetchEpisodesAlternative() {
		try {
			ScrapeEngine engine = new ScrapeEngine("https://facebruek.online/one.php?auth=68747470733a2f2f7777772e616e696d65736f6e6568642e78797a2f617373697374697253442e7068703f746f6b656e3d414436763564795a63364c58517244654c6f72456e5f6f4b38575a564a6c6a6b6541335977392d2d4e716a593551565433537773376242775a6a6f35306e444459695f36736c49546636433743747a5843424b58777a4d7056684344396b49585f4f67303379304753756554695f353872587842724d512d4f7055435a37434679356c5f53694d6659446326616d703b636170613d30323a323526616d703b63617061313d30333a3534");
			
//			try (BufferedInputStream in = new BufferedInputStream(new URL("https://r2---sn-uigxx5mcg-g5ve.googlevideo.com/videoplayback?expire=1611881777&ei=sewSYLWdKo2phwajhIO4Aw&ip=173.212.201.67&id=3a92bb880b0eaffe&itag=22&source=blogger&susc=bl&mime=video/mp4&vprv=1&dur=1434.319&lmt=1611667265864155&txp=1311224&sparams=expire,ei,ip,id,itag,source,susc,mime,vprv,dur,lmt&sig=AOq0QJ8wRQIhALn4y2sdaKvP_9h5Sjrufzj1Ss8qLRsHaujq5vLddrvaAiAyNTSXgij-13N6RK0-5CAqEtEwCM0UX7BFOKSnaS6i-A%3D%3D&redirect_counter=1&rm=sn-p5qe7e7l&req_id=d2c7bb8d543e36e2&cms_redirect=yes&ipbypass=yes&mh=xk&mip=45.71.131.48&mm=31&mn=sn-uigxx5mcg-g5ve&ms=au&mt=1611852276&mv=m&mvi=2&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgLXyEtssVC7TNK5_4sHKNBaBMC2jlVUxpkYvYOPxf05kCIAc3q1To6-ukbSapmNSgbKMQzGJUiTrjmZHY4Wmh-sv1").openStream());
//					  FileOutputStream fileOutputStream = new FileOutputStream("test")) {
//					    byte dataBuffer[] = new byte[1024];
//					    int bytesRead;
//					    int i = 0;
//					    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//					        i+= bytesRead;
//					    	fileOutputStream.write(dataBuffer, 0, bytesRead);
//					        System.out.println(BytesUtil.convertBytesToMegasBytes(i));
//					    }
//					} catch (IOException e) {
//					    // handle exception
//					}
			
			
			//mode.beginReader("https://r2---sn-uigxx5mcg-g5ve.googlevideo.com/videoplayback?expire=1611881777&ei=sewSYLWdKo2phwajhIO4Aw&ip=173.212.201.67&id=3a92bb880b0eaffe&itag=22&source=blogger&susc=bl&mime=video/mp4&vprv=1&dur=1434.319&lmt=1611667265864155&txp=1311224&sparams=expire,ei,ip,id,itag,source,susc,mime,vprv,dur,lmt&sig=AOq0QJ8wRQIhALn4y2sdaKvP_9h5Sjrufzj1Ss8qLRsHaujq5vLddrvaAiAyNTSXgij-13N6RK0-5CAqEtEwCM0UX7BFOKSnaS6i-A%3D%3D&redirect_counter=1&rm=sn-p5qe7e7l&req_id=d2c7bb8d543e36e2&cms_redirect=yes&ipbypass=yes&mh=xk&mip=45.71.131.48&mm=31&mn=sn-uigxx5mcg-g5ve&ms=au&mt=1611852276&mv=m&mvi=2&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgLXyEtssVC7TNK5_4sHKNBaBMC2jlVUxpkYvYOPxf05kCIAc3q1To6-ukbSapmNSgbKMQzGJUiTrjmZHY4Wmh-sv1");
		
			//	HtmlAnchor div = (HtmlAnchor) engine.getPage().querySelectorAll(".episode-info-tabs-item-red").get(0);
			
			
			System.out.println(engine.getPage().asXml());
			Scanner scan = new Scanner(System.in);
			scan.nextLine();
			
			System.out.println("\n==============================================\n");
			//Page page = engine.click(div);
			//System.out
				//	.println( ((HtmlPage)page).asXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebScrapingException(e.getMessage());
		}
	}

}
