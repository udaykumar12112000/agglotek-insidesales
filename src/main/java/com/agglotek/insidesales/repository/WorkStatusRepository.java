package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkStatusRepository extends JpaRepository<WorkStatus, Integer> {

    List<WorkStatus> findByUserId(Integer userId);
}
