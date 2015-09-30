package net.ocie.javaone2015.build.purefx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Mark Claassen, Joseph Foster
 */
public class PureFXGetSSHInfoController implements Initializable {
	//there are the fields that would be necessary to build up an SSHInfo configuration object,
	//but this example is to show why you can't directly use javafx (more than once)
	@FXML
	private TextField keyFileTextField;
	@FXML
	private TextField hostField;
	@FXML
	private TextField userField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button okButton;
	@FXML
	private PasswordField reenterField;
	@FXML
	private TextField portField;
	
	public void setInitialConfig(String config) {
		//if we were going to use a config object, it would be here
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		okButton.setOnAction((ActionEvent event) -> {
			//this where would pass the configuration object back
			//that had been gathered from all the @FXML injected objects
			PureFXGetSSHInfo.setConfigObj("CONFIGURED");
			//the only way we would find to return to the gradle build
			Platform.exit();
		});
	}
}
