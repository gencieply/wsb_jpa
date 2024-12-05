package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "TIME", nullable = false)
	private LocalDateTime time;

	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID", nullable = false)
	private DoctorEntity doctor;

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private PatientEntity patient;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MedicalTreatmentEntity> treatments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
