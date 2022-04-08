

/*
 *
 * @author Federico Montini
 */

import java.io.*;
import java.time.*;
import java.util.*;

public class Cache implements Serializable{
    public String nomeAzienda;
    public String nomeProdotto;
    public String fornitore;
    public float guadagno;
    public int numPezzi;
    public LocalDate dataScadenza;
    public int rigaSelezionata;
    public String dataInizioAnalisi;
    public String dataFineAnalisi;
    public List<EntryOrdine> entries;
    
    public Cache(String _nomeAzienda, String _nomeProdotto, String _fornitore, float _guadagno, int _numeroPezzi,
                 LocalDate _dataScadenza, int _rigaSelezionata,List<EntryOrdine> _entries, LocalDate _dataInizioAnalisi, LocalDate _dataFineAnalisi){
        
        this.nomeAzienda=_nomeAzienda;
        this.nomeProdotto=_nomeProdotto;
        this.fornitore=_fornitore;
        this.guadagno=_guadagno;
        this.numPezzi=_numeroPezzi;
        this.dataScadenza=_dataScadenza;
        this.rigaSelezionata=_rigaSelezionata;
        this.entries=_entries;
        this.dataInizioAnalisi=_dataInizioAnalisi.toString();
        this.dataFineAnalisi=_dataFineAnalisi.toString();
    }
}