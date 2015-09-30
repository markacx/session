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

/**
 *
 * @author Mark Claassen, Joseph Foster
 * @param <T> initial value
 * @param <V> return value
 */
public class FXPanelFrame<T, V> {
	//anchoring frame
	private final JFrame frame = new JFrame();
	//dialog that will hold our FXML generated content
	private final JDialog dialog = new JDialog(frame);
	//this JFXPanel is stupid because it's purpose is to initialize the framework early enough for gradle
	//to be able to call UI methods.
	private final static JFXPanel ONLY_TO_INIT_PLATFORM = new JFXPanel();
	//configuration object
	private final T initialVal;
	//URLs to load the FXML layout, and the stylesheet
	private final URL fxmlURL, styleURL;
	private SwingStageDesignate<T, V> designate;
	private final Map<Object, Object> extraProperties = new HashMap<>();
	
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
		frame.setVisible(false);
	}
	public void setExtraProperty(Object key, Object val) {
		extraProperties.put(key, val);
	}
	public Object getExtraProperty(Object key) {
		return extraProperties.get(key);
	}
}
