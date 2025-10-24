package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectDetailsWithQuotationDetails;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT " +
            "q.quotation_number AS quotationNumber, " +
            "p.quotation_id AS quotationId, " +
            "p.project_id AS projectId, " +
            "p.project_number AS projectNumber, " +
            "CURRENT_DATE AS date, " +
            "c.name AS clientName, " +
            "c.client_id AS clientId, " +
            "q.project_name AS projectName, " +
            "c.country AS country, " +
            "p.project_value AS projectValue, " +
            "p.client_job_number AS clientJobNumber, " +
            "p.purchase_order AS purchaseOrder, " +
            "p.conn_po AS connPO, " +
            "p.comments AS comments, " +
            "p.project_manager_id AS projectManager, " +
            "p.project_status AS projectStatus, " +
            "p.ifc_submission_date AS ifcSubDate, " +
            "p.ifa_submission_date AS ifaSubDate " +
            "FROM projects p " +
            "JOIN quotations q ON p.quotation_id = q.quotation_id " +
            "JOIN clients c ON q.client_id = c.client_id " +
            "WHERE q.user_id = :salesPersonId",
            nativeQuery = true)
    List<Object[]> findProjectDetailsBySalesPersonId(@Param("salesPersonId") Integer salesPersonId);

    @Query(value = "SELECT " +
            "q.quotation_number AS quotationNumber, " +
            "p.quotation_id AS quotationId, " +
            "p.project_id AS projectId, " +
            "p.project_number AS projectNumber, " +
            "CURRENT_DATE AS date, " +
            "c.name AS clientName, " +
            "c.client_id AS clientId, " +
            "q.project_name AS projectName, " +
            "c.country AS country, " +
            "p.project_value AS projectValue, " +
            "p.client_job_number AS clientJobNumber, " +
            "p.purchase_order AS purchaseOrder, " +
            "p.conn_po AS connPO, " +
            "p.comments AS comments, " +
            "p.project_manager_id AS projectManager, " +
            "p.project_status AS projectStatus, " +
            "p.ifc_submission_date AS ifcSubDate, " +
            "p.ifa_submission_date AS ifaSubDate " +
            "FROM projects p " +
            "JOIN quotations q ON p.quotation_id = q.quotation_id " +
            "JOIN clients c ON q.client_id = c.client_id",
            nativeQuery = true)
    List<Object[]> findAllProjectDetails();

    @Query(value = "SELECT project_number FROM projects WHERE project_number LIKE :prefix% ORDER BY project_number DESC LIMIT 1", nativeQuery = true)
    String findLastProjectNumberForYear(@Param("prefix") String prefix);


    @Query("SELECT p FROM Project p WHERE p.projectManagerId = :managerId")
    List<Project> findByProjectManagerId(@Param("managerId") Integer managerId);

    List<Project> findByProjectStatusIn(List<String> projectStatuses);

    @Query("SELECT new com.agglotek.insidesales.dto.ProjectDetailsWithQuotationDetails(" +
            "p.projectId, p.quotationId, p.comments, p.ifaDate, " +
            "p.ifcSubmissionDate, p.ifaSubmissionDate, p.plannedSubmittedDate, " +
            "p.updatedTime, p.createdTime, p.projectManagerId, p.projectStatus, " +
            "p.projectNumber, p.clientProjectNumber, p.purchaseOrder, " +
            "p.connPO, p.balanceAmt, q.projectName) " +
            "FROM Project p " +
            "LEFT JOIN Quotation q ON p.quotationId = q.quotationId " +
            "WHERE p.projectStatus IN :statuses")
    List<ProjectDetailsWithQuotationDetails> findProjectsWithNameByStatus(@Param("statuses") List<String> statuses);

    @Query("""
            SELECT p 
            FROM Project p 
            JOIN Quotation q ON p.quotationId = q.quotationId
            WHERE q.quotationStatus = 'allotted'
            AND p.projectManagerId IS NULL
    """)
    List<Project> findAllAllottedProjectsWithoutManager();

    Project getByProjectId(Integer projectId);
}
