/**
 *
 * @author Federico Montini
 */
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.basic.*;
import java.util.*;

public class EventoLogXML {
    
    public final String nomeApplicazione;
    public final String addressIPClient;
    public final Date orarioEvento;
    public final String tipoEvento;
    
    public EventoLogXML(String _nomeApplicazione, String _addressIPClient, Date _orarioEvento, String _tipoEvento){
        this.nomeApplicazione=_nomeApplicazione;
        this.addressIPClient=_addressIPClient;
        this.orarioEvento=_orarioEvento;
        this.tipoEvento=_tipoEvento;
    }
    
    public String toXML(){
        XStream xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getDefault()));
        return xstream.toXML(this);
    }
}
