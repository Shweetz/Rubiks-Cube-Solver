﻿package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.RandomAccessFile;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

@SuppressWarnings("serial")
public class View extends JFrame implements ActionListener, KeyListener {
	
	private static int calculatedWidth = 100;
	private static int calculatedHeight = 100;
	
	Model game;
	Solution solution;
	//Color[][][] rubiksCubeView = new Color[6][3][3];
	int faceAvant = 0;   // 0 : face avec centre blanc
	int faceDroite = 1;  // 1 : bleu
	int faceHaut = 2;    // 2 : orange
	int faceArriere = 3; // 3 : jaune
	int faceGauche = 4;  // 4 : vert 
	int faceBas = 5;     // 5 : rouge

	JPanel panelGeneral;
	JPanel panelCube;
	JPanel panelTexte;
	JPanel panelEtape;
	JPanel panelMessage;
	JTextArea ihmMessage;
	JTextArea ihmEtape;
	JButton[][] faceUnitaire;

	File fileChosen = null;
	int curI = -1;
	int curJ = -1;
	int turnTimes;
	boolean isSolving = false;
	int moveI = 0;
	int numberOfMoves = 0;
	
	public View(int largeur, int hauteur){ // on cree la fenetre
		super("Rubik's Cube");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(largeur, hauteur);
		this.setLocationRelativeTo(null); // La fenetre apparait au centre de l'ecran

		this.creerMenus();  
		this.creerInterface();
		
		this.setVisible(true);		//show();
	}
	
