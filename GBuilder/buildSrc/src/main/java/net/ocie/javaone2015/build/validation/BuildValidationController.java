package net.ocie.javaone2015.build.validation;

import com.sun.javafx.application.PlatformImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import net.ocie.javaone2015.build.fx.*;

/**
 *
 * @author ocJLFoster
 */
public class BuildValidationController implements Initializable, FXPanelController<String,ValidationResponse> {
	@FXML
	private Label titleLabel;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField reentryField;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	private SwingStageDesignate<String, ValidationResponse> designate;
	private BooleanBinding passwordValidBinding;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PlatformImpl.setImplicitExit(false);	
		applyValidation(passwordField, false);
		applyValidation(reentryField, false);
		passwordValidBinding = Bindings.createBooleanBinding(() -> {
			String password = passwordField.getText();
			String reentry = reentryField.getText();
			return password != null && !password.isEmpty() && reentry != null && password.equals(reentry);
		}, passwordField.textProperty(), reentryField.textProperty());
		okButton.disableProperty().bind(passwordValidBinding.not());
		passwordValidBinding.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			applyValidation(passwordField, newValue);
			applyValidation(reentryField, newValue);
		});
	}
	@Override
	public void setDesignate(SwingStageDesignate<String, ValidationResponse> designate) {
		this.designate = designate;
		titleLabel.setText((String) designate.getExtraProperty("title"));
	}
	@FXML
	void ok(ActionEvent event) {
		if (!okButton.isDisabled()) {
			designate.setReturnValue(ValidationResponse.createValid(passwordField.getText()));
			designate.close();
		}
	}
	@FXML
	void cancel(ActionEvent event) {
		designate.setReturnValue(ValidationResponse.createInvalid());
		designate.close();
	}
	@FXML
	void reenterAction(ActionEvent event) {
		ok(null);
	}
	@FXML
	void passwordAction(ActionEvent event) {
		ok(null);
	}
	@FXML
	void keyTyped(KeyEvent event) {
//		Platform.runLater(() -> {
//			validate();
//		});

	}
	/**
	 * Modifies the style classes of the node to show input validity
	 * @param node item to apply validation to
	 * @param valid validity to be applied
	 */
	private void applyValidation(Node node, boolean valid) {
		if (valid) {
			if (node.getStyleClass().contains("invalidTextField"))
				node.getStyleClass().remove("invalidTextField");
		}
		else {
			if (!node.getStyleClass().contains("invalidTextField"))
				node.getStyleClass().add("invalidTextField");
		}
	}
	
}
