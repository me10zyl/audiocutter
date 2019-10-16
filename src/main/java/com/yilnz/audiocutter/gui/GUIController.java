package com.yilnz.audiocutter.gui;

import com.yilnz.audiocutter.AudioCutter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    ChoiceBox<String> choice1;
    @FXML
    TextField input1;
    @FXML
    TextField input2;
    @FXML
    TextField input3;

    public Map<String, String> getStoreValues(){
        final HashMap<String, String> str = new HashMap<>();
        String cutDirection = choice1.getValue();
        String inputDir = input1.getText();
        String outDir = input2.getText();
        String sec = input3.getText();
        str.put("inputDir", inputDir);
        str.put("outDir", outDir);
        str.put("cutDirection", cutDirection);
        str.put("sec", sec);
        return str;
    }

    public void startProcess(){
        try {
            String cutDirection = (String) choice1.getValue();
            String inputDir = input1.getText();
            String outDir = input2.getText();
            String sec = input3.getText();
            if (!(inputDir != null && !inputDir.equals("") && outDir != null && !outDir.equals("") && sec != null && !sec.equals(""))) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "请填写所有输入框", ButtonType.YES);
                alert.showAndWait();
                return;
            }
            if (cutDirection.equals("剪切前面")) {
                sec = "-" + sec;
            }
            AudioCutter.cutAudio(inputDir, outDir, Float.parseFloat(sec));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "处理完毕", ButtonType.YES);
            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "出错：" + e.getMessage(), ButtonType.YES);
            alert.showAndWait();
            return;
        }
    }

    public void browseInput(){
        final File file = showBrowseDir();
        if(file != null){
            input1.setText(file.getPath());
        }
    }

    public void browseOutput(){
        final File file = showBrowseDir();
        if(file != null){
            input2.setText(file.getPath());
        }
    }

    private File showBrowseDir() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("选择文件夹");
        final File file = dirChooser.showDialog(null);
        return file;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice1.setItems(FXCollections.observableArrayList("剪切前面", "剪切后面"));
        choice1.setValue("剪切后面");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("audiocutter.properties"));
            input1.setText(properties.getProperty("inputDir"));
            input2.setText(properties.getProperty("outDir"));
            input3.setText(properties.getProperty("sec"));
            choice1.setValue(properties.getProperty("cutDirection"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
