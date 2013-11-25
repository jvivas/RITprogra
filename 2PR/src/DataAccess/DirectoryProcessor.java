/*
 * Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package DataAccess;
import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author eavendano
 */
public class DirectoryProcessor {
    
    //Declaracion de variables
    String _DirectoryPath = ".";
    int _OpenDirectorySuccess = 0;
    int _AmountFilesFound = 0;
    ArrayList<String> _SubDirectories = new ArrayList<String>();
    ArrayList<String> _FilesInDirectory = new ArrayList<String>();
    
    String files;
    File _Folder = new File(_DirectoryPath);
    File[] _ListOfFiles = _Folder.listFiles(); 

    // Constructor de la clase
    public DirectoryProcessor(String pDirectoryPath) {
        if(pDirectoryPath.equals("")){
            _OpenDirectorySuccess = -1;
        } else {
            try {
                _DirectoryPath = pDirectoryPath;
                _Folder = new File(_DirectoryPath);
                _ListOfFiles = _Folder.listFiles();
                _OpenDirectorySuccess = 0;
            } catch (Exception e) {
                _OpenDirectorySuccess = -1;
                e.printStackTrace();
            }
        }
    }

    //Metodo para cargar los directorios que tiene el directorio
    public void ReadDirectory(){
        _AmountFilesFound = 0;
        for (int i = 0; i < _ListOfFiles.length; i++) 
        {

         if (_ListOfFiles[i].isFile()) 
         {
           //System.out.println("File Found: " + _ListOfFiles[i].getName());
           _AmountFilesFound++;
           _FilesInDirectory.add(_ListOfFiles[i].getName());
         } else if(_ListOfFiles[i].isDirectory()){
           _SubDirectories.add(_ListOfFiles[i].getName());
             System.out.println(_ListOfFiles[i].getName());
         }
        }
        //System.out.println("Amount of Files Found: " + _AmountFilesFound);
    }

    public void SortFilesList(){
        Collections.sort(_FilesInDirectory);
    }
    
    public int getOpenDirectorySuccess() {
        return _OpenDirectorySuccess;
    }

    public ArrayList<String> getSubDirectories() {
        return _SubDirectories;
    }

    public ArrayList<String> getFilesInDirectory() {
        SortFilesList();
        return _FilesInDirectory;
    }
    
    
    
}
