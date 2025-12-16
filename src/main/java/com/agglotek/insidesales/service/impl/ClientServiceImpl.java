package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
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

    public List<Client> getUnassignedClients() {
        return clientRepository.findByAssignedSalesUserIdIsNull();
    }

    public List<Client> getClientsByAssignedSalesUserId(Integer userId) {
        return clientRepository.findByAssignedSalesUserId(userId);
    }

    public ApiResponse editClient(Client clientData) {

        Optional<Client> optionalClient = clientRepository.findById(clientData.getClientId());
        if (!optionalClient.isPresent()) {
            return new ApiResponse(false, "Client not found");
        }

        Client existingClient = optionalClient.get();

        if (clientData.getName() != null && !clientData.getName().equals(existingClient.getName())) {
            existingClient.setName(clientData.getName());
        }
        if (clientData.getEmail() != null && !clientData.getEmail().equals(existingClient.getEmail())) {
            existingClient.setEmail(clientData.getEmail());
        }
        if (clientData.getPhoneNumber() != null && !clientData.getPhoneNumber().equals(existingClient.getPhoneNumber())) {
            existingClient.setPhoneNumber(clientData.getPhoneNumber());
        }
        if (clientData.getAlterPhoneNumber() != null && !clientData.getAlterPhoneNumber().equals(existingClient.getAlterPhoneNumber())) {
            existingClient.setAlterPhoneNumber(clientData.getAlterPhoneNumber());
        }
        if (clientData.getClientType() != null && !clientData.getClientType().equals(existingClient.getClientType())) {
            existingClient.setClientType(clientData.getClientType());
        }
        if (clientData.getTimeZone() != null && !clientData.getTimeZone().equals(existingClient.getTimeZone())) {
            existingClient.setTimeZone(clientData.getTimeZone());
        }
        if (clientData.getAvailableHrs() != null && !clientData.getAvailableHrs().equals(existingClient.getAvailableHrs())) {
            existingClient.setAvailableHrs(clientData.getAvailableHrs());
        }
        if (clientData.getOurTime() != null && !clientData.getOurTime().equals(existingClient.getOurTime())) {
            existingClient.setOurTime(clientData.getOurTime());
        }
        if (clientData.getCountry() != null && !clientData.getCountry().equals(existingClient.getCountry())) {
            existingClient.setCountry(clientData.getCountry());
        }

        if (clientData.getState() != null && !clientData.getState().equals(existingClient.getState())) {
            existingClient.setState(clientData.getState());
        }
        if (clientData.getAddress() != null && !clientData.getAddress().equals(existingClient.getAddress())) {
            existingClient.setAddress(clientData.getAddress());
        }
        if (clientData.getDetails() != null && !clientData.getDetails().equals(existingClient.getDetails())) {
            existingClient.setDetails(clientData.getDetails());
        }
        if (clientData.getStakeHolders() != null && !clientData.getStakeHolders().equals(existingClient.getStakeHolders())) {
            existingClient.setStakeHolders(clientData.getStakeHolders());
        }
        if (clientData.getDateOfEntry() != null && !clientData.getDateOfEntry().equals(existingClient.getDateOfEntry())) {
            existingClient.setDateOfEntry(clientData.getDateOfEntry());
        }

        clientRepository.save(existingClient);
        return new ApiResponse(true, "Client updated successfully!", existingClient);
    }

    @Override
    public Client getClientByProjectId(Integer projectId) {
        return clientRepository.findClientByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("Client not found for projectId: " + projectId));
    }

    public ApiResponse assignClientsToSales(Map<Integer, List<Integer>> usersToClientsMap) {
        int totalUpdated = 0;
        int totalUsers = 0;

        try {
            for (Integer userId : usersToClientsMap.keySet()) {

                List<Integer> clientIds = usersToClientsMap.get(userId);

                if (clientIds == null || clientIds.isEmpty()) continue;

                int updated = clientRepository.assignClientsToUser(userId, clientIds);
                totalUpdated += updated;
                totalUsers ++;
            }

            return new ApiResponse(true, totalUpdated + " Clients assigned successfully to " + totalUsers + " Sales persons", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Failed to assign clients to users!", null);
        }
    }

    public void updateClientToFabricator(Integer clientId) {
        clientRepository.markAsFabricator(clientId);
    }
}

