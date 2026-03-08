package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.ConnectionEng;
import com.agglotek.insidesales.service.api.IConnectionEngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(
//        origins = {"http://localhost:4200"},
//        allowedHeaders = "*"
//)
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
@RequestMapping("/api/connectionEng")
public class ConnectionEngController {

    @Autowired
    private IConnectionEngService service;

    // Create (Insert)
    @PostMapping("/createConnEng")
    public ResponseEntity<ApiResponse> create(@RequestBody ConnectionEng connectionEng) {

        ConnectionEng connEng = new ConnectionEng();
        System.out.println("JAXX :: connectionEng : "+connectionEng.getContactPersonName());
        try{
            connEng = service.save(connectionEng);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Failed To insert Connection Engineering details"));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Inserted Connection Engineering details successfully", connEng));

    }

    // Read all
    @GetMapping("/getAllConnEng")
    public ResponseEntity<List<ConnectionEng>> getAll(@RequestParam(value = "id", required = false) Long id) {

        List<ConnectionEng> connecEngList = new ArrayList<>();

        try {
            if (id != null) {
                service.getById(id).ifPresent(connecEngList::add);
                if (connecEngList.isEmpty()) {
                    return ResponseEntity.ok(new ArrayList<>());
                }
            } else {
                connecEngList = service.getAll();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(connecEngList);
    }

    // Update
    @PutMapping("/updateConnEngById")
    public ResponseEntity<ApiResponse> update(@RequestBody ConnectionEng updated) {
        ConnectionEng updatedConEng = null;
        Long id = updated.getConnectionEngId();
        try {
            updatedConEng = service.update(id, updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Failed to update Connection Engineering details", null));
        }
       return ResponseEntity.ok(new ApiResponse(true, "Updated Connection Engineering details successfully", updatedConEng));
    }

    // Delete
    @DeleteMapping("/deleteConnEngById/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

