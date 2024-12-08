package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.PatientTO.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientTO toPatientTO(PatientEntity entity) {
        if (entity == null) {
            return null;
        }

        PatientTO to = new PatientTO();
        to.setId(entity.getId());
        to.setFirstName(entity.getFirstName());
        to.setLastName(entity.getLastName());
        to.setTelephoneNumber(entity.getTelephoneNumber());
        to.setEmail(entity.getEmail());
        to.setPatientNumber(entity.getPatientNumber());
        to.setDateOfBirth(entity.getDateOfBirth());
        to.setRegistrationDate(entity.getRegistrationDate());
        to.setVisits(entity.getVisits() != null
                ? entity.getVisits().stream().map(PatientMapper::toVisitTO).collect(Collectors.toList())
                : null);

        return to;
    }

    private static VisitTO toVisitTO(VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTO visitTO = new VisitTO();
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        visitTO.setTreatmentTypes(visitEntity.getTreatments() != null
                ? visitEntity.getTreatments().stream()
                .map(MedicalTreatmentEntity::getType)
                .map(Enum::name)
                .collect(Collectors.toList())
                : null);

        return visitTO;
    }
}
