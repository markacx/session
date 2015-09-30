package net.ocie.javaone2015.build.fx;

import java.util.Map;
import javafx.stage.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mark Claassen, Joseph Foster
 * @param <T> initial value
 * @param <V> return value
 */
public class SwingStageDesignate<T, V> {
	private V returnValue;
	private final T initialValue;
	private Window fxOwner;
	private final FXPanelFrame<T, V> panelFrame;
	private final Map<Object, Object> extraProperties;
	public SwingStageDesignate(T initialValue, FXPanelFrame<T, V> panelFrame, Map<Object, Object> extraProperties) {
		this.initialValue = initialValue;
		this.panelFrame = panelFrame;
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
		SwingUtilities.invokeLater(panelFrame::close);
	}
	public Object getExtraProperty(Object key) {
		return extraProperties.get(key);
	}
}
