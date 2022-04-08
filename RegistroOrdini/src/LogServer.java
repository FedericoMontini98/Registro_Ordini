/**
 *
 * @author Federico Montini
 */
import java.io.*;
import java.net.*;

public class LogServer {
    private static final int portaServerLog = 8080;
    private static final String fileDiLog = "../stuffFiles/serverDiLog.txt";
    private static final String schemaDiLog = "../stuffFiles/schemaDiLog.xsd";
    
    public static void main(String args[]){
        System.out.println("Server di Log avviato \n");
        try(ServerSocket serverSocket = new ServerSocket(portaServerLog)) 
        {
            while(true)
            {
                try(Socket socket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socket.getInputStream());)
                {
                    String recordLog = in.readUTF();
                    System.out.println("Log ricevuto\n");
                    if(XML_Validatore.valida(recordLog,schemaDiLog))
                    {
                        GestoreFile.salvaSuFile(recordLog + "\n", fileDiLog, true);
                    }
                }
            }
        }
        catch(IOException exception)
        {
            System.err.println("Errore: " + exception.getMessage() + "\n" );
        }
    }
}
