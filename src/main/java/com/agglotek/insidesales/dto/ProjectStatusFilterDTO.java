package com.agglotek.insidesales.dto;

import java.util.List;

public class ProjectStatusFilterDTO {
    public List<String> getProjectStatuses() {
        return projectStatuses;
    }

    public void setProjectStatuses(List<String> projectStatuses) {
        this.projectStatuses = projectStatuses;
    }

    private List<String> projectStatuses;

    public ProjectStatusFilterDTO(List<String> projectStatuses) {
        this.projectStatuses = projectStatuses;
    }
}
