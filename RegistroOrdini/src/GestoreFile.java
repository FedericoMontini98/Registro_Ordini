/**
 *
 * @author Federico Montini
 */

import java.io.*;
import java.nio.file.*;

public class GestoreFile {
    
    public static String caricaDaFile(String _file)
    {
        String risultatoCaricamento = "Nothing";
        
        try
        {
            risultatoCaricamento = new String(Files.readAllBytes(Paths.get(_file)));
        }
        catch(IOException exception)
        {
            System.err.println("Caricamento dal file fallito " + exception.getMessage());
        }
        return risultatoCaricamento;
    }
    
    public static void salvaSuFile(Object _oggettoDaSalvare, String _fileSuCuiSalvarlo, boolean _append)
    {
        try
        {
            Files.write(Paths.get(_fileSuCuiSalvarlo),_oggettoDaSalvare.toString().getBytes(),StandardOpenOption.CREATE,
                                 (_append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch(IOException exception)
        {
            System.err.println("Salvataggio su file fallito: " + exception.getMessage());
        }
    }
}
