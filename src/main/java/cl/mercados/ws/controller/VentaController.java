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

import cl.mercados.ws.domain.Venta;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.InputVentaActualizar;
import cl.mercados.ws.dto.v10.InputVentaCrear;
import cl.mercados.ws.dto.v10.OutputVentaConsultar;
import cl.mercados.ws.dto.v10.OutputVentaCrear;
import cl.mercados.ws.dto.v10.OutputVentaObtener;
import cl.mercados.ws.dto.v10.VentaType;
import cl.mercados.ws.service.VentaService;

@RestController
@RequestMapping("/venta")
public class VentaController {

	private final VentaService ventaService;
	
	@Autowired
	public VentaController(final VentaService ventaService) {
		this.ventaService = ventaService;
	}
	
	@PostMapping
	ResponseEntity<OutputVentaCrear> crear(@RequestBody InputVentaCrear inputDTO) {
		JaxbUtil.validar(InputVentaCrear.class, inputDTO);
		
		final Venta venta = ventaService.crear(inputDTO);
		
		final OutputVentaCrear outputDTO = new OutputVentaCrear();
		outputDTO.setId(venta.getId());
		outputDTO.setTotal(venta.getTotal());
		outputDTO.setClienteId(venta.getCliente().getId());
		outputDTO.setCajaId(venta.getCaja().getId());
		
		JaxbUtil.validar(OutputVentaCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputVentaConsultar> consultar(@RequestParam(name = "clienteId", required = false) final Long clienteId) {
		final OutputVentaConsultar outputDTO = new OutputVentaConsultar();
		final List<Venta> ventas = ventaService.consultar(clienteId);
		for (Venta venta : ventas) {
			VentaType ventaType = new VentaType();
			ventaType.setId(venta.getId());
			ventaType.setTotal(venta.getTotal());
			ventaType.setClienteId(venta.getCliente().getId());
			ventaType.setCajaId(venta.getCaja().getId());
			outputDTO.getRegistro().add(ventaType);
		}
		
		JaxbUtil.validar(OutputVentaConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputVentaObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputVentaObtener outputDTO = new OutputVentaObtener();
		final Venta venta = ventaService.obtener(id);
		outputDTO.setId(venta.getId());
		outputDTO.setTotal(venta.getTotal());
		outputDTO.setClienteId(venta.getCliente().getId());
		outputDTO.setCajaId(venta.getCaja().getId());
		
		JaxbUtil.validar(OutputVentaObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}	
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputVentaActualizar inputDTO) {
		JaxbUtil.validar(InputVentaActualizar.class, inputDTO);
		return ResponseEntity.ok(ventaService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(ventaService.eliminar(id));
	}	
}
