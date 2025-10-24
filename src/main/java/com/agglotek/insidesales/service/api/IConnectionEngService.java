package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.ConnectionEng;
import java.util.List;
import java.util.Optional;

public interface IConnectionEngService {

    ConnectionEng save(ConnectionEng connectionEng);

    List<ConnectionEng> getAll();

    Optional<ConnectionEng> getById(Long id);

    ConnectionEng update(Long id, ConnectionEng updated);

    void delete(Long id);
}
