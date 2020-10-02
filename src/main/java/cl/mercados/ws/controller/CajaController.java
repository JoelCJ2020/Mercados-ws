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
import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.CajaType;
import cl.mercados.ws.dto.v10.InputCajaActualizar;
import cl.mercados.ws.dto.v10.InputCajaCrear;
import cl.mercados.ws.dto.v10.OutputArticuloObtener;
import cl.mercados.ws.dto.v10.OutputCajaConsultar;
import cl.mercados.ws.dto.v10.OutputCajaCrear;
import cl.mercados.ws.dto.v10.OutputCajaObtener;
import cl.mercados.ws.service.CajaService;

@RestController
@RequestMapping("/caja")
public class CajaController {
	
	private final CajaService cajaService;
	
	@Autowired
	public CajaController(final CajaService cajaService) {
		this.cajaService = cajaService;
	}
	
	@PostMapping
	ResponseEntity<OutputCajaCrear> crear(@RequestBody InputCajaCrear inputDTO) {
		JaxbUtil.validar(InputCajaCrear.class, inputDTO);
		
		final Caja caja = cajaService.crear(inputDTO);
		
		final OutputCajaCrear outputDTO = new OutputCajaCrear();
		outputDTO.setId(caja.getId());
		outputDTO.setClienteId(caja.getCliente().getId());;
		
		JaxbUtil.validar(OutputCajaCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputCajaConsultar> consultar(@RequestParam(name = "clienteId", required = false) final Long clienteId) {
		final OutputCajaConsultar outputDTO = new OutputCajaConsultar();
		final List<Caja> cajas = cajaService.consultar(clienteId);
		for (Caja caja : cajas) {
			CajaType cajaType = new CajaType();
			cajaType.setId(caja.getId());
			cajaType.setClienteId(caja.getCliente().getId());
			outputDTO.getRegistro().add(cajaType);
		}
		
		JaxbUtil.validar(OutputCajaConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputCajaObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputCajaObtener outputDTO = new OutputCajaObtener();
		final Caja caja = cajaService.obtener(id);
		outputDTO.setId(caja.getId());
		outputDTO.setClienteId(caja.getCliente().getId());;
		
		JaxbUtil.validar(OutputCajaObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}	
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputCajaActualizar inputDTO) {
		JaxbUtil.validar(InputCajaActualizar.class, inputDTO);
		return ResponseEntity.ok(cajaService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(cajaService.eliminar(id));
	}	
}
