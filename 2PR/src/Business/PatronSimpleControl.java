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

/**
 *
 * @author eavendano
 */
public class PatronSimpleControl {
    
    //Declaracion de variables
    DirectoryProcessor _DirectoryProcessor;
    String _DirectoryPath = ".";

    //Constructor
    public PatronSimpleControl(String pDirectoryPath) {
        this._DirectoryPath = pDirectoryPath;
        this._DirectoryProcessor = new DirectoryProcessor(this._DirectoryPath);
    }
    
    
    public void EjecutarBusqueda(){
        this._DirectoryProcessor.ReadDirectory();
    }
    
    
    
    
}
