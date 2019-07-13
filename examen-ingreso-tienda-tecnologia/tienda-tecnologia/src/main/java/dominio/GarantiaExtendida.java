package dominio;

import java.util.Calendar;
import java.util.Date;

public class GarantiaExtendida {

	private Producto producto;
	private Date fechaSolicitudGarantia;
	private Date fechaFinGarantia;
	private double precioGarantia;
	private String nombreCliente;

	public GarantiaExtendida(Producto producto) {
		this.fechaSolicitudGarantia = new Date();
		this.producto = producto;
	}

	public GarantiaExtendida(Producto producto, Date fechaSolicitudGarantia, Date fechaFinGarantia,
			double precioGarantia, String nombreCliente) {

		this.producto = producto;
		this.fechaSolicitudGarantia = fechaSolicitudGarantia;
		this.fechaFinGarantia = fechaFinGarantia;
		this.precioGarantia = precioGarantia;
		this.nombreCliente = nombreCliente;
	}

	
	public Producto getProducto() {
		return producto;
	}

	public Date getFechaSolicitudGarantia() {
		return fechaSolicitudGarantia;
	}

	public Date getFechaFinGarantia() {
		return fechaFinGarantia;
	}

	public double getPrecioGarantia() {
		return precioGarantia;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public Date calcularFechaActual(){
		Date fechaActual = new Date();
		return fechaActual;
	}
	
	public Date calcularFechaFin(int cantDias){
		//tomar 200 días para mayor 500
		//else 100 días
		//si es domingo sumar 2
		//no contar lunes
		Date fechaFin;
		Calendar now = Calendar.getInstance();
		
		for (int i = 0; i < cantDias; i++) {
			if (!(now.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)) {
				now.add(Calendar.DAY_OF_WEEK,1);
				System.out.println(now.getTime());

			}
		}
		
		return null;
	}
}
