

TRUNCATE TABLE EMISSIONS_REPORT CASCADE;

--EMISSION REPORTS
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999997', '9758611', '9758611', 'GA', '2019', 'InProgress', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999996', '9758611', '9758611', 'GA', '2018', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999995', '9758611', '9758611', 'GA', '2017', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999994', '9758611', '9758611', 'GA', '2016', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999993', '9758611', '9758611', 'GA', '2015', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999998', '2774511', '2774511', 'GA', '2014', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
 INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999999', '2774511', '2774511', 'GA', '2019', 'InProgress', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999910', '2774511', '2774511', 'GA', '2018', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO emissions_report(id, frs_facility_id, eis_program_id, agency_code, year, status, validation_status, created_by, created_date, last_modified_by, last_modified_date)
 VALUES ('9999911', '2774511', '2774511', 'GA', '2017', 'Certified', '', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--FACILITY
INSERT INTO FACILITY (id, report_id, frs_facility_id, alt_site_identifier, category_code, source_type_code, name, description, status_code, status_year, program_system_code, 
 	naics_code, street_address, city, county, state_code, country_code, postal_code, latitude, longitude, mailing_street_address, mailing_city, mailing_state_code, mailing_postal_code,  created_by, created_date, last_modified_by, last_modified_date) 
	VALUES ('9999991', '9999997', '9758611', '', 'CAP', '133', 'Gilman Building Products LLC', 'Pulp and Paper Processing Plant', 'OP', '1985', '63JJJJ', '4241',
	'173 Peachtree Rd', 'Fitzgerald', '', 'GA' , '', '31750', '33.7490', '84.3880', '173 Peachtree Rd', 'Fitzgerald', 'GA', '31750', 
	'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--EMISSION UNITS
INSERT INTO EMISSIONS_UNIT (id, facility_id, unit_identifier, program_system_code, description, type_code, type_code_description, status_code, status_year, unit_measure_cd,
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES('9999991', '9999991', 'Boiler 001', '63JJJJ', 'Gas Boiler - Industrial Size', '206', 'Air Gas Furnace', 'OP', '1985', 'LB/DAY', 'THOMAS.FESPERMAN', 
		current_timestamp, 'THOMAS.FESPERMAN', current_timestamp); 
INSERT INTO EMISSIONS_UNIT (id, facility_id, unit_identifier, program_system_code, description, type_code, type_code_description, status_code, status_year,  unit_measure_cd,
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES('9999992', '9999991', 'Boiler 002', '63JJJJ', 'Coal Boiler - Industrial Size', '100', 'Boiler', 'OP', '1985', 'TON/HR', 'THOMAS.FESPERMAN', 
		current_timestamp, 'THOMAS.FESPERMAN', current_timestamp); 
INSERT INTO EMISSIONS_UNIT (id, facility_id, unit_identifier, program_system_code, description, type_code, type_code_description, status_code, status_year,  unit_measure_cd,
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES('9999993', '9999991', 'Dryer 001', '63JJJJ', 'Big Dryer', '1252', 'Primary Tube Dryer', 'OP', '1985', 'TON', 'THOMAS.FESPERMAN', 
		current_timestamp, 'THOMAS.FESPERMAN', current_timestamp); 

--RELEASE POINTS
INSERT INTO RELEASE_POINT (id, facility_id, release_point_identifier, program_system_code, type_code, description, stack_height, stack_height_uom_code, stack_diameter, 
	stack_diameter_uom_code, exit_gas_velocity, exit_gas_velocity_uom_code, exit_gas_temperature, exit_gas_flow_rate, exit_gas_flow_uom_code, status_code, status_year, 
	fugitive_line_1_latitude, fugitive_line_1_longitude, fugitive_line_2_latitude, fugitive_line_2_longitude, latitude, longitude, 
	created_by, created_date, last_modified_by, last_modified_date) 
	VALUES ('9999991', '9999991', 'Smokestack 1', '63JJJJ', 'STACK', 'A big smokestack', 40, 'M', 7, 'M', 20, 'FT/MIN', 75, 25, 'YD3/HR', 'OP', '1985', 
		'32.7490', '83.3880', '34.7490', '85.3880', '33.7490', '84.3880', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT (id, facility_id, release_point_identifier, program_system_code, type_code, description, stack_height, stack_height_uom_code, stack_diameter, 
	stack_diameter_uom_code, exit_gas_velocity, exit_gas_velocity_uom_code, exit_gas_temperature, exit_gas_flow_uom_code, status_code, status_year, latitude, longitude, 
	created_by, created_date, last_modified_by, last_modified_date) 
	VALUES ('9999992', '9999991', 'Smokestack 2', '63JJJJ', 'STACK', 'Another big smokestack', NULL, '', NULL, '', NULL, '', NULL, '', 'OP', '1985', '33.7490', '84.3880', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT (id, facility_id, release_point_identifier, program_system_code, type_code, description, stack_height, stack_height_uom_code, stack_diameter, 
	stack_diameter_uom_code, exit_gas_velocity, exit_gas_velocity_uom_code, exit_gas_temperature, exit_gas_flow_uom_code, status_code, status_year, latitude, longitude, 
	created_by, created_date, last_modified_by, last_modified_date) 
	VALUES ('9999993', '9999991', 'Vent 1', '63JJJJ', 'VENT', 'A big vent', NULL, '', NULL, '', NULL, '', NULL, '', 'OP', '1985', '33.7490', '84.3880', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--EMISSION PROCESS
INSERT INTO EMISSIONS_PROCESS (id, emissions_unit_id, emissions_process_identifier, status_code, status_year, scc_code, scc_short_name, description, 
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999991', '9999991', 'Drying Process', 'OP', '1985', '30700752', '', 'Softwood Veneer Dryer: Direct Natural Gas-fired: Heated Zones', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO EMISSIONS_PROCESS (id, emissions_unit_id, emissions_process_identifier, status_code, status_year, scc_code, scc_short_name, description, 
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999992', '9999991', 'Disposal Process', 'OP', '1985', '50300106', '', 'Air Curtain Combustor: Wood', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO EMISSIONS_PROCESS (id, emissions_unit_id, emissions_process_identifier, status_code, status_year, scc_code, scc_short_name, description, 
	created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999993', '9999991', 'Storage Process', 'OP', '1985', '30700828', '', 'Chip Storage Piles: Hardwood', 
		'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--RELEASE POINT APPORTIONMENT
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999991', '9999991', '9999991', '33', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999992', '9999992', '9999991', '33', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999993', '9999993', '9999991', '33', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999994', '9999991', '9999992', '60', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999995', '9999992', '9999992', '40', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999996', '9999991', '9999993', '10', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999997', '9999992', '9999993', '20', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO RELEASE_POINT_APPT (id, release_point_id, emissions_process_id, percent, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999998', '9999993', '9999993', '70', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

--REPORTING_PERIOD
INSERT INTO REPORTING_PERIOD (id, emissions_process_id, reporting_period_type_code, emissions_operating_type_code, calculation_parameter_type_code,
	calculation_parameter_value, calculation_parameter_uom, calculation_material_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999991', '9999991', 'Annual', 'OP', 'TEST', '111', 'TEST', 'TEST', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO REPORTING_PERIOD (id, emissions_process_id, reporting_period_type_code, emissions_operating_type_code, calculation_parameter_type_code,
	calculation_parameter_value, calculation_parameter_uom, calculation_material_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999992', '9999992', 'Annual', 'OP', 'TEST', '111', 'TEST', 'TEST', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO REPORTING_PERIOD (id, emissions_process_id, reporting_period_type_code, emissions_operating_type_code, calculation_parameter_type_code,
	calculation_parameter_value, calculation_parameter_uom, calculation_material_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999993', '9999993', 'Annual', 'OP', 'TEST', '111', 'TEST', 'TEST', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);


INSERT INTO OPERATING_DETAIL (id, reporting_period_id, actual_hours_per_period, avg_hours_per_day, avg_days_per_week, percent_winter, percent_spring, percent_summer, 
	percent_fall, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999991', '9999991', '8700', '24','7','25', '25', '25', '25', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp); 
INSERT INTO OPERATING_DETAIL (id, reporting_period_id, actual_hours_per_period, avg_hours_per_day, avg_days_per_week, percent_winter, percent_spring, percent_summer, 
	percent_fall, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999992', '9999992', '8700', '20','6','10', '10', '50', '30', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp); 
INSERT INTO OPERATING_DETAIL (id, reporting_period_id, actual_hours_per_period, avg_hours_per_day, avg_days_per_week, percent_winter, percent_spring, percent_summer, 
	percent_fall, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999993', '9999993', '8700', '18','5','45', '5', '25', '25', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);

INSERT INTO EMISSION (id, reporting_period_id, pollutant_code, pollutant_name, total_emissions, emissions_uom_code, emissions_factor, emissions_factor_text, 
	emissions_calc_method_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999991', '9999991', '5280', 'Acetaldehyde', '1000', 'tons', '0.6200', '6.200E-2 Lb per 1000 Square Feet 3/8-inch Thick Veneer Produced', 
		'CEMS', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO EMISSION (id, reporting_period_id, pollutant_code, pollutant_name, total_emissions, emissions_uom_code, emissions_factor, emissions_factor_text, 
	emissions_calc_method_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999992', '9999991', '17298852', 'Acetone', '1007.75', 'tons', '0.5920', '5.900E-2 Lb per 1000 Square Feet 3/8-inch Thick Veneer Produced', 
		'CEMS', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);
INSERT INTO EMISSION (id, reporting_period_id, pollutant_code, pollutant_name, total_emissions, emissions_uom_code, emissions_factor, emissions_factor_text, 
	emissions_calc_method_code, created_by, created_date, last_modified_by, last_modified_date)
	VALUES ('9999993', '9999991', '4754', 'Benzene', '2015.6', 'tons', '0.5700', '5.700E-3 Lb per 1000 Square Feet 3/8-inch Thick Veneer Produced', 
		'CEMS', 'THOMAS.FESPERMAN', current_timestamp, 'THOMAS.FESPERMAN', current_timestamp);