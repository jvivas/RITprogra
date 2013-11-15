/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import java.io.IOException;

/**
 *
 * @author eavendano
 */
public class BusinessLogic {
    
    //Declaracion de variables
    String _DirectoryPath = ".";
    String _UserPattern = "";
    //Control para el caso de buscar palabras en los directorios
    PatronSimpleControl _PatronSimpleControl;
    
    //Constructor
    public BusinessLogic() {
    }

    public String getDirectoryPath() {
        return _DirectoryPath;
    }

    public void setDirectoryPath(String _DirectoryPath) {
        this._DirectoryPath = _DirectoryPath;
    }

    public void setPatronUsuario(String _UserPattern) {
        this._UserPattern = _UserPattern;
    }
    
    //Metodo para ejecutar el control del patron simple
    public void EjecutarPatronSimple() throws IOException{
        this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath,_UserPattern);
        boolean patternValidation = _PatronSimpleControl.ValidatePattern();
        if(patternValidation){
            this._PatronSimpleControl.EjecutarBusqueda();
        } else {
            //El patron no es correcto
            System.out.println("El patron no es el correcto");
        }
    }
    
    
}
