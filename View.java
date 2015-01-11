import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.io.RandomAccessFile;

@SuppressWarnings("serial")
public class View extends JFrame implements ActionListener, KeyListener {
	
	Model game;
	//Color[][][] rubiksCubeView = new Color[6][3][3];
	int faceAvant = 0;   // 0 : face avec centre blanc
	int faceDroite = 1;  // 1 : bleu
	int faceHaut = 2;    // 2 : orange
	int faceArriere = 3; // 3 : jaune
	int faceGauche = 4;  // 4 : vert 
	int faceBas = 5;     // 5 : rouge
	
	JPanel panelGeneral;
	JButton[][] faceUnitaire;

	File fileChosen = null;
	int curI = -1;
	int curJ = -1;
	int turnTimes;
	
	public View(int largeur, int hauteur){ //on cree la fenetre
		super("Rubik's Cube");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(largeur, hauteur);
		setLocationRelativeTo(null); // La fenêtre apparait au centre de l'écran

		creerMenus();  
		creerInterface();
		
		setVisible(true);		//show();
	}
	
	private void creerMenus() 
	{
		// Menu Fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F);
        
        ActionListener a1 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	for(int i=0;i<6;i++)
    			{
    				for(int j=0;j<9;j++)
    				{
    					game.InitialisationGrille();
    				}
    			}
            	actualiserFaceAvant();
            	fileChosen = null;
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
            	game.turnRubiksCube(0, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face blanche sens horaire", menuTournerRubiksCube, a10);
        
        ActionListener a11 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(0, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face blanche sens anti-horaire", menuTournerRubiksCube, a11);
        
        ActionListener a12 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(1, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face bleue sens horaire", menuTournerRubiksCube, a12);
        
        ActionListener a13 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(1, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face bleue sens anti-horaire", menuTournerRubiksCube, a13);
        
        ActionListener a14 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(2, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face orange sens horaire", menuTournerRubiksCube, a14);
        
        ActionListener a15 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(2, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face orange sens anti-horaire", menuTournerRubiksCube, a15);
        
        ActionListener a16 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(3, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face jaune sens horaire", menuTournerRubiksCube, a16);
        
        ActionListener a17 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(3, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face jaune sens anti-horaire", menuTournerRubiksCube, a17);
        
        ActionListener a18 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(4, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face verte sens horaire", menuTournerRubiksCube, a18);
        
        ActionListener a19 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(4, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face verte sens anti-horaire", menuTournerRubiksCube, a19);
        
        ActionListener a1a = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(5, true);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face rouge sens horaire", menuTournerRubiksCube, a1a);
        
        ActionListener a1b = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	game.turnRubiksCube(5, false);
            	actualiserFaceAvant();
            }
        };
        ajouterItem("Tourner face rouge sens anti-horaire", menuTournerRubiksCube, a1b);

        // Menu Resoudre
        JMenu menuResoudre = new JMenu("Résoudre");
        menuResoudre.setMnemonic(KeyEvent.VK_R);
        
        ActionListener a20 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	//chargerFichier("./Sudoku Database/Facile");
            }
        };
        ajouterItem("Résoudre complètement", menuResoudre, a20);
        
        ActionListener a21 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	//chargerFichier("./Sudoku Database/Moyen");
            }
        };
        ajouterItem("Faire une face", menuResoudre, a21);
        
         // Relier les menus à la fenêtre
		JMenuBar barreDeMenu = new JMenuBar();
		barreDeMenu.add(menuFichier);
		barreDeMenu.add(menuTournerRubiksCube);
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
	
	private void chargerFichier(String dossier)
	{
		// Utiliser jnlp si possible : FileOpenService fos;
		
		// Dossier d'ouverture de l'explorateur
    	JFileChooser dialogue = new JFileChooser(new File(dossier)); 
    	
    	if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		fileChosen = dialogue.getSelectedFile();
    		
    		reporterFichierDansCube(fileChosen); // Charger IHM
    	}	
	}		
	
	private void enregistrerFichier()
	{
		if (fileChosen != null)
		{
			reporterCubeDansFichier(fileChosen.toString());
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
    	}
	}
	
	private void creerInterface()
	{
		Container c = getContentPane();
		
		game = new Model();
		Color[][] face = game.getFace(0);
		
		// On crée la face de devant
		panelGeneral = new JPanel();
		panelGeneral.setLayout(new GridLayout(3,3)); 
		
		// On crée les couleurs de la face de devant
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
				panelGeneral.add(faceUnitaire[i][j]);
			}
		}
		
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
					
					// On ne remplit pas si le char n'est pas un chiffre entre 0 et 5
					if (c == '0') 
						game.rubiksCube[i][j/3][j%3] = Color.white;
					else if (c == '1') 
						game.rubiksCube[i][j/3][j%3] = Color.blue;
					else if (c == '2') 
						game.rubiksCube[i][j/3][j%3] = Color.orange;
					else if (c == '3') 
						game.rubiksCube[i][j/3][j%3] = Color.yellow;
					else if (c == '4') 
						game.rubiksCube[i][j/3][j%3] = Color.green;
					else if (c == '5') 
						game.rubiksCube[i][j/3][j%3] = Color.red;
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
					Color c = game.rubiksCube[i][j/3][j%3];
					
					if (c.equals(Color.white))
						raf.writeBytes("0");
					else if (c.equals(Color.blue)) 
						raf.writeBytes("1");
					else if (c.equals(Color.orange))
						raf.writeBytes("2");
					else if (c.equals(Color.yellow)) 
						raf.writeBytes("3");
					else if (c.equals(Color.green)) 
						raf.writeBytes("4");
					else if (c.equals(Color.red))
						raf.writeBytes("5");
					else
						raf.writeBytes("?");
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
		
		if (faceHaut == 2)
		{
			turnTimes = 0;
		}
		else if (faceBas == 2)
		{
			turnTimes = 2;
		}
		else if (faceGauche == 2)
		{
			turnTimes = 3;
		}
		else if (faceDroite == 2)
		{
			turnTimes = 1;
		}
		else if (faceAvant == 2)
		{
			if (faceHaut == 0)
			{
				turnTimes = 2;
			}
			else if (faceBas == 0)
			{
				turnTimes = 0;
			}
			else if (faceGauche == 0)
			{
				turnTimes = 1;
			}
			else if (faceDroite == 0)
			{
				turnTimes = 3;
			}
		}
		else if (faceArriere == 2)
		{
			if (faceHaut == 0)
			{
				turnTimes = 0;
			}
			else if (faceBas == 0)
			{
				turnTimes = 2;
			}
			else if (faceGauche == 0)
			{
				turnTimes = 3;
			}
			else if (faceDroite == 0)
			{
				turnTimes = 1;
			}
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
		faceUnitaire[curI][curJ].setBorder(BorderFactory.createEtchedBorder());
		curI = 0;
		curJ = 0;
		faceUnitaire[curI][curJ].setBorder(BorderFactory.createMatteBorder(25, 25, 25, 25, Color.black));
		
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
		faceUnitaire[curI][curJ].setBorder(BorderFactory.createMatteBorder(25, 25, 25, 25, Color.black));
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
				if (i != 1 || j != 1)
				{
					faceUnitaire[i][j].setBorder(BorderFactory.createEtchedBorder());
					
					if(e.getSource() == faceUnitaire[i][j])
					{
						faceUnitaire[i][j].setBorder(BorderFactory.createMatteBorder(25, 25, 25, 25, Color.black));
						curI = i;
						curJ = j;
					}
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
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.white);
			
			nextCell();		
		}

		if (evt.getKeyCode() == KeyEvent.VK_B) // Si une case est sélectionnée, elle devient blue 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.blue);
			
			nextCell();		
		}

		if (evt.getKeyCode() == KeyEvent.VK_O) // Si une case est sélectionnée, elle devient orange 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.orange);
			
			nextCell();		
		}

		if (evt.getKeyCode() == KeyEvent.VK_Y) // Si une case est sélectionnée, elle devient jaune 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.yellow);
			
			nextCell();		
		}

		if (evt.getKeyCode() == KeyEvent.VK_G) // Si une case est sélectionnée, elle devient verte 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.green);
			
			nextCell();		
		}

		if (evt.getKeyCode() == KeyEvent.VK_R) // Si une case est sélectionnée, elle devient rouge 
		{			
			if (curI >= 0 && curI <= 2 && curJ >= 0 && curJ <= 2 && (curI != 1 || curJ != 1))
				changerUneCouleur(Color.red);
			
			nextCell();		
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
            	new View(800, 800);
            }
        });
	}
}
