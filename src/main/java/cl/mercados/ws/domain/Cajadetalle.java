package cl.mercados.ws.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Cajadetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int cantidad;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "PRODUCTO_ID")
	private Producto producto;	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CAJA_ID")
	private Caja caja;	

}
