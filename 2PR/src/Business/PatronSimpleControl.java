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

    //Constructor
    public PatronSimpleControl(String pDirectoryPath) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
    }
    
    
    public void EjecutarBusqueda() throws FileNotFoundException, IOException{
        int openDirectorySuccess = _DirectoryProcessor.getOpenDirectorySuccess();
        if(openDirectorySuccess == 0){
            //Tuvo éxito
            this._DirectoryProcessor.ReadDirectory();
            ArrayList<String> subDirectories = _DirectoryProcessor.getSubDirectories();
            DirectoryProcessor subDirectoryProcessor;
            //Por cada Subdirectorio en el Directorio seleccionado
            for(int subDirectory = 0; subDirectory < subDirectories.size(); subDirectory++){
                subDirectoryProcessor = new DirectoryProcessor(subDirectories.get(subDirectory));
                subDirectoryProcessor.ReadDirectory();
                ArrayList<String> subFiles = subDirectoryProcessor.getFilesInDirectory();
                //POr cada archivo en el subdirectorio
                for(int subFile = 0; subFile < subFiles.size(); subFile++){
                    String fileName = subFiles.get(subFile);
                    _FileRead = new FileRead(fileName);
                    _FileRead.ReadLines();
                    ProcessFileLines(_FileRead.getFileLines(),subDirectories.get(subDirectory),subFiles.get(subFile));
                }
            }
        } else {
            //Ocurrio un error mientras se le
        }
    }
    
    //Metodo para procesar las lineas de texto del archivo
    public void ProcessFileLines(ArrayList<String> pFileLines, String pDirectoryName, String pFileName){
        //Por cada linea del archivo separarla para luego procesar los patrones
        for(int fileLine = 0; fileLine < pFileLines.size(); fileLine++){
            System.out.println(pFileLines.get(fileLine)+"\n");
        }
    }
    
    //Metodo de busqueda para el patron horsepool
    public void HorsepoolMethod(){
        
    }
    
    
}
