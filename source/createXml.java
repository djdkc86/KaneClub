import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;

public class createXml{
    private static String[] names = {"Aaron Latimer", "Bill Phillips", "Al Martinez", "Cedric Lee", "Chris Thometz", "Craig Yester", "Dan Connors", "Dan Hurley", "Dane Field", "Darryl Wassum", "Brian Monroe", "Dave Parrish", "David Kucic", "Bruce Nakano", "Dick Hawes", "Donavan Jones", "Alan Wataguchi", "Dustin Traylor", "Fai Sofa", "Frank Kahoohanohano", "Fred Schulz", "Freddy Fan", "Gaeton Cavarocchi", "Garrett Okubo", "Gary Theil", "Joe Hayes", "Mario Beekes", "John Gafney", "Jason Palmer", "Jim Lindberg", "Jeff Loyd", "Jeff Borja", "Joe Boivin", "Jemory Rater", "John Tamayo", "John Harvell", "John Sego", "Jeff Fiandt", "Phil Drogosch", "Kyle Sego", "Lester De Peralta", "Dan Gleason", "Greg Tacon", "Michael Fan", "Mike Di Lorenzo", "Mike Hruby", "Mike Ohnuma", "Nick Smallwood", "Phill Fox", "Ray Gorman", "Ray Romero", "RC Payne", "Ronnie Terry", "Nick Devorak", "Robert Madow", "Keith Applegate", "Robert Chin", "Russ Harada", "Scott Yoder", "Sean Ryan", "Steve Newhouse", "Steve  Kane", "Thomas Wassum", "Todd Bedford", "Nick Jurski", "Peter Phengvath", "Mike Dilorenzo Sr", "Vaughn Kanenaga", "Sharon Fan", "Steven Richardson"};
    private static int[] quota = {22, 23, 31, 9, 15, 0, 27, 11, 10, 37, 32, 34, 19, 14, 28, 26, 20, 30, 24, 36, 36, 31, 17, 36, 36, 20, 21, 15, 23, 23, 36, 23, 22, 34, 15, 29, 28, 34, 17, 1, 32, 30, 15, 36, 27, 26, 36, 25, 18, 30, 11, 19, 27, 9, 19, 14, 22, 23, 25, 17, 14, 18, 34, 31, 20, 7, 12, 21, 16, 12};
    public static void main(String [] args){
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try{
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element rootElement = doc.createElement("Players");
            doc.appendChild(rootElement);
            for(int i = 0; i< names.length;i++){
                rootElement.appendChild(getPlayer(doc, names[i], quota[i]));
            }
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource s = new DOMSource(doc);
            
            StreamResult file = new StreamResult(new File("players.xml"));
            t.transform(s, file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private static Node getPlayer(Document doc, String name, int id){
        Element player = doc.createElement("Player");
        Attr attr = doc.createAttribute("name");
        attr.setValue(name);
        player.setAttributeNode(attr);
        //player.appendChild(getPlayerElement(doc, player, "name", name));
        player.appendChild(getPlayerElement(doc, player, "quota", Integer.toString(id)));
        player.appendChild(getPlayerElement(doc, player, "up_to_date", "true"));
        player.appendChild(getPlayerElement(doc, player, "weeks_behind", "0"));
        return player;
    }
    
    private static Node getPlayerElement(Document doc, Element element, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}