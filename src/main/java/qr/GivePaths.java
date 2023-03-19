package qr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GivePaths extends Application {

    private TextField excelPathField;
    private TextField qrCodesPathField;
    private static String excelPath;
    private static String safeExcelPath;

    private ArrayList<String> paths = new ArrayList<>(2);

    @Override
    public void start(Stage primaryStage) throws Exception {

        Label excelPathLabel = new Label("Введите путь до вашей Excel таблицы:");
        excelPathField = new TextField();
        Label qrCodesPathLabel = new Label("Введите путь до папки куда сохранить QR-коды пользователей:");
        qrCodesPathField = new TextField();

        javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Сохранить");
        saveButton.setOnAction(event -> {
            excelPath = excelPathField.getText();
            safeExcelPath = qrCodesPathField.getText();
            File file1 = new File(excelPath);
            File file2 = new File(safeExcelPath);
            if (!file1.exists() || !file2.exists()) {
                primaryStage.close();
                Main.main4();
            } else {
                String path = GivePaths.getSafeExcelPath();
                if (!Files.exists(Paths.get(path))) {
                    try {
                        Files.createDirectory(Paths.get(path));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(7);
                    }
                }
                ProgressDialog progressDialog = new ProgressDialog();
                progressDialog.show();
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        Main.main2();
                        Platform.runLater(() -> {
                            progressDialog.close();
                            Main.main3();
                        });
                        return null;
                    }
                };
                new Thread(task).start();
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, excelPathLabel);
        gridPane.addRow(1, excelPathField);
        gridPane.addRow(2, qrCodesPathLabel);
        gridPane.addRow(3, qrCodesPathField);

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, saveButton);

        Scene scene = new Scene(root, 400, 250);

        primaryStage.setTitle("Ввод данных пользователя");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void nextPanel(Stage primaryStage) throws Exception {
        primaryStage.setTitle("QR Code Generator");

        Label messageLabel = new Label("");

        Button doneButton = new Button("Готово");
        doneButton.setOnAction(e -> {
            primaryStage.close();
            System.exit(0);
        });

        Button copyButton = new Button("Скопировать путь");
        copyButton.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(safeExcelPath);
            clipboard.setContent(content);
        });

        HBox buttonBox = new HBox(10, doneButton, copyButton);

        VBox vBox = new VBox(10, messageLabel, buttonBox);

        vBox.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        String safeExcelPath = getSafeExcelPath().substring(0, getSafeExcelPath().length() - 1);
        messageLabel.setText("Готово! Пользовательские QR-коды находятся по пути:\n" + safeExcelPath);
    }

    private class ProgressDialog extends Stage {
        private ProgressDialog() {
            initOwner(getOwner());
            setResizable(false);
            setTitle("Загрузка...");
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setProgress(-1.0f);
            setScene(new Scene(progressIndicator, 200, 200));
        }
    }

    public static void except(Stage primaryStage) {
        Text text = new Text("Данных путей не существует");
        Button button = new Button("Закрыть");

        button.setOnAction(event -> {
            System.exit(0);
        });

        VBox root = new VBox(text, button);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Ошибка");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String getExcelPath() {
        return excelPath;
    }

    public static String getSafeExcelPath() {
        return safeExcelPath + "\\User QR-codes\\";
    }

}
