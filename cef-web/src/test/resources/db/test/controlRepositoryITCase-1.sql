
delete from control;
delete from facility_site;
delete from emissions_report;

INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999997', '110015680798', '9758611', 'GA', '2019', 'SUBMITTED', 'PASSED_WARNINGS', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--FACILITY
INSERT INTO FACILITY_SITE (id, report_id, frs_facility_id, eis_program_id, alt_site_identifier, category_code, source_type_code, name, description, status_code, status_year,
 program_system_code, street_address, city, county, state_code, country_code, postal_code, latitude, longitude,
 mailing_street_address, mailing_city, mailing_state_code, mailing_postal_code,  created_by, created_date, last_modified_by, last_modified_date)
    VALUES ('9999991', '9999997', '110015680798', '9758611', '1301700008', 'CAP', '133', 'Gilman Building Products LLC', 'Pulp and Paper Processing Plant',
     'OP', '1985', '63JJJJ', '173 Peachtree Rd', 'Fitzgerald', 'Whitfield', 'GA' , '', '31750', '33.7490', '-84.3880', '173 Peachtree Rd', 'Fitzgerald', 'GA', '31750',
    'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

INSERT INTO CONTROL (id, facility_site_id, status_code, identifier, description, percent_capture, percent_control, created_by, created_date, last_modified_by, last_modified_date, comments)
    VALUES ('9999991', '9999991', 'OP', 'Control 001', 'Acetaldehyde and Benzene Control', 50, 50, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp,
    'Sample comments for Control 001');

INSERT INTO CONTROL (id, facility_site_id, status_code, identifier, description, percent_capture, percent_control, created_by, created_date, last_modified_by, last_modified_date, comments)
    VALUES ('9999992', '9999991', 'OP', 'Control 002', 'Acetaldehyde Control', 25, 75, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp,
    'Sample comments for Control 002');
