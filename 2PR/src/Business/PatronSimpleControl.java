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

/**
 *
 * @author eavendano
 */
public class PatronSimpleControl {
    
    //Declaracion de variables
    DirectoryProcessor _DirectoryProcessor;
    FileRead _FileRead;
    String _DirectoryPath = ".";
    String _UserPattern = "";
    boolean _CaseSensitive = false;

    //Constructor
    public PatronSimpleControl(String pDirectoryPath, String pUserPattern) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
        this._UserPattern = pUserPattern;
        this._CaseSensitive = GetIgnoreCase();
    }
    
    
    public boolean ValidatePattern(){
        boolean patternResult = true;
        return patternResult;
    }
    
    public boolean GetIgnoreCase(){
        boolean ignoreCaseResult = false;
        String lastChars = this._UserPattern.substring(this._UserPattern.length()-2, this._UserPattern.length());
        if(lastChars.equals("@i")){
            ignoreCaseResult = true;
            this._UserPattern = this._UserPattern.substring(0, this._UserPattern.length()-2);
        }
        return ignoreCaseResult;
    }
    
    public void EjecutarBusqueda() throws FileNotFoundException, IOException{
        int openDirectorySuccess = _DirectoryProcessor.getOpenDirectorySuccess();
        if(openDirectorySuccess == 0){
            //Tuvo éxito
            this._DirectoryProcessor.ReadDirectory();
            ArrayList<String> subFiles = _DirectoryProcessor.getFilesInDirectory();
            //Por cada archivo en el directorio
            for(int subFile = 0; subFile < subFiles.size(); subFile++){
                System.out.println("Procesar Archivo:" + subFiles.get(subFile) + " " +subFile);
                String fileName = subFiles.get(subFile);
                _FileRead = new FileRead(_DirectoryPath+"/"+fileName);
                if(_FileRead.getFileOpenSuccess() == 0){
                    _FileRead.ReadLines();
                    if(_FileRead.getFileReadSuccess() == 0){
                        ProcessFileLines(_FileRead.getFileLines(),_DirectoryPath,subFiles.get(subFile));
                    } else {
                        System.err.println("No se pudo leer el archivo: " + fileName + "\n");
                    }
                } else {
                    System.err.println("No se pudo abrir el archivo: " + fileName + "\n");
                }
            }
        } else {
            //Ocurrio un error y no se pudo abrir el directorio
        }
        
        System.out.println("Final de la Ejecucion de la busqueda.");
    }
    
    //Metodo para procesar las lineas de texto del archivo
    public void ProcessFileLines(ArrayList<String> pFileLines, String pDirectoryName, String pFileName){
        //Por cada linea del archivo separarla para luego procesar los patrones
        for(int fileLineNumber = 0; fileLineNumber < pFileLines.size(); fileLineNumber++){
            if(fileLineNumber == 0){
                String[] tokenList;
                String fileLine = pFileLines.get(fileLineNumber);
                tokenList = fileLine.split("\\s++");
                for(int tokenIndex = 0; tokenIndex < tokenList.length; tokenIndex++){
                    String tokenPrepared = PrepareToken(tokenList[tokenIndex]);
                    System.out.println("token: " + tokenPrepared + "\n");
                }
            }
        }
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

    //Metodo de busqueda para el patron horsepool
    public void HorsepoolMethod(){
        
    }
    
    
}
