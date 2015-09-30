/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rebuild.app.viewer;

import com.rebuild.client.Module;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mark
 */
public class Viewer extends JFrame {

	private JTabbedPane tabbedPane;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> start());
	}
	private static void start() {
		try {
			Viewer viewer = new Viewer();
			viewer.setSize(400, 400);
			viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			viewer.init();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void init() throws Exception {
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		loadModules();
		setVisible(true);
	}
	private void loadModules() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("modules.txt")))) {
			for (;;) {
				String line = reader.readLine();
				if (line == null)
					break;

				Class<?> clazz = Class.forName(line);
				Module m = (Module) clazz.newInstance();
				JPanel panel = new JPanel();
				tabbedPane.addTab(m.getName(), panel);
			}
		}
	}
}
