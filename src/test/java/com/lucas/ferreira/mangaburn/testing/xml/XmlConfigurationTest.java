package com.lucas.ferreira.mangaburn.testing.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.lucas.ferreira.maburn.model.documents.xml.XmlConfigurationOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class XmlConfigurationTest {
	private XmlConfigurationOrchestrator orchestrator = new XmlConfigurationOrchestrator();

	@Test
	public void write() {
		ConfigForm form = new ConfigForm();
		form.getAnimeConfig().setCollectionDestination("test");
		boolean result = orchestrator.write(form);
		assertTrue(result);

	}

	@Test
	public void read() {
		try {
			ConfigForm form = orchestrator.read();
			
			String expect = "test";
			String result = form.getAnimeConfig().getCollectionDestination();

			assertThat(result, is(expect));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
