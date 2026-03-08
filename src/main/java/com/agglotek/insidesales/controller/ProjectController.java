package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.PurchaseOrder;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
 @CrossOrigin(
         origins = "http://localhost:4200",
         allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
         methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
         allowCredentials = "false" // set true only if you use cookies
 )
/** use for local **/
//@CrossOrigin(
//       origins = {"http://localhost:4200"},
//       allowedHeaders = "*"
//)
@RequestMapping(ApiConstants.PROJECT_APIS)
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IConnectionEngService connectionEngService;

    @GetMapping(ApiConstants.PROJECT_DETAILS)
    public ResponseEntity<List<ProjectInfoDTO>> getProjectDetailsBySalesPerson(@RequestHeader("User-Id") Integer salesPersonId, @RequestParam(required = false) Boolean isConnEng) throws IOException {
        List<ProjectInfoDTO> projectDetails = projectService.getProjectDetailsBySalesPersonId(salesPersonId, isConnEng);
        for(ProjectInfoDTO project : projectDetails) {
            project.setFilesList(fileService.listFiles(project.getProjectNumber(), project.getProjectName(), project.getClientId()));
        }
        return ResponseEntity.ok(projectDetails);
    }

    @PutMapping(ApiConstants.UPDATE_PROJECT)
    public ResponseEntity<ApiResponse> updateProjectDetails(@RequestBody ProjectInfoDTO request) {

        boolean success = projectService.updateProjectDetails(request);

        if (success) {
            return ResponseEntity.ok(new ApiResponse(true, "Project Details updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Project not found!"));
        }
    }

    @PostMapping("/createPurchaseOrder")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder created = purchaseOrderService.createPurchaseOrder(purchaseOrder);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/getAllPurchaseOrders")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrders(@RequestParam(required = false) Long id) {

        try {
            List<PurchaseOrder> list= new ArrayList<>();
            if (id != null) {
                PurchaseOrder po = purchaseOrderService.getPurchaseOrderById(id).orElse(null);
                list.add(po);
            } else {
                list = purchaseOrderService.getAllPurchaseOrders();
            }
            if(!list.isEmpty() && list!=null){
                formatPurchaseOrderResult(list);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PutMapping("/updatePurchaseOrder")
    public ResponseEntity<ApiResponse> updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        try {
            Long id = purchaseOrder.getPoId();
            PurchaseOrder updated = purchaseOrderService.updatePurchaseOrder(id, purchaseOrder);
            return ResponseEntity.ok(new ApiResponse(true, "Purchase Order Updated Successfully", formatPurchaseOrderResult(List.of(updated))));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResponse(false, "Failed to Update Purchase Order"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PurchaseOrder> formatPurchaseOrderResult(List<PurchaseOrder> purchaseOrders) throws IOException {
        for(PurchaseOrder po : purchaseOrders){
            Project project = projectRepository.getByProjectId(po.getProjectId());
            Quotation quotation = quotationRepository.getByQuotationId(project.getQuotationId());

            if(po!=null && project!=null && quotation!=null) {
                po.setBuyerName(userService.getUserByUserId(quotation.getUserId()).get(0).getName());
                po.setProjectName(quotation.getProjectName());
                po.setClientName(clientService.getClientByProjectId(project.getProjectId()).getName());
                po.setClientId(quotation.getClientId());
                po.setProjectNumber(project.getProjectNumber());

                connectionEngService.getById(Long.valueOf(quotation.getConnectionEngId())).ifPresent(connEng -> {
                    po.setConnEng(connEng);
                });
                po.setFilesList(fileService.listFiles(project.getProjectNumber(), quotation.getProjectName(), quotation.getClientId()));
            }
        }
        return purchaseOrders;
    }

    @DeleteMapping("/deletePurchaseOrder{id}")
    public ResponseEntity<ApiResponse> deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok(new ApiResponse(true, "Deleted Purchase Order Successfully"));
    }

}
