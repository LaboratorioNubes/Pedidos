package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.domain.Obra;

public interface ClienteService {
    public Double deudaCliente(Obra id);
    public Double maximoSaldoNegativo(Obra id);
    public Integer situacionCrediticiaBCRA(Obra id);
}
