import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.spi.FileTypeDetector;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import AlleTestklassen.HSPersonalTest;
import AlleTestklassen.TestClass;
import AlleTestklassen.Testsammlungsklasse;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Stop;
import junit.textui.TestRunner;
import turban.utils.ReflectionUtils;

public class MyGuiTestRunner extends JFrame {
	DefaultTreeModel DefaultTree;
	JTree jtr;
	DefaultMutableTreeNode wurzel;
	JTextField textfeld;
	JProgressBar meinLadebalken = new JProgressBar(0, 100);
	protected Component frame;
	static Class<?>[] testKlassen = { HSPersonalTest.class, TestClass.class, Testsammlungsklasse.class };
	static Class<?>[] differentAnnotation = { Test.class, Ignore.class };
	List<String> tests = new ArrayList<>();
	List<String> failed = new ArrayList<>();
	List<String> ignorierte = new ArrayList<>();
	List<String> failedParent = new ArrayList<>();
	List<String> goodParent = new ArrayList<>();
	Result result;
	boolean pressed = false;
	int blätter = 0;
	int blätterIG = 0;
	int parent = 0;
	long runTime;
	private ImageIcon image;
	boolean stopButton = false;
	boolean finished = false;
	Thread usain;
	Thread lade;
	Timestamp begin;
	Timestamp ende;
	int value;

	public MyGuiTestRunner(String a) {
		super(a);
		// Baum Settings
		wurzel = new DefaultMutableTreeNode(new String("MeineTests"));
		DefaultTree = new DefaultTreeModel(wurzel);
		jtr = new JTree(DefaultTree);
		jtr.setRootVisible(true);
		JScrollPane treeview = new JScrollPane(jtr);
		treeview.setPreferredSize(new Dimension(450, 250));
		treeview.setBounds(40, 10, 450, 260);
		this.getContentPane().add(treeview);
		ImageIcon legende = new ImageIcon(getClass().getResource("Legende.png"));
		ImageIcon save = new ImageIcon(getClass().getResource("save.png"));
		JLabel label = new JLabel(legende);
		label.setBounds(500, 10, 150, 260);
		label.setBackground(Color.black);

		this.getContentPane().add(label);

		// Baum aufbauen
		buildTree();
//Button für Runner und Ladebalken

		// Wenn die Klasse startet wenn die Tests ausgeführt, um für den Ladebalken
		// getRunTime() zu bekommen bevor die eigentlichen Tests starten
		Result plazebo = JUnitCore.runClasses(testKlassen);
		// Runnable Go (für TestRunner) und Go1 (für Ladebalken) um immer Thread neu
		// zustarten

		Runnable Go = new Runnable() {
			public void run() {
				result = JUnitCore.runClasses(testKlassen);
				try {
					testRunner(result);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Runnable Go1 = new Runnable() {
			public void run() {

				int minimum = meinLadebalken.getMinimum();
				int maximum = meinLadebalken.getMaximum();

				for (int i = minimum; i < maximum; i++) {
					while (stopButton == false) {
						try {
							value = meinLadebalken.getValue();
							meinLadebalken.setValue(value + 1);

							Thread.sleep(plazebo.getRunTime() / 100);
							System.out.println(value);
							if (value == 100) {
								finished = true;
								ende = new Timestamp(System.currentTimeMillis());
								meinLadebalken.setValue(100);
								stopButton = true;
							}
						} catch (InterruptedException ignoredException) {
						}
					}
				}
				if (stopButton = true) {
					meinLadebalken.setValue(0);
				}
				if (finished == true) {
					meinLadebalken.setValue(100);
				}

			}
		};

		lade = new Thread(Go1);
		usain = new Thread(Go);
		meinLadebalken.setValue(0);
		meinLadebalken.setStringPainted(true);
		meinLadebalken.setForeground(Color.black);
		meinLadebalken.setBounds(190, 280, 300, 90);
		meinLadebalken.setBackground(Color.white);
		JButton start = new JButton("Start");
		start.setBounds(40, 280, 140, 40);
		start.setBackground(Color.WHITE);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (finished == false) {
					begin = new Timestamp(System.currentTimeMillis());
					if (stopButton == true) {
						stopButton = false;
						usain = new Thread(Go);
						usain.start();
						lade = new Thread(Go1);
						lade.start();
					} else {
						usain.start();

						new Thread(Go1).start();
					}
					start.setEnabled(false);

					stopButton = false;
					pressed = true; // pressed nach dem der button gedrückt wurde true setzen um später damit dann
									// arbeiten zu könnenn z.b bei Information von Tests bekommen
				}
			}
		});

		JButton stop = new JButton("Stop/Reset");
		stop.setBounds(40, 330, 140, 40);
		stop.setBackground(Color.white);

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (finished == false) {
					try {
						usain.join(10);
						lade.join(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					stopButton = true;
					pressed = false;
					finished = false;
					start.setEnabled(true);

					JOptionPane.showMessageDialog(frame, "Sie haben Vorgang abgebrochen");
				} else {
					JOptionPane.showMessageDialog(frame, "Sie haben die Tests schon erfolgreich ausgeführt!!");
				}
			}
		});

