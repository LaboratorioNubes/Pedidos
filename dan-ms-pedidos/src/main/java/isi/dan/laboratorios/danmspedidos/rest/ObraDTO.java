package isi.dan.laboratorios.danmspedidos.rest;

public class ObraDTO {
    private Integer id;
	private String descripcion;
    
	public ObraDTO() {
        super();
    }
    public ObraDTO(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}
