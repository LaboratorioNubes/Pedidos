package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.ObraDTO;

public interface IClienteService {
    Double deudaCliente(String idObra);
    Double maximoSaldoNegativo(String idObra);
    Integer situacionCrediticiaBCRA(ObraDTO id);
}
