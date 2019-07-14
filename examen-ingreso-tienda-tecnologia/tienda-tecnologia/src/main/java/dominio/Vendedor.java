package dominio;

import dominio.repositorio.RepositorioProducto;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String EL_PRODUCTO_NO_TIENE_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final int PRECIO_PRODUCTO = 500000;
	public double porcentaje;
	public int cantidadDias;

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;
	private GarantiaExtendida garantiaExtendida;

	public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;

	}

	public void generarGarantia(String codigo, String nombreCliente) {
		int totalVocales = codigo.replaceAll("[^AEIOUaeiou]", "").length();
		Producto producto = repositorioProducto.obtenerPorCodigo(codigo);

		if (totalVocales == 3) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_NO_TIENE_GARANTIA);
		} else if (tieneGarantia(codigo)) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
		}
		if (producto.getPrecio() >= PRECIO_PRODUCTO) {
			porcentaje = 0.2;
			cantidadDias = 200;
			garantiaExtendida = new GarantiaExtendida(producto, calcularFechaActual(), calcularFechaFin(cantidadDias),
					producto.getPrecio() * porcentaje, nombreCliente);
			
			repositorioGarantia.agregar(garantiaExtendida);

		} else {
			porcentaje = 0.1;
			cantidadDias = 100;
			garantiaExtendida = new GarantiaExtendida(producto, calcularFechaActual(), calcularFechaFin(cantidadDias),
					producto.getPrecio() * porcentaje, garantiaExtendida.getNombreCliente());

			repositorioGarantia.agregar(garantiaExtendida);
		}
	}

	public boolean tieneGarantia(String codigo) {
		if (repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo) != null) {
			return true;
		}
		return false;
	}

	public Date calcularFechaActual() {
		Date fechaActual = new Date();
		return fechaActual;
	}

	public Date calcularFechaFin(int cantDias) {
		Calendar now = Calendar.getInstance();

		for (int i = 0; i < cantDias; i++) {
			if (!(now.get(Calendar.DAY_OF_WEEK) == 1)) {
				now.add(Calendar.DAY_OF_WEEK, 1);
			} else {
				now.add(Calendar.DAY_OF_WEEK, 1);
				i--;
			}
		}

		now.add(Calendar.DAY_OF_WEEK, -1);

		if ((now.get(Calendar.DAY_OF_WEEK) == 7)) {
			now.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		return now.getTime();
	}
}
