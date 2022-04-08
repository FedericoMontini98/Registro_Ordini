/**
 *
 * @author Federico Montini
 */

import java.io.Serializable;
import java.time.*;

public class EntryOrdine implements Serializable{
    
    private final int ordineID;
    private final String nomeProdotto;
    private final LocalDate dataScadenza;
    private final float guadagno;
    private final String fornitore;
    private final int numPezzi;
    
    public EntryOrdine(int ordineID, String nomeDelProdotto, LocalDate dataScadenza, float guadagno, String fornitore, int numPezzi){
        this.ordineID=ordineID;
        this.nomeProdotto=nomeDelProdotto;
        this.dataScadenza=dataScadenza;
        this.guadagno=guadagno;
        this.fornitore=fornitore;
        this.numPezzi=numPezzi;
    }
    
    public int getOrdineID(){
        return ordineID;
    }
    
    public String getNomeProdotto(){
        return nomeProdotto;
    }
    
    public LocalDate getDataScadenza(){
        return dataScadenza;
    }
    
    public float getGuadagno(){
        return guadagno;
    }
    
    public String getFornitore(){
        return fornitore;
    }
    
    public int getNumPezzi(){
        return numPezzi;
    }
}
