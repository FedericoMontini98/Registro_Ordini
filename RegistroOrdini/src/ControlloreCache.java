
/**
 *
 * @author Federico Montini
 */

import java.io.*;
import java.time.*;
import java.util.*;

public class ControlloreCache {
    private static final String fileCacheBinario = "stuffFiles/cache.bin";
        
    public static void salvataggio(InterfacciaRegistroOrdini interfacciaUtente){
        //Apro uno stream di dati in output per scrivere serialmente l'oggetto Cache su un file in binario
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileCacheBinario)))
        {
            //Scrivo l'oggetto serializzato sul file appositamente creato
            out.writeObject(creaCache(interfacciaUtente));
        }
        catch (IOException ex)
        {
            //Gestisco le eventuali eccezioni generate stampando via System err l'errore generato
            System.err.println("Salvataggio dati su Cache fallito: " + ex.getMessage());
        }
    }

    public static void caricamento(InterfacciaRegistroOrdini interfacciaUtente) {
        //Apro uno stream di dati in input per leggere l'oggetto Cache su un file in binario
        //Creo un oggetto cache locale, il campo FileCacheBinario è statico, non ci sono problemi
        Cache cache;
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileCacheBinario)))
        {
            //Faccio il cast a Cache per trasformare l'oggetto precedentemente serializzato dalla 'salvataggio' in un oggetto Cache
            cache = (Cache) in.readObject();
        }
        catch(IOException | ClassNotFoundException ex)
        {
            System.err.println("Caricamento dei dati da file binario fallito: " + ex.getMessage());
            return;
        }
        //Dati prelevati, adesso devo eseguire il caricamento nei campi dell'interfaccia
        //Inserisco prima la entry vacante 
        EntryOrdine ordine = new EntryOrdine(-1, cache.nomeProdotto, cache.dataScadenza, cache.guadagno , cache.fornitore,cache.numPezzi
                                       );
        interfacciaUtente.getFormInserimento().setEntry(ordine);
        interfacciaUtente.getBarraCaricamentoDati().setNomeAzienda(cache.nomeAzienda);
        interfacciaUtente.getTabellaOrdini().caricaEntrate(cache.entries);
        interfacciaUtente.getAreaCambioPeriodo().setDataInizio(LocalDate.parse(cache.dataInizioAnalisi));
        interfacciaUtente.getAreaCambioPeriodo().setDataFine(LocalDate.parse(cache.dataFineAnalisi));
        //adesso devo dire alla table view quale riga era stata selezionata prima della chiusura
        interfacciaUtente.getTabellaOrdini().setSelected(cache.rigaSelezionata);
    }
   
    private static Cache creaCache(InterfacciaRegistroOrdini interfacciaUtente){
        String nomeAzienda = interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda();
        EntryOrdine entry = interfacciaUtente.getFormInserimento().getNewEntry();
        List<EntryOrdine> entries=new ArrayList();
        List<EntryOrdineBean> entriesBean = interfacciaUtente.getTabellaOrdini().getEntries();
        for (EntryOrdineBean cursor : entriesBean)
        {
            EntryOrdine entryOrdine =cursor.trasformaInEntryOrdine();
            entries.add(entryOrdine);
        }
        String nomeProdotto = entry.getNomeProdotto();
        LocalDate dataScadenza = entry.getDataScadenza();
        float guadagno = entry.getGuadagno();
        String fornitore = entry.getFornitore();
        int numPezzi = entry.getNumPezzi();
        int rigaSelezionata = interfacciaUtente.getTabellaOrdini().getSelected();
        LocalDate dataInizio = interfacciaUtente.getAreaCambioPeriodo().getDataInizio();
        LocalDate dataFine = interfacciaUtente.getAreaCambioPeriodo().getDataFine();
        
        return new Cache(nomeAzienda, nomeProdotto, fornitore, guadagno, numPezzi, dataScadenza, rigaSelezionata,entries,dataInizio,dataFine);
    } 
    
}
