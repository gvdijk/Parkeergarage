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

public class QueueView extends AbstractView {

    private QueuePieChartPanel queuePieChartPanel;
    private QueueLineChartPanel queueLineChartPanel;

    /**
     * Constructor voor objecten van klasse QueueView.
     */
    public QueueView (SimulatorLogic simulatorLogic) {
        super (simulatorLogic);
        this.setBackground(new Color(51, 51, 51));

        queuePieChartPanel = new QueuePieChartPanel();
        queueLineChartPanel = new QueueLineChartPanel();

        add(queuePieChartPanel);
        add(queueLineChartPanel);

        setVisible(false);
    }

    @Override
    public void updateView() {
        queuePieChartPanel.update();
        queueLineChartPanel.update();
    }

    private class QueuePieChartPanel extends JFXPanel {
        private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        private PieChart pieChart;

        public QueuePieChartPanel() {
            data.add(0, new PieChart.Data("Entrance car queue", simulatorLogic.getEntranceCarQueue().carsInQueue()));
            data.add(1, new PieChart.Data("Entrance pass queue", simulatorLogic.getEntrancePassQueue().carsInQueue()));
            data.add(2, new PieChart.Data("Payment car queue", simulatorLogic.getPaymentCarQueue().carsInQueue()));
            data.add(3, new PieChart.Data("Exit car queue", simulatorLogic.getExitCarQueue().carsInQueue()));
            pieChart = new PieChart();
            pieChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");
            pieChart.setData(data);
            pieChart.setAnimated(false);
            pieChart.setTitle("Aanwezige Autos");
            pieChart.setLegendSide(Side.BOTTOM);
            pieChart.setLabelsVisible(true);

            setScene(new Scene(pieChart));
        }

        private void updateData(){
            data.set(0, new PieChart.Data("Entrance car queue", simulatorLogic.getEntranceCarQueue().carsInQueue()));
            data.set(1, new PieChart.Data("Entrance pass queue", simulatorLogic.getEntrancePassQueue().carsInQueue()));
            data.set(2, new PieChart.Data("Payment car queue", simulatorLogic.getPaymentCarQueue().carsInQueue()));
            data.set(3, new PieChart.Data("Exit car queue", simulatorLogic.getExitCarQueue().carsInQueue()));
        }

        public void update() { Platform.runLater(() -> updateData()); }
    }

    private class QueueLineChartPanel extends JFXPanel {
        private XYChart.Series seriesEntrance;
        private XYChart.Series seriesEntrancePass;
        private XYChart.Series seriesPayment;
        private XYChart.Series seriesExit;
        private AreaChart areaChart;
        private NumberAxis xAxis;
        private NumberAxis yAxis;
        private int entranceThisHour = 0;
        private int entrancePassThisHour = 0;
        private int paymentThisHour = 0;
        private int exitThisHour = 0;

        public QueueLineChartPanel(){
            xAxis = new NumberAxis();
            xAxis.setLabel("Tijd in uren");
            yAxis = new NumberAxis();
            yAxis.setLabel("Auto's in de rij per uur");
            seriesEntrance = new XYChart.Series();
            seriesEntrance.setName("Entrance queue");
            seriesEntrancePass = new XYChart.Series();
            seriesEntrancePass.setName("Entrance pass queue");
            seriesPayment = new XYChart.Series();
            seriesPayment.setName("Payment queue");
            seriesExit = new XYChart.Series();
            seriesExit.setName("Exit queue");

            seriesEntrance.getData().add(new XYChart.Data(0, 0));
            seriesEntrancePass.getData().add(new XYChart.Data(0, 0));
            seriesPayment.getData().add(new XYChart.Data(0, 0));
            seriesExit.getData().add(new XYChart.Data(0, 0));

            areaChart = new AreaChart(xAxis, yAxis);
            areaChart.setStyle("-fx-background-color: #333333; -fx-text-fill: #FFFFFF;");

            areaChart.getData().addAll(seriesEntrance, seriesEntrancePass, seriesPayment, seriesExit);

            areaChart.setAnimated(false);
            areaChart.setTitle("Rijen");
            areaChart.setLegendSide(Side.BOTTOM);

            setScene(new Scene(areaChart));
        }
        private void updateData() {
            if (simulatorLogic.getDay() == 0 && simulatorLogic.getHour() == 0 && simulatorLogic.getMinute() == 0) {
                seriesEntrance.getData().clear();
                seriesEntrancePass.getData().clear();
                seriesPayment.getData().clear();
                seriesExit.getData().clear();
            }

            entranceThisHour += simulatorLogic.getEntranceCarQueue().carsInQueue();
            entrancePassThisHour += simulatorLogic.getEntrancePassQueue().carsInQueue();
            paymentThisHour += simulatorLogic.getPaymentCarQueue().carsInQueue();
            exitThisHour += simulatorLogic.getExitCarQueue().carsInQueue();

            if(simulatorLogic.getMinute() == 0) {
                int hour = simulatorLogic.getHour() + simulatorLogic.getDay() * 24;
                seriesEntrance.getData().add(new XYChart.Data(hour, entranceThisHour ));
                seriesEntrancePass.getData().add(new XYChart.Data(hour, entrancePassThisHour + entranceThisHour ));
                seriesPayment.getData().add(new XYChart.Data(hour, paymentThisHour + entrancePassThisHour + entranceThisHour ));
                seriesExit.getData().add(new XYChart.Data(hour, exitThisHour + paymentThisHour + entrancePassThisHour + entranceThisHour ));

                entranceThisHour = 0;
                entrancePassThisHour = 0;
                paymentThisHour = 0;
                exitThisHour = 0;
            }
        }

        public void update() {
            Platform.runLater(() -> updateData());
        }

    }

}