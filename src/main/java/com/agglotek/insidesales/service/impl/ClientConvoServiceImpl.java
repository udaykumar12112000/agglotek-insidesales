package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.ClientConvo;
import com.agglotek.insidesales.dto.ClientConvoDTO;
import com.agglotek.insidesales.repository.ClientConvoRepository;
import com.agglotek.insidesales.service.api.IClientConvoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClientConvoServiceImpl implements IClientConvoService {

    @Autowired
    private ClientConvoRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    public ClientConvo insertClientConvoData(ClientConvoDTO dto) {
        ClientConvo convo = new ClientConvo();
        convo.setClientId(dto.getClientId());
        convo.setUserId(dto.getUserId());
        convo.setRemarks(dto.getRemarks());

        try {
            convo.setCallConvo(mapper.writeValueAsString(dto.getCallConvo()));
            convo.setStatusUpdate(mapper.writeValueAsString(dto.getStatusUpdate()));
        } catch (Exception e) {
            throw new RuntimeException("JSON conversion error", e);
        }

        return repository.save(convo);
    }

    public List<ClientConvoDTO> getByClientId(Integer clientId) {
        List<ClientConvo> convos = repository.findByClientId(clientId);
        List<ClientConvoDTO> result = new ArrayList<>();

        for (ClientConvo convo : convos) {
            ClientConvoDTO dto = new ClientConvoDTO();
            dto.setClientId(convo.getClientId());
            dto.setUserId(convo.getUserId());
            try {
                dto.setCallConvo(mapper.readValue(convo.getCallConvo(), Map.class));
                dto.setStatusUpdate(mapper.readValue(convo.getStatusUpdate(), Map.class));
            } catch (Exception e) {
                throw new RuntimeException("JSON parsing error", e);
            }
            dto.setRemarks(convo.getRemarks());

            result.add(dto);
        }

        return result;
    }
}
