package cl.mercados.ws.controller;

import java.util.List;

import org.hibernate.result.Output;
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
import cl.mercados.ws.dto.JaxbUtil;
import cl.mercados.ws.dto.v10.ArticuloType;
import cl.mercados.ws.dto.v10.InputArticuloActualizar;
import cl.mercados.ws.dto.v10.InputArticuloCrear;
import cl.mercados.ws.dto.v10.OutputArticuloConsultar;
import cl.mercados.ws.dto.v10.OutputArticuloCrear;
import cl.mercados.ws.dto.v10.OutputArticuloObtener;
import cl.mercados.ws.dto.v10.OutputVentaObtener;
import cl.mercados.ws.service.ArticuloService;

@RestController
@RequestMapping("/articulo")
public class ArticuloController {

	private final ArticuloService articuloService;
	
	@Autowired
	public ArticuloController(final ArticuloService articuloService) {
		this.articuloService = articuloService;
	}
	
	@PostMapping
	ResponseEntity<OutputArticuloCrear> crear(@RequestBody InputArticuloCrear inputDTO) {
		JaxbUtil.validar(InputArticuloCrear.class, inputDTO);
		
		final Articulo articulo = articuloService.crear(inputDTO);
		
		final OutputArticuloCrear outputDTO = new OutputArticuloCrear();
		outputDTO.setId(articulo.getId());
		outputDTO.setCod(articulo.getCod());
		outputDTO.setNombre(articulo.getNombre());
		outputDTO.setProductoId(articulo.getProducto().getId());
		
		JaxbUtil.validar(OutputArticuloCrear.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping
	ResponseEntity<OutputArticuloConsultar> consultar(@RequestParam(name = "cod", required = false) final String cod) {
		final OutputArticuloConsultar outputDTO = new OutputArticuloConsultar();
		final List<Articulo> articulos = articuloService.consultar(cod);
		for (Articulo articulo : articulos) {
			ArticuloType articuloType = new ArticuloType();
			articuloType.setId(articulo.getId());
			articuloType.setNombre(articulo.getNombre());
			articuloType.setProductoId(articulo.getProducto().getId());
			outputDTO.getRegistro().add(articuloType);
		}
		
		JaxbUtil.validar(OutputArticuloConsultar.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<OutputArticuloObtener> obtener(@PathVariable(name = "id") final Long id) {
		final OutputArticuloObtener outputDTO = new OutputArticuloObtener();
		final Articulo articulo = articuloService.obtener(id);
		outputDTO.setId(articulo.getId());
		outputDTO.setCod(articulo.getCod());
		outputDTO.setNombre(articulo.getNombre());
		outputDTO.setProductoId(articulo.getProducto().getId());
		
		JaxbUtil.validar(OutputArticuloObtener.class, outputDTO);
		return ResponseEntity.ok(outputDTO);
	}
	
	@PutMapping("/{id}")
	ResponseEntity<Boolean> actualizar(@PathVariable(name = "id") final Long id, @RequestBody InputArticuloActualizar inputDTO) {
		JaxbUtil.validar(InputArticuloActualizar.class, inputDTO);
		return ResponseEntity.ok(articuloService.actualizar(id, inputDTO));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(articuloService.eliminar(id));
	}	
}
