package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.dto.v10.InputCajaActualizar;
import cl.mercados.ws.dto.v10.InputCajaCrear;

public interface CajaService {

	Caja crear(InputCajaCrear inputDTO);

	List<Caja> consultar(Long clienteId);

	Caja obtener(Long id);
	
	Boolean actualizar(Long id, InputCajaActualizar inputDTO);

	Boolean eliminar(Long id);

}
