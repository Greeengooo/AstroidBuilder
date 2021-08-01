package sample;
import com.sun.glass.ui.Pixels;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
   private ScatterChart<Double, Double> lineGraph;

    @FXML
    private TextField textField_UPPER_RANGE;

    @FXML
    private TextField textField_LOWER_RANGE;

    @FXML
    private TextField textField_STEP;

    @FXML
    private TextField textField_P;

    @FXML
    private TextField textField_Zoom;

    @FXML
    private Label label_UPPER_RANGE;

    @FXML
    private Label label_LOWER_RANGE;

    @FXML
    private Label label_STEP;

    @FXML
    private Label label_P;

    @FXML
    private Button Draw;

    @FXML
    private Button SaveToFile;

    @FXML
    private Button SaveImg;

    @FXML
    private Button Clear;

    @FXML
    private Button Open;

    @FXML
    private Slider Slider;

    @FXML
    private NumberAxis axisX;

    @FXML
    private NumberAxis axisY;


    private MyGraph graph;

    private String inputLowerRange;
    private int LOWER_RANGE;

    private String inputUpperRange;
    private int UPPER_RANGE;

    private String inputStep;
    private int STEP;

    private String inputP;
    private int P;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Slider.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                axisX.setLowerBound((-1.0) * (Double)newValue);
                axisY.setUpperBound(1.0 * (Double)newValue);

                axisY.setLowerBound((-1.0) * (Double)newValue);
                axisX.setUpperBound(1.0 * (Double)newValue);

                textField_Zoom.setText(Double.toString(newValue.doubleValue()));
            }
        });
    }

    @FXML
    public void btn(ActionEvent actionEvent) {
        graph = new MyGraph(lineGraph, LOWER_RANGE, UPPER_RANGE, STEP, P);
        plotLine();
    }

    @FXML
    private void handleClearButtonAction(final ActionEvent event) {
       clear();
    }

    @FXML
    private void handleOpenButtonAction(final ActionEvent event) throws IOException {
        open();
    }

    @FXML
    private void handleEnterButtonAction(final ActionEvent event) {
        inputLowerRange = textField_LOWER_RANGE.getText();
        LOWER_RANGE = Integer.parseInt(inputLowerRange);

        inputUpperRange = textField_UPPER_RANGE.getText();
        UPPER_RANGE = Integer.parseInt(inputUpperRange);

        inputStep = textField_STEP.getText();
        STEP = Integer.parseInt(inputStep);

        inputP = textField_P.getText();
        P = Integer.parseInt(inputP);

        if (LOWER_RANGE < 0 || UPPER_RANGE < 0 || UPPER_RANGE > 360 || LOWER_RANGE > 360 || STEP < 0 || STEP > 360 || P < 0 || P > 360)
            throw new IllegalArgumentException("ILLEGAL FORMAT");

        label_LOWER_RANGE.setText("Lower Range: " + inputLowerRange);
        label_UPPER_RANGE.setText("Upper Range: " + inputUpperRange);
        label_STEP.setText("Step: " + inputStep);
        label_P.setText("P: " + inputP);
    }

    @FXML
    private void handleSaveToFileButtonAction(final ActionEvent event) {
        inputLowerRange = textField_LOWER_RANGE.getText();
        LOWER_RANGE = Integer.parseInt(inputLowerRange);

        inputUpperRange = textField_UPPER_RANGE.getText();
        UPPER_RANGE =  Integer.parseInt(inputUpperRange);

        inputStep = textField_STEP.getText();
        STEP =  Integer.parseInt(inputStep);

        inputP = textField_P.getText();
        P = Integer.parseInt(inputP);

         if (LOWER_RANGE < 0 || UPPER_RANGE < 0 || UPPER_RANGE > 360 || LOWER_RANGE > 360 || STEP < 0 || STEP > 360 || P < 0 || P > 360)
            throw new IllegalArgumentException("ILLEGAL FORMAT");

        Saver saver = new Saver(LOWER_RANGE, UPPER_RANGE, STEP, P);
        saver.save();
    }

    @FXML
    private void handleSaveToImgButtonAction(final ActionEvent event) {
        saveAsPng();
    }

    @FXML
    public void saveAsPng() {
        WritableImage image = lineGraph.snapshot(new SnapshotParameters(), null);
        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void plotLine() {
        if (lineGraph.isVisible()) {
            graph.plotLine();
        }
    }

    private void clear() {
        if (lineGraph.isVisible()) {
            graph.clear();
        }
    }

    private void open() throws IOException {
        String inputFile = "";
        BufferedReader reader = new BufferedReader(new FileReader("Astroid.txt"));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            inputFile = stringBuilder.toString();
        } finally {
            reader.close();
        }
        String[] parts = inputFile.split("#");
        LOWER_RANGE = Integer.parseInt(parts[0]);
        UPPER_RANGE =  Integer.parseInt(parts[1]);
        STEP =  Integer.parseInt(parts[2]);
        P = Integer.parseInt(parts[3]);

        label_LOWER_RANGE.setText("Lower Range: " + LOWER_RANGE);
        label_UPPER_RANGE.setText("Upper Range: " + UPPER_RANGE);
        label_STEP.setText("Step: " + STEP);
        label_P.setText("P: " +   P);

        textField_LOWER_RANGE.setText(String.valueOf(LOWER_RANGE));
        textField_UPPER_RANGE.setText(String.valueOf(UPPER_RANGE));
        textField_STEP.setText(String.valueOf(STEP));
        textField_P.setText(String.valueOf(P));
    }

}
