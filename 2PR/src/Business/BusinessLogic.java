/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GenerateExitFile;
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
    PatronOpcionesControl _PatronOpcionesControl;
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();    
    ArrayList<Integer> _WordAppeareances = new ArrayList<Integer>();
    ArrayList<Integer> _MatchesInFileLine = new ArrayList<Integer>();
    int _ProcessOperationState = 0;    
    int _PrefijoConsulta = 0;
    int _CantidadPatrones = 0;
    
    //Constructor
    public BusinessLogic() {
        _PrefijoConsulta = 0;
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
        this._WordAppeareances = new ArrayList<Integer>();
        this._MatchesInFileLine = new ArrayList<Integer>();
        this._MatchLineInfo = new ArrayList<String>();
        String executionResult = "";
        String[] tokenPattern = this._UserPattern.split("\\s+");
        for(int i = 0; i < tokenPattern.length; i++){
            this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath,tokenPattern[i]);
            if(_PatronSimpleControl.ValidatePattern()){
                this._PatronSimpleControl.EjecutarBusqueda();
                if(this._PatronSimpleControl.getProcessOperationState() == -1){
                   executionResult = "Error en la busqueda.";
                   this._ProcessOperationState = -1;
                } else if(this._PatronSimpleControl.getProcessOperationState() == 1){
                    executionResult = "Busqueda exitosa! Patrón Simple";
                    for(int j = 0; j < this._PatronSimpleControl.getMatchLineInfo().size(); j++){
                        this._MatchLineInfo.add(this._PatronSimpleControl.getMatchLineInfo().get(j));
                    }
                    this._MatchLineInfo.add("--");
                    this._WordAppeareances.add(this._PatronSimpleControl.getWordAppearances());
                    this._MatchesInFileLine.add(this._PatronSimpleControl.getMatchesInFileLine());
                    GenerateExitFile _ExitFile = new GenerateExitFile("Q"+ this._PrefijoConsulta +"_"+ this._PatronSimpleControl.getUserPattern() +".txt");
                    _ExitFile.setMatchesInFileLIne(this._MatchesInFileLine);
                    _ExitFile.setFileLines(this._MatchLineInfo);
                    _ExitFile.SaveFile();
                    this._ProcessOperationState = 1;                    
                    return executionResult;
                } else {
                    executionResult = "Error Desconocido.";
                    this._ProcessOperationState = -1;
                }
            } else {
                //El patron no es correcto
                executionResult = "El patron no es el correcto en Patron Simple";
                this._ProcessOperationState = -1;
                break;
            }
        }
        return executionResult;
    }

    //Metodo para ejecutar el control de programacion dinamica
    public String EjecutarProgDinamica() throws IOException{
        this._ProcessOperationState = 0;
        this._WordAppeareances = new ArrayList<Integer>();
        this._MatchesInFileLine = new ArrayList<Integer>();
        this._MatchLineInfo = new ArrayList<String>();
        String executionResult = "";
        String[] tokenPattern = this._UserPattern.split("\\s+");        
        for(int i = 0; i < tokenPattern.length; i++){    
            this._DinamicaControl = new DinamicaControl(_DirectoryPath,tokenPattern[i]);
            if(_DinamicaControl.ValidatePattern()){
                this._DinamicaControl.EjecutarBusqueda();
                if(this._DinamicaControl.getProcessOperationState() == -1){
                   executionResult = "Error en la busqueda.";
                   this._ProcessOperationState = -1;
                } else if(this._DinamicaControl.getProcessOperationState() == 1){
                    executionResult = "Busqueda exitosa! Programación Dinámica";
                    this._MatchLineInfo = new ArrayList<String>();
                    for(int j = 0; j < this._DinamicaControl.getMatchLineInfo().size(); j++){
                            this._MatchLineInfo.add(this._DinamicaControl.getMatchLineInfo().get(j));
                        }
                    this._MatchLineInfo.add("--");
                    this._WordAppeareances.add(this._DinamicaControl.getWordAppearances());
                    this._MatchesInFileLine.add(this._DinamicaControl.getMatchesInFileLine());
                    GenerateExitFile _ExitFile = new GenerateExitFile("Q"+this._PrefijoConsulta+"_"+ this._UserPattern +".txt");
                    _ExitFile.setMatchesInFileLIne(this._MatchesInFileLine);
                    _ExitFile.setFileLines(this._MatchLineInfo);
                    _ExitFile.SaveFile();
                    this._ProcessOperationState = 1;
                    return executionResult;
                } else {
                    executionResult = "Error Desconocido.";
                    this._ProcessOperationState = -1;
                }
            } else {
                //El patron no es correcto
                executionResult = "El patron no es el correcto en Programación Dinámica";
                this._ProcessOperationState = -1;
                break;
            }
        }
        return executionResult;
    }
    
    //Metodo para ejecutar el control del patron con opciones
    public String EjecutarPatronOpciones() throws IOException{
        this._ProcessOperationState = 0;
        this._WordAppeareances = new ArrayList<Integer>();
        this._MatchesInFileLine = new ArrayList<Integer>();
        this._MatchLineInfo = new ArrayList<String>();
        String executionResult = "";
        String[] tokenPattern = this._UserPattern.split("\\s+");
        for(int i = 0; i < tokenPattern.length; i++){
            this._PatronOpcionesControl = new PatronOpcionesControl(_DirectoryPath,tokenPattern[i]);
            if(_PatronOpcionesControl.ValidatePattern()){
                this._PatronOpcionesControl.EjecutarBusqueda();
                if(this._PatronOpcionesControl.getProcessOperationState() == -1){
                   executionResult = "Error en la busqueda.";
                   this._ProcessOperationState = -1;
                } else if(this._PatronOpcionesControl.getProcessOperationState() == 1){
                    executionResult = "Busqueda exitosa! Patrón con Opciones";
                    for(int j = 0; j < this._PatronOpcionesControl.getMatchLineInfo().size(); j++){
                        this._MatchLineInfo.add(this._PatronOpcionesControl.getMatchLineInfo().get(j));
                    }
                    this._MatchLineInfo.add("--");
                    this._WordAppeareances.add(this._PatronOpcionesControl.getWordAppearances());
                    this._MatchesInFileLine.add(this._PatronOpcionesControl.getMatchesInFileLine());
                    //System.out.println("Escribiendo Archivo");
                    GenerateExitFile _ExitFile = new GenerateExitFile("Q"+this._PrefijoConsulta+"_"+ _PatronOpcionesControl.getUserPattern() +".txt");
                    _ExitFile.setMatchesInFileLIne(this._MatchesInFileLine);
                    _ExitFile.setFileLines(this._MatchLineInfo);
                    _ExitFile.SaveFile();
                    //System.out.println("Fin archivo");
                    this._ProcessOperationState = 1;
                    return executionResult;
                } else {
                    executionResult = "Error Desconocido.";
                    this._ProcessOperationState = -1;
                }
            } else {
                //El patron no es correcto
                executionResult = "El patrón no es el correcto en Patrón con Opciones";
                this._ProcessOperationState = -1;
                break;
            }
        }
        return executionResult;
    }
    
    //Metodo para ejecutar el todos los controles de los patrones
    public String EjecutarPatrones(int pPrefijoConsulta,int pCantidadPatrones) throws IOException{
        this._PrefijoConsulta = pPrefijoConsulta;
        this._ProcessOperationState = 0;
        this._WordAppeareances = new ArrayList<Integer>();
        this._MatchesInFileLine = new ArrayList<Integer>();
        this._MatchLineInfo = new ArrayList<String>();
        String executionResult = "";
        String[] tokenPattern = this._UserPattern.split(" ");
        this._CantidadPatrones = pCantidadPatrones;
        for(int i = 0; i < tokenPattern.length; i++){            
            this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath,tokenPattern[i]);
            if(_PatronSimpleControl.ValidatePattern()){                
                executionResult = EjecutarPatronSimple();                                
                return executionResult;
            }
            else{
                this._DinamicaControl = new DinamicaControl(_DirectoryPath,tokenPattern[i]);
                if(_DinamicaControl.ValidatePattern()){                    
                    executionResult = EjecutarProgDinamica();                                      
                    return executionResult;
                }                
                else{
                    this._PatronOpcionesControl = new PatronOpcionesControl(_DirectoryPath,tokenPattern[i]);
                    if(_PatronOpcionesControl.ValidatePattern()){                        
                        executionResult = EjecutarPatronOpciones();                                                
                        return executionResult;
                    }
                }
            }
        }
        return executionResult;
    }
    
    
    public int getProcessOperationState() {
        return _ProcessOperationState;
    }

    public ArrayList<String> getMatchLineInfo() {
        return _MatchLineInfo;
    }

    public ArrayList<Integer> getWordAppeareances() {
        return _WordAppeareances;
    }

    public ArrayList<Integer> getMatchesInFileLIne() {
        return _MatchesInFileLine;
    }
    
}
