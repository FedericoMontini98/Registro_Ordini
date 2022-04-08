/**
 *
 * @author Federico Montini
 */

import java.time.*;
import javafx.beans.property.*;

public class EntryOrdineBean {
    
    private int identificatore=0;
    
    private SimpleStringProperty nomeProdotto;
    private SimpleObjectProperty<LocalDate> dataScadenza;
    private SimpleFloatProperty guadagno;
    private SimpleStringProperty  fornitore;
    private SimpleIntegerProperty numPezzi;
    
    public EntryOrdineBean(EntryOrdine entry){
       
        this(entry.getNomeProdotto(), entry.getDataScadenza(), entry.getGuadagno(), entry.getFornitore(), entry.getNumPezzi());
        this.identificatore= entry.getOrdineID();
    }
    
    public EntryOrdineBean(String nomeProdotto, LocalDate dataScadenza, float guadagno, String fornitore, int numPezzi){
        this.nomeProdotto=new SimpleStringProperty(nomeProdotto);
        this.dataScadenza=new SimpleObjectProperty<> (dataScadenza);
        this.guadagno= new SimpleFloatProperty(guadagno);
        this.fornitore = new SimpleStringProperty(fornitore);
        this.numPezzi= new SimpleIntegerProperty(numPezzi);
    }
    
    public EntryOrdine trasformaInEntryOrdine(){
        return new EntryOrdine(identificatore, getNomeProdotto(), getDataScadenza(), getGuadagno(), getFornitore(), getNumPezzi());
    }
    
    public String getNomeProdotto(){
        return nomeProdotto.getValue();
    }
    
    public LocalDate getDataScadenza(){
        return dataScadenza.getValue();
    }
    
    public float getGuadagno(){
        return guadagno.getValue();
    }
    
    public String getFornitore(){
        return fornitore.getValue();
    }
    
    public int getNumPezzi(){
        return numPezzi.getValue();
    }
    
    public void setNomeDelProdotto(String nomeDelProdotto){
        this.nomeProdotto.setValue(nomeDelProdotto);
    }
    
    public void setDataScadenza(LocalDate dataScadenza){
        this.dataScadenza.setValue(dataScadenza);
    }
    
    public void setGuadagno(float guadagno){
        this.guadagno.setValue(guadagno);
    }
    
    public void setFornitore(String fornitore){
        this.fornitore.setValue(fornitore);
    }
    
    public void setNumPezzi(int numPezzi){
        this.numPezzi.setValue(numPezzi);
    }
}