		this.getContentPane().add(meinLadebalken);
		this.getContentPane().add(start);
		this.getContentPane().add(stop);
//XML Button
		JButton xml = new JButton("Als XML speichern");
		xml.setBounds(500, 330, 150, 40);
		xml.setBackground(Color.white);
		xml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pressed == true) {
					writeXML x = new writeXML(wurzel, failed, ignorierte);

					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Save");
					fileChooser.setCurrentDirectory(new java.io.File("C:\\"));

					fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML Documents", "xml"));
					int fs = fileChooser.showSaveDialog(null);
					File fl = fileChooser.getSelectedFile();
					if (fs == JFileChooser.APPROVE_OPTION) {
						try {
							FileWriter lol = new FileWriter(fl.getPath() + ".xml");
							lol.write(x.printXML());
							lol.flush();
							lol.close();
							JOptionPane.showMessageDialog(null, "Gepeichert!", a, JOptionPane.INFORMATION_MESSAGE,
									save);
						} catch (Exception a) {

						}

					} else if (fs == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(frame, "Sie haben nichts gespeichert..");
					}
				} else
					JOptionPane.showMessageDialog(frame, "Sie müssen die Tests erst ausführen");
			}

		});
		// Datenbank Button
		JButton data = new JButton("Datenbankeintrag");
		data.setBounds(500, 280, 150, 40);
		data.setBackground(Color.white);
		data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pressed == true && finished == true) {
					Testergebnis x = new Testergebnis(begin, ende, tests.size(), failed.size(),
							tests.size() - ignorierte.size() - failed.size());
					Datenbankeintrag i = new Datenbankeintrag();
					try {
						System.out.println(begin);
						System.out.println(ende);
						i.verbindeMitDB();
						i.speichereInDB(x);
						JOptionPane.showMessageDialog(null, "Eintrag erfolgreich!", a, JOptionPane.PLAIN_MESSAGE, save);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else
					JOptionPane.showMessageDialog(frame, "Sie müssen die Tests erst bis zum Ende ausführen");
			}

		});

		this.getContentPane().add(xml);
		this.getContentPane().add(data);
//Textanzeige für Feedback von Testergebnissen
		JTextArea feed = new JTextArea();
		feed.setEditable(false);
		feed.setBounds(40, 380, 610, 170);
		feed.setBackground(Color.white);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		feed.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		feed.setFont(new Font("", Font.CENTER_BASELINE, 30));
		feed.setLineWrap(true);
		feed.setWrapStyleWord(true);
		this.getContentPane().add(feed);
		jtr.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtr.getLastSelectedPathComponent();
				if (pressed == true) {
					feed.setText(feedback(node));
				} else {
					JOptionPane.showMessageDialog(frame, "Sie müssen erst die Tests ausführen! :-)");
				}
			}
		});

