package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.ClientConvo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ClientConvoRepository extends JpaRepository<ClientConvo, Integer> {
    List<ClientConvo> findByClientId(Integer clientId);
}
