package net.ocie.javaone2015.build.purefx;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gradle.api.GradleException;

/**
 *
 * @author Mark Claassen, Joseph Foster
 */
public class PureFXGetSSHInfo extends Application {
	private static String configObj;
	private static String initialConfig;

	public static void setConfigObj(String configObj) {
		PureFXGetSSHInfo.configObj = configObj;
	}
	public static String openWindow(String initial) {
		initialConfig = initial;
		launch(new String[0]);
		if (configObj == null)
			throw new GradleException("Cancelled by user.");
		return configObj;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("sshinfo.fxml"));
			Parent root = loader.load();
//
			PureFXGetSSHInfoController controller = (PureFXGetSSHInfoController) loader.getController();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("sshinfo.css").toExternalForm());
			controller.setInitialConfig(initialConfig);
//
			primaryStage.setTitle("GET SSH INFO");
			primaryStage.setScene(scene);
			primaryStage.show();

		}
		catch (IOException ex) {
			Logger.getLogger(PureFXGetSSHInfo.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
