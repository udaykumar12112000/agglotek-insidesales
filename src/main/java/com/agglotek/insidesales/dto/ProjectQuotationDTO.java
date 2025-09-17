package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;

public class ProjectQuotationDTO {
    private Project project;
    private Quotation quotation;

    public ProjectQuotationDTO(Project project, Quotation quotation) {
        this.project = project;
        this.quotation = quotation;
    }

    // Getters and Setters

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
}
