package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import com.javafx.experiments.importers.obj.ObjImporter;

public class Model3D {
     
    // Cube.obj contains 117 meshes, marked as "Block46",...,"Block72 (6)" in this set: 
    private Set<String> meshes;
 
    // HashMap to store a MeshView of each mesh with its key
    private final Map<String,MeshView> mapMeshes=new HashMap<>();
     
    public void importObj(){
        try {// cube.obj
            ObjImporter reader = new ObjImporter(getClass().getResource("/resource/Cube.obj").toExternalForm());
            meshes=reader.getMeshes(); // set with the names of 117 meshes
                       
            Affine affineIni=new Affine();            
            affineIni.prepend(new Rotate(-90, Rotate.X_AXIS));
            affineIni.prepend(new Rotate(90, Rotate.Z_AXIS));
            meshes.stream().forEach(s-> { 
                MeshView cubiePart = reader.buildMeshView(s);
                // every part of the cubie is transformed with both rotations:
                cubiePart.getTransforms().add(affineIni); 
                // since the model has Ns=0 it doesn't reflect light, so we change it to 1
                PhongMaterial material = (PhongMaterial) cubiePart.getMaterial();
                material.setSpecularPower(1);
                cubiePart.setMaterial(material);
                // finally, add the name of the part and the cubie part to the hashMap:
                mapMeshes.put(s,cubiePart); 
            });
        } catch (IOException e) {
            System.out.println("Error loading model "+e.toString());
        }
    }
    public Map<String, MeshView> getMapMeshes() {
        return mapMeshes;
    }
}