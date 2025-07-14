package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Client;
import java.util.List;
public interface IClientService {

    public Client saveClient(Client client);

    public List<Client> getAllClients();

    public List<Client> getClientsByEmployeeId(Integer employeeId);
}
