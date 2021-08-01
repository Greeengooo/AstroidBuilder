package sample;
import javafx.scene.chart.ScatterChart;



public class MyGraph {


    private ScatterChart<Double, Double> graph;
    private double lower_range;
    private double upper_range;
    private double t;
    private double step;
    private int p;

    public MyGraph(final ScatterChart<Double, Double> graph, final int lower_range, final int upper_range, final int step, final int p) {
        this.graph = graph;
        this.lower_range = Math.toRadians(lower_range);
        this.upper_range = Math.toRadians(upper_range);
        this.step = Math.toRadians(step);
        this.p = p;
    }

    public void plotLine() {
        final ScatterChart.Series<Double, Double> series = new ScatterChart.Series<Double, Double>();

        for (t = lower_range; t <= upper_range; t = t + step) {
            plotPoint(p * Math.pow(Math.cos(t), 3), p * Math.pow(Math.sin(t), 3), series);
        }
        graph.getData().add(series);
    }

    public void plotPoint(final double x, final double y, final ScatterChart.Series<Double, Double> series) {
        series.getData().add(new ScatterChart.Data<Double, Double>(x, y));
    }

    public void clear() {
        graph.getData().clear();
    }
}