// JFrame Settings
		for (int i = 0; i < jtr.getRowCount(); i++) { // klappt die Nodes auf und anschließened Root verschwinden lassen
			jtr.expandRow(i);
		}
		jtr.setRootVisible(false);
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jtr.getCellRenderer();
		Icon Graues = new ImageIcon(getClass().getResource("Graues Licht.png"));
		renderer.setLeafIcon(Graues);
		renderer.setOpenIcon(Graues);
		ImageIcon img = new ImageIcon(getClass().getResource("Apple.png"));
		this.setIconImage(img.getImage());
		this.getContentPane().setBackground(Color.white);
		setLayout(null);
		setLocation(650, 200);
		setSize(690, 600);
		setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//Konstrukor von Frame zu Ende____________________________________________________________________________________
	public String feedback(DefaultMutableTreeNode node) {
		// Mit den Listen ignore und failed wird abgefragt ob der angeklickte letzte
		// Node gleich ist, wenn ja kommen die Infos unten auf JTextarea
		for (int j = 0; j < ignorierte.size(); j++) {
			if (ignorierte.get(j).equals(node.getUserObject()) && ignorierte.size() > 0) {
				return "Dieser Test wurde ignoriert! :(";
			}
		}
		for (int i = 0; i < failed.size(); i++) {
			if (failed.get(i).equals(node.getUserObject()) && failed.size() > 0) {
				return result.getFailures().get(i).toString();
			}
			for (int z = 0; z < goodParent.size(); z++) {
				if (goodParent.get(z).equals(node.getUserObject())) {
					return "Alle Tests von der " + goodParent.get(z) + " wurden mit Bravour abgeschlossen :-)";
				}
			}

			for (int g = 0; g < failedParent.size(); g++) {
				if (failedParent.get(g).equals(node.getUserObject())) {
					return "Es sind sind ein oder mehrere Tests von der " + failedParent.get(g) + " gescheitert!";
				}
			}

		}
		return "Test wurde erfolgreich abgeschlossen";
	}

	// Methode testRunner mit Listen die bestanden,gefailt und ignoriert worden sind
	public void testRunner(Result result) throws InterruptedException {

		runTime = result.getRunTime();
		// hier bekomme ich die Liste der ignorierten Testmethoden
		List ignorierte1 = new ArrayList<>();
		for (int i = 0; i < testKlassen.length; i++) {
			ignorierte1 = ReflectionUtils.getMethodNamesWithAnnotation(testKlassen[i], Ignore.class);
			for (int j = 0; j < ignorierte1.size(); j++) {
				ignorierte.add((String) ignorierte1.get(j));
			}
		}
		List tests1 = new ArrayList<>();
		for (int i = 0; i < testKlassen.length; i++) {
			tests1 = ReflectionUtils.getMethodNamesWithAnnotation(testKlassen[i], Test.class);
			for (int j = 0; j < tests1.size(); j++) {
				tests.add((String) tests1.get(j));
			}
		}

		for (Failure failure : result.getFailures()) {
			failed.add(zerleger(failure.toString()));
		}
		List<Integer> failedRows = new ArrayList<>();
		List<Integer> ignoreRows = new ArrayList<>();
		List<Integer> failedParentRow = new ArrayList<>();
		List<Integer> goodParentRow = new ArrayList<>();

		blätter = 0;
		blätterIG = 0;
		parent = 0;
		int leafCount = 0;
///Liste mit Reihenindex von den Knoten der ignorierten und gefailten Ergebnisse erstellen um später die Icon von den Zählen entsprechend umzuändern
		if (failed.size() > 0)
			for (int i = 0; i < wurzel.getChildCount(); i++) {
				for (int j = 0; j < wurzel.getChildAt(i).getChildCount(); j++) {
					blätter++; // zählt separat die durchgegangen Blätter mit
					for (int k = 0; k < failed.size(); k++) {
						if (failed.get(k).equals(wurzel.getChildAt(i).getChildAt(j).toString())) {
							failedRows.add(i + blätter);
						}
						// bevor er zum nächsten ParentNode in derSchleife geht wird hier überprüft ob
						// mind. ein kind vom Parent fehlgeschlagen ist
						// wenn ja wird auf eine List failedParentRow der Row draufgepackt wenn Nein auf
						// eine Liste goodParentRow
						// nebenbei eine Stringliste mit den Methodennamen der erfolgreichen und
						// gescheiterten Parents
					}
				}
				if (failedRows.size() > parent) {
					failedParentRow.add(i + leafCount);
					failedParent.add(wurzel.getChildAt(i).toString());
					leafCount = blätter;
					parent = failedRows.size();
				} else {
					goodParentRow.add(i + leafCount);
					goodParent.add(wurzel.getChildAt(i).toString());
					leafCount = blätter;
					parent = failedRows.size();
				}
			}

//für ignorierte Liste
		if (ignorierte.size() > 0) {
			for (int i = 0; i < wurzel.getChildCount(); i++) {
				for (int j = 0; j < wurzel.getChildAt(i).getChildCount(); j++) {
					blätterIG++;
					for (int k = 0; k < ignorierte.size(); k++) {

						if (ignorierte.get(k).equals(wurzel.getChildAt(i).getChildAt(j).toString())) {
							ignoreRows.add(i + blätterIG);
						}
					}
				}
			}

		}

//Aufruf von setCellRenderer um die Icons der Nodes jenach Erfolg der Testklassen und Methoden zu ändern
		if (stopButton == true) {
			DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jtr.getCellRenderer();
			Icon Graues = new ImageIcon(getClass().getResource("Graues Licht.png"));
			renderer.setLeafIcon(Graues);
			renderer.setOpenIcon(Graues);
		} else {
			jtr.setCellRenderer(new DefaultTreeCellRenderer() {
				Icon Grün = new ImageIcon(getClass().getResource("Grünes Licht.png"));
				Icon Rot = new ImageIcon(getClass().getResource("Rotes Licht.png"));
				Icon Gelb = new ImageIcon(getClass().getResource("Gelbes Licht.png"));
				Icon Gold = new ImageIcon(getClass().getResource("Goldener Stern.png"));
				Icon kreuz = new ImageIcon(getClass().getResource("Rotes Kreuz.png"));

				public Component getTreeCellRendererComponent(JTree jtr, Object value, boolean selected,
						boolean expanded, boolean isLeaf, int row, boolean focused) {

					Component c = super.getTreeCellRendererComponent(jtr, value, selected, expanded, isLeaf, row,
							focused);
					setIcon(Grün); // Erst alle auf Grün setzen
					// dann die Listen von oben mit Reihenindex nehmen und die rows entsprechend
					// Rot,Gelb,Stern, oder Kreuz setzen
					for (int i = 0; i < failedRows.size(); i++) {
						if (row == failedRows.get(i)) {
							setIcon(Rot);
						}
					}
					for (int i = 0; i < ignoreRows.size(); i++) {
						if (row == ignoreRows.get(i)) {
							setIcon(Gelb);
						}
					}
					for (int i = 0; i < failedParentRow.size(); i++) {
						if (row == failedParentRow.get(i)) {
							setIcon(kreuz);
						}
					}
					for (int i = 0; i < goodParentRow.size(); i++) {
						if (row == goodParentRow.get(i)) {
							setIcon(Gold);
						}
					}
					return c;
				}
			});
		}
	}

	public Result testRunnerinfo() {
		return result;
	}

	// __________________________________________________________________________________________________________________________________________________
	// ___________________________________________________________________________________________________________________________________________________
