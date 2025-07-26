package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.ClientConvo;
import com.agglotek.insidesales.dto.ClientConvoDTO;

import java.util.List;

public interface IClientConvoService {
    public ClientConvo insertClientConvoData(ClientConvoDTO dto);
    public List<ClientConvoDTO> getByClientId(Integer clientId);
}
