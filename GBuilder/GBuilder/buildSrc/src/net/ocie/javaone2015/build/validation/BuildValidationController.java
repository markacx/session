package net.ocie.javaone2015.build.validation;

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ocJLFoster
 */
public class BuildValidationController implements Initializable, FXPanelController<String,String> {
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

	private SwingStageDesignate<String, String> designate;
	private BooleanBinding passwordValidBinding;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	public void setDesignate(SwingStageDesignate<String, String> designate) {
		this.designate = designate;
		titleLabel.setText((String) designate.getExtraProperty("title"));
	}
	@FXML
	void ok(ActionEvent event) {
		if (!okButton.isDisabled()) {
			designate.setReturnValue(passwordField.getText());
			designate.close();
		}
	}
	@FXML
	void cancel(ActionEvent event) {
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
//	private void validate() {
//		String password = passwordField.getText();
//		String reentry = reentryField.getText();
//		
//		boolean passwordValid = password != null && !password.isEmpty() && reentry != null && password.equals(reentry);
//		
//		applyValidation(passwordField, passwordValid);
//		applyValidation(reentryField, passwordValid);
//	}
	private void applyValidation(Node node, boolean valid) {
//		if (!node.getStyleClass().contains("invalidTextField"))
//			node.getStyleClass().add("invalidTextField");
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
