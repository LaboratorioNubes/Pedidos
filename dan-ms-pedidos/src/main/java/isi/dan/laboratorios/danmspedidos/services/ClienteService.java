package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.ObraDTO;

public interface ClienteService {
    public Double deudaCliente(Integer idObra);
    public Double maximoSaldoNegativo(Integer idObra);
    public Integer situacionCrediticiaBCRA(ObraDTO id);
}
