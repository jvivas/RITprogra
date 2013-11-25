/*
 *  Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package Business;
import DataAccess.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jorge
 */
public class DinamicaControl {
    
    //Declaracion de variables
    DirectoryProcessor _DirectoryProcessor;
    FileRead _FileRead;
    String _DirectoryPath = ".";
    String _UserPattern = "";    
    boolean _TokenValidRegex = false;
    String _Regex = "[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+#[0-9]";
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();
    ArrayList<Integer> _ListOfApariciones = new ArrayList<Integer>();  
    int _NumeroErrores = 0;
    int _ContadorApariciones = 0;
    int _ProcessOperationState = 0;
    int _WordAppearances = 0;
    int _MatchesInFileLine = 0;

    //Constructor
    public DinamicaControl(String pDirectoryPath, String pUserPattern) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
        this._UserPattern = pUserPattern;        
        System.out.println("User Pattern: " + _UserPattern);
    }
    
    public boolean ValidatePattern(){
        boolean patternResult = false;
        Pattern pattern = Pattern.compile(_Regex);
        Matcher matcher = pattern.matcher(_UserPattern);
        if (matcher.matches()) {
            patternResult = true;
            String[] divisionPatron = _UserPattern.split("#");
            _NumeroErrores = Integer.parseInt(divisionPatron[1]);
            _UserPattern = divisionPatron[0];
            System.out.println("Match Regex");
        } else {
            patternResult = false;
            System.out.println("No Match");
        }
        return patternResult;
    }
    
        
    public void EjecutarBusqueda() throws FileNotFoundException, IOException{
        this._ProcessOperationState = 0;        
        this._ContadorApariciones = 0;
        this._WordAppearances = 0;
        if(_UserPattern.length() > 0){
            int openDirectorySuccess = _DirectoryProcessor.getOpenDirectorySuccess();
            if(openDirectorySuccess == 0){
                //Tuvo éxito
                this._DirectoryProcessor.ReadDirectory();
                ArrayList<String> subFiles = _DirectoryProcessor.getFilesInDirectory();
                //Por cada archivo en el directorio                
                for(int subFile = 0; subFile < subFiles.size(); subFile++){
                    //System.out.println("Procesar Archivo:" + subFiles.get(subFile) + " " +subFile);
                    String fileName = subFiles.get(subFile);
                    _FileRead = new FileRead(_DirectoryPath+"/"+fileName);
                    if(_FileRead.getFileOpenSuccess() == 0){
                        _FileRead.ReadLines();                        
                        if(_FileRead.getFileReadSuccess() == 0){
                            ProcessFileLines(_FileRead.getFileLines(),_DirectoryPath,subFiles.get(subFile));
                            this._ProcessOperationState = 1;
                        } else {
                            System.err.println("No se pudo leer el archivo: " + fileName + "\n");
                            this._ProcessOperationState = -1;
                            
                        }
                    } else {
                        System.err.println("No se pudo abrir el archivo: " + fileName + "\n");
                        this._ProcessOperationState = -1;
                    }
                }
            } else {
                //Ocurrio un error y no se pudo abrir el directorio
                System.out.println("Ocurrio un error y no se pudo abrir el directorio");
                this._ProcessOperationState = -1;
            }
            System.out.println("Final de la Ejecucion de la busqueda.");
        } else {
            System.out.println("Largo de patron invalido.");
            this._ProcessOperationState = -1;
        }
    }
    
    //Metodo para procesar las lineas de texto del archivo
    public void ProcessFileLines(ArrayList<String> pFileLines, String pDirectoryName, String pFileName){
        int cuantityOfMathcedTokens = 0;    
        //Por cada linea del archivo separarla para luego procesar los patrones
        int counterPerDoc = 0;
        for(int fileLineNumber = 0; fileLineNumber < pFileLines.size(); fileLineNumber++){
            String fileLine = pFileLines.get(fileLineNumber);
            counterPerDoc += DynamicTable(fileLine);
            if(counterPerDoc >= 1){
                this._MatchLineInfo.add("Match found at: " + pDirectoryName + "/" + pFileName + " in line: " + fileLineNumber + " on this line: " + fileLine);
                _MatchesInFileLine++;
            }
        }
        //this._ListOfApariciones.add(contadorAparicionesPorDoc);
        //this._ListOfFiles.add(pFileName);  
        if(counterPerDoc != 0){     
            this._WordAppearances++;
            this._MatchLineInfo.add(pDirectoryName + "/" + pFileName  + " on this word: " + _UserPattern +" " + counterPerDoc + " times.");
        }
    }
    
    // Imprimir matriz
    public void ImprimirMatriz(int[][] pTable,String pToken){
        int lengthOfUserPattern = this._UserPattern.length() + 1;
        int lengthOfText = pToken.length()+ 1;        
         for (int i = 0; i < lengthOfUserPattern; i++){ 
            for (int j = 0; j < lengthOfText; j++) {                
                System.out.print(pTable[i][j] + " ");                
            }
            System.out.println();
        }        
        System.out.println("Listo");       
    }
    
    // Preparar tabla para ejecutar programacion dinamica
    public int DynamicTable(String pToken){
        int lengthOfUserPattern = this._UserPattern.length() + 1;
        int lengthOfText = pToken.length()+ 1;        
        int table[][] = new int[lengthOfUserPattern][lengthOfText];             
        for (int i = 0; i < lengthOfUserPattern; i++){ 
            for (int j = 0; j < lengthOfText; j++){
                if(i == 0){
                    table[i][j] = 0;
                }
                else if(j == 0){
                    table[i][j] = i;
                }
            }
        }
        return DynamicMethod(pToken,table,lengthOfUserPattern,lengthOfText);
    }
        
    // Metodo de programacion dinamica
    public int DynamicMethod(String pToken,int[][] table,int lengthOfUserPattern,int lengthOfText){                                                
        int flagAparicion = 0;
        int conteoApariciones = 0;
        for (int i = 1; i < lengthOfUserPattern; i++){ 
            for (int j = 1; j < lengthOfText; j++){                                           
                if(_UserPattern.charAt(i - 1) == pToken.charAt(j - 1)){                    
                    table[i][j] = table[i-1][j-1];
                }
                else{
                    int menor = java.lang.Math.min(table[i-1][j],table[i][j-1]);
                    table[i][j] = 1 + java.lang.Math.min(menor,table[i-1][j-1]);
                    }
                if(i == lengthOfUserPattern - 1){
                    if(table[i][j] <= _NumeroErrores && flagAparicion == 0){
                    conteoApariciones++;                
                    flagAparicion = 1;
                    }
                else{
                    if(table[i][j] <= _NumeroErrores){
                        flagAparicion = 1;
                    }
                    else
                        flagAparicion = 0;
                    }
                }
           }
            
         }
        return conteoApariciones;
    }
    
    public ArrayList<String> getMatchLineInfo() {
        return _MatchLineInfo;
    }

    public int getProcessOperationState() {
        return _ProcessOperationState;
    }

    public int getWordAppearances() {
        return _WordAppearances;
    }
    
    public int getMatchesInFileLine() {
        return _MatchesInFileLine;
    }
}

