package com.david.trabajogradoetsiit.Datos;


import java.io.Serializable;

 /**

 * Esta clase define objetos contaminantes asociados a complejos

 * @author: David Martínez Vázquez

 */
public class Contaminante implements Serializable {

    private int id_complejo;
    private String anio;
    private String tipo;
    private String cantidad;

	/**

     * Constructor de Contaminante

     * @param id_complejo Representa el id del complejo que tiene el contaminante
	 * @param anio El anio en el que se produce el contaminante
	 * @param tipo Representa el tipo de contaminante
	 * @param cantidad Cantidad de contaminante 

     */
    public Contaminante(int id_complejo, String anio, String tipo, String cantidad)  {
        this.id_complejo = id_complejo;
        this.anio = anio;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

	/**

     * Devuelve el id del complejo

     * @return id del complejo asociado al contaminante 

     */
    public int getId_complejo() {
        return id_complejo;
    }


	/**

     * Asigna id de complejo al contaminante

     * @param id_complejo Representa el id del complejo que tiene el contaminante

     */	
    public void setId_complejo(int id_complejo) {
        this.id_complejo = id_complejo;
    }
	
	/**

     * Devuelve el id del complejo

     * @return id del complejo asociado al contaminante 

     */
    public String getAnio() {
        return anio;
    }

	
	/**

     * Asigna anio al contaminante

     * @param anio Representa el anio en el que se produce la contaminacion

     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

	/**

     * Devuelve el tipo de contaminante

     * @return tipo de contamiante

     */
    public String getTipo() {
        return tipo;
    }

	
	/**

     * Asigna el tipo de contaminante

     * @param tipo Representa el tipo de contaminante

     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

	/**

     * Devuelve la cantidad de contaminacion producida

     * @return cantidad de contaminante

     */
    public String getCantidad() {
        return cantidad;
    }

	
	/**

     * Asigna cantidad de contaminación

     * @param cantidad Representa la cantidad de contaminante a asignar

     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


}
