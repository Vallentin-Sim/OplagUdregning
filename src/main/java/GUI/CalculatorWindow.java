package GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Calculation;

import java.util.Objects;

public class CalculatorWindow extends Application {
    private final Calculation calculation = Controller.createCalculationObject();
    private final LanguageSettings languageSettings = LanguageSettings.getInstance();
    private CalculationState currentCalculationState;
    private TextField currentOplagTextField;

    public CalculatorWindow() {

    }

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.getStyleClass().add("calculator-grid");

        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setMinWidth(240);
        labelColumn.setPercentWidth(50);

        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        inputColumn.setMinWidth(240);
        inputColumn.setPercentWidth(50);

        grid.getColumnConstraints().addAll(labelColumn, inputColumn);

        Text textOplagStk = new Text();
        TextField txfOplagStk = new TextField();
        Text textOplagMeter = new Text();
        TextField txfOplagMeter = new TextField();
        Text textEtiketHoejde = new Text();
        TextField txfEtiketHoejde = new TextField();
        Text textMellemspildRundt = new Text();
        TextField txfMellemspildRundt = new TextField();
        Text textEtiketterTvaers = new Text();
        TextField txfEtiketterTvaers = new TextField();
        Text textBeregnetValue = new Text();
        TextField txfBeregnetValue = new TextField();
        txfBeregnetValue.setEditable(false);

        addStyleClass(
                "label-text",
                textOplagStk,
                textOplagMeter,
                textEtiketHoejde,
                textMellemspildRundt,
                textEtiketterTvaers,
                textBeregnetValue
        );

        Text titleText = new Text();
        titleText.getStyleClass().add("title-text");

        Button buttonLanguage = new Button();
        buttonLanguage.getStyleClass().add("language-button");

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        HBox titleBar = new HBox(10, titleText, titleSpacer, buttonLanguage);
        titleBar.getStyleClass().add("title-bar");
        grid.add(titleBar, 0, 0, 2, 1);

        Button buttonOplag = new Button();
        buttonOplag.getStyleClass().add("mode-button");
        grid.add(buttonOplag, 0, 1);

        Button buttonMeter = new Button();
        buttonMeter.getStyleClass().add("mode-button");
        grid.add(buttonMeter, 1, 1);

        Button buttonBeregn = new Button();
        buttonBeregn.getStyleClass().add("primary-button");

        setMaxWidth(
                txfOplagStk,
                txfOplagMeter,
                txfEtiketHoejde,
                txfMellemspildRundt,
                txfEtiketterTvaers,
                txfBeregnetValue,
                buttonOplag,
                buttonMeter,
                buttonBeregn
        );
        buttonLanguage.setMinSize(40, 32);
        buttonLanguage.setPrefSize(40, 32);
        buttonLanguage.setMaxSize(40, 32);

        txfBeregnetValue.getStyleClass().add("result-field");

        txfOplagStk.setPromptText("...");
        txfOplagMeter.setPromptText("...");
        txfEtiketHoejde.setPromptText("...");
        txfMellemspildRundt.setPromptText("...");
        txfEtiketterTvaers.setPromptText("...");

        CalculationState pieceState = new PieceCalculationState();
        CalculationState meterState = new MeterCalculationState();

        buttonOplag.setOnAction(event -> {
            currentCalculationState = pieceState;
            currentOplagTextField = txfOplagStk;
            txfBeregnetValue.clear();
            clearCalculationFields(grid);
            addCalculationFields(
                    grid,
                    textOplagStk,
                    txfOplagStk,
                    textEtiketHoejde,
                    txfEtiketHoejde,
                    textMellemspildRundt,
                    txfMellemspildRundt,
                    textEtiketterTvaers,
                    txfEtiketterTvaers,
                    textBeregnetValue,
                    txfBeregnetValue,
                    buttonBeregn
            );
        });

        buttonMeter.setOnAction(event -> {
            currentCalculationState = meterState;
            currentOplagTextField = txfOplagMeter;
            txfBeregnetValue.clear();
            clearCalculationFields(grid);
            addCalculationFields(
                    grid,
                    textOplagMeter,
                    txfOplagMeter,
                    textEtiketHoejde,
                    txfEtiketHoejde,
                    textMellemspildRundt,
                    txfMellemspildRundt,
                    textEtiketterTvaers,
                    txfEtiketterTvaers,
                    textBeregnetValue,
                    txfBeregnetValue,
                    buttonBeregn
            );
        });

        buttonLanguage.setOnAction(event -> {
            languageSettings.toggleLanguage();
            updateLanguage(
                    titleText,
                    textOplagStk,
                    textOplagMeter,
                    textEtiketHoejde,
                    textMellemspildRundt,
                    textEtiketterTvaers,
                    textBeregnetValue,
                    buttonOplag,
                    buttonMeter,
                    buttonBeregn,
                    buttonLanguage
            );
        });

