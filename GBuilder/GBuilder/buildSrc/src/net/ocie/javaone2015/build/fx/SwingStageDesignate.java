package net.ocie.javaone2015.build.fx;

import java.util.Map;
import javafx.stage.Window;
import javax.swing.SwingUtilities;

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
public class SwingStageDesignate<T, V> {
	private V returnValue;
	private final T initialValue;
	private Window fxOwner;
	private final FXPanelFrame frameThing;
	private final Map<Object, Object> extraProperties;
	public SwingStageDesignate(T initialValue, FXPanelFrame frameThing, Map<Object, Object> extraProperties) {
		this.initialValue = initialValue;
		this.frameThing = frameThing;
		this.extraProperties = extraProperties;
	}
	public void setReturnValue(V t) {
		this.returnValue = t;
	}
	public V getReturnValue() {
		return returnValue;
	}
	public T getInitialValue() {
		return initialValue;
	}
	public Window getWindow() {
		return fxOwner;
	}
	public void close() {
		SwingUtilities.invokeLater(frameThing::close);
	}
	public Object getExtraProperty(Object key) {
		return extraProperties.get(key);
	}
}
