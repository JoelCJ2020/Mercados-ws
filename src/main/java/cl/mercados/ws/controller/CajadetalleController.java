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

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Cajadetalle;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.CajadetalleType;
import cl.mercados.ws.dto.v10.InputCajadetalleActualizar;
import cl.mercados.ws.dto.v10.InputCajadetalleCrear;
import cl.mercados.ws.dto.v10.OutputCajaObtener;
import cl.mercados.ws.dto.v10.OutputCajadetalleConsultar;
import cl.mercados.ws.dto.v10.OutputCajadetalleCrear;
import cl.mercados.ws.dto.v10.OutputCajadetalleObtener;
import cl.mercados.ws.service.CajadetalleService;

@RestController
@RequestMapping("/cajadetalle")
public class CajadetalleController {
	
	private final CajadetalleService cajadetalleService;
	
	@Autowired
	public CajadetalleController(final CajadetalleService cajadetalleService) {
		this.cajadetalleService = cajadetalleService;
	}
	
	@PostMapping
	ResponseEntity<OutputCajadetalleCrear> crear(@RequestBody InputCajadetalleCrear inputDTO) {
		JaxbUtil.validar(InputCajadetalleCrear.class, inputDTO);
		
		final Cajadetalle cajadetalle = cajadetalleService.crear(inputDTO);
		
		final OutputCajadetalleCrear outputDTO = new OutputCajadetalleCrear();
		outputDTO.setId(cajadetalle.getId());
		outputDTO.setCantidad(cajadetalle.getCantidad());
		outputDTO.setCajaId(cajadetalle.getCaja().getId());
		outputDTO.setProductoId(cajadetalle.getProducto().getId());
		
		JaxbUtil.validar(OutputCajadetalleCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputCajadetalleConsultar> consultar(@RequestParam(name = "cajaId", required = false) final Long cajaId) {
		final OutputCajadetalleConsultar outputDTO = new OutputCajadetalleConsultar();
		final List<Cajadetalle> cajadetalles = cajadetalleService.consultar(cajaId);
		for (Cajadetalle cajadetalle : cajadetalles) {
			CajadetalleType cajadetalleType = new CajadetalleType();
			cajadetalleType.setId(cajadetalle.getId());
			cajadetalleType.setCantidad(cajadetalle.getCantidad());
			cajadetalleType.setCajaId(cajadetalle.getCaja().getId());
			cajadetalleType.setProductoId(cajadetalle.getProducto().getId());
			outputDTO.getRegistro().add(cajadetalleType);
		}
		
		JaxbUtil.validar(OutputCajadetalleConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputCajadetalleObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputCajadetalleObtener outputDTO = new OutputCajadetalleObtener();
		final Cajadetalle cajadetalle = cajadetalleService.obtener(id);
		outputDTO.setId(cajadetalle.getId());
		outputDTO.setCantidad(cajadetalle.getCantidad());
		outputDTO.setCajaId(cajadetalle.getCaja().getId());
		outputDTO.setProductoId(cajadetalle.getProducto().getId());
		
		JaxbUtil.validar(OutputCajadetalleObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}		
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputCajadetalleActualizar inputDTO) {
		JaxbUtil.validar(InputCajadetalleActualizar.class, inputDTO);
		return ResponseEntity.ok(cajadetalleService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(cajadetalleService.eliminar(id));
	}	
}
