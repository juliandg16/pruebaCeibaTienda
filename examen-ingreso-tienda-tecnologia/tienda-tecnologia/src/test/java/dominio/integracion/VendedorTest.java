package dominio.integracion;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Vendedor;
import dominio.Producto;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {

	private static final String COMPUTADOR_LENOVO = "Computador Lenovo";
	private static final String NOMBRE_CLIENTE = "Daniela Zuluaga";
	private static final String CODIGO = "A01H1AU51";// Codigo con 3 vocales
	private static final String CODIGO2 = "S01H1AT51";// C�digo con precio garantia mayor a 500

	private SistemaDePersistencia sistemaPersistencia;

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	@Before
	public void setUp() {

		sistemaPersistencia = new SistemaDePersistencia();

		repositorioProducto = sistemaPersistencia.obtenerRepositorioProductos();
		repositorioGarantia = sistemaPersistencia.obtenerRepositorioGarantia();

		sistemaPersistencia.iniciar();
	}

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	@Test
	public void generarGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		// act
		vendedor.generarGarantia(producto.getCodigo(), NOMBRE_CLIENTE);

		// assert
		Assert.assertTrue(vendedor.tieneGarantia(producto.getCodigo()));
		Assert.assertNotNull(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo()));

	}

	@Test
	public void productoYaTieneGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();

		repositorioProducto.agregar(producto);

		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		// act
		vendedor.generarGarantia(producto.getCodigo(), NOMBRE_CLIENTE);
		
		try {

			vendedor.generarGarantia(producto.getCodigo(), NOMBRE_CLIENTE);
			fail();

		} catch (GarantiaExtendidaException e) {
			// assert
			Assert.assertEquals(Vendedor.EL_PRODUCTO_TIENE_GARANTIA, e.getMessage());
		}
	}

	@Test
	public void validarExcepcionGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conCodigo(CODIGO).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		// act
		try {
			vendedor.generarGarantia(producto.getCodigo(), NOMBRE_CLIENTE);
		} catch (GarantiaExtendidaException e) {
			// assert
			Assert.assertEquals(Vendedor.EL_PRODUCTO_NO_TIENE_GARANTIA, e.getMessage());
		}
	}
	
	@Test
	public void validarPrecioMayorTest() {
		// arrange

		Producto producto = new ProductoTestDataBuilder().conCodigo(CODIGO2).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);		
		
		// act
		vendedor.generarGarantia(CODIGO2, NOMBRE_CLIENTE);
		
		double precioFinal = repositorioGarantia.obtener(CODIGO2).getPrecioGarantia();
		double precioProducto = producto.getPrecio() * 0.2;
		
		boolean precioMayor = (precioProducto == precioFinal);
		// assert
		assertTrue(precioMayor);

	}
	@Test
	public void validarPrecioMenorTest() {
		// arrange
		
		Producto producto = new ProductoTestDataBuilder().conCodigo(CODIGO2).conPrecio(480000).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);		
		
		// act
		vendedor.generarGarantia(CODIGO2, NOMBRE_CLIENTE);
		
		double precioFinal = repositorioGarantia.obtener(CODIGO2).getPrecioGarantia();
		double precioProducto = producto.getPrecio() * 0.1;
		
		boolean precioMayor = (precioProducto == precioFinal);
		// assert
		assertTrue(precioMayor);

	}
	
}
