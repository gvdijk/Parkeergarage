package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;


public class ChartView extends AbstractView {
    private PieChartPanel pieChartPanel;

    public ChartView(SimulatorLogic simulatorLogic){
        super (simulatorLogic);
        pieChartPanel = new PieChartPanel();
        add(pieChartPanel);
        setVisible(false);
    }

    private class PieChartPanel extends JFXPanel {
        private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        private PieChart pieChart;

        public PieChartPanel() {
            data.add(0, new PieChart.Data("Entrance car queue", simulatorLogic.getEntranceCarQueue().carsInQueue()));
            data.add(1, new PieChart.Data("Entrance pass queue" , simulatorLogic.getEntrancePassQueue().carsInQueue()));
            pieChart = new PieChart();
            pieChart.setData(data);
            pieChart.setAnimated(false);
            pieChart.setTitle("Queues");
            pieChart.setLegendSide(Side.RIGHT);
            pieChart.setLabelsVisible(true);

            setScene(new Scene(pieChart));
        }

        private void updateData() {
            data.set(0, new PieChart.Data("Entrance car queue", simulatorLogic.getEntranceCarQueue().carsInQueue()));
            data.set(1, new PieChart.Data("Entrance pass queue" , simulatorLogic.getEntrancePassQueue().carsInQueue()));
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }

    @Override
    public void updateView(){
        pieChartPanel.update();
    }

    @Override
    public String getName() {
        return "ChartView";
    }
}