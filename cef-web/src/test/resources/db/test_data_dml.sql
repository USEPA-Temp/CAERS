TRUNCATE emissions_report;
TRUNCATE facility;

--cef-105 test data for emissions_report
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'Certified', 2013, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'Failed', 2014, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'InProgress', 2015, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'NotStarted', 2016, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'Passed', 2017, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'PassedAlerts', 2018, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('2774511', 'Pending', 2019, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('9758611', 'Certified', 2016, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('9758611', 'Certified', 2017, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('9758611', 'Certified', 2018, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(facility_id, status, year, created_by, created_date, last_modified_by, last_modified_date) VALUES ('9758611', 'InProgress', 2019, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
COMMIT;

-- CEF-145 adding facility records to the new facility table
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 1', 'GA', 'Peach', 'NAICS', 123456, 100, 90, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 2', 'GA', 'Macon', 'NAICS', 7891011, 200, 200, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 3', 'GA', 'Dekalb', 'NAICS', 121314, 300, 310, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 4', 'GA', 'Gwinnett', 'NAICS', 151617, 400, 420, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 5', 'GA', 'Monroe', 'NAICS', 181920, 500, 530, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 6', 'GA', 'Bibb', 'NAICS', 212223, 600, 640, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 7', 'GA', 'Decatur', 'NAICS', 242526, 700, 750, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 8', 'GA', 'Wilkes', 'NAICS', 272829, 800, 790, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 9', 'GA', 'Camden', 'NAICS', 303132, 900, 910, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO facility(name, state, county, naics, eis_id, current_tons, previous_tons, created_by, created_date, last_modified_by, last_modified_date) VALUES ('Facility Name 10', 'NC', 'Wake', 'NAICS', 333435, 1000, 1100, 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
COMMIT;
