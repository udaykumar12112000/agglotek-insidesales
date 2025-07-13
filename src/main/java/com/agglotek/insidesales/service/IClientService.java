package com.agglotek.insidesales.service;

import com.agglotek.insidesales.model.Client;
import java.util.List;
public interface IClientService {

    public Client saveClient(Client client);

    public List<Client> getAllClients();

    public List<Client> getClientsByEmployeeId(Integer employeeId);
}
