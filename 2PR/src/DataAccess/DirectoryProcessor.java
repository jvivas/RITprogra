/*
 * Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package DataAccess;
import java.io.*;

/**
 *
 * @author eavendano
 */
public class DirectoryProcessor {
    
    String _DirectoryPath = ".";
    int _OpenDirectorySuccess = 0;
    
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
        for (int i = 0; i < _ListOfFiles.length; i++) 
        {

         if (_ListOfFiles[i].isFile()) 
         {
         files = _ListOfFiles[i].getName();
         System.out.println(files);
            }
        }
    }
}
