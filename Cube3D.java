import java.awt.*;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Vector3f;

@SuppressWarnings("serial")
public class Cube3D extends JPanel {

	public Cube3D() {
		this.setLayout(new BorderLayout());		
	    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());	
	    this.add(canvas3D, BorderLayout.CENTER);
		
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);		
		simpleU.getViewingPlatform().setNominalViewingTransform();
		
		BranchGroup scene = createSceneGraph();

	    scene.compile();
	    simpleU.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph() {
	    // Creation de l'objet parent qui contiendra tous les autres objets 3D
	    BranchGroup parent = new BranchGroup();
  
	    Transform3D myTransform3D = new Transform3D();
	    myTransform3D.setScale(0.4);
	    
	    for (int i = -1; i < 2; ++i)
	    {
	    	for (int j = -1; j < 2; ++j)
		    {
	    		for (int k = 2; k < 5; ++k)
			    {
		    	    myTransform3D.setTranslation(new Vector3f(i, j, -k));    
		    	    TransformGroup cubeTransformGroup = new TransformGroup(myTransform3D);	 
		    	    
		    	    // cube 
		    	    Shape3D myCube = new ColorCube();
		    	    cubeTransformGroup.addChild(myCube); 		    	   			  			
		    	    
		    	    parent.addChild(cubeTransformGroup);
			    }
		    }
	    }
	    
	    /*for (int i = -1; i < 2; ++i)
	    {
	    	for (int j = -1; j < 2; ++j)
		    {
	    		for (int k = 2; k < 5; ++k)
			    {
		    	    myTransform3D.setTranslation(new Vector3f(i, j, -k));   
		    	    TransformGroup cubeTransformGroup = new TransformGroup(myTransform3D);	 
		    	    
		    	    // cube
		    	    Shape3D myCube = new ColorCube();
		    	    cubeTransformGroup.addChild(myCube);
		    	    
		    	    // rotater
	    			Alpha myAlpha = new Alpha();
	    			myAlpha.setIncreasingAlphaDuration(10000);
	    			myAlpha.setLoopCount(-1);
	    			RotationInterpolator myRotater = new RotationInterpolator(myAlpha,cubeTransformGroup);
	    			Transform3D myTransform3Da = new Transform3D();
	    			myTransform3Da.setScale(0.4);	    		
		    	    myTransform3Da.setTranslation(new Vector3f(0, 1, -1));       
	    		    myRotater.setTransformAxis(myTransform3Da);
	    			myRotater.setMinimumAngle(0.0f);
	    			myRotater.setMaximumAngle((float)(Math.PI*2.0));
	    			BoundingSphere myBounds = new BoundingSphere();
	    			myRotater.setSchedulingBounds(myBounds);
	    			cubeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    			cubeTransformGroup.addChild(myRotater);	    			  			
		    	    
		    	    parent.addChild(cubeTransformGroup);
			    }
		    }
	    }*/

	    return parent;
	}
	
}
