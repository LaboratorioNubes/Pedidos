package isi.dan.laboratorios.danmspedidos.rest;

public class ClienteDTO {
    
    private Integer id;
    private String razonSocial;
    private String cuit;

    public ClienteDTO() {
        super();
    }

    public ClienteDTO(Integer id, String razonSocial, String cuit) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}


}
