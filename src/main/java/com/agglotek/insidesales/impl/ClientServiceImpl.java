package com.agglotek.insidesales.impl;

import com.agglotek.insidesales.model.Client;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<Client> getClientsByEmployeeId(Integer employeeId) {
        return clientRepository.findByEmployeeId(employeeId);
    }
}

