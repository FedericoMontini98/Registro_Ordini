
/**
 *
 * @author Federico Montini
 */

import java.sql.*;
import java.time.*;
import java.util.*;
import javafx.util.*;

public class ControlloreDatabase {
    private final String nomeDatabase;
    private final String usernameDatabase;
    private final String passwordDatabase;
    private final String addressIPDatabase;
    private final int portaDatabase;
    
    public  ControlloreDatabase(String nomeDatabase, String usernameDatabase, String passwordDatabase, String addressIPDatabase, int portaDatabase){
        this.nomeDatabase=nomeDatabase;
        this.usernameDatabase=usernameDatabase;
        this.passwordDatabase=passwordDatabase;
        this.addressIPDatabase=addressIPDatabase;
        this.portaDatabase=portaDatabase;
    }
    
    public boolean inserimentoOrdine(String nomeAzienda, String nomeProdotto, LocalDate dataScadenza, float guadagno, String fornitore, int numPezzi){
        int esito=-1;
        try(Connection connessione = DriverManager.getConnection(
                "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement queryVerifica = connessione.prepareStatement("CALL verifica_azienda(?);");
            PreparedStatement query = connessione.prepareStatement("INSERT INTO Ordini VALUES(?,0,?,?,?,?,?)");
                )
        {
            //Stored Procedure che verifica l'esistenza dell'azienda nel database, se non esiste la crea
            queryVerifica.setString(1,nomeAzienda);
            queryVerifica.execute();
            
            query.setString(1,nomeAzienda);
            query.setString(2,nomeProdotto);
            query.setString(3,dataScadenza.toString());
            query.setFloat(4, guadagno);
            query.setString(5, fornitore);
            query.setInt(6, numPezzi);
            
            esito = query.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.err.println("Inserimento impossibile: " + exception.getMessage());
        }
        return (esito!=-1);
    }
    
    public boolean modificaOrdine(String nomeAzienda, int ordineID, String nomeProdotto,  LocalDate dataScadenza,Float guadagno, String fornitore, int numPezzi){
        int esito=-1;
        try(Connection connessione = DriverManager.getConnection(
                "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement query = connessione.prepareStatement("UPDATE `Ordini`"
                    + "SET `nomeProdotto` = ?, `dataScadenza`=?,`guadagno`=?, `fornitore`=? , `numPezzi`= ?"
                    + " WHERE `ordineID` = ? AND `nomeAziendaFK` = ? ;");
                )
        {
            query.setString(1,nomeProdotto);
            query.setString(2, dataScadenza.toString());
            query.setFloat(3,guadagno);
            query.setString(4, fornitore);
            query.setInt(5,numPezzi);
            query.setInt(6,ordineID);
            query.setString(7, nomeAzienda);
            
            esito = query.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.err.println("Modifica impossibile: " + exception.getMessage());
        }
        return (esito!=-1);
    }
    
    public boolean eliminaOrdine(String nomeAzienda, int ordineID){
        int esito = -1;
        
        try(Connection connessione = DriverManager.getConnection(
            "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement query = connessione.prepareStatement("DELETE FROM Ordini WHERE nomeAziendaFK= ? AND ordineID=?");
            )
        {
            query.setString(1,nomeAzienda);
            query.setInt(2, ordineID);
            
            esito = query.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.err.println("Impossibile eliminare: " + exception.getMessage());
        }
        return (esito!=-1);
    }
    
    public List<EntryOrdine> getEntryList(String nomeAzienda, LocalDate dataInizio, LocalDate dataFine){
        List<EntryOrdine> entries = new ArrayList<>();
        
        if (nomeAzienda.equals(""))
            return entries;
        
        try(Connection connessione = DriverManager.getConnection(
                "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement query = connessione.prepareStatement("SELECT * FROM Ordini WHERE nomeAziendaFK= ? AND "
                    + "dataScadenza BETWEEN ? AND ? ORDER BY dataScadenza DESC");
                )
        {
            query.setString(1, nomeAzienda);
            query.setString(2, dataInizio.toString());
            query.setString(3, dataFine.toString());
            
            ResultSet risultato = query.executeQuery();
            
            while(risultato.next())
            {
                entries.add(new EntryOrdine(risultato.getInt("ordineID"),risultato.getString("nomeProdotto"),
                        risultato.getDate("dataScadenza").toLocalDate(), risultato.getFloat("guadagno"), 
                        risultato.getString("fornitore"), risultato.getInt("numPezzi")));
            }
        }
        catch (SQLException exception)
        {
            System.err.println("Impossibile caricare i dati dal Database(Tabella): " + exception.getMessage());
        }
        return entries;
    }
    
    public List<Pair<Integer,Float>> getEntitaGrafico(String nomeAzienda,LocalDate dataInizioIntervallo,LocalDate dataFineIntervallo){
        List<Pair<Integer,Float>> lista = new ArrayList<>();
        
        if (nomeAzienda==null) 
            return lista;
        try(Connection connessione = DriverManager.getConnection(
            "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement query = connessione.prepareStatement("SELECT MONTH(dataScadenza) as Mese, ROUND(SUM(guadagno)) as guadagnoMensile FROM Ordini "
                    + "WHERE nomeAziendaFK = ? AND dataScadenza BETWEEN ? AND ? GROUP BY MONTH(dataScadenza)"))
        {
            query.setString(1,nomeAzienda);
            query.setString(2,dataInizioIntervallo.toString());
            query.setString(3,dataFineIntervallo.toString());
                       
            ResultSet risultatoQuery= query.executeQuery();
            
            while(risultatoQuery.next())
            {
                lista.add(new Pair<>(risultatoQuery.getInt("Mese"),risultatoQuery.getFloat("guadagnoMensile")));
            }
        }
        catch (SQLException exception)
        {
            System.err.println("Impossibile caricare i dati dal Database(Grafico): " + exception.getMessage());
        }
        return lista;
    }
    
    public List<Pair<Integer,Float>> getEntitaGraficoInit(String nomeAzienda){
        List<Pair<Integer,Float>> lista = new ArrayList<>();
        
        if (nomeAzienda==null) 
            return lista;
        try(Connection connessione = DriverManager.getConnection(
            "jdbc:mysql://" + addressIPDatabase + ":" + portaDatabase + "/" + nomeDatabase,usernameDatabase,passwordDatabase);
            PreparedStatement query = connessione.prepareStatement("SELECT MONTH(dataScadenza) as Mese, ROUND(SUM(guadagno)) as guadagnoMensile FROM Ordini "
                    + "WHERE nomeAziendaFK = ? AND YEAR(dataScadenza) = YEAR(CURRENT_DATE()) GROUP BY MONTH(dataScadenza)"))
        {
            query.setString(1,nomeAzienda);
                       
            ResultSet risultatoQuery= query.executeQuery();
            
            while(risultatoQuery.next())
            {
                lista.add(new Pair<>(risultatoQuery.getInt("Mese"),risultatoQuery.getFloat("guadagnoMensile")));
            }
        }
        catch (SQLException exception)
        {
            System.err.println("Impossibile caricare i dati dal Database(Grafico): " + exception.getMessage());
        }
        return lista;
    }
}
