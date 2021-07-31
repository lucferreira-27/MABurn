package com.lucas.ferreira.mangaburn.testing.xml;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.model.documents.xml.UpdateType;
import com.lucas.ferreira.maburn.model.documents.xml.XmlCollectionOrchestrator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ListItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.SiteForm;
import com.lucas.ferreira.maburn.model.enums.Sites;

public class XmlCollectionOrchestratorTest {
	private XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();

	@Test
	public void newCollectionFormTest() {
		try {
			orchestrator.create();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void readTest() {
		try {

			CollectionForm form = orchestrator.read();
			
			assertNotNull(form);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void writeTest() {
		try {
			CollectionForm form = new CollectionForm();
			ListItemForm itemForm = new ListItemForm();
			itemForm.setTitleDatabase("Test");
			itemForm.setId(1);
			form.getItems().add(itemForm);
			orchestrator.write(form);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void removeByIdTest() {
		try {
			CollectionForm form = new CollectionForm();
			ListItemForm itemForm = new ListItemForm();
			itemForm.setTitleDatabase("Test");
			itemForm.setId(1);
			form.getItems().add(itemForm);
			orchestrator.removeById(form, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void updateById() {
		try {
			CollectionForm form = new CollectionForm();
			ListItemForm itemForm = new ListItemForm();
			SiteForm siteForm = new SiteForm();
			siteForm.setSiteName(Sites.GOYABU);
			siteForm.setItemLink(Sites.GOYABU.getUrl());
			itemForm.setTitleDatabase("Test");
			itemForm.setId(1);
			form.getItems().add(itemForm);
			orchestrator.updateById(form, 1, UpdateType.TITLE_DATABASE, "update Title");
			orchestrator.updateById(form, 1, UpdateType.IMAGE_LOCAL, "update ImageLocal");
		
			orchestrator.updateById(form, 1, UpdateType.CURRENT_SCRAPING_LINK, "update ImageLocal");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
