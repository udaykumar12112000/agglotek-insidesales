package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public List<Client> getClientsByUserId(Integer userId) {
        return clientRepository.findByUserId(userId);
    }

    public ApiResponse editClient(Client clientData) {

        Optional<Client> optionalClient = clientRepository.findById(clientData.getClientId());
        if (!optionalClient.isPresent()) {
            return new ApiResponse(false, "Client not found");
        }

        Client existingClient = optionalClient.get();
        boolean updated = false;

        if (clientData.getName() != null && !clientData.getName().equals(existingClient.getName())) {
            existingClient.setName(clientData.getName());
            updated = true;
        }
        if (clientData.getEmail() != null && !clientData.getEmail().equals(existingClient.getEmail())) {
            existingClient.setEmail(clientData.getEmail());
            updated = true;
        }
        if (clientData.getPhoneNumber() != null && !clientData.getPhoneNumber().equals(existingClient.getPhoneNumber())) {
            existingClient.setPhoneNumber(clientData.getPhoneNumber());
            updated = true;
        }
        if (clientData.getAlterPhoneNumber() != null && !clientData.getAlterPhoneNumber().equals(existingClient.getAlterPhoneNumber())) {
            existingClient.setAlterPhoneNumber(clientData.getAlterPhoneNumber());
            updated = true;
        }
        if (clientData.getClientType() != null && !clientData.getClientType().equals(existingClient.getClientType())) {
            existingClient.setClientType(clientData.getClientType());
            updated = true;
        }
        if (clientData.getTimeZone() != null && !clientData.getTimeZone().equals(existingClient.getTimeZone())) {
            existingClient.setTimeZone(clientData.getTimeZone());
            updated = true;
        }
        if (clientData.getAvailableHrs() != null && !clientData.getAvailableHrs().equals(existingClient.getAvailableHrs())) {
            existingClient.setAvailableHrs(clientData.getAvailableHrs());
            updated = true;
        }
        if (clientData.getOurTime() != null && !clientData.getOurTime().equals(existingClient.getOurTime())) {
            existingClient.setOurTime(clientData.getOurTime());
            updated = true;
        }
        if (clientData.getCountry() != null && !clientData.getCountry().equals(existingClient.getCountry())) {
            existingClient.setCountry(clientData.getCountry());
            updated = true;
        }
        if (clientData.getAddress() != null && !clientData.getAddress().equals(existingClient.getAddress())) {
            existingClient.setAddress(clientData.getAddress());
            updated = true;
        }
        if (clientData.getDetails() != null && !clientData.getDetails().equals(existingClient.getDetails())) {
            existingClient.setDetails(clientData.getDetails());
            updated = true;
        }
        if (clientData.getStakeHolders() != null && !clientData.getStakeHolders().equals(existingClient.getStakeHolders())) {
            existingClient.setStakeHolders(clientData.getStakeHolders());
            updated = true;
        }
        if (clientData.getDateOfEntry() != null && !clientData.getDateOfEntry().equals(existingClient.getDateOfEntry())) {
            existingClient.setDateOfEntry(clientData.getDateOfEntry());
            updated = true;
        }

        if (updated) {
            clientRepository.save(existingClient);
            return new ApiResponse(true, "Client updated successfully!", existingClient);
        } else {
            return new ApiResponse(false, "No changes detected", existingClient);
        }
    }

    @Override
    public Client getClientByProjectId(Integer projectId) {
        return clientRepository.findClientByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("Client not found for projectId: " + projectId));
    }
}

