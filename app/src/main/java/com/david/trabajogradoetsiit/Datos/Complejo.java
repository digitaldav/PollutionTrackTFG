package com.david.trabajogradoetsiit.Datos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**

 * Esta clase define objetos que representan Complejos industriales

 * @author: David Martínez Vázquez

 */
public class Complejo implements Serializable {

    private int id_complejo;
    private String nombre;
    private String empresa;
    private String actividad;
    private String direccion;
    private String cp;
    private String municipio;
    private String provincia;
    private String comunidad;
    private float latitud;
    private float longitud;
	
    private List<Contaminante> contaminantes;

/**
     * Constructor de Complejo

     * @param id_complejo Representa el id del complejo
	 * @param nombre Nombre del complejo
	 * @param empresa Empresa propietaria del complejo
	 * @param actividad Actividad que realiza el complejo
	 * @param direccion Calle en la que se encuentra el complejo
	 * @param cp Codigo postal donde se encuentra el complejo
	 * @param municipio Municipio donde se encuentra el complejo
	 * @param provincia Provincia donde se encuentra el complejo
	 * @param comunidad Comunidad Autonoma donde se encuentra el complejo
	 * @param latitud Latitud del complejo
	 * @param longitud Longitud del complejo
	
     */
    public Complejo(int id_complejo, String nombre, String empresa, String actividad, String direccion, String cp , String municipio, String provincia, String comunidad, float latitud, float longitud) {
        this.id_complejo = id_complejo;
        this.nombre = nombre;
        this.empresa = empresa;
        this.actividad = actividad;
        this.cp = cp;
        this.direccion = direccion;
        this.municipio = municipio;
        this.provincia = provincia;
        this.latitud = latitud;
        this.comunidad = comunidad;
        this.longitud = longitud;
    }

	
	/**

     * Devuelve el id del complejo

     * @return id del complejo asociado al contaminante 

     */
    public int getId_complejo() {
        return id_complejo;
    }

	
	/**

     * Asigna el id de complejo
	 
     * @param id_complejo Representa el id del complejo

     */	
    public void setId_complejo(int id_complejo) {
        this.id_complejo = id_complejo;
    }

	
	/**

     * Devuelve el nombre del complejo

     * @return nombre del complejo

     */
    public String getNombre() {
        return nombre;
    }

	/**

     * Asigna el nombre al complejo

     * @param nombre Nombre del complejo nuevo

     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	/**

     * Devuelve el nombre de la empresa propietaria del complejo

     * @return nombre de la empresa

     */
    public String getEmpresa() {
        return empresa;
    }

	/**

     * Asigna empresa propietaria del complejo

     * @param empresa Nombre de empresa

     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

	/**

     * Devuelve la direccion del complejo

     * @return direccion del complejo

     */
    public String getDireccion() {
        return direccion;
    }

	/**

     * Asigna direccion al complejo

     * @param direccion Nueva direccion a asignar

     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

	/**

     * Devuelve la actividad del complejo

     * @return actividad que se realiza en el complejo 

     */
    public String getActividad() {
        return actividad;
    }

	/**

     * Asigna actividad al complejo

     * @param actividad a asignar al complejo

     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

	/**

     * Devuelve el codigo postal del complejo

     * @return codigo postal de donde se encuentra el complejo

     */
    public String getCp() {
        return cp;
    }

	/**

     * Asigna el codigo postal

     * @cp Codigo postal a asignar 

     */
    public void setCp(String cp) {
        this.cp = cp;
    }

	
	/**

     * Devuelve el municipio del complejo

     * @return municipio del complejo

     */
    public String getMunicipio() {
        return municipio;
    }

	/**

     * Asignar municipio al complejo

     * @param municipio Municipio que se va a asignar al complejo

     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

	
	/**

     * Devuelve la provincia del complejo

     * @return provincia del complejo

     */
    public String getProvincia() {
        return provincia;
    }

	/**

     * Asigna la provincia del complejo

     * @param provincia Provincia a asignar al complejo 

     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

	
	/**

     * Devuelve la provincia del complejo

     * @return provincia del complejo 

     */
    public String getComunidad() {
        return comunidad;
    }

	
	/**

     * Asigna una comunidad autonoma al complejo

     * @param comunidad Comunidad a asignar al complejo

     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

	
	/**

     * Devuelve la latitud del complejo

     * @return latitud del complejo 

     */
    public float getLatitud() {
        return latitud;
    }

	
	/**

     * Asigna la latitud del complejo

     * @param latitud Latitud a asignar al complejo 

     */
    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

	
	/**

     * Devuelve la longitud del complejo

     * @return longitud del complejo 

     */
    public float getLongitud() {
        return longitud;
    }

	
	/**

     * Asigna la longitud del complejo

     * @param longitud Longitud a asignar al complejo 

     */
    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

	/**

     * Convierte toda la informacion de Complejo en una cadena de texto

     * @return cadena de texto con toda la informacion

     */
    @Override
    public String toString() {
        return "Complejo{" +
                "id_complejo=" + id_complejo +
                ", nombre='" + nombre + '\'' +
                ", empresa='" + empresa + '\'' +
                ", actividad='" + actividad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cp='" + cp + '\'' +
                ", municipio='" + municipio + '\'' +
                ", provincia='" + provincia + '\'' +
                ", comunidad='" + comunidad + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }


	/**

     * Agrega un contaminante al complejo (emision o residuo)

     * @param c Contaminante a agregar

     */
    public void addContaminante(Contaminante c){
        if(contaminantes == null){
            contaminantes = new ArrayList<>();
        }
        contaminantes.add(c);
    }

	/**

     * Obtiene una emision del complejo en caso de que exista

     * @return emision del complejo

     */
    public Emision getEmision(){
        Emision em = null;
        if(contaminantes!=null) {
            for (Contaminante c : contaminantes) {
                if (c.getClass().equals(Emision.class)) {
                    em = (Emision) c;
                }
            }
        }

        return em;
    }

	
	/**

     * Obtiene un residuo del complejo en caso de que exista

     * @return residuo del complejo

     */
    public Residuo getResiduo(){
        Residuo em = null;
        if(contaminantes!=null) {
            for (Contaminante c : contaminantes) {
                if (c.getClass().equals(Residuo.class)) {
                    em = (Residuo) c;
                }
            }
        }
        return em;
    }



}
