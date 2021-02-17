package com.lucas.ferreira.maburn.model.documents.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.lucas.ferreira.maburn.model.documents.xml.form.CollectionForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.ItemForm;
import com.lucas.ferreira.maburn.model.documents.xml.form.config.ConfigForm;

public class Writer {
	public boolean writeConfigFromXml(File out) {
		XmlMapper  xmlMapper = new XmlMapper();
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true );
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			xmlMapper.writeValue(out, new ConfigForm());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean writeConfigFromXml(ConfigForm form, File out) {
		XmlMapper  xmlMapper = new XmlMapper();
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true );
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			xmlMapper.writeValue(out, form);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	public String writeCollectionFormValueAsXml(CollectionForm form) throws JsonProcessingException {
		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String xml = xmlMapper.writeValueAsString(form);

		return xml;
		// or
	}

	public boolean writeNewCollectionFormValueAsXml(File out) throws JsonProcessingException {
		XmlMapper  xmlMapper = new XmlMapper();
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true );
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			xmlMapper.writeValue(out, new CollectionForm());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	public boolean writeCollectionFormValueAsXml(CollectionForm form, File out) throws JsonProcessingException {
		XmlMapper  xmlMapper = new XmlMapper();
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true );
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			xmlMapper.writeValue(out, form);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
