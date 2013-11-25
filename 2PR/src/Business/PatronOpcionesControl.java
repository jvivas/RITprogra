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
    String _RegexNormal = "[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+";
    String _Regex = "[\\w_ñÑáéíóúüÁÉÍÓÚÜ]*\\[[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+\\][\\w_ñÑáéíóúüÁÉÍÓÚÜ]*";
    String _RegexOption = "\\[[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+\\]";
    String _RegexHead = "\\[[\\w_ñÑáéíóúüÁÉÍÓÚÜ]+";
    String _RegexTail = "\\][\\w_ñÑáéíóúüÁÉÍÓÚÜ]+";
    ArrayList<String> _ListOfLetters = new ArrayList<String>();
    ArrayList<Integer> _RegressionIndex = new ArrayList<Integer>();
    ArrayList<String> _MatchLineInfo = new ArrayList<String>();
    int _ProcessOperationState = 0;
    int _WordAppearances = 0;
    int _MatchesInFileLine = 0;
    String _PatternHead = "";
    String _PatternTail = "";
    String _PatternOptions = "";
    ArrayList<boolean[]> _MaskTableList = new ArrayList<boolean[]>();

    //Constructor
    public PatronOpcionesControl(String pDirectoryPath, String pUserPattern) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
        this._UserPattern = pUserPattern;
        this._CaseSensitive = GetIgnoreCase();
        //System.out.println("User Pattern: " + _UserPattern);
    }
    
    public boolean ValidatePattern(){
        boolean patternResult = false;
        Pattern pattern = Pattern.compile(_Regex);
        Matcher matcher = pattern.matcher(_UserPattern);
        if (matcher.matches()) {
            try{
                String head = "";
                String tail = "";
                String options = "";
                String[] tokenPattern = _UserPattern.split(_RegexHead);
                head = tokenPattern[0];
                tokenPattern = _UserPattern.split("\\]");
                if(tokenPattern.length > 1)
                    tail = tokenPattern[1];
                else 
                    tail = "";
                tokenPattern = _UserPattern.split("\\[");
                tokenPattern = tokenPattern[1].split("\\]");
                options = tokenPattern[0];
                this._PatternHead = head;
                this._PatternTail = tail;
                this._PatternOptions = options;
                patternResult = true;
            //System.out.println("Match Regex");
            } catch(Exception e){
                e.printStackTrace();
                patternResult = false;
            }
        } else {
            patternResult = false;
            //System.out.println("No Match");
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
        String[] tokenOpcional = this._PatternOptions.split("");
        for(int fileLineNumber = 0; fileLineNumber < pFileLines.size(); fileLineNumber++){
            int cuantityOfMathcedTokens = 0;
            String[] tokenList;
            String fileLine = pFileLines.get(fileLineNumber);
            tokenList = fileLine.split("\\s++");
            for(int tokenIndex = 0; tokenIndex < tokenList.length; tokenIndex++){
                String tokenPrepared = PrepareToken(tokenList[tokenIndex]);
                if(tokenPrepared.length() >= _PatternHead.length()+_PatternTail.length()+1){
                    //System.out.println("token: " + tokenPrepared + "\n");
                    for(int indexOfOption = 1; indexOfOption < tokenOpcional.length; indexOfOption++){
                        BuildMaskTable(tokenOpcional[indexOfOption]);
                        int matchedToken = ShiftAndMethod(this._PatternHead + tokenOpcional[indexOfOption] + this._PatternTail, tokenPrepared, pDirectoryName, pFileName, fileLineNumber);
                        if(cuantityOfMathcedTokens == 0 && matchedToken >= 1){
                            cuantityOfMathcedTokens++;
                        }
                    }
                }
            }
            if(cuantityOfMathcedTokens >= 1){
                this._MatchLineInfo.add(pDirectoryName + "/" + pFileName + " in line: " + fileLineNumber + " on this line: " + fileLine);
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
    public void BuildMaskTable(String pOptionLetter){
        ArrayList<String> listOfLetters = new ArrayList<String>();
        this._MaskTableList = new ArrayList<boolean[]>();
        this._ListOfLetters = new ArrayList<String>();
        //Primero hacer un for para la cabeza
        for(int patternSubStrIndex = 0; patternSubStrIndex < _PatternHead.length(); patternSubStrIndex++){
            String letterFound = this._PatternHead.substring(patternSubStrIndex, patternSubStrIndex+1);
            if(!listOfLetters.contains(letterFound)){
               listOfLetters.add(letterFound);
            }
        }
        //Se busca que la letra este en la lista de letras
        if(!listOfLetters.contains(pOptionLetter)){
           listOfLetters.add(pOptionLetter);
        }
        //Por ultimo se hacer un for para la cola
        for(int patternSubStrIndex = 0; patternSubStrIndex < _PatternTail.length(); patternSubStrIndex++){
            String letterFound = this._PatternTail.substring(patternSubStrIndex, patternSubStrIndex+1);
            if(!listOfLetters.contains(letterFound)){
               listOfLetters.add(letterFound);
            }
        }
        listOfLetters.add("eop");
        //Una vez que se sabe cuales son las letras se procede a armar un arreglo para cada letra
        for(int i = 0; i < listOfLetters.size(); i++){
            boolean[] temporalMaskTable = new boolean[_PatternHead.length()+_PatternTail.length()+1];
            //For para encontrar los indices de la letra en la cabeza
            int indexOfPatternBuild = 0;
            for(int patternSubStrIndex = 0; patternSubStrIndex < _PatternHead.length(); patternSubStrIndex++){
                String letterFound = this._PatternHead.substring(patternSubStrIndex, patternSubStrIndex+1);
                if(letterFound.equals(listOfLetters.get(i))){
                    temporalMaskTable[indexOfPatternBuild] = true;
                    indexOfPatternBuild++;
                } else {
                    temporalMaskTable[indexOfPatternBuild] = false;
                    indexOfPatternBuild++;
                }
            }
            //Indice del patron opcional
            if(pOptionLetter.equals(listOfLetters.get(i))){
                temporalMaskTable[indexOfPatternBuild] = true;
                indexOfPatternBuild++;
            } else {
                temporalMaskTable[indexOfPatternBuild] = false;
                indexOfPatternBuild++;
            }
            //For para encontrar los indices de la letra en la cola
            for(int patternSubStrIndex = 0; patternSubStrIndex < _PatternTail.length(); patternSubStrIndex++){
                String letterFound = this._PatternTail.substring(patternSubStrIndex, patternSubStrIndex+1);
                if(letterFound.equals(listOfLetters.get(i))){
                    temporalMaskTable[indexOfPatternBuild] = true;
                    indexOfPatternBuild++;
                } else {
                    temporalMaskTable[indexOfPatternBuild] = false;
                    indexOfPatternBuild++;
                }
            }
            //Cuando se tienen todos los inices se guarda la lista
            this._MaskTableList.add(temporalMaskTable);
        }
        this._ListOfLetters = listOfLetters;
    }
    
    //Metodo de busqueda para el patron horsepool
    public int ShiftAndMethod(String patternOption, String pToken, String pDirectoryName, String pFileName, int pFileLine){
        int cuantityOfMatchesInLine = 0;
        int cuantityOfMatchesOnToken = 0;
        boolean[] letterMask = new boolean[this._PatternHead.length()+this._PatternTail.length()+1];
        boolean[] acumulatedMask = new boolean[this._PatternHead.length()+this._PatternTail.length()+1];
        for(int z = 0; z < acumulatedMask.length; z++){
            acumulatedMask[z] = false;
        }
        for(int indexOfToken = 0; indexOfToken < pToken.length(); indexOfToken++){
            //Buscar el index de la letra del token
            int indexOfPatternCompared = this._ListOfLetters.indexOf(pToken.substring(indexOfToken, indexOfToken+1));
            //En caso de que la letra no exista traerse el vector de solo falsos
            if(indexOfPatternCompared == -1){
                    indexOfPatternCompared = this._ListOfLetters.indexOf("eop");
            }
            letterMask = this._MaskTableList.get(indexOfPatternCompared);
            //Aplicar el shift
            boolean[] auxiliarShiftMask = new boolean[this._PatternHead.length()+this._PatternTail.length()+1];
            for(int z = 0; z < auxiliarShiftMask.length; z++){
                if(z == 0){
                    auxiliarShiftMask[z] = true;
                } else {
                    auxiliarShiftMask[z] = acumulatedMask[z-1];
                }
            }
            acumulatedMask = auxiliarShiftMask;
            //Aplicar el and a las mascaras
            for(int z = 0; z < acumulatedMask.length; z++){
                acumulatedMask[z] = acumulatedMask[z] && letterMask[z];
            }
            if(acumulatedMask[acumulatedMask.length-1] && indexOfToken >= patternOption.length()-1){
                //System.out.println("Matches!!");
                cuantityOfMatchesOnToken++;
            }
        }
        
        if(cuantityOfMatchesOnToken > 0){
            if(cuantityOfMatchesInLine == 0){
                cuantityOfMatchesInLine++;
            }
            this._WordAppearances++;
            //this._MatchLineInfo.add("pDirectoryName + "/" + pFileName + " in line: " + pFileLine + " on this word: " + pToken +" " + cuantityOfMatchesOnToken + " times.");
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
