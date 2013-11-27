/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

import DataAccess.GenerateExitFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author eavendano
 */
public class BusinessLogic {
    
    //Declaracion de variables
    String _DirectoryPath = ".";
    String _UserPattern = "";
    //Control para el caso de buscar palabras en los directorios
    public PatronSimpleControl _PatronSimpleControl;
    public DinamicaControl _DinamicaControl;
    public PatronOpcionesControl _PatronOpcionesControl;
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();    
    ArrayList<Integer> _WordAppeareances = new ArrayList<Integer>();
    ArrayList<Integer> _MatchesInFileLine = new ArrayList<Integer>();
    ArrayList<Double> _SimilitudSimple = new ArrayList<Double>();
    ArrayList<String> _FileNamesSimple = new ArrayList<String>();
    ArrayList<Integer> _CountPerDocSimple = new ArrayList<Integer>();
    ArrayList<Double> _SimilitudDinamica = new ArrayList<Double>();
    ArrayList<String> _FileNamesDinamica = new ArrayList<String>();
    ArrayList<Integer> _CountPerDocDinamica = new ArrayList<Integer>();
    ArrayList<Double> _SimilitudOpciones = new ArrayList<Double>();
    ArrayList<String> _FileNamesOpciones = new ArrayList<String>();
    ArrayList<Integer> _CountPerDocOpciones = new ArrayList<Integer>();
    public ArrayList<Integer> _OrdenPatrones = new ArrayList<Integer>();
    public int _PatronUsado = -1;
    
