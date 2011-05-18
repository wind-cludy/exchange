package com.cn.zxq.xmlparser.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public abstract class AbstractHandler<T> extends DefaultHandler implements
		IResultData<T> {

	private StringBuilder builder;

	@Override
	public void startDocument() throws SAXException {
		// initialize "list"
		// lists = new ArrayList<T>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		super.startElement(namespaceURI, localName, qName, atts);
		generateXml(localName, atts);
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		super.endElement(namespaceURI, localName, qName);
		abstractEndElement(localName,builder);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder = new StringBuilder();
		builder.append(ch, start, length);
	}

	public abstract void generateXml(String localName, Attributes atts);

	public abstract void abstractEndElement(String localName,StringBuilder builder);

}
