/*
 * Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package DataAccess;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author eavendano
 */
public class FileRead {
    
    //Declaracion de variables de la clase.
    int _FileOpenSuccess = 0;
    int _FileReadSuccess = 0;
    BufferedReader _BufferedReader;
    DataInputStream _DataInputStream;
    ArrayList<String> _FileLines;
    
    // Constructor de la clase
    public FileRead(String pFilePath) throws FileNotFoundException {
        try{
        FileInputStream fstream = new FileInputStream(pFilePath);
        _DataInputStream = new DataInputStream(fstream);
        _BufferedReader = new BufferedReader(new InputStreamReader(_DataInputStream));
        _FileOpenSuccess = 0;
        } catch (FileNotFoundException e){
            _FileOpenSuccess = -1;
            e.printStackTrace();
        }
    }
    
    public void ReadLines() throws IOException{
        _FileLines = new ArrayList<String>();
        String strLine;
        strLine = "";
        try{
            while ((strLine = _BufferedReader.readLine()) != null)   {
                //Recuperar las lineas del archivo
                _FileLines.add(strLine);
            }
            _DataInputStream.close();
            _FileReadSuccess = 0;
        } catch (IOException e){
            _FileReadSuccess = -1;
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFileLines() {
        return _FileLines;
    }

    public int getFileOpenSuccess() {
        return _FileOpenSuccess;
    }

    public int getFileReadSuccess() {
        return _FileReadSuccess;
    }
    
    
    
}
