package com.david.trabajogradoetsiit.Datos;


import java.io.Serializable;

/**

 * Esta clase define objetos que representan emisiones

 * @author: David Martínez Vázquez

 */

public class Emision extends Contaminante implements Serializable {

    private String medio;

	/**
     * Constructor de Emision

     * @param id_complejo Representa el id del complejo que tiene la emision
	 * @param anio El anio en el que se produce la emision
	 * @param tipo Representa el tipo de emision
	 * @param cantidad Cantidad de emision
	 * @param medio Medio en el que se produce la emision
	
     */
    public Emision(int id_complejo, String anio, String tipo, String cantidad, String medio) {
        super(id_complejo, anio, tipo, cantidad);
        this.medio = medio;
    }

	
	/**

     * Devuelve el medio en el que emite 

     * @return medio que ha sido contaminado

     */
    public String getMedio() {
        return medio;
    }

	/**

     * Asigna el medio en el que emite 

     * @param medio Medio en el que se produce la emision

     */
    public void setMedio(String medio) {
        this.medio = medio;
    }

	/**

     * Convierte toda la informacion de Emision en una cadena de texto

     * @return cadena de texto con toda la informacion

     */
    @Override
    public String toString() {
        return "Emision{" +
                "id_complejo=" + getId_complejo() +
                ", anio='" + getAnio() + '\'' +
                ", tipo='" + getTipo() + '\'' +
                ", cantidad='" + getCantidad() + '\'' +
                ", medio='" + getMedio() + '\'' +
                '}';
    }

}
