package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.dao.entity.ClientConvo;
import com.agglotek.insidesales.dto.ClientConvoDTO;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.api.IClientConvoService;
import com.agglotek.insidesales.service.api.IClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
//        allowCredentials = "false" // set true only if you use cookies
//)
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.CLIENT_APIS)
public class ClientController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private IClientConvoService clientConvoService;

    @Autowired
    private ClientRepository clientRepository;


    @PostMapping(ApiConstants.ADD_CLIENTS)
    public Client saveClient(@RequestBody Client client, @RequestHeader("User-Id") Integer userId) {
        client.setUserId(userId);
        client.setInsertTime(new Timestamp(System.currentTimeMillis()));
        return clientService.saveClient(client);
    }

    @PutMapping(ApiConstants.EDIT_CLIENT)
    public ResponseEntity<ApiResponse> editClientData(@RequestBody Client clientData){

        try {
            ApiResponse response = clientService.editClient(clientData);
            return ResponseEntity.status(response.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update client"));
        }
    }

    @GetMapping(ApiConstants.GET_CLIENTS)
    public List<Client> getClients(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return clientService.getClientsByUserId(userId);
        } else {
            return clientService.getAllClients();
        }
    }

    @PostMapping(ApiConstants.ADD_CLIENT_CONVO)
    public ResponseEntity<ApiResponse> addClientConvo(@RequestBody ClientConvoDTO dto) {
        clientConvoService.insertClientConvoData(dto);
        List<ClientConvoDTO> result = clientConvoService.getClientConvoDataByUserId(dto.getUserId());
        return ResponseEntity.ok(new ApiResponse(true,"Client Convo saved!", result));
    }

    @GetMapping(ApiConstants.GET_CLIENT_CONVO)
    public ResponseEntity<List<ClientConvoDTO>> getConvos(@RequestHeader("User-Id") Integer userId) {
        List<ClientConvoDTO> result = clientConvoService.getClientConvoDataByUserId(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping(ApiConstants.EDIT_CLIENT_CONVO)
    public ResponseEntity<ApiResponse> editClientConvo(
            @RequestBody ClientConvoDTO request, @RequestHeader("User-Id") Integer userId) throws JsonProcessingException {

        request.setUserId(userId);
        ApiResponse response = clientConvoService.editClientConvo(request);
        return ResponseEntity.ok(response);
    }

}
