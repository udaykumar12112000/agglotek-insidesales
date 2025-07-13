package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.model.Client;
import com.agglotek.insidesales.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping("/addClient")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/getClients")
    public List<Client> getClients(@RequestParam(required = false) Integer employeeId) {
        if (employeeId != null) {
            return clientService.getClientsByEmployeeId(employeeId);
        } else {
            return clientService.getAllClients();
        }
    }

}
