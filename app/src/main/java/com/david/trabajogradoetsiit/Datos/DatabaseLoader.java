package com.david.trabajogradoetsiit.Datos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**

 * Esta clase tiene los metodos necesarios para la conexion con la base de datos
 * Tambien permite cargar la base de datos en caso de que no exista

 */
public class DatabaseLoader extends SQLiteOpenHelper{
    
	//localizacion de la base de datos
    private static String DB_PATH = "/data/data/com.david.trabajogradoetsiit/databases/";
    
	//nombre de la base de datos
    private static final String DATABASE_NAME = "tfg.db";
    
	//Version de la base de datos
    private static final int DATABASE_VERSION = 1;

    public Context context;
    static SQLiteDatabase sqliteDataBase;


    public static class TablaComplejos{
        public static String TABLA_COMPLEJOS = "complejos";
        public static String ID = "id_complejo";
        public static String NOMBRE = "nombre";
        public static String EMPRESA = "empresa";
        public static String ACTIVIDAD = "actividad";
        public static String DIRECCION = "direccion";
        public static String CP = "cp";
        public static String MUNICIPIO = "municipio";
        public static String PROVINCIA = "provincia";
        public static String COMUNIDAD = "comunidad";
        public static String LATITUD = "latitud";
        public static String LONGITUD = "longitud";
    }

    public static class TablaEmision{
        public static String TABLA_EMISIONES = "emisiones";
        public static String ID = "id_complejo";
        public static String ANIO = "año";
        public static String CONTAMINANTE = "contaminante";
        public static String CANTIDAD = "cantidad";
        public static String MEDIO = "medio";
    }

    public static class TablaResiduo{
        public static String TABLA_RESIDUOS = "residuos";
        public static String ID = "id_complejo";
        public static String ANIO = "año";
        public static String CONTAMINANTE = "contaminante";
        public static String CANTIDAD = "cantidad";
        public static String TRATAMIENTO = "tratamiento";
    }


    /*
     * Constructor de DatabaseLoader
	 * Recibe una referencia del Context para acceder a assets y recursos de aplicación
     * @param context Referencia a Context para acceso a recursos
     */
    public DatabaseLoader(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
    }

    /**
	
     * Crea la base de datos en caso de que no exista.
     
	 */
    public void createDataBase() throws IOException{
		//comprobar que exista
        boolean databaseExist = checkDataBase();

        if(databaseExist){ 
		//si no existe no hace nada
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }
    }

    /*
     * Comprueba que la base de datos existe en DB_PATH+DATABASE_NAME
     * @return true si existe, false en caso contrario
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /*
	 * Metodo que carga y copia la base de datos a DB_PATH
     */
    private void copyDataBase() throws IOException{
		
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
		
        String outFileName = DB_PATH + DATABASE_NAME;
		
        OutputStream myOutput = new FileOutputStream(outFileName);
		
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /*
     * Abre la conexion con la base de datos
     */
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /*
     * Cierra la conexion con la base de datos
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}