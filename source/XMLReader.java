
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

public class XMLReader{
    private String filePath;
    public List<Player> playerList;
    public XMLReader(String filePath){
        setFilePath(filePath);
        File xmlFile = new File(getFilePath());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Player");
            playerList = new ArrayList<Player>();
            for(int i = 0; i<nodeList.getLength();i++){
                playerList.add(getPlayer(nodeList.item(i)));
            }
        }catch (SAXException | ParserConfigurationException | IOException e){
            e.printStackTrace();
        }
    }
    
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    
    public String getFilePath(){
        return filePath;
    }
    
    private Player getPlayer(Node node){
        Player player = new Player();
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            player.setName(element.getAttribute("name"));
            player.setQuota(Integer.parseInt(getTagValue("quota", element)));
            player.setUpToDate(Boolean.parseBoolean(getTagValue("up_to_date", element)));
            player.setWeeksBehind(Integer.parseInt(getTagValue("weeks_behind", element)));
        }
        return player;
    }
    private String getTagValue(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}