package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientTO getPatientById(Long id) {
        PatientEntity patientEntity = patientRepository.findById(id).orElse(null);
        return PatientMapper.toPatientTO(patientEntity);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<VisitEntity> getVisitsByPatientId(Long patientId) {
        return null;
    }
}
