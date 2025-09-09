package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT " +
            "q.quotation_number AS quotationNumber, " +
            "p.project_id AS projectId, " +
            "p.project_number AS projectNumber, " +
            "CURRENT_DATE AS date, " +
            "c.name AS clientName, " +
            "c.client_id AS clientId, " +
            "q.project_name AS projectName, " +
            "c.country AS country, " +
            "q.project_value AS projectValue, " +
            "p.client_job_number AS clientJobNumber, " +
            "p.purchase_order AS purchaseOrder, " +
            "p.conn_po AS connPO, " +
            "p.comments AS comments " +
            "FROM projects p " +
            "JOIN quotations q ON p.quotation_id = q.quotation_id " +
            "JOIN clients c ON q.client_id = c.client_id " +
            "WHERE q.user_id = :salesPersonId",
            nativeQuery = true)
    List<Object[]> findProjectDetailsBySalesPersonId(@Param("salesPersonId") Integer salesPersonId);

    @Query(value = "SELECT project_number FROM projects WHERE project_number LIKE :prefix% ORDER BY project_number DESC LIMIT 1", nativeQuery = true)
    String findLastProjectNumberForYear(@Param("prefix") String prefix);


}
