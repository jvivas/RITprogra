/*
 * Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package DataAccess;
//import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author eavendano
 */
public class GenerateExitFile {
    
    //Declaracion de variables
    private String _FileName;
    private ArrayList<String> _FileLines;
    ArrayList<Integer> _WordApearances = new ArrayList<Integer>();
    ArrayList<Integer> _MatchesInFileLIne = new ArrayList<Integer>();

    
    public GenerateExitFile(String _FileName) {
        this._FileName = _FileName;
    }
    
    
    public void setFileLines(ArrayList<String> pFileLines) {
        this._FileLines = pFileLines;
    }
    
    public void setFileName(String _FileName) {
        this._FileName = _FileName;
    }

    
    public void SaveFile() throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(this._FileName, "UTF-8");
         int appearancesIndex = 0;
        for(int i = 0; i < _FileLines.size(); i++){
            if(_FileLines.get(i).equals("--")){
                writer.println("\nGrand Total: " + this._MatchesInFileLIne.get(appearancesIndex) + " match(es) found."+ "\n************************\n\n");
                appearancesIndex++;
            } else {
                writer.println(_FileLines.get(i));
            }
        }
        writer.close();
    }
    
    
    public void InsertWordAppearances(ArrayList<Integer> wordAppeareances) {
        this._WordApearances = wordAppeareances;
    }

    public void setMatchesInFileLIne(ArrayList<Integer> _MatchesInFileLIne) {
        this._MatchesInFileLIne = _MatchesInFileLIne;
    }
}
