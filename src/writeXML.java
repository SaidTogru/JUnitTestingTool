
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.*;
import javax.xml.*;
import com.sun.*;

import org.w3c.dom.*;
import java.lang.*;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class writeXML {
	DefaultMutableTreeNode wurzel;
	List failed;
	List ignored;

	writeXML(DefaultMutableTreeNode wurzel, List failed, List ignored) {
		this.wurzel = wurzel;
		this.failed = failed;
		this.ignored = ignored;
	}

	public String printXML() throws ParserConfigurationException, IOException, TransformerException {
		List<DefaultMutableTreeNode> childs = new ArrayList<>();
		List<Element> childsElement = new ArrayList<>();
		List<Element> tests = new ArrayList<>();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmldoc = docBuilder.newDocument();

		Element root = xmldoc.createElement(wurzel.getUserObject().toString());
		xmldoc.appendChild(root);
		// erstellen von XML Testklassen Elemente
		for (int i = 0; i < wurzel.getChildCount(); i++) {
			Element child = xmldoc.createElement(wurzel.getChildAt(i).toString());
			root.appendChild(child);
			childs.add((DefaultMutableTreeNode) wurzel.getChildAt(i));
			childsElement.add(child);
		}
		// erstellen von XML Testmethoden zu den Testklassen
		for (int i = 0; i < childs.size(); i++) {
			for (int z = 0; z < childs.get(i).getChildCount(); z++) {

				Element child = xmldoc.createElement(wurzel.getChildAt(i).getChildAt(z).toString());
				childsElement.get(i).appendChild(child);
				tests.add(child);
			}
		}

		for (int t = 0; t < tests.size(); t++) {

			if (compareIt(failed, tests.get(t).getNodeName()) == true) {
				tests.get(t).appendChild(xmldoc.createTextNode("Failed"));
				;
			} else if (compareIt(ignored, tests.get(t).getNodeName()) == true) {
				tests.get(t).appendChild(xmldoc.createTextNode("Ignored"));
			} else {
				tests.get(t).appendChild(xmldoc.createTextNode("Succeed"));
			}
		}

		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));

		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(xmldoc.getDocumentElement());

		trans.transform(source, result);
		String xmlString = sw.toString();
		return xmlString;

	}

	public boolean compareIt(List x, String y) {
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).equals(y)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

	}
}