	private void creerMenus() 
	{
		// Menu Fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F);
        
        ActionListener a1 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	nouveauFichier();
            }
        };
        ajouterItem("Nouveau", menuFichier, a1, KeyEvent.VK_N); // Nouveau reset la grille
        
        ActionListener a2 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	chargerFichier("./Rubik's Cube Database");
            }
        };
        ajouterItem("Charger", menuFichier, a2, KeyEvent.VK_L);
        
        ActionListener a3 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	enregistrerFichier();
            }
        };
        ajouterItem("Enregistrer", menuFichier, a3, KeyEvent.VK_S);
        
        ActionListener a4 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	enregistrerSousFichier();
            }
        };
        ajouterItem("Enregistrer sous", menuFichier, a4);
        
        ActionListener a5 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	System.exit(0);
            }
        };
        ajouterItem("Quitter", menuFichier, a5, KeyEvent.VK_Q);

        // Menu Tourner Rubik's Cube
        JMenu menuTournerRubiksCube = new JMenu("Tourner Rubik's Cube");
        menuTournerRubiksCube.setMnemonic(KeyEvent.VK_T);
        
        ActionListener a10 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 0, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face blanche sens horaire", menuTournerRubiksCube, a10);
        
        ActionListener a11 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 0, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face blanche sens anti-horaire", menuTournerRubiksCube, a11);
        
        ActionListener a12 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 1, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face bleue sens horaire", menuTournerRubiksCube, a12);
        
        ActionListener a13 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 1, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face bleue sens anti-horaire", menuTournerRubiksCube, a13);
        
        ActionListener a14 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 2, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face orange sens horaire", menuTournerRubiksCube, a14);
        
        ActionListener a15 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 2, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face orange sens anti-horaire", menuTournerRubiksCube, a15);
        
        ActionListener a16 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 3, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face jaune sens horaire", menuTournerRubiksCube, a16);
        
        ActionListener a17 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 3, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face jaune sens anti-horaire", menuTournerRubiksCube, a17);
        
        ActionListener a18 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 4, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face verte sens horaire", menuTournerRubiksCube, a18);
        
        ActionListener a19 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 4, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face verte sens anti-horaire", menuTournerRubiksCube, a19);
        
        ActionListener a1a = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 5, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face rouge sens horaire", menuTournerRubiksCube, a1a);
        
        ActionListener a1b = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(game.rubiksCube, 5, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face rouge sens anti-horaire", menuTournerRubiksCube, a1b);

        // Menu Melanger
        JMenu menuMelanger = new JMenu("Mélanger");
        menuMelanger.setMnemonic(KeyEvent.VK_M);
        MenuListener a20 = new MenuListener(){        	
    	    public void menuSelected(MenuEvent e) { scrambleCube(200); }    	    
    	    public void menuDeselected(MenuEvent e) { } // need for compilation  
    	    public void menuCanceled(MenuEvent e) { }   // need for compilation  
        };
        menuMelanger.addMenuListener(a20);
        
        // Menu Resoudre
        JMenu menuResoudre = new JMenu("Résoudre");
        menuResoudre.setMnemonic(KeyEvent.VK_R);
        
        ActionListener a30 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("full");
            }
        };
        ajouterItem("Résoudre complètement", menuResoudre, a30);
        
        ActionListener a31 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("first cross");
            }
        };
        ajouterItem("Faire la croix blanche", menuResoudre, a31);
        
        ActionListener a32 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("first corners");
            }
        };
        ajouterItem("Faire une face", menuResoudre, a32);
        
        ActionListener a33 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("second layer");
            }
        };
        ajouterItem("Faire 2 couronnes", menuResoudre, a33);
        
        ActionListener a34 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("second cross");
            }
        };
        ajouterItem("Faire 2 couronnes et la croix jaune", menuResoudre, a34);
        
        ActionListener a35 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("second edges");
            }
        };
        ajouterItem("Faire 2 couronnes et la croix jaune avec bords orientés", menuResoudre, a35);
        
        ActionListener a36 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	showSolution("second corners position");
            }
        };
        ajouterItem("Faire 2 couronnes, la croix, les bords orientés et les coins placés", menuResoudre, a36);
        
         // Relier les menus à la fenêtre
		JMenuBar barreDeMenu = new JMenuBar();
		barreDeMenu.add(menuFichier);
		barreDeMenu.add(menuTournerRubiksCube);
		barreDeMenu.add(menuMelanger);
		barreDeMenu.add(menuResoudre);
		this.setJMenuBar(barreDeMenu);
	}
	
	private void ajouterItem(String intitule, JMenu menu, ActionListener a)
	{
        JMenuItem item = new JMenuItem(intitule);
        menu.add(item);
        item.addActionListener(a);
	}
	
	private void ajouterItem(String intitule, JMenu menu, ActionListener a, int key)
	{
        JMenuItem item = new JMenuItem(intitule);
        menu.add(item);
        item.addActionListener(a);
        item.setAccelerator(KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK));
	}

	private void nouveauFichier()
	{
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<9;j++)
			{
				game.InitializeGrid();
			}
		}
		actualiserFaceAvant();
		fileChosen = null;
		isSolving = false;
		
		ihmEtape.setText("[ ] Faire la 1e face (+ 1e couronne)\n"
					   + "    [ ] Faire la croix\n"
					   + "    [ ] Faire les coins\n"
					   + "[ ] Faire la 2e couronne\n"
					   + "[ ] Faire la croix de la face opposée\n"
					   + "[ ] Orienter les bords\n"
					   + "[ ] Orienter les coins\n");	
	}
	
	private void chargerFichier(String dossier)
	{
		// Utiliser jnlp si possible : FileOpenService fos;
		
		// Dossier d'ouverture de l'explorateur
    	JFileChooser dialogue = new JFileChooser(new File(dossier)); 
    	
    	if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		fileChosen = dialogue.getSelectedFile();
    		
    		reporterFichierDansCube(fileChosen); // Charger IHM

			ihmMessage.setText("Fichier " + fileChosen.getName() + " chargé !");
    	}	
    	
    	isSolving = false;
    	
    	ihmEtape.setText("[ ] Faire la 1e face (+ 1e couronne)\n"
    				   + "    [ ] Faire la croix\n"
    				   + "    [ ] Faire les coins\n"
    				   + "[ ] Faire la 2e couronne\n"
    				   + "[ ] Faire la croix de la face opposée\n"
    				   + "[ ] Orienter les derniers bords\n"
    				   + "[ ] Faire les derniers coins\n"
    				   + "    [ ] Placer les coins\n"
    				   + "    [ ] Orienter les coins\n");	
	}		
	
	private void enregistrerFichier()
	{
		if (fileChosen != null)
		{
			reporterCubeDansFichier(fileChosen.toString());
			ihmMessage.setText("Fichier " + fileChosen.getName() + " sauvegardé !");
		}
		else
			enregistrerSousFichier();
	}
	
	private void enregistrerSousFichier()
	{
		// Utiliser jnlp si possible : FileOpenService fos;
		
		// Dossier d'ouverture de l'explorateur
    	JFileChooser dialogue = new JFileChooser(new File("./Rubik's Cube Database")); 
    	dialogue.setSelectedFile(new File("exemple.txt"));
    	
    	if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
    		fileChosen = dialogue.getSelectedFile();
    		reporterCubeDansFichier(fileChosen.toString());
			ihmMessage.setText("Fichier " + fileChosen.getName() + " sauvegardé !");
    	}
	}
	
	private void scrambleCube(int numberOfScrambles)
	{
		for (int i = 0; i < numberOfScrambles; i++)
		{
			// Get random number between 0 (included) and 6 (not included) and turn this face clockwise
			int face = (int)(Math.random()*6); 
			game.turnRubiksCube(game.rubiksCube, face, true);			
		}
    	actualiserFaceAvant();
	}
	
	private String traductFaceToMove(int faceFromSolution, int moveI)
	{
		/* rubiksCube
		 * 0: white
		 * 1: blue
		 * 2: orange
		 * 3: yellow
		 * 4: green
		 * 5: red*/
		
		// rubiksCubeFirstCross
		if (solution.step[moveI].equals("first cross"))
		{
			if (faceFromSolution == 0)	return "rouge";
			if (faceFromSolution == 1)	return "bleue";
			if (faceFromSolution == 2)	return "blanche";
			if (faceFromSolution == 3)	return "orange";
			if (faceFromSolution == 4)	return "verte";
			if (faceFromSolution == 5)	return "jaune";
		}
		
		// rubiksCubeFirstCorners
		else
		{
			if (faceFromSolution == 0)	return "orange";
			if (faceFromSolution == 1)	return "bleue";
			if (faceFromSolution == 2)	return "jaune";
			if (faceFromSolution == 3)	return "rouge";
			if (faceFromSolution == 4)	return "verte";
			if (faceFromSolution == 5)	return "blanche";
		}
		
		return "none";
	}
	
	private void showMove()
	{
		if (solution.turn[moveI] == 0) 
		{
			moveI--;
			return;
		}
		
		String face = traductFaceToMove(solution.move[moveI], moveI);
		int faceToTurn = -1;
		if 		(face == "blanche")	faceToTurn = 0;
		else if (face == "bleue")   faceToTurn = 1;
		else if (face == "orange") 	faceToTurn = 2;
		else if (face == "jaune") 	faceToTurn = 3;
		else if (face == "verte")  	faceToTurn = 4;
		else if (face == "rouge")   faceToTurn = 5;
		
		String messageIntermediate = "";
		if (solution.turn[moveI] == 1)
		{
            	game.turnRubiksCube(game.rubiksCube, faceToTurn, true);
            	actualiserFaceAvant();
            	messageIntermediate = solution.message[moveI] 
            			+ "\n\nOn vient ici de tourner la face " + face + " dans le sens horaire.";
		}
		else if (solution.turn[moveI] == 2)
		{
            	game.turnRubiksCube(game.rubiksCube, faceToTurn, true);
            	game.turnRubiksCube(game.rubiksCube, faceToTurn, true);
            	actualiserFaceAvant();
            	messageIntermediate = solution.message[moveI] 
            			+ "\n\nOn vient ici de tourner la face " + face + " d'un demi-tour.";
		}
		else if (solution.turn[moveI] == 3)
		{
            	game.turnRubiksCube(game.rubiksCube, faceToTurn, false);
            	actualiserFaceAvant();
            	messageIntermediate = solution.message[moveI] 
            			+ "\n\nOn vient ici de tourner la face " + face + " dans le sens anti-horaire.";
		}
		
		if (solution.turn[moveI] != 0)
			ihmMessage.setText("Mouvement n°" + Integer.toString(moveI+1) + "/" + numberOfMoves + "\n\n" 
							+ messageIntermediate + "\n\n"
							+ "Appuyer sur enter pour voir le move suivant, étoile (*) pour le précédent.");
		
		// ihmEtape
		String faceInitials = "Signification des initiales :\n"
							 + "U : Face du haut sens horaire (Up)\n"
							 + "R : Face de droite sens horaire (Right)\n"
		 					 + "L : Face de gauche sens horaire (Left)\n"
		 					 + "F : Face de devant sens horaire (Front)\n\n"
		 					 + "' signifie sens anti-horaire\n"
							 + "2 signifie tourner 2 fois\n";
		
		if (solution.step[moveI] == "first cross")
		{
			ihmEtape.setText("-> Faire la 1e face (+ 1e couronne)\n"
					   	   + "    -> Faire la croix\n"
					   	   + "    [ ] Faire les coins\n"
					   	   + "[ ] Faire la 2e couronne\n"
	    				   + "[ ] Faire la croix de la face opposée\n"
	    				   + "[ ] Orienter les derniers bords\n"
	    				   + "[ ] Faire les derniers coins\n"
	    				   + "    [ ] Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n");	
		}
		else if (solution.step[moveI] == "first corners")
		{
			ihmEtape.setText("-> Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    -> Faire les coins\n"
					   	   + "[ ] Faire la 2e couronne\n"
	    				   + "[ ] Faire la croix de la face opposée\n"
	    				   + "[ ] Orienter les derniers bords\n"
	    				   + "[ ] Faire les derniers coins\n"
	    				   + "    [ ] Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n");	
		}
		else if (solution.step[moveI] == "second layer")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "-> Faire la 2e couronne\n"
	    				   + "[ ] Faire la croix de la face opposée\n"
	    				   + "[ ] Orienter les derniers bords\n"
	    				   + "[ ] Faire les derniers coins\n"
	    				   + "    [ ] Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n\n"	
					   	   + faceInitials);
		}
		else if (solution.step[moveI] == "second cross")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "[X] Faire la 2e couronne\n"
					   	   + "-> Faire la croix de la face opposée\n"
	    				   + "[ ] Orienter les derniers bords\n"
	    				   + "[ ] Faire les derniers coins\n"
	    				   + "    [ ] Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n\n"	
					   	   + faceInitials);
		}
		else if (solution.step[moveI] == "second edges")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "[X] Faire la 2e couronne\n"
					   	   + "[X] Faire la croix de la face opposée\n"
	    				   + "-> Orienter les derniers bords\n"
	    				   + "[ ] Faire les derniers coins\n"
	    				   + "    [ ] Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n\n"	
					   	   + faceInitials);
		}
		else if (solution.step[moveI] == "second corners position")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "[X] Faire la 2e couronne\n"
					   	   + "[X] Faire la croix de la face opposée\n"
	    				   + "[X] Orienter les derniers bords\n"
	    				   + "-> Faire les derniers coins\n"
	    				   + "    -> Placer les coins\n"
	    				   + "    [ ] Orienter les coins\n\n"	
					   	   + faceInitials);
		}
		else if (solution.step[moveI] == "second corners orientation")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "[X] Faire la 2e couronne\n"
					   	   + "[X] Faire la croix de la face opposée\n"
	    				   + "[X] Orienter les derniers bords\n"
	    				   + "-> Faire les derniers coins\n"
	    				   + "    [X] Placer les coins\n"
	    				   + "    -> Orienter les coins\n\n"	
					   	   + faceInitials);
		}
		else if (solution.step[moveI] == "finish")
		{
			ihmEtape.setText("[X] Faire la 1e face (+ 1e couronne)\n"
					   	   + "    [X] Faire la croix\n"
					   	   + "    [X] Faire les coins\n"
					   	   + "[X] Faire la 2e couronne\n"
					   	   + "[X] Faire la croix de la face opposée\n"
	    				   + "[X] Orienter les derniers bords\n"
	    				   + "[X] Faire les derniers coins\n"
	    				   + "    [X] Placer les coins\n"
	    				   + "    [X] Orienter les coins\n\n"	
					   	   + "Rubik's Cube terminé !");
		}
	}
	
	private void showSolution(String solveStep)
	{
		isSolving = true;
		
    	solution = game.solve(solveStep);
    	if (solution.isSolvable == true)
    	{
    		// Count number of moves total
    		numberOfMoves = 0;
    		while (solution.turn[numberOfMoves] != 0)
    			numberOfMoves++;
    		
    		// Do first move
    		moveI = 0;
	    	showMove();
    	}
    	else
    	{
    		ihmMessage.setText("Rubik's cube non résolvable, vérifiez les couleurs entrées.\n\n"
    				+ "Si elles sont justes, il est conseillé de se rapprocher d'un "
    				+ "Rubik's Cube fini pour avoir le moins d'étiquettes à décoller/recoller.");
    	}
	}
	
	private void creerInterface()
	{
		Container c = getContentPane();
		
		// On crée l'IHM
		panelGeneral = new JPanel();
		panelGeneral.setLayout(new GridLayout(1,2));		
		panelTexte = new JPanel(new GridLayout(1,2));
		panelTexte.setBorder(BorderFactory.createEtchedBorder());
		panelEtape = new JPanel(new BorderLayout());
		panelEtape.setBorder(BorderFactory.createEtchedBorder());
		panelMessage = new JPanel(new BorderLayout());
		panelCube = new JPanel(new BorderLayout());
		
		// On crée la zone etape
		ihmEtape = new JTextArea();
		ihmEtape.setText("[ ] Faire la 1e face (+ 1e couronne)\n"
					   + "    [ ] Faire la croix\n"
					   + "    [ ] Faire les coins\n"
					   + "[ ] Faire la 2e couronne\n"
					   + "[ ] Faire la croix de la face opposée\n"
					   + "[ ] Orienter les bords\n"
					   + "[ ] Orienter les coins\n");		
		ihmEtape.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 18));
		ihmEtape.setEditable(false);
		ihmEtape.setLineWrap(true);
		ihmEtape.setWrapStyleWord(true);
		ihmEtape.addKeyListener(this);
		panelEtape.add(ihmEtape);
		
		// On crée la zone message
		ihmMessage = new JTextArea();
		ihmMessage.setText("Utiliser les flèches pour visualiser une face différente.\n\n"
						 + "Cliquer sur une vignette puis "
						 + "sur la touche corresponte pour changer sa couleur (notations anglaises pour "
						 + "différencier blanc et bleu donc W pour White, B pour Blue, Orange, Yellow, Green, Red)\n"
						 + "\n"
						 + "Il n'est pas possible de modifier le centre des faces, il faut changer les couleurs autour.");		
		ihmMessage.setFont(new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 18));
		ihmMessage.setEditable(false);
		ihmMessage.setLineWrap(true);
		ihmMessage.setWrapStyleWord(true);
		ihmMessage.addKeyListener(this);
		JScrollPane scrollPane = new JScrollPane(ihmMessage);
		panelMessage.add(scrollPane);
		
		// On crée le cube
		game = new Model();
		
		/* On crée le cube (face de devant)
		Color[][] face = game.getFace(0);
		
		faceUnitaire = new JButton[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				faceUnitaire[i][j] = new JButton();
				faceUnitaire[i][j].setBorder(BorderFactory.createEtchedBorder());
				faceUnitaire[i][j].setBackground(face[i][j]);
				faceUnitaire[i][j].addActionListener(this);
				faceUnitaire[i][j].setFocusable(true); // Nécessaire pour addKeyListener
				faceUnitaire[i][j].addKeyListener(this);
				panelCube.add(faceUnitaire[i][j]);
			}
		}*/
		
		/*Model3D model=new Model3D();
        model.importObj();
        mapMeshes=model.getMapMeshes();
        cube.getChildren().setAll(mapMeshes.values());*/
		
        //JPanel cube = new Cube3D();
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
        	final BorderPane pane = new BorderPane();
        	Rubik rubik;

        	rubik = new Rubik(calculatedWidth/2, calculatedHeight);

    		pane.setCenter(rubik.getSubScene());

    		final Scene scene = new Scene(pane, calculatedWidth/2, calculatedHeight, true);
    		
            jfxPanel.setScene(scene);
        });
		panelCube.add(jfxPanel);
		
		// On lie le tout		
		panelTexte.add(panelEtape);	
		panelTexte.add(panelMessage);	
		panelGeneral.add(panelTexte);	
		panelGeneral.add(panelCube);	
		c.add(panelGeneral);
	}
	
	public void reporterFichierDansCube(File fileChosen) {

		try {
			RandomAccessFile raf = new RandomAccessFile(fileChosen, "r");
			String ligne;
			
			for (int i = 0; (ligne = raf.readLine()) != null; ++i) 
			{
				if (i >= 6) break;
				for (int j = 0; j < ligne.length(); ++j)
				{
					if (j >= 9) break;
					char c = ligne.charAt(j);
					Color currentColor = null;
					
					// On ne remplit pas si le char n'est pas un chiffre entre 0 et 5
					if (c == '0') 		currentColor = Color.white;
					else if (c == '1') 	currentColor = Color.blue;
					else if (c == '2') 	currentColor = Color.orange;
					else if (c == '3') 	currentColor = Color.yellow;
					else if (c == '4') 	currentColor = Color.green;
					else if (c == '5') 	currentColor = Color.red;
					
					game.rubiksCube[i][j/3][j%3] = currentColor;
				}
			}
			raf.close();
			
		}
		catch (Exception e) {
			System.out.print(fileChosen + " not found: " + e.toString() + "\n");
		}
		
		actualiserFaceAvant();
	}
	
	public String reporterCubeDansFichier(String fileName) {

		try {
			
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<9;j++)
				{
					Color currentColor = game.rubiksCube[i][j/3][j%3];
					
					if (currentColor.equals(Color.white))			raf.writeBytes("0");
					else if (currentColor.equals(Color.blue)) 		raf.writeBytes("1");
					else if (currentColor.equals(Color.orange))		raf.writeBytes("2");
					else if (currentColor.equals(Color.yellow)) 	raf.writeBytes("3");
					else if (currentColor.equals(Color.green)) 		raf.writeBytes("4");			
					else if (currentColor.equals(Color.red))		raf.writeBytes("5");
					else											raf.writeBytes("?");
				}
				if (i != 5)
					raf.writeBytes("\n");
			}
			raf.close();
		}
		catch (Exception e) {
			System.out.println(fileName + " not found: " + e.toString());
		}
		
		return fileName;
	}
	
	private void actualiserFaceAvant()
	{
		// On récupère la face avant
		Color[][] face = game.getFace(faceAvant);
		
		/* Les face latérales sont orientées vers la face haut
		 * La face haut est orientée vers la face arriere
		 * La face bas est orientée vers la face avant*/
		
		// On la tourne si besoin (2 est la faceHaut par défaut (orange) et 0 faceAvant (blanc)
		turnTimes = -1;
		
		if (faceHaut == 2)				turnTimes = 0;
		else if (faceBas == 2)			turnTimes = 2;
		else if (faceGauche == 2)		turnTimes = 3;
		else if (faceDroite == 2)		turnTimes = 1;
		else if (faceAvant == 2)
		{
			if (faceHaut == 0)			turnTimes = 2;
			else if (faceBas == 0)		turnTimes = 0;
			else if (faceGauche == 0)	turnTimes = 1;
			else if (faceDroite == 0)	turnTimes = 3;
		}
		else if (faceArriere == 2)
		{
			if (faceHaut == 0)			turnTimes = 0;
			else if (faceBas == 0)		turnTimes = 2;
			else if (faceGauche == 0)	turnTimes = 3;
			else if (faceDroite == 0)	turnTimes = 1;
		}
		
		for (int i = 0; i < turnTimes; i++)
		{
			face = game.turnFace(face);
		}
		
		// On l'affiche
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				faceUnitaire[i][j].setBackground(face[i][j]);
			}
		}
	}
	
	private void changerFace()
	{
		if (curI != -1 && curJ != -1)
		{
			faceUnitaire[curI][curJ].setBorder(BorderFactory.createEtchedBorder());
			curI = 0;
			curJ = 0;
			faceUnitaire[curI][curJ].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.black));
		}
		
		actualiserFaceAvant();
	}
	
	private void changerUneCouleur(Color newColor)
	{		
		// On récupère le modèle
		Color[][] face = game.rubiksCube[faceAvant];
	
		// On le tourne pour qu'il soit en phase avec la vue
		for (int i = 0; i < turnTimes; i++)
		{
			face = game.turnFace(face);
		}
		
		// On modifie dans le modèle la case cliquée dans la vue
		face[curI][curJ] = newColor;
		
		// On re-tourne le modèle dans le bon sens
		for (int i = 0; i < 4-turnTimes; i++)
		{
			face = game.turnFace(face);
		}
		
		// On actualise le modèle
		game.rubiksCube[faceAvant] = face;
		
		// On actualise la vue
		actualiserFaceAvant();
	}
	
	private void nextCell()
	{
		faceUnitaire[curI][curJ].setBorder(BorderFactory.createEtchedBorder());
		if (curJ != 2)
		{
			curJ++;	
		}
		else if (curJ == 2 && curI != 2)
		{
			curI++;		
			curJ = 0;		
		}
		faceUnitaire[curI][curJ].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.black));
	}
	
	public void actionPerformed (ActionEvent e) // Clic sur une case
	{
		// On met les bordures par défaut sauf la case sélectionnée s'il y en a une
		// S'il y en a une, on note ses coordonnées
		curI = -1;
		curJ = -1;
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++) 
			{
				faceUnitaire[i][j].setBorder(BorderFactory.createEtchedBorder());
				
				if(e.getSource() == faceUnitaire[i][j])
				{
					faceUnitaire[i][j].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.black));
					curI = i;
					curJ = j;
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent evt) { 
		
		if (evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_KP_LEFT) 
		{	
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceGauche;
			faceGauche = faceArriere;
			faceArriere = faceDroite;
			faceDroite = temp;
			
			changerFace();
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT || evt.getKeyCode() == KeyEvent.VK_KP_RIGHT) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceDroite;
			faceDroite = faceArriere;
			faceArriere = faceGauche;
			faceGauche = temp;
			
			changerFace();
		}
		if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_KP_UP) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceHaut;
			faceHaut = faceArriere;
			faceArriere = faceBas;
			faceBas = temp;
			
			changerFace();
		}
		if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_KP_DOWN) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceBas;
			faceBas = faceArriere;
			faceArriere = faceHaut;
			faceHaut = temp;
			
			changerFace();
		}

		if (evt.getKeyCode() == KeyEvent.VK_W) // Si une case est sélectionnée, elle devient blanche 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.white);

				nextCell();		
			}			
		}
		if (evt.getKeyCode() == KeyEvent.VK_B) // Si une case est sélectionnée, elle devient blue 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.blue);

				nextCell();		
			}	
		}
		if (evt.getKeyCode() == KeyEvent.VK_O) // Si une case est sélectionnée, elle devient orange 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.orange);

				nextCell();		
			}			
		}
		if (evt.getKeyCode() == KeyEvent.VK_Y) // Si une case est sélectionnée, elle devient jaune 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.yellow);

				nextCell();		
			}		
		}
		if (evt.getKeyCode() == KeyEvent.VK_G) // Si une case est sélectionnée, elle devient verte 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.green);

				nextCell();		
			}			
		}
		if (evt.getKeyCode() == KeyEvent.VK_R) // Si une case est sélectionnée, elle devient rouge 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2)
			{
				if (curI != 1 || curJ != 1)
					changerUneCouleur(Color.red);

				nextCell();		
			}		
		}
		
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
		{
			if (isSolving && moveI < numberOfMoves)
			{
				moveI++;
				showMove();
			}
		}
		if (evt.getKeyCode() == KeyEvent.VK_ASTERISK) 
		{
			if (isSolving && moveI >= 0)
			{
				// Do 3 times more the move we did to undo it... not performance-optimized but most readable way to do it
				showMove();
				showMove();
				showMove();
				
				moveI--;
			}
		}
	}
	
	public void keyReleased(KeyEvent evt) { // nécessaire pour compiler

	}
	
	public void keyTyped(KeyEvent evt) { // nécessaire pour compiler

	}
	
	public static void main(String[] args)
	{
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	// Get dimensions of the screen (current screen if multi-monitor)
            	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            	int width = gd.getDisplayMode().getWidth();
            	int height = gd.getDisplayMode().getHeight();
            	
            	// Make sure it doesn't go out of the screen
            	if (height < (width/2)+50) 
            		width = (height-50)*2;
            	
            	calculatedWidth = (int)(width/1.5);
            	calculatedHeight = (int)(((width/2)+50)/1.5);
            	
            	// Launch the application
            	new View(calculatedWidth, calculatedHeight);
            }
        });
	}
}
