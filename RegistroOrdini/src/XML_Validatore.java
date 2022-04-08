/**
 *
 * @author Federico Montini
 */
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XML_Validatore {
    public static boolean valida(String _stringaXMLDaValidare, String _fileSchemaXSD){
        try
        {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();            /*1*/
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);            /*2*/
            Document documento = documentBuilder.parse(new InputSource(new StringReader(_stringaXMLDaValidare)));   /*3*/
            Schema schema = schemaFactory.newSchema(new StreamSource(new File(_fileSchemaXSD)));                    /*4*/
            schema.newValidator().validate(new DOMSource(documento));                                               /*5*/
            
            return true;
        }
        catch(SAXException exception)
        {
            System.err.println("Errore durante la validazione: " + exception.getMessage());
        }
        catch (IOException | ParserConfigurationException exception)
        {
            System.err.println("Stringa XML non validabile: " + exception.getMessage());
        }
        return false;
    }
}
/*
Classe che offre un metodo (valida) che si propone di validare file XML passati come parametri mediante schemi XSD anche questi passati come parametri.
La funzione restituisce true se la validazione ha esito positivo, false altrimenti.
1: Instanzio un parser in grado di generare oggetti DOM a partire da documenti XSL
2: Instanzio uno SchemeFactory che legga le rappresentazioni esterne di schemi XML da validare
3: Estraggo l'oggetto DOM precedentemente prodotto dalla stringa XML per la validazione
4: Estraggo lo schema dal file XSD
5: Creo il validatore che esegua la validazione del file XML con lo schema precedentemente estratto
*/
