package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ChartView extends AbstractView {

    public ChartView(SimulatorLogic simulatorLogic){
        super (simulatorLogic);
        PieChart pieChart = new PieChart();
        Stage primaryStage = new Stage();

        PieChart.Data slice1 = new PieChart.Data("Entrance car queue", simulatorLogic.getEntranceCarQueue().carsInQueue());
        PieChart.Data slice2 = new PieChart.Data("Entrance pass queue"  , simulatorLogic.getEntrancePassQueue().carsInQueue());

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);

        VBox vbox = new VBox(pieChart);

        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();
    }

    @Override
    public void updateView(){

    }

    @Override
    public String getName() {
        return "ChartView";
    }
}
