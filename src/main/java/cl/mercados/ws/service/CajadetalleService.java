package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Cajadetalle;
import cl.mercados.ws.dto.v10.InputCajadetalleActualizar;
import cl.mercados.ws.dto.v10.InputCajadetalleCrear;

public interface CajadetalleService {

	Cajadetalle crear(InputCajadetalleCrear inputDTO);

	List<Cajadetalle> consultar(Long cajaId);

	Cajadetalle obtener(Long id);	
	
	Boolean actualizar(Long id, InputCajadetalleActualizar inputDTO);

	Boolean eliminar(Long id);
}
