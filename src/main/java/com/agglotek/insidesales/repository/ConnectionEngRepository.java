package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.ConnectionEng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionEngRepository extends JpaRepository<ConnectionEng, Long> {
}
