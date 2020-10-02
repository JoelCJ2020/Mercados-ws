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
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cod;
	private String nombre;
	private String descr;
	private int precio;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "PROVEEDOR_ID")
	private Proveedor proveedor;
	
}
