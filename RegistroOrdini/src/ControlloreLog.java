/**
 *
 * @author Federico Montini
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ControlloreLog {
    private final String addressIPServerLog;
    private final int portaServerLog;
    
    public ControlloreLog(String _addressIPServerLog, int _portaServerLog){
        this.addressIPServerLog=_addressIPServerLog;
        this.portaServerLog=_portaServerLog;
    }
    
    public void sendEvento(String _nomeApp, String _tipoEvento){
        try(Socket socket = new Socket(addressIPServerLog, portaServerLog);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            )
        {
            EventoLogXML log = new EventoLogXML(_nomeApp,((InetSocketAddress)socket.getLocalSocketAddress()).getAddress().getHostAddress(),
                                                new Date(), _tipoEvento);
            out.writeUTF(log.toXML());
        }
        catch(IOException exception){
            System.err.println("Invio log impossibile: " + exception.getMessage());
        }
        
    }
    
    
}
