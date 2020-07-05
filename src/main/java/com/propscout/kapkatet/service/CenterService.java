package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.Center;
import com.propscout.kapkatet.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    private boolean existsById(int id) {
        return centerRepository.existsById(id);
    }

    public Center findById(Integer id) {
        return centerRepository.findById(id).orElse(null);
    }

    public List<Center> findAll() {
        return centerRepository.findAll();
    }

    public Center save(Center center) throws Exception {

        validateCenterInputs(center);

        if (center.getId() != null && existsById(center.getId()))
            throw new Exception(String.format("Center with the id: %d already exists", center.getId()));

        return centerRepository.save(center);
    }

    public void update(Center center) throws Exception {
        validateCenterInputs(center);

        if (!existsById(center.getId())) {
            throw new Exception(String.format("Cannot find center with the id: %d", center.getId()));
        }

        centerRepository.save(center);
    }

    public void deleteByIt(Integer id) throws Exception {
        if (!existsById(id)) {
            throw new Exception(String.format("Cannot find center with the id: %d", id));
        }
        centerRepository.deleteById(id);
    }

    private void validateCenterInputs(Center center) throws Exception {

        if (StringUtils.isEmpty(center.getName())) throw new Exception("Center name is required");
        if (StringUtils.isEmpty(center.getLocation())) throw new Exception("Center location is required");
        if (StringUtils.isEmpty(center.getSubLocation())) throw new Exception("Center sub-location is required");

    }

    public long count() {
        return centerRepository.count();
    }
}
