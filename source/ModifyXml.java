
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Attr;

public class ModifyXml{
    public ModifyXml(){
        updateAll();
    }
    
    public ModifyXml(String name){
        
        modify(name, -100, false);
        
    }
    
    public ModifyXml(String name, int quota){
        modify(name, quota, false);
    }
    
    public ModifyXml(String name, int quota, boolean upToDate){
            modify(name, quota, upToDate);
    }
    
    public void modify(String name, int quota, boolean upToDate){
        
        int id = quota;
        boolean ss = upToDate;
        
        try{
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docB= docFac.newDocumentBuilder();
            Document doc = docB.parse("players.xml");
            Node players = doc.getFirstChild();
            
            if(id == -100){
                players.appendChild(getPlayer(doc, name, id, ss));
            }
            else{
                NodeList playersList = doc.getElementsByTagName("Player");
                
                for(int i = 0; i < playersList.getLength(); i++){
                    boolean changed = false;
                    Node thisPlayer = playersList.item(i);
                    Element element = (Element) thisPlayer;
                    String key = element.getAttribute("name");
                    if (name.equalsIgnoreCase(key)){
                        NodeList pList = thisPlayer.getChildNodes();
                        
                        for(int j = 0; j < pList.getLength(); j++){
                            Node stuff = pList.item(j);
                            
                            if("quota".equals(stuff.getNodeName())){
                                stuff.setTextContent(Integer.toString(id));
                                changed = true;
                            }
                            if("up_to_date".equals(stuff.getNodeName())){
                                stuff.setTextContent(Boolean.toString(ss));
                            }
                            if(ss){
                                if("weeks_behind".equals(stuff.getNodeName())){
                                    stuff.setTextContent(Integer.toString(0));
                                }
                            }
                        }
                        if(changed) i = playersList.getLength();
                    }
                }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("players.xml"));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
    
    public void updateAll(){
        try{
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docB= docFac.newDocumentBuilder();
            Document doc = docB.parse("players.xml");
            Node players = doc.getFirstChild();
            
            
            NodeList playersList = doc.getElementsByTagName("Player");
                
            for(int i = 0; i < playersList.getLength(); i++){
                boolean changed = false;
                Node thisPlayer = playersList.item(i);
                Element element = (Element) thisPlayer;
                NodeList pList = thisPlayer.getChildNodes();
                
                for(int j = 0; j < pList.getLength(); j++){
                    Node stuff = pList.item(j);
                    
                    if("up_to_date".equals(stuff.getNodeName())){
                        stuff.setTextContent(Boolean.toString(false));
                    }
                    if("weeks_behind".equals(stuff.getNodeName())){
                        int hold = Integer.parseInt(getTagValue("weeks_behind", element));
                        stuff.setTextContent(Integer.toString(hold+1));
                    }
                }
            }
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("players.xml"));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }

    }
    
    private Node getPlayer(Document doc, String name, int id, boolean ss){
        Element player = doc.createElement("Player");
        Attr attr = doc.createAttribute("name");
        attr.setValue(name);
        player.setAttributeNode(attr);
        //player.appendChild(getPlayerElement(doc, player, "name", name));
        player.appendChild(getPlayerElement(doc, player, "quota", Integer.toString(id)));
        player.appendChild(getPlayerElement(doc, player, "super_skin", Integer.toString(0)));
        player.appendChild(getPlayerElement(doc, player, "up_to_date", Boolean.toString(ss)));
        player.appendChild(getPlayerElement(doc, player, "weeks_behind", Integer.toString(100)));
        return player;
    }
    
    private Node getPlayerElement(Document doc, Element element, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
    private String getTagValue(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    
}