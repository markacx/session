package net.ocie.javaone2015.build.fx;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JDialog;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ocJLFoster
 * @param <T>
 */
public class FXPanelFrame<T, V> {
	private final JFrame frame = new JFrame();
	private final JDialog dialog = new JDialog(frame);
	private JFXPanel stupid = new JFXPanel();
	private final T initialVal;
	private final URL fxmlURL, styleURL;
	private SwingStageDesignate<T, V> designate;
	private Map<Object, Object> extraProperties = new HashMap<>();
	public FXPanelFrame(T initialVal, URL fxmlURL, URL styleURL) {
		this.initialVal = initialVal;
		this.fxmlURL = fxmlURL;
		this.styleURL = styleURL;
	}
	public void show() {
		try {
			FutureTask<Boolean> task = new FutureTask<Boolean>(new Runnable() {
				@Override
				public void run() {
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					initFX();
				}
			}, false);
			Platform.runLater(task);
			task.get();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
	public V getReturnValue() {
		return designate.getReturnValue();
	}
	private void initFX() {
		final Scene scene = createScene();
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setScene(scene);
		dialog.setContentPane(fxPanel);
		frame.setVisible(true);
		dialog.setSize((int)scene.getWidth() + 20, (int)scene.getHeight() + 30);
		dialog.setVisible(true);
	}
	private Scene createScene() {
		try {
			FXMLLoader loader = new FXMLLoader(fxmlURL);
			loader.setClassLoader(getClass().getClassLoader());
			Parent root = loader.load();
			FXPanelController controller = (FXPanelController) loader.getController();
			designate = new SwingStageDesignate<>(initialVal, this, extraProperties);
			controller.setDesignate(designate);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(styleURL.toExternalForm());
			return scene;
		}
		catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
	public void close() {
//		dialog.setVisible(false);
//		dialog.dispose();
//		frame.dispose();
		frame.setVisible(false);
	}
	public void setExtraProperty(Object key, Object val) {
		extraProperties.put(key, val);
	}
	public Object getExtraProperty(Object key) {
		return extraProperties.get(key);
	}
}
