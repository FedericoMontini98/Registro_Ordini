/**
 *
 * @author Federico Montini
 */
import java.util.*;
import java.time.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class TabellaOrdini {
    private final TableView<EntryOrdineBean> tab;
    private final ObservableList<EntryOrdineBean> entries;
    
    private final InterfacciaRegistroOrdini interfacciaUtente;
    
    public TabellaOrdini(InterfacciaRegistroOrdini _interfacciaUtente, ConfigParametri _datiXML){
        tab = new TableView<>();
        this.interfacciaUtente=_interfacciaUtente;
        entries = FXCollections.observableArrayList();
        
        tab.setFixedCellSize(32);
        tab.setPrefSize(1002,_datiXML.numeroRigheTabella * tab.getFixedCellSize() + 30);
        tab.setMaxSize(tab.getPrefWidth(),tab.getPrefHeight());
        
        TableColumn<EntryOrdineBean, String> colonnaNomeProdotto = new TableColumn<>("Nome del prodotto");
        colonnaNomeProdotto.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        colonnaNomeProdotto.setPrefWidth(400);
        colonnaNomeProdotto.setMaxWidth(colonnaNomeProdotto.getPrefWidth());
        
        TableColumn<EntryOrdineBean, LocalDate> colonnaDataScadenza = new TableColumn<>("Data scadenza");
        colonnaDataScadenza.setCellValueFactory(new PropertyValueFactory<>("dataScadenza"));
        colonnaDataScadenza.setPrefWidth(150);
        colonnaDataScadenza.setMaxWidth(colonnaDataScadenza.getPrefWidth());
        
        TableColumn<EntryOrdineBean, Float> colonnaGuadagno = new TableColumn<>("Guadagno");
        colonnaGuadagno.setCellValueFactory(new PropertyValueFactory<>("guadagno"));
        colonnaGuadagno.setPrefWidth(150);
        colonnaGuadagno.setMaxWidth(colonnaGuadagno.getPrefWidth());
        
        TableColumn<EntryOrdineBean, String> colonnaFornitore = new TableColumn<>("Fornitore");
        colonnaFornitore.setCellValueFactory(new PropertyValueFactory<>("fornitore"));
        colonnaFornitore.setPrefWidth(150);
        colonnaFornitore.setMaxWidth(colonnaFornitore.getPrefWidth());
        
        TableColumn<EntryOrdineBean, Integer> colonnaNumPezzi = new TableColumn<>("Numero pezzi");
        colonnaNumPezzi.setCellValueFactory(new PropertyValueFactory<>("numPezzi"));
        colonnaNumPezzi.setPrefWidth(150);
        colonnaNumPezzi.setMaxWidth(colonnaNumPezzi.getPrefWidth());
        
        tab.setItems(entries);
        tab.getColumns().add(colonnaNomeProdotto); tab.getColumns().add(colonnaDataScadenza);
        tab.getColumns().add(colonnaGuadagno); tab.getColumns().add(colonnaFornitore);
        tab.getColumns().add(colonnaNumPezzi);
        
        tab.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    public Node getGUI(){
        return tab;
    }
    
    public void caricaEntrate(List<EntryOrdine> _entries)
    {
        entries.clear();
        for(EntryOrdine e : _entries)
        {
            entries.add(new EntryOrdineBean(e));
        }
    }
    
    public void eliminaEntryOrdine()
    {
        interfacciaUtente.getRegistroOrdini().eliminaOrdine(tab.getSelectionModel().getSelectedItem().trasformaInEntryOrdine());
    }
    
    public EntryOrdine modificaEntryOrdine()
    {
        EntryOrdine modifiedEntry= null;
        modifiedEntry=tab.getSelectionModel().getSelectedItem().trasformaInEntryOrdine();
        return modifiedEntry;     
    }
    
    public void setSelected(int _riga){
        tab.getSelectionModel().select(_riga);
    }
    
    public int getSelected(){
        return tab.getSelectionModel().getSelectedIndex();
    }
    
    public List<EntryOrdineBean> getEntries(){
        return this.entries;
    }
}

//colonnaFornitore.setCellFactory(TextFieldTableCell.forTableColumn()); /*???*/
