package Parkeersimulator.View;

import Parkeersimulator.Model.SimulatorLogic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import java.awt.*;

public class ReservationView extends AbstractView {

    private OnTimePieChartPanel onTimePieChartPanel;
    private ReservationsLineChartPanel reservationsLineChartPanel;

    /**
     * Constructor voor objecten van klasse GraphView.
     */
    public ReservationView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
        this.setBackground(new Color(51, 51, 51));

        onTimePieChartPanel = new OnTimePieChartPanel();
        reservationsLineChartPanel = new ReservationsLineChartPanel();

        add(onTimePieChartPanel);
        add(reservationsLineChartPanel);

        setVisible(false);
    }

    @Override
    public void updateView() {
        onTimePieChartPanel.update();
        reservationsLineChartPanel.update();
    }

    @Override
    public String getName() {
        return "ReservationView";
    }

    private class OnTimePieChartPanel extends JFXPanel {
        private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        private PieChart pieChart;

        public OnTimePieChartPanel() {
            data.add(0, new PieChart.Data("Op tijd", simulatorLogic.getOnTimeRes()));
            data.add(1, new PieChart.Data("Te vroeg" , simulatorLogic.getTooEarlyRes()));
            data.add(2, new PieChart.Data("Te laat" , simulatorLogic.getTooLateRes()));
            pieChart = new PieChart();
            pieChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");
            pieChart.setData(data);
            pieChart.setAnimated(false);
            pieChart.setTitle("Reservering Aankomsten");
            pieChart.setLegendSide(Side.BOTTOM);
            pieChart.setLabelsVisible(true);

            setScene(new Scene(pieChart));
        }

        private void updateData() {
            data.set(0, new PieChart.Data("Op tijd", simulatorLogic.getOnTimeRes()));
            data.set(1, new PieChart.Data("Te vroeg" , simulatorLogic.getTooEarlyRes()));
            data.set(2, new PieChart.Data("Te laat" , simulatorLogic.getTooLateRes()));
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }

    private class ReservationsLineChartPanel extends JFXPanel {
        private XYChart.Series series;
        private LineChart lineChart;
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public ReservationsLineChartPanel() {
            xAxis = new NumberAxis();
            xAxis.setLabel("Tijd in uren");
            yAxis = new NumberAxis();
            series = new XYChart.Series();
            series.setName("Totale Inkomsten");

            series.getData().add(new XYChart.Data(0, 0));

            lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            lineChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");

            lineChart.getData().add(series);

            lineChart.setAnimated(false);
            lineChart.setTitle("Reserveringen per uur");
            lineChart.setLegendSide(Side.BOTTOM);

            setScene(new Scene(lineChart));
        }

        private void updateData() {
            if (simulatorLogic.getDay() == 0 && simulatorLogic.getHour() == 0 && simulatorLogic.getMinute() == 0) {
                series.getData().clear();
            }
            if(simulatorLogic.getMinute() == 0) {
                int hour = simulatorLogic.getHour() + simulatorLogic.getDay() * 24;
                int value = simulatorLogic.getReservationsThisHour();
                series.getData().add(new XYChart.Data(hour, value));
            }
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }
}