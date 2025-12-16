package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.Client;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IClientService {

    public Client saveClient(Client client);

    public List<Client> getAllClients();

    public List<Client> getClientsByUserId(Integer userId);

    ApiResponse editClient(Client clientData);

    Client getClientByProjectId(Integer projectId);

    ApiResponse assignClientsToSales(Map<Integer, List<Integer>> usersToClientsMap) ;

    public List<Client> getUnassignedClients();

    public List<Client> getClientsByAssignedSalesUserId(Integer userId);

    public void updateClientToFabricator(Integer clientId);
}