//String zerleger für die Ergebnissliste von junit Failedtests um die genauen Methodennamen zu bekommen
	public static String zerleger(String a) {
		String result = "";
		int i = 0;
		char x = '(';
		while (a.charAt(i) != x) {
			result += a.charAt(i);
			i++;
		}
		return result;
	}

	// Baumstrukur aufbauen
	public void buildTree() {
		// erstmal Knoten als ParentKlassen
		for (int x = 0; x < testKlassen.length; x++) {
			DefaultMutableTreeNode parentKlassen = new DefaultMutableTreeNode(new String(testKlassen[x].getName()));
			wurzel.add(parentKlassen);

		}
		// dann mit zwei for-schleifen die Liste der annotierten Methoden erstellen
		for (int i = 0; i < testKlassen.length; i++) {
			for (int k = 0; k < differentAnnotation.length; k++) {
				
				List<String> liste = ReflectionUtils.getMethodNamesWithAnnotation(testKlassen[i],
						(Class<? extends Annotation>) differentAnnotation[k]);
				// und während man in der For-Schleife noch ist, alle annotierten methoden zu
				// dem entsprechend Parent (testKlassen[i]) hinzufügen
				for (int j = 0; j < liste.size(); j++) {
					((DefaultMutableTreeNode) wurzel.getChildAt(i))
							.add(new DefaultMutableTreeNode(new String(liste.get(j))));
				}
			}
		}
	}

	public static void main(String[] args) {

		MyGuiTestRunner eins = new MyGuiTestRunner("iTestRunner");

	}

}
