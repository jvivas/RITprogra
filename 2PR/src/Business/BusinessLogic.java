/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import java.io.IOException;
import java.util.ArrayList;

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
    DinamicaControl _DinamicaControl;
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();
    int _ProcessOperationState = 0;
    int _WordAppeareances = 0;
    
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
    public String EjecutarPatronSimple() throws IOException{
        this._ProcessOperationState = 0;
        this._WordAppeareances = 0;
        String executionResult = "";
        this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath,_UserPattern);
        if(_PatronSimpleControl.ValidatePattern()){
            this._PatronSimpleControl.EjecutarBusqueda();
            if(this._PatronSimpleControl.getProcessOperationState() == -1){
               executionResult = "Error en la busqueda.";
               this._ProcessOperationState = -1;
            } else if(this._PatronSimpleControl.getProcessOperationState() == 1){
                executionResult = "Busqueda exitosa!";
                this._MatchLineInfo = new ArrayList<String>();
                this._MatchLineInfo = this._PatronSimpleControl.getMatchLineInfo();
                this._WordAppeareances = this._PatronSimpleControl.getWordAppearances();
                this._ProcessOperationState = 1;
            } else {
                executionResult = "Error Desconocido.";
                this._ProcessOperationState = -1;
            }
        } else {
            //El patron no es correcto
            executionResult = "El patron no es el correcto";
            this._ProcessOperationState = -1;
        }
        return executionResult;
    }

        //Metodo para ejecutar el control del patron simple
    public String EjecutarProgDinamica() throws IOException{
        this._ProcessOperationState = 0;
        this._WordAppeareances = 0;
        String executionResult = "";
        this._DinamicaControl = new DinamicaControl(_DirectoryPath,_UserPattern);
        if(_DinamicaControl.ValidatePattern()){
            this._DinamicaControl.EjecutarBusqueda();
            if(this._DinamicaControl.getProcessOperationState() == -1){
               executionResult = "Error en la busqueda.";
               this._ProcessOperationState = -1;
            } else if(this._DinamicaControl.getProcessOperationState() == 1){
                executionResult = "Busqueda exitosa!";
                this._MatchLineInfo = new ArrayList<String>();
                this._MatchLineInfo = this._DinamicaControl.getMatchLineInfo();
                this._WordAppeareances = this._DinamicaControl.getWordAppearances();
                this._ProcessOperationState = 1;
            } else {
                executionResult = "Error Desconocido.";
                this._ProcessOperationState = -1;
            }
        } else {
            //El patron no es correcto
            executionResult = "El patron no es el correcto";
            this._ProcessOperationState = -1;
        }
        return executionResult;
    }
    
    public int getProcessOperationState() {
        return _ProcessOperationState;
    }

    public ArrayList<String> getMatchLineInfo() {
        return _MatchLineInfo;
    }

    public int getWordAppeareances() {
        return _WordAppeareances;
    }
    
}
