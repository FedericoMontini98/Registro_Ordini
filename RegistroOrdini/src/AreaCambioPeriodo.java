/**
 *
 * @author Federico Montini
 */

import java.time.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class AreaCambioPeriodo {
    private final HBox container;
    private final DatePicker dataInizioPick;
    private final DatePicker dataFinePick;
    
    private final InterfacciaRegistroOrdini interfacciaUtente;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    
    public AreaCambioPeriodo(InterfacciaRegistroOrdini interfacciaUtente,ConfigParametri datiXML){
        this.interfacciaUtente=interfacciaUtente;
        
        dataFine=LocalDate.now().plusDays(datiXML.intervalloDiGiorni/2);
        dataInizio=LocalDate.now().minusDays(datiXML.intervalloDiGiorni/2);
        
        dataInizioPick = new DatePicker(dataInizio);
        dataFinePick = new DatePicker(dataFine);
        
        Button aggiornaTab = new Button("Aggiorna Tabella");
        aggiornaTab.setOnAction((ActionEvent event)->{
            dataInizio =dataInizioPick.getValue();dataFine= dataFinePick.getValue();
             interfacciaUtente.getRegistroOrdini().aggiornaRegistroOrdiniTabella();
             interfacciaUtente.getRegistroOrdini().inviaLog("AGGIORNA INTERVALLO TEMPORALE TABELLA");
        });
        
        aggiornaTab.setOnMouseDragEntered((MouseEvent event)-> interfacciaUtente.getRegistroOrdini().setCursoreHand());
        aggiornaTab.setOnMouseDragExited((MouseEvent event)-> interfacciaUtente.getRegistroOrdini().setCursoreDiDefault());
        
        aggiornaTab.setStyle("-fx-padding: 5px 20px;-fx-text-align: center;-fx-font-size: 12pt;"
                + "-fx-font-family: serif;-fx-text-fill: white;-fx-font-weight: bold;-fx-background-color: blue;");
        aggiornaTab.setPrefHeight(45);
        VBox.setMargin(aggiornaTab, new Insets(30,0,0,0));
        
        Button aggiornaGrafico= new Button("Aggiorna Grafico");
        aggiornaGrafico.setOnAction((ActionEvent ev)->{
            dataInizio =dataInizioPick.getValue();dataFine= dataFinePick.getValue();
            interfacciaUtente.getRegistroOrdini().aggiornaRegistroOrdiniGrafico();
            interfacciaUtente.getRegistroOrdini().inviaLog("AGGIORNA INTERVALLO TEMPORALE GRAFICO");
        });
        aggiornaGrafico.setStyle("-fx-padding: 5px 20px;-fx-text-align: center;-fx-font-size: 12pt;"
                + "-fx-font-family: serif;-fx-text-fill: white;-fx-font-weight: bold;-fx-background-color: blue;");
        aggiornaGrafico.setPrefHeight(45);
        VBox.setMargin(aggiornaGrafico, new Insets(30,0,0,0));
        aggiornaGrafico.setOnMouseDragEntered((MouseEvent event)-> interfacciaUtente.getRegistroOrdini().setCursoreHand());
        aggiornaGrafico.setOnMouseDragExited((MouseEvent event)-> interfacciaUtente.getRegistroOrdini().setCursoreDiDefault());
        Label dataInizioLabel = new Label("Data inizio:");
        dataInizioLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: black;-fx-font-weight: bold;-fx-font-family: serif;");
        VBox.setMargin(dataInizioLabel, new Insets(0,0,10,0));
        
        Label dataFineLabel= new Label("Data fine:");
        dataFineLabel.setStyle("-fx-font-size: 12pt; -fx-text-fill: black; -fx-font-weight: bold;-fx-font-family: serif;");
        VBox.setMargin(dataFineLabel, new Insets(30,0,10,0));
        container = new HBox(dataInizioLabel,dataInizioPick,dataFineLabel,dataFinePick,aggiornaTab,aggiornaGrafico);
        container.setAlignment(Pos.CENTER); container.setSpacing(20);
    }
    
    public LocalDate getDataInizio(){
        return this.dataInizio;
    }
    
    public LocalDate getDataFine(){
        return this.dataFine;
    }
    
    public Node getGUI(){
        return container;
    }
    
    public void setDataInizio(LocalDate dataInizio){
        this.dataInizio= dataInizio;
        dataInizioPick.setValue(dataInizio);
    }
    
    public void setDataFine(LocalDate dataFine){
        this.dataFine= dataFine;
        dataFinePick.setValue(dataFine);
    }
}
