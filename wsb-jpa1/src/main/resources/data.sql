INSERT INTO address (id, address_line1, address_line2, city, postal_code)
    VALUES
        (1, '123 Main St', 'Apt 4B', 'New York', '10001'),
        (2, '456 Maple Ave', 'Suite 101', 'Los Angeles', '90001');

-- Wypełnianie tabeli Doctor
INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
    VALUES
    (1, 'John', 'Doe', '555-1234', 'john.doe@example.com', 'D001', 'CARDIOLOGY', 1);

-- Wypełnianie tabeli Patient
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
    VALUES
    (1, 'Jane', 'Smith', '555-5678', 'jane.smith@example.com', 'P001', '1985-07-15', 2);

-- Wypełnianie tabeli Visit
INSERT INTO visit (id, description, time, doctor_id, patient_id)
    VALUES
    (1, 'Routine checkup', '2024-12-05 10:00:00', 1, 1);

-- Wypełnianie tabeli MedicalTreatment
INSERT INTO medical_treatment (id, description, type, visit_id)
    VALUES
    (1, 'Blood pressure medication', 'PHARMACOLOGICAL', 1);