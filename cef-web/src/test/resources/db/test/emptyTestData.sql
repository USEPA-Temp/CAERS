TRUNCATE TABLE MASTER_FACILITY_RECORD CASCADE;
TRUNCATE TABLE CONTROL_PATH CASCADE;

INSERT INTO MASTER_FACILITY_RECORD (id, eis_program_id, agency_facility_id, category_code, source_type_code, name, description, status_code, status_year,
 program_system_code, street_address, city, county_code, state_code, country_code, postal_code, latitude, longitude, 
 mailing_street_address, mailing_city, mailing_state_code, mailing_postal_code,  created_by, created_date, last_modified_by, last_modified_date) 
    VALUES ('9999991', '9758611', '12700027', 'CAP', '133', 'Gilman Building Products LLC', 'Pulp and Paper Processing Plant',
     'OP', '1985', '63JJJJ', '173 Peachtree Rd', 'Fitzgerald', '13313', '13' , '', '31750', '33.7490', '-84.3880', '173 Peachtree Rd', 'Fitzgerald', '13', '31750', 
    'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);