package com.lucas.ferreira.maburn.model.documents.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;

public class Remover {
	public boolean removeItemFormById(CollectionForm form, Integer id) throws JsonProcessingException {

		form.getItems().forEach(f -> {
			
		});

		boolean remove = form.getItems().removeIf(item -> item.getId().equals(id));


		return remove;
	}
	public boolean removeItemFormByIdAndWrite(CollectionForm form, Integer id) throws JsonProcessingException {

		form.getItems().forEach(f -> {
			
		});

		boolean remove = form.getItems().removeIf(item -> item.getId().equals(id));

		XmlCollectionOrchestrator orchestrator = new XmlCollectionOrchestrator();
		orchestrator.write(form);

		return remove;
	}

}
