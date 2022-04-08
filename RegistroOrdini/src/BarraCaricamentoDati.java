
/**
 *
 * @author fedem
 */

import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class BarraCaricamentoDati {
    private static final String labelPulsanteCarica = "Carica";
    
    private String nomeAzienda;
    private InterfacciaRegistroOrdini interfacciaUtente;
    private final HBox container;
    private final TextField campoInserimentoNomeAzienda;
    
    public BarraCaricamentoDati(InterfacciaRegistroOrdini interfacciaUtente)
    {
        this.nomeAzienda = "";
        this.interfacciaUtente=interfacciaUtente;
        
        
        campoInserimentoNomeAzienda = new TextField();
        campoInserimentoNomeAzienda.setStyle("-fx-font-size: 15pt;");
        campoInserimentoNomeAzienda.setPrefSize(300, 15);
        
        Label titolo=new Label("Nome azienda:");
        titolo.setStyle("-fx-font-family: serif;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 20 pt;");
        
        Button carica = new Button(labelPulsanteCarica);
        carica.setStyle("-fx-padding: 5px 20px;"
                + "-fx-text-align: center;"
                + "-fx-text-fill: white;"
                + "-fx-font-family: sans;"
                + "-fx-background-color: blue;"
                + "-fx-border-radius: 15px;"
                + "-fx-font-size: 12 pt;"
                + "-fx-font-weight: bold;");
        
        carica.setOnAction((ActionEvent event)-> {
            this.nomeAzienda = campoInserimentoNomeAzienda.getText();
            this.interfacciaUtente.getRegistroOrdini().aggiornaRegistroOrdiniTabella();
            this.interfacciaUtente.getRegistroOrdini().aggiornaRegistroOrdiniGrafico();
            this.interfacciaUtente.getRegistroOrdini().inviaLog("CARICA");
        });
        
        carica.setOnMouseEntered((MouseEvent event)->{
            this.interfacciaUtente.getRegistroOrdini().setCursoreHand();
        });
        
        carica.setOnMouseExited((MouseEvent event)->{
            this.interfacciaUtente.getRegistroOrdini().setCursoreDiDefault();
        });
        
        container =new HBox(titolo, campoInserimentoNomeAzienda, carica);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(40.0);
    }
    
    public Node getGUI(){
        return container;
    }
    
    public String getNomeAzienda(){
        return this.nomeAzienda;
    }
    
    public void setNomeAzienda(String _nomeAzienda){
        campoInserimentoNomeAzienda.setText(_nomeAzienda);
        this.nomeAzienda=_nomeAzienda;
    }
}
