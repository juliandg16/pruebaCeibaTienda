package dominio.unitaria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import dominio.Vendedor;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {
	private static final String CODIGO = "S01H1AT51";//S01H1AT51 A01H1AU51
	private static final String NOMBRE_CLIENTE = "Daniela Zuluaga";

	@Test
	public void productoYaTieneGarantiaTest() {

		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();

		Producto producto = productoTestDataBuilder.build();

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);

		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		// act
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());

		// assert
		assertTrue(existeProducto);
	}

	@Test
	public void productoNoTieneGarantiaTest() {

		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();

		Producto producto = productoestDataBuilder.build();

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);

		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);

		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		// act
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());

		// assert
		assertFalse(existeProducto);
	}

	@Test
	public void validarPrecioMayorTest() {
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();

		Producto producto = productoestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		
		// act
		vendedor.generarGarantia(CODIGO, NOMBRE_CLIENTE);
		double precioFinal = repositorioGarantia.obtener(CODIGO).getPrecioGarantia();
		double precioProducto = producto.getPrecio() * 0.2;
		boolean precioMayor = (precioProducto == precioFinal);
		// assert
		assertTrue(precioMayor);

	}

	@Test
	public void validarPrecioMenorTest() {
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();

		Producto producto = productoestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		
		// act
		vendedor.generarGarantia(CODIGO, NOMBRE_CLIENTE);
		double precioFinal = repositorioGarantia.obtener(CODIGO).getPrecioGarantia();
		double precioProducto = producto.getPrecio() * 0.1;
		boolean precioMenor = (precioProducto == precioFinal);
		// assert
		assertTrue(precioMenor);
	}
}
