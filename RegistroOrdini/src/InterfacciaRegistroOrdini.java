
/*
 *
 * @author Federico Montini
 */

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class InterfacciaRegistroOrdini{
    private final RegistroOrdini registroOrdini;
    
    private final VBox mainContainer;
    private final HBox temporalSelectionArea;
    private final HBox buttonArea;
    private final AnchorPane headerArea;
    
    private final BarraCaricamentoDati barraCaricamentoDati;
    private final FormInserimento formInserimento;
    private final GraficoGuadagni graficoGuadagni;
    private final AreaCambioPeriodo areaCambioPeriodo;
    private final TabellaOrdini tabellaOrdini;
    
    
   public InterfacciaRegistroOrdini(Group main, RegistroOrdini registroOrdini, ConfigParametri datiXML, String nomeApplicazione ){
       this.registroOrdini= registroOrdini;
       //Inizializzo tutte le strutture
       headerArea = new AnchorPane();
       buttonArea = new HBox();
       barraCaricamentoDati = new BarraCaricamentoDati(this);
       formInserimento = new FormInserimento();
       graficoGuadagni = new GraficoGuadagni();
       areaCambioPeriodo = new AreaCambioPeriodo(this, datiXML);
       tabellaOrdini = new TabellaOrdini(this,datiXML);
       //Delego la costruzione delle aree a funzioni specializzate
       costruttoreHeaderArea(nomeApplicazione);
       costruttoreButtonArea(datiXML);
       temporalSelectionArea= new HBox(areaCambioPeriodo.getGUI());temporalSelectionArea.setAlignment(Pos.TOP_CENTER);
       mainContainer = new VBox(headerArea,temporalSelectionArea,tabellaOrdini.getGUI(),formInserimento.getForm(),buttonArea,graficoGuadagni.getGraficoGuadagni());
       
       mainContainer.setSpacing(10);
       mainContainer.setPadding(new Insets(0,20,0,20));
       VBox.setMargin(buttonArea,new Insets(0,15,0,10));
       
       mainContainer.setAlignment(Pos.TOP_CENTER);
       
       mainContainer.setPrefWidth(registroOrdini.larghezzaScena); mainContainer.setMaxWidth(registroOrdini.larghezzaScena);
       mainContainer.setMaxHeight(registroOrdini.altezzaScena); mainContainer.setPrefHeight(registroOrdini.altezzaScena);
       mainContainer.setStyle("-fx-background-color: #C0C0C0;");
       main.getChildren().add(mainContainer);
       mainContainer.setAlignment(Pos.CENTER);
   }
   
   private void costruttoreHeaderArea(String nomeApplicazione){
       
       headerArea.getChildren().clear();
       Label titolo = new Label(nomeApplicazione);
       titolo.setStyle("-fx-font-size: 25pt; "
               + "-fx-text-fill: #191970;"
               + "-fx-font-weight: bold;"
               + "-fx-font-family: \"Courier New\", sans;"
              );
       headerArea.getChildren().addAll(titolo, barraCaricamentoDati.getGUI());
       headerArea.setLeftAnchor(titolo,50.0);
       headerArea.setRightAnchor(barraCaricamentoDati.getGUI(), 50.0);
   }
   
   private void costruttoreButtonArea(ConfigParametri datiXML){
       
       buttonArea.getChildren().clear();
       
       buttonArea.setAlignment(Pos.CENTER);
       
       Button inserisci = new Button("Inserisci");
       impostaPulsante(inserisci,datiXML.coloreInserisci);
       
       inserisci.setOnAction((ActionEvent event)->{
           registroOrdini.aggiungiOrdine(formInserimento.getNewEntry());
           formInserimento.liberaForm();
           registroOrdini.inviaLog("INSERISCI");
       });
       
       inserisci.setOnMouseDragEntered((MouseEvent event)->registroOrdini.setCursoreHand());
       inserisci.setOnMouseDragExited((MouseEvent event)->registroOrdini.setCursoreDiDefault());
       
       Button annulla = new Button("Annulla");
       impostaPulsante(annulla,datiXML.coloreAnnulla);
       
       annulla.setOnAction((ActionEvent event)->{
          formInserimento.liberaForm();
          registroOrdini.inviaLog("ANNULLA");
       });
       
       annulla.setOnMouseDragEntered((MouseEvent event)->registroOrdini.setCursoreHand());
       annulla.setOnMouseDragExited((MouseEvent event)->registroOrdini.setCursoreDiDefault());
       
       Button modifica = new Button("Modifica");
       impostaPulsante(modifica,datiXML.coloreModifica);
       
       modifica.setOnAction((ActionEvent event)->{
          EntryOrdine entryDaModificare= tabellaOrdini.modificaEntryOrdine();
          EntryOrdine entryModificata=formInserimento.getNewEntry();
          registroOrdini.modificaOrdine(entryDaModificare,entryModificata);
          registroOrdini.inviaLog("MODIFICA");
       });
       
       modifica.setOnMouseDragEntered((MouseEvent event)->registroOrdini.setCursoreHand());
       modifica.setOnMouseDragExited((MouseEvent event)->registroOrdini.setCursoreDiDefault());
       
       Button elimina = new Button("Elimina"); // 15)
		impostaPulsante(elimina, datiXML.coloreElimina);
		elimina.setOnAction((ActionEvent ev)-> {
			tabellaOrdini.eliminaEntryOrdine(); registroOrdini.inviaLog("ELIMINA");
		});
		elimina.setOnMouseEntered((MouseEvent ev)-> getRegistroOrdini().setCursoreHand()); // 13b)
		elimina.setOnMouseExited((MouseEvent ev)-> getRegistroOrdini().setCursoreDiDefault());
       buttonArea.getChildren().addAll(inserisci,modifica,annulla,elimina); buttonArea.setSpacing(100);
   }
   
   private void impostaPulsante(Button button, Color colore){
       button.setStyle("-fx-padding: 7px 15px;"
               + "-fx-border-radius: 15px;"
               + "-fx-text-align: center;"
               + "-fx-font-size: 12pt;"
               + "-fx-text-fill: white;"
               + "-fx-font-weight: bold;"
               + "-fx-background-color: " + colore.toString().replace("0x", "#"));
       button.setPrefSize(120,30);
   }
   
   public RegistroOrdini getRegistroOrdini(){
       return registroOrdini;
   }
   
   public BarraCaricamentoDati getBarraCaricamentoDati(){
       return barraCaricamentoDati;
   }
   
   public AreaCambioPeriodo getAreaCambioPeriodo(){
       return areaCambioPeriodo;
   }
   
   public GraficoGuadagni getGraficoGuadagni(){
       return graficoGuadagni;
   }
   
   public FormInserimento getFormInserimento(){
       return formInserimento;
   }
   
   public TabellaOrdini getTabellaOrdini(){
       return tabellaOrdini;
   }
}

