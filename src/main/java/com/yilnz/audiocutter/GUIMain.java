package com.yilnz.audiocutter;

import com.yilnz.audiocutter.gui.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class GUIMain extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		// Create the FXMLLoader
		FXMLLoader loader = new FXMLLoader();
		// Path to the FXML File
		String fxmlDocPath = "main.fxml";
		//FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

		// Create the Pane and all Details
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Pane root = (Pane) loader.load(contextClassLoader.getResourceAsStream(fxmlDocPath));

		// Create the Scene
		Scene scene = new Scene(root);
		// Set the Scene to the Stage
		stage.setScene(scene);
		// Set the Title to the Stage
		stage.setTitle("音频剪切工具");
		// Display the Stage
		stage.show();


		stage.setOnCloseRequest(e->{
			GUIController guiController = loader.getController();
			final Map<String, String> storeValues = guiController.getStoreValues();
			Properties p = new Properties();
			p.putAll(storeValues);
			try {
				p.store(new OutputStreamWriter(new FileOutputStream("audiocutter.properties"),StandardCharsets.UTF_8), "音频剪切工具配置文件");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
}
