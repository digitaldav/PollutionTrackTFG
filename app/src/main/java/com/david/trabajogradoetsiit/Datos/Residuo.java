package com.david.trabajogradoetsiit.Datos;

/**

 * Esta clase define objetos que representan residuos

 * @author: David Martínez Vázquez

 */
public class Residuo extends Contaminante {

    private String tratamiento;

	
	/**
     * Constructor de Residuo

     * @param id_complejo Representa el id del complejo que tiene el residuo
	 * @param anio El anio en el que se produce el residuo
	 * @param tipo Representa el tipo de residuo
	 * @param cantidad Cantidad de residuo
	 * @param tratamiento Representa el tratamiento que se ha dado al residuo
	
     */
    public Residuo(int id_complejo, String anio, String tipo, String cantidad, String tratamiento) {
        super(id_complejo, anio, tipo, cantidad);
        this.tratamiento = tratamiento;
    }

	/**

     * Devuelve el tratamiento que se da al residuo 

     * @return tratamiento que se le ha dado al residuo

     */
    public String getTratamiento() {
        return tratamiento;
    }

	
	/**

     * Asigna el tratamiento que se da al residuo

     * @param tratamiento Representa el tratamiento a asignar

     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

	/**

     * Convierte toda la informacion de Residuo en una cadena de texto

     * @return cadena de texto con toda la informacion

     */
    @Override
    public String toString() {
        return "Residuo{" +
                "id_complejo=" + getId_complejo() +
                ", anio='" + getAnio() + '\'' +
                ", tipo='" + getTipo() + '\'' +
                ", cantidad='" + getCantidad() + '\'' +
                ", tratamiento='" + getTratamiento() + '\'' +
                '}';
    }

}
