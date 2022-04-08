/**
 *
 * @author Federico Montini
 */
import java.util.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.util.*;

public class GraficoGuadagni {
    private final LineChart<?,?> graficoGuadagni;
    private XYChart.Series valori;
    private final NumberAxis X;
    private final NumberAxis Y;
    
    public GraficoGuadagni(){
        valori  = new XYChart.Series();
        X=new NumberAxis(1,12,1);
        Y=new NumberAxis(0,1500,100);
        X.setLabel("Mesi");
        Y.setLabel("Guadagno cumulativo");
        graficoGuadagni= new LineChart(X,Y);
        graficoGuadagni.setStyle("-fx-border: thin; -fx-border-color: black;");
        graficoGuadagni.setPrefSize(550,250);
    }
    
    public void aggiorna(List<Pair<Integer, Float>> _risultati){
        valori.getData().removeAll(Collections.singleton(graficoGuadagni.getData().setAll()));
        graficoGuadagni.setTitle("Grafico dei guadagni");
        valori = new XYChart.Series();
        valori.setName("Guadagno Mensile");
        for(Pair<Integer,Float> _risultato : _risultati)
        {
                valori.getData().add(new XYChart.Data(_risultato.getKey(),_risultato.getValue()));
        }
        graficoGuadagni.getData().add(valori);
    }
    
    public Node getGraficoGuadagni(){
        return graficoGuadagni;
    }
}
