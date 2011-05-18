package com.cn.zxq.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.cn.zxq.xmlparser.handler.AbstractHandler;

public class XmlPaser<T> {

	public List<T> getNewsData(String url, AbstractHandler<T> handler)
			throws ParserConfigurationException, SAXException, IOException {

		// create the factory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// create a parser
		SAXParser parser = factory.newSAXParser();
		
		
		// create the reader (scanner)
		XMLReader xmlreader = parser.getXMLReader();
		
		// assign our handler
		xmlreader.setContentHandler(handler);
		
		// perform the synchronous parse
		URL  u = new URL (url);
		InputStream in = u.openConnection().getInputStream();
		InputStreamReader streamReader = new InputStreamReader(in,"Shift-jis");
		InputSource is = new InputSource(streamReader);

		xmlreader.parse(is);
	
		List<T> lists = handler.getResult();
		return lists;
	}
}
