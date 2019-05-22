--REPORTING TABLES
DROP TABLE IF EXISTS public.emission;
DROP TABLE IF EXISTS public.operating_detail;
DROP TABLE IF EXISTS public.reporting_period;
DROP TABLE IF EXISTS public.release_point_appt;
DROP TABLE IF EXISTS public.release_point;
DROP TABLE IF EXISTS public.emissions_process;
DROP TABLE IF EXISTS public.emissions_unit;
DROP TABLE IF EXISTS public.facility;
DROP TABLE IF EXISTS public.emissions_report;

--REFERENCE TABLES
DROP TABLE IF EXISTS public.agency;
DROP TABLE IF EXISTS public.aircraft_engine_type_code;
DROP TABLE IF EXISTS public.coordinate_datasource_code;
DROP TABLE IF EXISTS public.facility_category_code;
DROP TABLE IF EXISTS public.facility_source_type_code;
DROP TABLE IF EXISTS public.fips_country_code;
DROP TABLE IF EXISTS public.fips_county;
DROP TABLE IF EXISTS public.fips_state_code;
DROP TABLE IF EXISTS public.geographic_reference_point_code;
DROP TABLE IF EXISTS public.geometric_type_code;
DROP TABLE IF EXISTS public.hap_facility_category_code;
DROP TABLE IF EXISTS public.horizontal_collection_method_code;
DROP TABLE IF EXISTS public.horizontal_reference_datum_code;
DROP TABLE IF EXISTS public.naics_code;
DROP TABLE IF EXISTS public.operating_status_code;
DROP TABLE IF EXISTS public.program_system_code;
DROP TABLE IF EXISTS public.region_code;
DROP TABLE IF EXISTS public.regulatory_code;
DROP TABLE IF EXISTS public.unit_measure_code;
DROP TABLE IF EXISTS public.unit_type_code;
DROP TABLE IF EXISTS public.verification_code;
DROP TABLE IF EXISTS public.vertical_collection_method_code;
DROP TABLE IF EXISTS public.vertical_reference_datum_code;
DROP TABLE IF EXISTS public.schema_version_cef;

--SEQUENCES
DROP SEQUENCE IF EXISTS public.emission_id_seq;
DROP SEQUENCE IF EXISTS public.emissions_process_id_seq;
DROP SEQUENCE IF EXISTS public.emissions_report_id_seq;
DROP SEQUENCE IF EXISTS public.emissions_unit_id_seq;
DROP SEQUENCE IF EXISTS public.facility_id_seq;
DROP SEQUENCE IF EXISTS public.operating_detail_id_seq;
DROP SEQUENCE IF EXISTS public.release_point_appt_id_seq;
DROP SEQUENCE IF EXISTS public.release_point_id_seq;
DROP SEQUENCE IF EXISTS public.reporting_period_id_seq;
