package cl.mercados.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.mercados.ws.domain.Articulo;
import cl.mercados.ws.domain.Producto;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.ProductoType;
import cl.mercados.ws.dto.v10.InputProductoActualizar;
import cl.mercados.ws.dto.v10.InputProductoCrear;
import cl.mercados.ws.dto.v10.OutputArticuloObtener;
import cl.mercados.ws.dto.v10.OutputProductoConsultar;
import cl.mercados.ws.dto.v10.OutputProductoCrear;
import cl.mercados.ws.dto.v10.OutputProductoObtener;
import cl.mercados.ws.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {

	private final ProductoService productoService;
	
	@Autowired
	public ProductoController(final ProductoService productoService) {
		this.productoService = productoService;
	}
	
	@PostMapping
	ResponseEntity<OutputProductoCrear> crear(@RequestBody InputProductoCrear inputDTO) {
		JaxbUtil.validar(InputProductoCrear.class, inputDTO);
		
		final Producto producto = productoService.crear(inputDTO);
		
		final OutputProductoCrear outputDTO = new OutputProductoCrear();
		outputDTO.setId(producto.getId());
		outputDTO.setCod(producto.getCod());
		outputDTO.setNombre(producto.getNombre());
		outputDTO.setDescr(producto.getDescr());
		outputDTO.setPrecio(producto.getPrecio());
		outputDTO.setProveedorId(producto.getProveedor().getId());
		
		JaxbUtil.validar(OutputProductoCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputProductoConsultar> consultar(@RequestParam(name = "cod", required = false) final String cod) {
		final OutputProductoConsultar outputDTO = new OutputProductoConsultar();
		final List<Producto> productos = productoService.consultar(cod);
		for (Producto producto : productos) {
			ProductoType productoType = new ProductoType();
			productoType.setId(producto.getId());
			productoType.setCod(producto.getCod());
			productoType.setNombre(producto.getNombre());
			productoType.setDescr(producto.getDescr());
			productoType.setPrecio(producto.getPrecio());
			productoType.setProveedorId(producto.getProveedor().getId());
			outputDTO.getRegistro().add(productoType);
		}
		
		JaxbUtil.validar(OutputProductoConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputProductoObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputProductoObtener outputDTO = new OutputProductoObtener();
		final Producto producto = productoService.obtener(id);
		outputDTO.setId(producto.getId());
		outputDTO.setCod(producto.getCod());
		outputDTO.setNombre(producto.getNombre());
		outputDTO.setDescr(producto.getDescr());
		outputDTO.setPrecio(producto.getPrecio());		
		outputDTO.setProveedorId(producto.getProveedor().getId());
		
		JaxbUtil.validar(OutputProductoObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}	
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputProductoActualizar inputDTO) {
		JaxbUtil.validar(InputProductoActualizar.class, inputDTO);
		return ResponseEntity.ok(productoService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(productoService.eliminar(id));
	}	
}
