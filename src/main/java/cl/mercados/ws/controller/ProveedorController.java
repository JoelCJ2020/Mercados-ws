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

import cl.mercados.ws.domain.Cajadetalle;
import cl.mercados.ws.domain.Proveedor;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.ProveedorType;
import cl.mercados.ws.dto.v10.InputProveedorActualizar;
import cl.mercados.ws.dto.v10.InputProveedorCrear;
import cl.mercados.ws.dto.v10.OutputCajadetalleObtener;
import cl.mercados.ws.dto.v10.OutputProveedorConsultar;
import cl.mercados.ws.dto.v10.OutputProveedorCrear;
import cl.mercados.ws.dto.v10.OutputProveedorObtener;
import cl.mercados.ws.service.ProveedorService;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

	private final ProveedorService proveedorService;
	
	@Autowired
	public ProveedorController(final ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}
	
	@PostMapping
	ResponseEntity<OutputProveedorCrear> crear(@RequestBody InputProveedorCrear inputDTO) {
		JaxbUtil.validar(InputProveedorCrear.class, inputDTO);
		
		final Proveedor proveedor = proveedorService.crear(inputDTO);
		
		final OutputProveedorCrear outputDTO = new OutputProveedorCrear();
		outputDTO.setId(proveedor.getId());
		outputDTO.setRut(proveedor.getRut());
		outputDTO.setNombre(proveedor.getNombre());
		
		JaxbUtil.validar(OutputProveedorCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputProveedorConsultar> consultar(@RequestParam(name = "nombre", required = false) final String nombre) {
		final OutputProveedorConsultar outputDTO = new OutputProveedorConsultar();
		final List<Proveedor> proveedors = proveedorService.consultar(nombre);
		for (Proveedor proveedor : proveedors) {
			ProveedorType proveedorType = new ProveedorType();
			proveedorType.setId(proveedor.getId());
			proveedorType.setRut(proveedor.getRut());
			proveedorType.setNombre(proveedor.getNombre());
			outputDTO.getRegistro().add(proveedorType);
		}
		
		JaxbUtil.validar(OutputProveedorConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputProveedorObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputProveedorObtener outputDTO = new OutputProveedorObtener();
		final Proveedor proveedor = proveedorService.obtener(id);
		outputDTO.setId(proveedor.getId());
		outputDTO.setRut(proveedor.getRut());
		outputDTO.setNombre(proveedor.getNombre());
		
		JaxbUtil.validar(OutputProveedorObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}		
		
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputProveedorActualizar inputDTO) {
		JaxbUtil.validar(InputProveedorActualizar.class, inputDTO);
		return ResponseEntity.ok(proveedorService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(proveedorService.eliminar(id));
	}	
}
