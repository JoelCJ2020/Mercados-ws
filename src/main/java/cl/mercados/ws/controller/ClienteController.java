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
import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.ClienteType;
import cl.mercados.ws.dto.v10.InputClienteActualizar;
import cl.mercados.ws.dto.v10.InputClienteCrear;
import cl.mercados.ws.dto.v10.OutputArticuloObtener;
import cl.mercados.ws.dto.v10.OutputClienteConsultar;
import cl.mercados.ws.dto.v10.OutputClienteCrear;
import cl.mercados.ws.dto.v10.OutputClienteObtener;
import cl.mercados.ws.service.ClienteService;


@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(final ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@PostMapping
	ResponseEntity<OutputClienteCrear> crear(@RequestBody InputClienteCrear inputDTO) {
		JaxbUtil.validar(InputClienteCrear.class, inputDTO);
		
		final Cliente cliente = clienteService.crear(inputDTO);
		
		final OutputClienteCrear outputDTO = new OutputClienteCrear();
		outputDTO.setId(cliente.getId());
		outputDTO.setRut(cliente.getRut());
		outputDTO.setNombre(cliente.getNombre());
		
		JaxbUtil.validar(OutputClienteCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputClienteConsultar> consultar(@RequestParam(name = "nombre", required = false) final String nombre) {
		final OutputClienteConsultar outputDTO = new OutputClienteConsultar();
		final List<Cliente> clientes = clienteService.consultar(nombre);
		for (Cliente cliente : clientes) {
			ClienteType clienteType = new ClienteType();
			clienteType.setId(cliente.getId());
			clienteType.setNombre(cliente.getNombre());
			clienteType.setRut(cliente.getRut());
			outputDTO.getRegistro().add(clienteType);
		}
		
		JaxbUtil.validar(OutputClienteConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputClienteObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputClienteObtener outputDTO = new OutputClienteObtener();
		final Cliente cliente = clienteService.obtener(id);
		outputDTO.setId(cliente.getId());
		outputDTO.setRut(cliente.getRut());
		outputDTO.setNombre(cliente.getNombre());
		
		JaxbUtil.validar(OutputClienteObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}	
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputClienteActualizar inputDTO) {
		JaxbUtil.validar(InputClienteActualizar.class, inputDTO);
		return ResponseEntity.ok(clienteService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(clienteService.eliminar(id));
	}	
}
