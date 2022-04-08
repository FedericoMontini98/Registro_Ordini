/**
 *
 * @author Federico Montini
 */
import com.thoughtworks.xstream.*;
import javafx.scene.paint.*;

public class ConfigParametri {
    private static final int numeroRigheTabellaDefault = 5;
    private static final int intervalloDiGiorniDefault = 31;
    private static final Color coloreInserisciDefault = new Color(0,0.6,0,1);   //Verde
    private static final Color coloreModificaDefault = new Color(0,0,1,1); //Blu
    private static final Color coloreAnnullaDefault = new Color(0,0,0,0.5); //Grigio
    private static final Color coloreEliminaDefault = new Color(1,0,0,1);   //Rosso
    
    private static final String addressIPDatabaseDefault = "127.0.0.1";
    private static final int portaDatabaseDefault = 3320;
    private static final String nomeDatabaseDefault="RegistroOrdini";
    private static final String usernameDatabaseDefault = "root";
    private static final String passwordDatabaseDefault = "";
    
    private static final String addressIPServerLogDefault = "127.0.0.1";
    private static final int portaServerLogDefault =8080;
    
    public int numeroRigheTabella;
    public int intervalloDiGiorni;
    public Color coloreInserisci;
    public Color coloreModifica;
    public Color coloreAnnulla;
    public Color coloreElimina;
    public String addressIPDatabase;
    public int portaDatabase;
    public String nomeDatabase;
    public String usernameDatabase;
    public String passwordDatabase;
    public String addressIPServerLog;
    public int portaServerLog;
    
    public ConfigParametri(String stringaXML){
        if (stringaXML != null)
        {
            ConfigParametri par = (ConfigParametri)creaXStream().fromXML(stringaXML);
            numeroRigheTabella=par.numeroRigheTabella;
            intervalloDiGiorni=par.intervalloDiGiorni;
            coloreInserisci=par.coloreInserisci;
            coloreModifica=par.coloreModifica;
            coloreAnnulla=par.coloreAnnulla;
            coloreElimina=par.coloreElimina;
            addressIPDatabase=par.addressIPDatabase;
            portaDatabase=par.portaDatabase;
            nomeDatabase=par.nomeDatabase;
            usernameDatabase=par.usernameDatabase;
            passwordDatabase=par.passwordDatabase;
            addressIPServerLog=par.addressIPServerLog;
            portaServerLog=par.portaServerLog;
        }
        verificaInizializzazioneCampi();
    }
    
    private void verificaInizializzazioneCampi(){
        if(numeroRigheTabella==0){
            numeroRigheTabella=numeroRigheTabellaDefault;
        }
        if(intervalloDiGiorni==0){
            intervalloDiGiorni=intervalloDiGiorniDefault;
        }
        if(coloreInserisci==null){
            coloreInserisci=coloreInserisciDefault;
        }
        if(coloreModifica==null){
            coloreModifica=coloreModificaDefault;
        }
        if(coloreAnnulla==null){
            coloreAnnulla=coloreAnnullaDefault;
        }
        if(coloreElimina==null){
            coloreElimina=coloreEliminaDefault;
        }
        if(addressIPDatabase==null){
            addressIPDatabase=addressIPDatabaseDefault;
        }
        if(portaDatabase==0){
            portaDatabase=portaDatabaseDefault;
        }
        if(nomeDatabase==null){
            nomeDatabase=nomeDatabaseDefault;
        }
        if(usernameDatabase==null){
            usernameDatabase=usernameDatabaseDefault;
        }
        if(passwordDatabase==null){
            passwordDatabase=passwordDatabaseDefault;
        }
        if(addressIPServerLog==null){
            addressIPServerLog=addressIPServerLogDefault;
        }
        if(portaServerLog==0){
            portaServerLog=portaServerLogDefault;
        }
    }
    
    public static XStream creaXStream(){
        return new XStream();
    }
    
    public String toXML(){
        return creaXStream().toXML(this);
    }
    
}
