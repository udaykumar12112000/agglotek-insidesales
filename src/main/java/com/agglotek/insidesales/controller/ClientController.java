package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.service.api.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.agglotek.insidesales.constants.ApiConstants.*;

@RestController
@RequestMapping(CLIENT_APIS)
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping(ADD_CLIENTS)
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping(GET_CLIENTS)
    public List<Client> getClients(@RequestParam(required = false) Integer employeeId) {
        if (employeeId != null) {
            return clientService.getClientsByEmployeeId(employeeId);
        } else {
            return clientService.getAllClients();
        }
    }

}