        buttonBeregn.setOnAction(event -> {
            try {
                if (currentCalculationState == null || currentOplagTextField == null) {
                    txfBeregnetValue.setText(getChooseCalculationText());
                    return;
                }

                double oplag = parseDouble(currentOplagTextField);
                double etiketHoejde = parseDouble(txfEtiketHoejde);
                double mellemspildRundt = parseDouble(txfMellemspildRundt);
                double etiketterTvaers = parseDouble(txfEtiketterTvaers);

                currentCalculationState.calculate(
                        calculation,
                        oplag,
                        etiketHoejde,
                        mellemspildRundt,
                        etiketterTvaers
                );

                txfBeregnetValue.setText(String.format("%.2f", calculation.getCalculatedValue()));
            } catch (NumberFormatException e) {
                txfBeregnetValue.setText(getInvalidNumberText());
            }
        });

        updateLanguage(
                titleText,
                textOplagStk,
                textOplagMeter,
                textEtiketHoejde,
                textMellemspildRundt,
                textEtiketterTvaers,
                textBeregnetValue,
                buttonOplag,
                buttonMeter,
                buttonBeregn,
                buttonLanguage
        );

        Scene scene = new Scene(grid, 590, 380);
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/calculator.css")
        ).toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(590);
        stage.setMinHeight(380);
        stage.setTitle("Oplag beregning");
        stage.show();
    }

    private void updateLanguage(
            Text titleText,
            Text textOplagStk,
            Text textOplagMeter,
            Text textEtiketHoejde,
            Text textMellemspildRundt,
            Text textEtiketterTvaers,
            Text textBeregnetValue,
            Button buttonOplag,
            Button buttonMeter,
            Button buttonBeregn,
            Button buttonLanguage
    ) {
        if (languageSettings.getLanguage() == Language.DANISH) {
            titleText.setText("Beregn oplag");
            textOplagStk.setText("Indtast Oplag med stk.");
            textOplagMeter.setText("Indtast Oplag med meter.");
            textEtiketHoejde.setText("Indtast Etiket højde. (mm)");
            textMellemspildRundt.setText("Indtast Mellemspild rundt. (mm)");
            textEtiketterTvaers.setText("Indtast Etiketter tværs. (mm)");
            textBeregnetValue.setText("Beregnet værdi. (m/stk)");
            buttonOplag.setText("Beregn med stk.");
            buttonMeter.setText("Beregn med meter");
            buttonBeregn.setText("Beregn");
            buttonLanguage.setText("🇬🇧");
            buttonLanguage.setTooltip(new Tooltip("Switch to English"));
        } else {
            titleText.setText("Calculate quantity");
            textOplagStk.setText("Enter quantity in pieces.");
            textOplagMeter.setText("Enter quantity in meters.");
            textEtiketHoejde.setText("Enter label height. (mm)");
            textMellemspildRundt.setText("Enter spacing around. (mm)");
            textEtiketterTvaers.setText("Enter labels across. (mm)");
            textBeregnetValue.setText("Calculated value. (m/pcs)");
            buttonOplag.setText("Calculate by pieces");
            buttonMeter.setText("Calculate by meters");
            buttonBeregn.setText("Calculate");
            buttonLanguage.setText("🇩🇰");
            buttonLanguage.setTooltip(new Tooltip("Skift til dansk"));
        }
    }

    private String getInvalidNumberText() {
        return languageSettings.getLanguage() == Language.DANISH
                ? "Ugyldigt tal"
                : "Invalid number";
    }

    private String getChooseCalculationText() {
        return languageSettings.getLanguage() == Language.DANISH
                ? "Vælg type"
                : "Choose type";
    }

    private double parseDouble(TextField textField) {
        return Double.parseDouble(textField.getText().trim().replace(",", "."));
    }

    private void addStyleClass(String styleClass, Node... nodes) {
        for (Node node : nodes) {
            node.getStyleClass().add(styleClass);
        }
    }

    private void setMaxWidth(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof TextField textField) {
                textField.setMaxWidth(Double.MAX_VALUE);
            } else if (node instanceof Button button) {
                button.setMaxWidth(Double.MAX_VALUE);
            }
        }
    }

    private void clearCalculationFields(GridPane grid) {
        grid.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex >= 2;
        });
    }

    private void addCalculationFields(
            GridPane grid,
            Node textOplag,
            Node txfOplag,
            Node textEtiketHoejde,
            Node txfEtiketHoejde,
            Node textMellemspildRundt,
            Node txfMellemspildRundt,
            Node textEtiketterTvaers,
            Node txfEtiketterTvaers,
            Node textBeregnetValue,
            Node txfBeregnetValue,
            Node buttonBeregn
    ) {
        grid.add(textOplag, 0, 2);
        grid.add(txfOplag, 1, 2);
        grid.add(textEtiketHoejde, 0, 3);
        grid.add(txfEtiketHoejde, 1, 3);
        grid.add(textMellemspildRundt, 0, 4);
        grid.add(txfMellemspildRundt, 1, 4);
        grid.add(textEtiketterTvaers, 0, 5);
        grid.add(txfEtiketterTvaers, 1, 5);
        grid.add(textBeregnetValue, 0, 6);
        grid.add(txfBeregnetValue, 1, 6);
        grid.add(buttonBeregn, 1, 7);
    }

}