    ArrayList<Float> _SimilitudesObtenidas = new ArrayList<Float>();
    ArrayList<String> _ArchivosObtenidos = new ArrayList<String>();
    int _ProcessOperationState = 0;    
    int _PrefijoConsulta = 0;
    public int _CantidadPatrones = 0;
    
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
        this._SimilitudSimple = new ArrayList<Double>();
        this._FileNamesSimple = new ArrayList<String>();
        this._SimilitudDinamica = new ArrayList<Double>();
        this._FileNamesDinamica = new ArrayList<String>();
        this._SimilitudOpciones = new ArrayList<Double>();
        this._FileNamesOpciones = new ArrayList<String>();
        this._OrdenPatrones = new ArrayList<Integer>();
        this._ArchivosObtenidos = new ArrayList<String>();
        this._SimilitudesObtenidas = new ArrayList<Float>();
        String executionResult = "";
        String[] tokenPattern = this._UserPattern.split(" ");
        this._CantidadPatrones = pCantidadPatrones;
        for(int i = 0; i < tokenPattern.length; i++){            
            this._PatronSimpleControl = new PatronSimpleControl(_DirectoryPath,tokenPattern[i]);
            if(_PatronSimpleControl.ValidatePattern()){                
                executionResult = EjecutarPatronSimple();                                
                this._SimilitudSimple = _PatronSimpleControl.getSimilitud();
                this._FileNamesSimple = _PatronSimpleControl.getFiles();
                this._CountPerDocSimple = _PatronSimpleControl.getCounter();
                this._OrdenPatrones.add(1);
                this._PatronUsado = 1;
                System.out.println("El patron usado es" + _PatronUsado);
                //return executionResult;
            }
                this._DinamicaControl = new DinamicaControl(_DirectoryPath,tokenPattern[i]);
                if(_DinamicaControl.ValidatePattern()){                    
                    executionResult = EjecutarProgDinamica();                                      
                    this._SimilitudDinamica = this._DinamicaControl.getSimilitud();
                    this._FileNamesDinamica = this._DinamicaControl.getFiles();
                    this._CountPerDocDinamica = this._DinamicaControl.getCounter();
                    this._OrdenPatrones.add(3);                    
                    this._PatronUsado = 3;
                    System.out.println("El patron usado es" + _PatronUsado);
                    //return executionResult;
                }                
                
                    this._PatronOpcionesControl = new PatronOpcionesControl(_DirectoryPath,tokenPattern[i]);
                    if(_PatronOpcionesControl.ValidatePattern()){                        
                        executionResult = EjecutarPatronOpciones();                                                
                        this._SimilitudOpciones = _PatronOpcionesControl.getSimilitud();
                        this._FileNamesOpciones = _PatronOpcionesControl.getFiles();
                        this._CountPerDocOpciones = _PatronOpcionesControl.getCounter();
                        this._OrdenPatrones.add(2);
                        this._PatronUsado = 2;
                        System.out.println("El patron usado es" + _PatronUsado);
                      //  return executionResult;
                    }
                
        }        
        return executionResult;
    }
        
    
    public void CalcularSimilitudes() throws FileNotFoundException, UnsupportedEncodingException{
        
            //Integer num_patron = _OrdenPatrones.get(i);
            String fileName = "";
            double similitudSimple = 0;
            double similitudOpciones = 0;
            double similitudDinamica = 0;
            ArrayList<String> fileNamesSimpleAux = this._FileNamesSimple;
            ArrayList<Double> similitudSimpleAux = this._SimilitudSimple;
            ArrayList<Integer> countPerDocSimpleAux = this._CountPerDocSimple;            
            ArrayList<String> fileNamesOpcionesAux = this._FileNamesOpciones;
            ArrayList<Double> similitudOpcionesAux = this._SimilitudOpciones;
            ArrayList<Integer> countPerDocOpcionesAux = this._CountPerDocOpciones;            
            ArrayList<String> fileNamesDinamicaAux = this._FileNamesDinamica;
            ArrayList<Double> similitudDinamicaAux = this._SimilitudDinamica;
            ArrayList<Integer> countPerDocDinamicaAux = this._CountPerDocDinamica;            
            int counter = 0;
            int contadorPatron = 0;
            HashMap<String,Float> hashResultado = new HashMap<String,Float>();
            for(int i = 0; i < fileNamesSimpleAux.size();i++){
                    counter = 0;
                    //contadorPatron = 0;
                    fileName = fileNamesSimpleAux.get(i);
                    System.out.println("Archivo procesando " + fileName);
                    if(fileNamesSimpleAux.contains(fileName)){
                        int positionSimple = fileNamesSimpleAux.indexOf(fileName);                            
                        similitudSimple = similitudSimpleAux.get(positionSimple);
                        int counterPerDoc = countPerDocSimpleAux.get(positionSimple);
                        counter += counterPerDoc;
                        fileNamesSimpleAux.remove(positionSimple);
                        similitudSimpleAux.remove(positionSimple);                              
                        countPerDocSimpleAux.remove(positionSimple);
                        
                            contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesOpcionesAux.contains(fileName)){
                        int positionOpciones = fileNamesOpcionesAux.indexOf(fileName);
                        similitudOpciones = similitudOpcionesAux.get(positionOpciones);
                        fileNamesOpcionesAux.remove(positionOpciones);
                        similitudOpcionesAux.remove(positionOpciones);
                        int counterPerDoc = countPerDocOpcionesAux.get(positionOpciones);
                        counter += counterPerDoc;
                        countPerDocOpcionesAux.remove(positionOpciones);
                        
                            contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesDinamicaAux.contains(fileName)){
                        int positionDinamica = fileNamesDinamicaAux.indexOf(fileName);
                        similitudDinamica = similitudDinamicaAux.get(positionDinamica);
                        fileNamesDinamicaAux.remove(positionDinamica);
                        similitudDinamicaAux.remove(positionDinamica);
                        int counterPerDoc = countPerDocDinamicaAux.get(positionDinamica);
                        counter += counterPerDoc;
                        countPerDocDinamicaAux.remove(positionDinamica);
                        
                            contadorPatron++;
                        
                        i--;    
                    }                 
                        
                        double suma = similitudSimple + similitudOpciones + similitudDinamica;                                                
                        System.out.println("Suma " + similitudSimple +  " " + similitudOpciones + " "  + similitudDinamica);
                        float divi = (float) 1 / this._CantidadPatrones;                        
                        System.out.println("El contador es " + contadorPatron);
                        float similitud = contadorPatron + (float)(divi * suma);                                                
                        hashResultado.put(fileName,similitud);
                        System.out.println("Similitud del archivo " + fileName + " es " + similitud);
                        contadorPatron = 0;
                        System.out.println("El contador actualizado" + contadorPatron);
                        
               }
            contadorPatron = 0;
            for(int i = 0; i < fileNamesOpcionesAux.size();i++){
                    counter = 0;
                    //contadorPatron = 0;
                    fileName = fileNamesOpcionesAux.get(i);
                    //System.out.println("Archivo procesando " + fileName);
                    if(fileNamesSimpleAux.contains(fileName)){
                        int positionSimple = fileNamesSimpleAux.indexOf(fileName);                            
                        similitudSimple = similitudSimpleAux.get(positionSimple);                        
                        int counterPerDoc = countPerDocSimpleAux.get(positionSimple);
                        counter += counterPerDoc;                        
                        fileNamesSimpleAux.remove(positionSimple);
                        similitudSimpleAux.remove(positionSimple);                              
                        countPerDocSimpleAux.remove(positionSimple);
                        
                            contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesOpcionesAux.contains(fileName)){
                        int positionOpciones = fileNamesOpcionesAux.indexOf(fileName);
                        similitudOpciones = similitudOpcionesAux.get(positionOpciones);
                        fileNamesOpcionesAux.remove(positionOpciones);
                        similitudOpcionesAux.remove(positionOpciones);
                        int counterPerDoc = countPerDocOpcionesAux.get(positionOpciones);
                        counter += counterPerDoc;                        
                        countPerDocOpcionesAux.remove(positionOpciones);
                        
                            contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesDinamicaAux.contains(fileName)){
                        int positionDinamica = fileNamesDinamicaAux.indexOf(fileName);
                        similitudDinamica = similitudDinamicaAux.get(positionDinamica);
                        fileNamesDinamicaAux.remove(positionDinamica);
                        similitudDinamicaAux.remove(positionDinamica);                        
                        int counterPerDoc = countPerDocDinamicaAux.get(positionDinamica);
                        counter += counterPerDoc;                                             
                        countPerDocDinamicaAux.remove(positionDinamica);
                        
                            contadorPatron++;
                        
                        i--;    
                    }
                        double suma = similitudSimple + similitudOpciones + similitudDinamica;                        
                        System.out.println("Suma " + similitudSimple +  " " + similitudOpciones + " "  + similitudDinamica);
                        float divi = (float) 1 / this._CantidadPatrones;                        
                        System.out.println("El contador es " + contadorPatron);
                        float similitud = contadorPatron + (float)(divi * suma);                        
                        hashResultado.put(fileName,similitud);
                        System.out.println("Similitud del archivo " + fileName + " es " + similitud);
                        contadorPatron = 0;
                        System.out.println("El contador actualizado" + contadorPatron);
            }
            contadorPatron = 0;
            for(int i = 0; i < fileNamesDinamicaAux.size();i++){
                    counter = 0;
                    //contadorPatron = 0;
                    fileName = fileNamesDinamicaAux.get(i);
                    //System.out.println("Archivo procesando " + fileName);
                    if(fileNamesSimpleAux.contains(fileName)){
                        int positionSimple = fileNamesSimpleAux.indexOf(fileName);                            
                        similitudSimple = similitudSimpleAux.get(positionSimple);
                        int counterPerDoc = countPerDocSimpleAux.get(positionSimple);
                        counter += counterPerDoc;
                        fileNamesSimpleAux.remove(positionSimple);
                        similitudSimpleAux.remove(positionSimple);                              
                        countPerDocSimpleAux.remove(positionSimple);
                        
                            contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesOpcionesAux.contains(fileName)){
                        int positionOpciones = fileNamesOpcionesAux.indexOf(fileName);
                        similitudOpciones = similitudOpcionesAux.get(positionOpciones);
                        fileNamesOpcionesAux.remove(positionOpciones);
                        similitudOpcionesAux.remove(positionOpciones);                        
                        int counterPerDoc = countPerDocOpcionesAux.get(positionOpciones);
                        counter += counterPerDoc;                     
                        countPerDocOpcionesAux.remove(positionOpciones);
                        
                        contadorPatron++;
                        
                        i--;
                    }
                    if(fileNamesDinamicaAux.contains(fileName)){
                        int positionDinamica = fileNamesDinamicaAux.indexOf(fileName);
                        similitudDinamica = similitudDinamicaAux.get(positionDinamica);
                        fileNamesDinamicaAux.remove(positionDinamica);
                        similitudDinamicaAux.remove(positionDinamica);                        
                        int counterPerDoc = countPerDocDinamicaAux.get(positionDinamica);
                        counter += counterPerDoc;                                             
                        countPerDocDinamicaAux.remove(positionDinamica);
                        
                            contadorPatron++;
                        
                        i--;    
                    }
                        double suma = similitudSimple + similitudOpciones + similitudDinamica;                        
                        System.out.println("Suma " + similitudSimple +  " " + similitudOpciones + " "  + similitudDinamica);
                        float divi = (float) 1 / this._CantidadPatrones;       
                        System.out.println("El contador es " + contadorPatron);
                        float similitud = contadorPatron + (float)(divi * suma);                        
                        hashResultado.put(fileName,similitud);
                        System.out.println("Similitud del archivo " + fileName + " es " + similitud);
                        contadorPatron = 0;
                        System.out.println("El contador actualizado" + contadorPatron);
            }
            OrdenarHash(hashResultado);
    }
    
    public void OrdenarHash(HashMap pHashResultado) throws FileNotFoundException, UnsupportedEncodingException{        
        ValueComparator bvc =  new ValueComparator(pHashResultado);
        TreeMap<String,Float> sorted_map = new TreeMap<String,Float>(bvc);
        sorted_map.putAll(pHashResultado);                
        EscribirRanking(sorted_map);
    }
       
    public void EscribirRanking(TreeMap<String,Float> sorted_map) throws FileNotFoundException, UnsupportedEncodingException{        
        PrintWriter writer = new PrintWriter("Q"+this._PrefijoConsulta+"_salida" +".txt", "UTF-8");
        int posicion = 1;
        
        for (Map.Entry entry : sorted_map.entrySet()) {            
            String nombreArchivo = entry.getKey().toString();
            Float similitud =  (float)entry.getValue();
            writer.println("Q" + this._PrefijoConsulta + " " + posicion + " " + nombreArchivo + " "  + similitud);
            posicion++;
        }        
        writer.close();
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

class ValueComparator implements Comparator<String> {

    Map<String, Float> base;
    public ValueComparator(Map<String, Float> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
