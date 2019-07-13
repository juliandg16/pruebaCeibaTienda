package dominio;

import dominio.repositorio.RepositorioProducto;
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

	public void generarGarantia(String codigo) {
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
			garantiaExtendida = new GarantiaExtendida(producto, garantiaExtendida.calcularFechaActual(),
					garantiaExtendida.calcularFechaFin(cantidadDias), producto.getPrecio() * porcentaje,
					garantiaExtendida.getNombreCliente());
		} else {
			porcentaje = 0.1;
			cantidadDias = 100;
			garantiaExtendida = new GarantiaExtendida(producto, garantiaExtendida.calcularFechaActual(),
					garantiaExtendida.calcularFechaFin(cantidadDias), producto.getPrecio() * porcentaje,
					garantiaExtendida.getNombreCliente());
		}
	}

	public boolean tieneGarantia(String codigo) {
		if (repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo) != null) {
			return true;
		}
		return false;
	}
}
