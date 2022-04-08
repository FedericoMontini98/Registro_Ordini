/**
 *
 * @author Federico Montini
 */

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class FormInserimento {
    private static final String iconaInserimento = "file:../../stuffFiles/iconaIns.png";
    
    private final AnchorPane form;
    
    private TextField nomeProdotto;
    private DatePicker dataScadenza;
    private TextField guadagno;
    private TextField fornitore;
    private TextField numPezzi;
    
    public FormInserimento(){
         ImageView image = new ImageView(iconaInserimento);
         image.setFitWidth(25);
         image.setPreserveRatio(true);
         
         HBox singoliCampi;
         singoliCampi = new HBox(creaCampoNomeProdotto(),creaCampoDataScadenza(), creaCampoGuadagno(), creaCampoFornitore(), creaCampoNumeroPezzi());
         singoliCampi.setAlignment(Pos.CENTER);
         singoliCampi.setSpacing(10);
         
         AnchorPane.setLeftAnchor(image,8.0);
         AnchorPane.setTopAnchor(image, 20.0);
         
         AnchorPane.setRightAnchor(singoliCampi, 32.0);
         
         form = new AnchorPane(image, singoliCampi);
    }
    
    private Node creaCampoNomeProdotto(){
        nomeProdotto = new TextField();
        
        nomeProdotto.setPrefWidth(350);
        nomeProdotto.setStyle("-fx-border: thin;"
                + "-fx-font-size: 10pt;"
                + "-fx-border-color: black;"
                + "-fx-background-color: white;");
        
        Label labelNomeProdotto = new Label ("Nome del Prodotto");
        labelNomeProdotto.setStyle("-fx-font-size: 8pt;"
                + "-fx-font-weight: bold;");
        VBox vbox = new VBox(labelNomeProdotto, nomeProdotto);
        vbox.setAlignment(Pos.TOP_LEFT);
        return vbox;
    }
    
    private Node creaCampoDataScadenza(){
        dataScadenza = new DatePicker();
        
        dataScadenza.setPrefWidth(150);
        dataScadenza.setStyle("-fx-font-size: 10pt;"
                + "-fx-border: thin;"
                + "-fx-border-color: black;"
                + "-fx-background-color: white;");
        
        Label labelDataScadenza = new Label ("Data di Scadenza");
        labelDataScadenza.setStyle("-fx-font-size: 10pt;"
                + "-fx-font-weight: bold;");
        
        VBox vbox = new VBox(labelDataScadenza, dataScadenza);
        vbox.setAlignment(Pos.TOP_LEFT);
        return vbox;
    }
    
    private Node creaCampoGuadagno(){
        guadagno = new TextField();
        
        guadagno.setPrefWidth(150);
        guadagno.setStyle("-fx-border: thin;"
                + "-fx-font-size: 10pt;"
                + "-fx-border-color: black;"
                + "-fx-background-color: white;");
        
        Label labelGuadagno = new Label ("Guadagno");
        labelGuadagno.setStyle("-fx-font-size: 8pt;"
                + "-fx-font-weight: bold;");
        
        VBox vbox = new VBox(labelGuadagno, guadagno);
        return vbox;
    }
    
    private Node creaCampoFornitore(){
        fornitore = new TextField();
        
        fornitore.setPrefWidth(150);
        fornitore.setStyle("-fx-border: thin;"
                + "-fx-font-size: 10pt;"
                + "-fx-border-color: black;"
                + "-fx-background-color: white;");
        
        Label labelFornitore = new Label ("Fornitore");
        labelFornitore.setStyle("-fx-font-size: 8pt;"
                + "-fx-font-weight: bold;");
        
        VBox vbox = new VBox(labelFornitore, fornitore);
        return vbox;
    }
    
    private Node creaCampoNumeroPezzi(){
        numPezzi = new TextField();
        
        numPezzi.setPrefWidth(150);
        numPezzi.setStyle("-fx-border: thin;"
                + "-fx-font-size: 10pt;"
                + "-fx-border-color: black;"
                + "-fx-background-color: white;");
        
        Label labelNumPezzi = new Label ("Numero Pezzi");
        labelNumPezzi.setStyle("-fx-font-size: 8pt;"
                + "-fx-font-weight: bold;");
        
        VBox vbox = new VBox(labelNumPezzi, numPezzi);
        return vbox;
    }
    
    public EntryOrdine getNewEntry(){
        float guadagnoInserito;
        int numPezziInseriti;
        
        try{guadagnoInserito = Float.valueOf(guadagno.getText());}
        catch(NumberFormatException exception)
        {
            guadagnoInserito = 0;
        }
        
        try{numPezziInseriti = Integer.valueOf(numPezzi.getText());}
        catch(NumberFormatException exception)
        {
            numPezziInseriti= 1;
        }
        
        return new EntryOrdine(-1, nomeProdotto.getText(), dataScadenza.getValue(), guadagnoInserito, fornitore.getText(),numPezziInseriti);
    }
    
    public void liberaForm(){
            nomeProdotto.setText("");
            dataScadenza.setValue(null);
            guadagno.setText("");
            fornitore.setText("");
            numPezzi.setText("");
    }
    
    public Node getForm(){
        return form;
    }
    
    public void setEntry(EntryOrdine entry){
        nomeProdotto.setText(entry.getNomeProdotto());
        dataScadenza.setValue(entry.getDataScadenza());
        Float _guadagno=(Float)entry.getGuadagno();
        guadagno.setText(_guadagno.toString());
        fornitore.setText(entry.getFornitore());
        Integer _numPezzi = (Integer)entry.getNumPezzi();
        numPezzi.setText(_numPezzi.toString());
    }
}
