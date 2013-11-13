/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

/**
 *
 * @author eavendano
 */
public class BusinessLogic {
    
    //Declaracion de variables
    String _DirectoryPath = ".";
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
    
    //Metodo para ejecutar el control del patron simple
    public void EjecutarPatronSimple(){
        this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath);
        this._PatronSimpleControl.EjecutarBusqueda();
    }
    
    
}
