package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.awt.*;

public class GraphView extends AbstractView {

    private CarsPieChartPanel carsPieChartPanel;
    private EarningsLineChartPanel earningsLineChartPanel;

    /**
     * Constructor voor objecten van klasse GraphView.
     */
    public GraphView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
        this.setBackground(new Color(51, 51, 51));

        carsPieChartPanel = new CarsPieChartPanel();
        earningsLineChartPanel = new EarningsLineChartPanel();

        add(carsPieChartPanel);
        add(earningsLineChartPanel);

        setVisible(false);
    }

    @Override
    public void updateView() {
        carsPieChartPanel.update();
        earningsLineChartPanel.update();
    }

    @Override
    public String getName() {
        return "GraphView";
    }



    private class CarsPieChartPanel extends JFXPanel {
        private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        private PieChart pieChart;

        public CarsPieChartPanel() {
            data.add(0, new PieChart.Data("Reguliere Autos", simulatorLogic.getNormalCars()));
            data.add(1, new PieChart.Data("Pashouder Autos" , simulatorLogic.getPassCars()));
            data.add(2, new PieChart.Data("Reserverings Autos" , simulatorLogic.getReservationCars()));
            pieChart = new PieChart();
            pieChart.setStyle("-fx-background-color: #333333;");
            pieChart.setData(data);
            pieChart.setAnimated(false);
            pieChart.setTitle("Aanwezige Autos");
            pieChart.setLegendSide(Side.BOTTOM);
            pieChart.setLabelsVisible(true);

            setScene(new Scene(pieChart));
        }

        private void updateData() {
            data.set(0, new PieChart.Data("Reguliere Autos", simulatorLogic.getNormalCars()));
            data.set(1, new PieChart.Data("Pashouder Autos" , simulatorLogic.getPassCars()));
            data.set(2, new PieChart.Data("Reserverings Autos" , simulatorLogic.getReservationCars()));
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }



    private class EarningsLineChartPanel extends JFXPanel {
        private XYChart.Series series;
        private LineChart lineChart;
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public EarningsLineChartPanel() {
            xAxis = new NumberAxis();
            xAxis.setLabel("Tijd in uren");
            yAxis = new NumberAxis();
            series = new XYChart.Series();
            series.setName("Biem");

            series.getData().add(new XYChart.Data(0, 0));

            lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            lineChart.setStyle("-fx-background-color: #333333;");

            lineChart.getData().add(series);

            lineChart.setAnimated(false);
            lineChart.setTitle("Inkomsten");
            lineChart.setLegendSide(Side.BOTTOM);

            setScene(new Scene(lineChart));
        }

        private void updateData() {
            if(simulatorLogic.getMinute() == 0) {
                int hour = simulatorLogic.getHour() + simulatorLogic.getDay() * 24;
                int total = simulatorLogic.getTotalEarnings();
                series.getData().add(new XYChart.Data(hour, total));
            }
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }
}