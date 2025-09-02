package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.dao.entity.ClientConvo;
import com.agglotek.insidesales.dto.ClientConvoDTO;
import com.agglotek.insidesales.repository.ClientConvoRepository;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.api.IClientConvoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientConvoServiceImpl implements IClientConvoService {

    @Autowired
    private ClientConvoRepository clientConvoRepository;

    @Autowired
    private ClientRepository clientRepository;

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

        return clientConvoRepository.save(convo);
    }

    public List<ClientConvoDTO> getByClientId(Integer clientId) {
        List<ClientConvo> convos = clientConvoRepository.findByClientId(clientId);
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

    public List<ClientConvoDTO> getClientConvoDataByUserId(Integer userId) {
        List<Client> clients = clientRepository.findByUserId(userId); // userId filter
        List<ClientConvoDTO> result = new ArrayList<>();

        for (Client client : clients) {
            List<ClientConvo> convos = clientConvoRepository.findByClientId(client.getClientId());

            if (convos.isEmpty()) {
                ClientConvoDTO dto = buildClientConvoDTO(client, userId, null);
                result.add(dto);
            } else {
                for (ClientConvo convo : convos) {
                    ClientConvoDTO dto = buildClientConvoDTO(client, convo.getUserId(), convo);
                    result.add(dto);
                }
            }
        }

        return result;
    }

    private ClientConvoDTO buildClientConvoDTO(Client client, Integer userId, ClientConvo convo) {
        ClientConvoDTO dto = new ClientConvoDTO();
        dto.setClientId(client.getClientId());
        dto.setUserId(userId);
        dto.setName(client.getName());
        dto.setCountry(client.getCountry());
        dto.setStakeHolders(client.getStakeHolders());
        dto.setAddress(client.getAddress());
        dto.setTimeZone(client.getTimeZone());
        dto.setClientType(client.getClientType());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAvailableHrs(client.getAvailableHrs());

        if (convo != null) {
            try {
                dto.setCallConvo(mapper.readValue(convo.getCallConvo(), Map.class));
                dto.setStatusUpdate(mapper.readValue(convo.getStatusUpdate(), Map.class));
                dto.setClientConvoId(convo.getClientConvoId());
            } catch (Exception e) {
                throw new RuntimeException("JSON parsing error", e);
            }
            dto.setRemarks(convo.getRemarks());
        } else {
            dto.setCallConvo(null);
            dto.setStatusUpdate(null);
            dto.setRemarks(null);
        }

        return dto;
    }

    public ApiResponse editClientConvo(ClientConvoDTO request) {
        Optional<ClientConvo> convoOpt = clientConvoRepository.findById(request.getClientConvoId());

        if (!convoOpt.isPresent()) {
            return new ApiResponse(false, "Client conversation not found!");
        }

        ClientConvo convo = convoOpt.get();

        if (request.getCallConvo() != null)
            convo.setCallConvo(request.getCallConvo().toString());

        if (request.getStatusUpdate() != null)
            convo.setStatusUpdate(request.getStatusUpdate().toString());

        if (request.getRemarks() != null)
            convo.setRemarks(request.getRemarks());

        clientConvoRepository.save(convo);

        return new ApiResponse(true, "Client conversation updated successfully!", convo);
    }


}
