package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Producto;
import cl.mercados.ws.domain.Proveedor;
import cl.mercados.ws.dto.v10.InputProductoActualizar;
import cl.mercados.ws.dto.v10.InputProductoCrear;
import cl.mercados.ws.repository.ProductoRepo;
import cl.mercados.ws.service.ProductoService;
import cl.mercados.ws.service.ProveedorService;

@Service
public class ProductoImpl implements ProductoService {
	
	private ProductoRepo productoRepo;
	private ProveedorService proveedorService;
	
	@Autowired
	public ProductoImpl (final ProductoRepo productoRepo, final ProveedorService proveedorService) {
		this.productoRepo = productoRepo;
		this.proveedorService = proveedorService;
	}
	
	@Override
	public Producto crear(InputProductoCrear inputDTO) {
		Proveedor Proveedor = proveedorService.obtener(inputDTO.getProveedorId());
		final Producto producto = new Producto();
		producto.setCod(inputDTO.getCod());
		producto.setNombre(inputDTO.getNombre());
		producto.setDescr(inputDTO.getDescr());
		producto.setPrecio(inputDTO.getPrecio());
		producto.setProveedor(Proveedor);
		return productoRepo.save(producto);
	}
	
	@Override
	public List<Producto> consultar(String cod) {
		if (cod == null) {
			return (List<Producto>) productoRepo.findAll();
		}
		return productoRepo.findAllByCod(cod);
	}
	
	@Override 
	public Producto obtener(Long id) {
		final Optional<Producto> _producto = productoRepo.findById(id);
		Assert.isTrue(_producto.isPresent(), "producto no existe");
		return _producto.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputProductoActualizar inputDTO) {
		final Producto producto = obtener(id);
		producto.setNombre(inputDTO.getNombre());
		producto.setDescr(inputDTO.getDescr());
		producto.setPrecio(inputDTO.getPrecio());
		productoRepo.save(producto);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Producto producto = obtener(id);
		productoRepo.delete(producto);
		return Boolean.TRUE;
	}
}
