package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.ConnectionEng;
import com.agglotek.insidesales.repository.ConnectionEngRepository;
import com.agglotek.insidesales.service.api.IConnectionEngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionEngServiceImpl implements IConnectionEngService {

    @Autowired
    private ConnectionEngRepository repository;

    @Override
    public ConnectionEng save(ConnectionEng connectionEng) {
        return repository.save(connectionEng);
    }

    @Override
    public List<ConnectionEng> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ConnectionEng> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ConnectionEng update(Long id, ConnectionEng updated) {
        return repository.findById(id)
                .map(existing -> {

                    if (updated.getCompanyName() != null)
                        existing.setCompanyName(updated.getCompanyName());

                    if (updated.getContactPersonName() != null)
                        existing.setContactPersonName(updated.getContactPersonName());

                    if (updated.getCountry() != null)
                        existing.setCountry(updated.getCountry());

                    if (updated.getRemark() != null)
                        existing.setRemark(updated.getRemark());

                    if (updated.getAddress() != null)
                        existing.setAddress(updated.getAddress());

                    if (updated.getState() != null)
                        existing.setState(updated.getState());

                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ConnectionEng not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
