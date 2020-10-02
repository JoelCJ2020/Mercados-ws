package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Producto;
import cl.mercados.ws.dto.v10.InputProductoActualizar;
import cl.mercados.ws.dto.v10.InputProductoCrear;

public interface ProductoService {

	Producto crear(InputProductoCrear inputDTO);

	List<Producto> consultar(String cod);

	Producto obtener(Long id);
	
	Boolean actualizar(Long id, InputProductoActualizar inputDTO);

	Boolean eliminar(Long id);

}
