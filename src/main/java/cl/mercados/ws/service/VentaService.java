package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Venta;
import cl.mercados.ws.dto.v10.InputVentaActualizar;
import cl.mercados.ws.dto.v10.InputVentaCrear;

public interface VentaService {

	Venta crear(InputVentaCrear inputDTO);

	List<Venta> consultar(Long clienteId);

	Venta obtener(Long id);
	
	Boolean actualizar(Long id, InputVentaActualizar inputDTO);

	Boolean eliminar(Long id);

}
