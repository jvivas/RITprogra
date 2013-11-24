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
 * @author eavendano
 */
public class PatronOpcionesControl {
     //Declaracion de variables
    DirectoryProcessor _DirectoryProcessor;
    FileRead _FileRead;
    String _DirectoryPath = ".";
    String _UserPattern = "";
    boolean _CaseSensitive = false;
    boolean _TokenValidRegex = false;
    String _Regex = "[\\w_ñÑáéíóúüÁÉÍÓÚÜ\\[\\]]+";
    String _RegexOption = "[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+";
    ArrayList<String> _ListOfLetters = new ArrayList<String>();
    ArrayList<Integer> _RegressionIndex = new ArrayList<Integer>();
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();
    int _ProcessOperationState = 0;
    int _WordAppearances = 0;
    int _MatchesInFileLine = 0;

    //Constructor
    public PatronOpcionesControl(String pDirectoryPath, String pUserPattern) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
        this._UserPattern = pUserPattern;
        this._CaseSensitive = GetIgnoreCase();
        System.out.println("User Pattern: " + _UserPattern);
    }
    
    public boolean ValidatePattern(){
        boolean patternResult = false;
        Pattern pattern = Pattern.compile(_Regex);
        Matcher matcher = pattern.matcher(_UserPattern);
        if (matcher.matches()) {
            patternResult = true;
            System.out.println("Match Regex");
        } else {
            patternResult = false;
            System.out.println("No Match");
        }
        return patternResult;
    }
    
    public boolean ValidateToken(String pTokenValidate){
        boolean patternResult = false;
        Pattern pattern = Pattern.compile(_Regex);
        Matcher matcher = pattern.matcher(pTokenValidate);
        if (matcher.matches( )) {
            
            patternResult = true;
            //System.out.println("Match Token Regex");
        } else {
            patternResult = false;
            //System.out.println("No Token Match");
        }
        return patternResult;
    }
    
    public boolean GetIgnoreCase(){
        boolean ignoreCaseResult = false;
        if(_UserPattern.length() >= 2){
            String lastChars = this._UserPattern.substring(this._UserPattern.length()-2, this._UserPattern.length());
            if(lastChars.equals("@i")){
                ignoreCaseResult = true;
                this._UserPattern = this._UserPattern.substring(0, this._UserPattern.length()-2).toLowerCase();
            }
        } else {
            ignoreCaseResult = false;
        }
        return ignoreCaseResult;
    }
    
    public void EjecutarBusqueda() throws FileNotFoundException, IOException{
        this._ProcessOperationState = 0;
        this._WordAppearances = 0;
        this._MatchesInFileLine = 0;
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
        //Por cada linea del archivo separarla para luego procesar los patrones
        HorsepoolTable();
        //System.out.println(this._ListOfLetters.toString());
        //System.out.println(this._RegressionIndex.toString());
        for(int fileLineNumber = 0; fileLineNumber < pFileLines.size(); fileLineNumber++){
            int cuantityOfMathcedTokens = 0;
            String[] tokenList;
            String fileLine = pFileLines.get(fileLineNumber);
            tokenList = fileLine.split("\\s++");
            for(int tokenIndex = 0; tokenIndex < tokenList.length; tokenIndex++){
                String tokenPrepared = PrepareToken(tokenList[tokenIndex]);
                //if(ValidateToken(tokenPrepared) && tokenPrepared.length() >= _UserPattern.length()){
                if(tokenPrepared.length() >= _UserPattern.length()){
                    //System.out.println("token: " + tokenPrepared + "\n");
                    int matchedToken = HorsepoolMethod(tokenPrepared, pDirectoryName, pFileName, fileLineNumber);
                    if(cuantityOfMathcedTokens == 0 && matchedToken >= 1){
                        cuantityOfMathcedTokens++;
                    }
                }
            }
            if(cuantityOfMathcedTokens >= 1){
                this._MatchLineInfo.add("Match found at: " + pDirectoryName + "/" + pFileName + " in line: " + fileLineNumber + " on this line: " + fileLine);
                _MatchesInFileLine++;
            }
        }
        //System.out.println(this._MatchLineInfo.toString());
    }
    
    //Metodo para pre procesar el token que se busca
    public String PrepareToken(String pToken){
        String tokenResult = "";
        if(this._CaseSensitive)
            tokenResult = pToken.toLowerCase();
        else
            tokenResult = pToken;
        
        return tokenResult;
    }

    //Metodo para preparar la tabla de procesamiento del horsepool
    public void HorsepoolTable(){
        int lengthOfUserPattern = this._UserPattern.length()-1;
        int regressionValue = 1;
        ArrayList<String> listOfLetters = new ArrayList<String>();
        ArrayList<Integer> regressionIndex = new ArrayList<Integer>();
        while(lengthOfUserPattern >= 1){
            String letterFound = this._UserPattern.substring(lengthOfUserPattern-1,lengthOfUserPattern);
            if(!listOfLetters.contains(letterFound)){
                //La letra no estaba tomada en cuenta por lo que se tiene que agregar
                listOfLetters.add(letterFound);
                regressionIndex.add(regressionValue);
                regressionValue++;
                lengthOfUserPattern--;
            } else {
                //La letra ya estaba pero la cantidad de campos siguie aumentando.
                regressionValue++;
                lengthOfUserPattern--;
            }
        }
        listOfLetters.add("eop");
        regressionIndex.add(regressionValue++);
        this._ListOfLetters = listOfLetters;
        this._RegressionIndex = regressionIndex;
    }
    
    //Metodo de busqueda para el patron horsepool
    public int HorsepoolMethod(String pToken, String pDirectoryName, String pFileName, int pFileLine){
        int lengthOfToken = pToken.length();
        int lengthOfPattern = this._UserPattern.length();
        int scannedIndexToken = 0;
        int scannedIndexPattern = 0;
        int startComparing = 0;
        int cuantityOfMatchesOnToken = 0;
        int cuantityOfMatchesInLine = 0;
        while(scannedIndexToken < lengthOfToken){
            String patternCompare = this._UserPattern.substring(scannedIndexPattern, scannedIndexPattern+1);
            String tokenCompare = pToken.substring(scannedIndexToken, scannedIndexToken+1);
            if(patternCompare.equals(tokenCompare) &&  scannedIndexPattern+1 == lengthOfPattern) {
                cuantityOfMatchesOnToken++;
                scannedIndexPattern = 0;
                scannedIndexToken++;
                startComparing = scannedIndexToken;
            } else if(patternCompare.equals(tokenCompare)) {
                //Solo mover los indices
                scannedIndexPattern++;
                scannedIndexToken++;
            } else if(!patternCompare.equals(tokenCompare)){
                int indexOfPatternCompared = 0;
                if(scannedIndexPattern == 0 && startComparing == 0){
                    indexOfPatternCompared = this._ListOfLetters.indexOf(pToken.substring(startComparing+lengthOfPattern-1, startComparing+lengthOfPattern));
                } else {
                    indexOfPatternCompared = this._ListOfLetters.indexOf(pToken.substring(startComparing+scannedIndexPattern-1, startComparing+scannedIndexPattern));
                }
                //Obtener la cantidad de veces que se tiene que desplazar
                int regressionIndex = 0;
                if(indexOfPatternCompared == -1){
                    indexOfPatternCompared = this._ListOfLetters.indexOf("eop");
                }
                regressionIndex = this._RegressionIndex.get(indexOfPatternCompared);
                //Se tiene que mover startIndex + regressionIndex
                if(startComparing + regressionIndex + lengthOfPattern - 1 < lengthOfToken){
                    scannedIndexPattern = 0;
                    scannedIndexToken = startComparing+regressionIndex;
                    startComparing = scannedIndexToken;
                } else {
                    break;
                }   
            }
        }
        if(cuantityOfMatchesOnToken > 0){
            if(cuantityOfMatchesInLine == 0){
                cuantityOfMatchesInLine++;
            }
            this._WordAppearances++;
            //this._MatchLineInfo.add("Match found at: " + pDirectoryName + "/" + pFileName + " in line: " + pFileLine + " on this word: " + pToken +" " + cuantityOfMatchesOnToken + " times.");
        }
        return cuantityOfMatchesInLine;
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
