package isi.dan.laboratorios.danmspedidos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {
	@Id
    private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
}
