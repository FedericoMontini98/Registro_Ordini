
/*
 *
 * @author Federico Montini
 */

import java.time.*;
import java.util.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.application.*;


public class RegistroOrdini extends Application{
    private static final String nomeApplicazione= "Registro Ordini";
    public static final int altezzaScena = 750;
    public static final int larghezzaScena = 1100; 
    private static final String nomeFileConfigurazioneXML = "stuffFiles/config.xml";
    private static final String schemaFileConfigurazioneXSD = "stuffFiles/config_schema.xsd";
    private ConfigParametri datiXML;
    private ControlloreLog controlloreLog;
    private ControlloreDatabase controlloreDatabase;
    private InterfacciaRegistroOrdini interfacciaUtente;
    
    private Scene scena;
    
    public void start(Stage stage){
        String configurazioneXML = GestoreFile.caricaDaFile(nomeFileConfigurazioneXML);
        
	datiXML=new ConfigParametri(XML_Validatore.valida(configurazioneXML, schemaFileConfigurazioneXSD) ? configurazioneXML : null);
        
        controlloreLog= new ControlloreLog(datiXML.addressIPServerLog, datiXML.portaServerLog);
        
        //controlloreDatabase= new ControlloreDatabase(datiXML.nomeDatabase ,datiXML.usernameDatabase, datiXML.passwordDatabase,datiXML.addressIPDatabase, datiXML.portaDatabase );
        controlloreDatabase= new ControlloreDatabase("RegistroOrdini" ,"root", "","127.0.0.1", 3306 );

        Group main = new Group();
        interfacciaUtente = new InterfacciaRegistroOrdini(main, this, datiXML , nomeApplicazione);
        aggiornaRegistroOrdiniTabella();
        aggiornaRegistroOrdiniGrafico();
        ControlloreCache.caricamento(interfacciaUtente);

        inviaLog("COD_AVVIO");
        
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
					inviaLog("COD_TERMINAZIONE");
					ControlloreCache.salvataggio(interfacciaUtente);
				}
			);
        
        stage.setTitle(nomeApplicazione);
        scena = new Scene(main, larghezzaScena, altezzaScena);
        stage.setScene(scena);
        stage.show();
    }
    
    public void inviaLog(String evento){
        controlloreLog.sendEvento(nomeApplicazione,evento);
    }
    
    public void aggiungiOrdine(EntryOrdine ordine){
        controlloreDatabase.inserimentoOrdine(interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda(),
                                        ordine.getNomeProdotto(),
                                        ordine.getDataScadenza(),
                                        ordine.getGuadagno(),
                                        ordine.getFornitore(),
                                        ordine.getNumPezzi()
                                        );
        aggiornaRegistroOrdiniTabella();
        aggiornaRegistroOrdiniGrafico();
    }
    
    public void eliminaOrdine(EntryOrdine ordine){
        controlloreDatabase.eliminaOrdine(interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda(), ordine.getOrdineID());
        
        aggiornaRegistroOrdiniTabella();
        aggiornaRegistroOrdiniGrafico();
    }
    
    public void modificaOrdine (EntryOrdine entryVecchia, EntryOrdine entryNuova){
        controlloreDatabase.modificaOrdine(interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda(),
                                            entryVecchia.getOrdineID(),entryNuova.getNomeProdotto(), entryNuova.getDataScadenza(), 
                                            entryNuova.getGuadagno(), entryNuova.getFornitore() ,entryNuova.getNumPezzi());
        aggiornaRegistroOrdiniTabella();
        aggiornaRegistroOrdiniGrafico();
    }
    
    /*public void aggiornaRegistroOrdini(){
        LocalDate dataInizioIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataInizio();
        LocalDate dataFineIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataFine();
        String nomeAzienda = interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda();
        
        List<EntryOrdine> ordini= controlloreDatabase.getEntryList(nomeAzienda,dataInizioIntervallo, dataFineIntervallo);
        interfacciaUtente.getTabellaOrdini().caricaEntrate(ordini);
        
        List<Pair<Integer,Float>> entitaPerGrafico = controlloreDatabase.getEntitaGraficoInit(nomeAzienda);
        interfacciaUtente.getGraficoGuadagni().aggiorna(entitaPerGrafico);
    }*/
    
    public void aggiornaRegistroOrdiniTabella(){
        LocalDate dataInizioIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataInizio();
        LocalDate dataFineIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataFine();
        String nomeAzienda = interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda();
        
        List<EntryOrdine> ordini= controlloreDatabase.getEntryList(nomeAzienda,dataInizioIntervallo, dataFineIntervallo);
        interfacciaUtente.getTabellaOrdini().caricaEntrate(ordini);
    }
    
    public void aggiornaRegistroOrdiniGrafico(){
        LocalDate dataInizioIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataInizio();
        LocalDate dataFineIntervallo = interfacciaUtente.getAreaCambioPeriodo().getDataFine();
        String nomeAzienda = interfacciaUtente.getBarraCaricamentoDati().getNomeAzienda();
        
        List<Pair<Integer,Float>> entitaPerGrafico = controlloreDatabase.getEntitaGrafico(nomeAzienda,dataInizioIntervallo,dataFineIntervallo);
        interfacciaUtente.getGraficoGuadagni().aggiorna(entitaPerGrafico);
    }
    
    public void setCursoreHand(){
        scena.setCursor(Cursor.HAND);
    }
    
    public void setCursoreDiDefault(){
        scena.setCursor(Cursor.DEFAULT);
    }
    
}