package com.david.trabajogradoetsiit.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**

 * Esta clase actua como DAO
 * Realiza operaciones con la base de datos
 
 * @author: David Martínez Vázquez

 */
public class ComplejoDataSource {

    private SQLiteDatabase db;
    private DatabaseLoader dbHelper;
    private String[] columnasComplejos = { DatabaseLoader.TablaComplejos.ID, DatabaseLoader.TablaComplejos.NOMBRE, DatabaseLoader.TablaComplejos.EMPRESA,
            DatabaseLoader.TablaComplejos.ACTIVIDAD, DatabaseLoader.TablaComplejos.DIRECCION, DatabaseLoader.TablaComplejos.CP, DatabaseLoader.TablaComplejos.MUNICIPIO,
            DatabaseLoader.TablaComplejos.PROVINCIA, DatabaseLoader.TablaComplejos.COMUNIDAD, DatabaseLoader.TablaComplejos.LATITUD, DatabaseLoader.TablaComplejos.LONGITUD};

	/*
     * Constructor de CompejoDataSource
	 * Recibe una referencia del Context para acceder a assets y recursos de aplicación
     * @param context Referencia a Context para acceso a recursos
     */		
    public ComplejoDataSource(Context context) {
        dbHelper = new DatabaseLoader(context);
    }

	/**
	
     * Crea la base de datos
     
	 */
    public void crear(){
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/*
     * Abre la conexion con la base de datos
     */
    public void open() {
        db = dbHelper.getWritableDatabase();
    }

	/*
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }

	/**

     * Obtiene todos los Complejos de la base de datos

     * @return lista de Complejo con todos los objetos Complejo

     */
    public List<Complejo> getAllComplejos() {
        List<Complejo> listaComplejos = new ArrayList<Complejo>();

        Cursor cursor = db.query(DatabaseLoader.TablaComplejos.TABLA_COMPLEJOS, columnasComplejos, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Complejo nuevo = getComplejoCursor(cursor);
            listaComplejos.add(nuevo);
            cursor.moveToNext();
        }

        cursor.close();
        return listaComplejos;
    }


	/**

     * Obtiene un objeto complejo a partir de un objeto Cursor

	 * @param cursor Cursor con los datos de complejo tras una consulta
	 
     * @return objeto Complejo

     */
    private Complejo getComplejoCursor(Cursor cursor) {
        Complejo com = new Complejo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8),  Float.parseFloat(cursor.getString(9)),  Float.parseFloat(cursor.getString(10)) );

        String query = "select * From emisiones where id_complejo= ? ";
        Cursor c = db.rawQuery(query, new String[] { Integer.toString(com.getId_complejo()) });

        c.moveToFirst();
        if(!c.isAfterLast()){
            com.addContaminante(new Emision( c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4) ));
        }
        c.close();
        query = "select * From residuos where id_complejo= ? ";
        c = db.rawQuery(query, new String[] { Integer.toString(com.getId_complejo()) });

        c.moveToFirst();
        if(!c.isAfterLast()){
            com.addContaminante(new Residuo( c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4) ));
        }
        c.close();

        return com;
    }
	
	
	/**

     * Obtiene una lista con todas las provincias

     * @return lista con todas las provincias

     */
    public List<String> getProvincias() {
        List<String> lista = new ArrayList<String>();

        String query = "select provincia From complejos ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return ordenarString(lista);

    }

	
	/**

     * Obtiene una lista con todas los municipios

     * @return lista con todas los municipios

     */
    public List<String> getMunicipios() {
        List<String> lista = new ArrayList<String>();

        String query = "select municipio From complejos ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return ordenarString(lista);

    }

	/**

     * Obtiene una lista con todas las comunidades autonomas

     * @return lista con todas las comunidades autonomas

     */
    public List<String> getCCAA() {
        List<String> lista = new ArrayList<String>();

        String query = "select comunidad From complejos ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return ordenarString(lista);

    }

	/**

     * Obtiene una lista con todos los complejos de un lugar

	 * @param tipo Representa que tipo de lugar es, por ejemplo provincia
	 
	 * @param lugar Lugar del que queremos saber los complejos
	 
     * @return lista con los complejos

     */
    public List<Complejo> getComplejosLugar(String tipo, String lugar) {
        List<Complejo> lista = new ArrayList<Complejo>();

        String query;
        if(tipo.equals("provincia")) {
            query = "select * From complejos where provincia= ? ";
        }else if(tipo.equals("comunidad")){
            query = "select * From complejos where comunidad= ? ";
        }else{
            query = "select * From complejos where municipio= ? ";
        }
        Cursor cursor = db.rawQuery(query, new String[] {lugar});

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                lista.add(getComplejoCursor(cursor));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return lista;
    }

	/**

     * Devuelve una lista de String sin repeticiones y ordenada alfabeticamente

	 * @param lista Lista de string desordenada
	 
     * @return lista ordenada

     */
    public List<String> ordenarString(List<String> lista){
        HashSet hs = new HashSet();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        hs.clear();
        java.util.Collections.sort(lista);
        return lista;
    }


}
