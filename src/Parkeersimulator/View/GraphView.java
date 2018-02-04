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

public class GraphView extends AbstractView {

    private CarsPieChartPanel carsPieChartPanel;
    private EarningsLineChartPanel earningsLineChartPanel;
    private CarsAreaChartPanel carsAreaChartPanel;

    /**
     * Constructor voor objecten van klasse GraphView.
     */
    public GraphView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
        this.setBackground(new Color(51, 51, 51));

        carsPieChartPanel = new CarsPieChartPanel();
        earningsLineChartPanel = new EarningsLineChartPanel();
        carsAreaChartPanel = new CarsAreaChartPanel();

        add(carsPieChartPanel);
        add(earningsLineChartPanel);
        add(carsAreaChartPanel);

        setVisible(false);
    }

    @Override
    public void updateView() {
        carsPieChartPanel.update();
        earningsLineChartPanel.update();
        carsAreaChartPanel.update();
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
            pieChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");
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
            series.setName("Totale Inkomsten");

            series.getData().add(new XYChart.Data(0, 0));

            lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            lineChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");

            lineChart.getData().add(series);

            lineChart.setAnimated(false);
            lineChart.setTitle("Totale Inkomsten");
            lineChart.setLegendSide(Side.BOTTOM);

            setScene(new Scene(lineChart));
        }

        private void updateData() {
            if (simulatorLogic.getDay() == 0 && simulatorLogic.getHour() == 0 && simulatorLogic.getMinute() == 0) {
                series.getData().clear();
            }
            if(simulatorLogic.getMinute() == 0) {
                int hour = simulatorLogic.getHour() + simulatorLogic.getDay() * 24;
                double total = simulatorLogic.getTotalEarnings();
                series.getData().add(new XYChart.Data(hour, total));
            }
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }


    private class CarsAreaChartPanel extends JFXPanel {
        private XYChart.Series seriesAdHoc;
        private XYChart.Series seriesPass;
        private XYChart.Series seriesRes;
        private AreaChart areaChart;
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public CarsAreaChartPanel() {

            xAxis = new NumberAxis();
            xAxis.setLabel("Tijd in uren");
            yAxis = new NumberAxis();
            seriesAdHoc = new XYChart.Series();
            seriesAdHoc.setName("Regulier");
            seriesPass = new XYChart.Series();
            seriesPass.setName("Pashouders");
            seriesRes = new XYChart.Series();
            seriesRes.setName("Reserveringen");

            seriesRes.getData().add(new XYChart.Data(0, 0));
            seriesPass.getData().add(new XYChart.Data(0, 0));
            seriesAdHoc.getData().add(new XYChart.Data(0, 0));

            areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
            areaChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");

            areaChart.getData().addAll(seriesAdHoc, seriesPass, seriesRes);

            areaChart.setAnimated(false);
            areaChart.setTitle("Bezettingsgraad");
            areaChart.setLegendSide(Side.BOTTOM);

            setScene(new Scene(areaChart));
        }

        private void updateData() {
            if (simulatorLogic.getDay() == 0 && simulatorLogic.getHour() == 0 && simulatorLogic.getMinute() == 0) {
                seriesAdHoc.getData().clear();
                seriesPass.getData().clear();
                seriesRes.getData().clear();
            }
            if(simulatorLogic.getMinute() == 0) {
                    int hour = simulatorLogic.getHour() + simulatorLogic.getDay() * 24;
                    int adHoc = simulatorLogic.getNormalCars();
                    int pass = simulatorLogic.getPassCars();
                    int res = simulatorLogic.getReservationCars();
                    seriesRes.getData().add(new XYChart.Data(hour, res));
                    seriesPass.getData().add(new XYChart.Data(hour, pass + res));
                    seriesAdHoc.getData().add(new XYChart.Data(hour, adHoc + pass + res));
                }
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }
    }
}