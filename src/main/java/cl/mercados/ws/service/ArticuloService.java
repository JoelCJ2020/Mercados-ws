package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Articulo;
import cl.mercados.ws.dto.v10.InputArticuloActualizar;
import cl.mercados.ws.dto.v10.InputArticuloCrear;

public interface ArticuloService {

	Articulo crear(InputArticuloCrear inputDTO);

	List<Articulo> consultar(String cod);

	Articulo obtener(Long id);	
	
	Boolean actualizar(Long id, InputArticuloActualizar inputDTO);

	Boolean eliminar(Long id);

}
