package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.domain.Obra;
import isi.dan.laboratorios.danmspedidos.dtos.ObraDTO;

public interface ClienteService {
    public Double deudaCliente(ObraDTO id);
    public Double maximoSaldoNegativo(ObraDTO id);
    public Integer situacionCrediticiaBCRA(ObraDTO id);
}
