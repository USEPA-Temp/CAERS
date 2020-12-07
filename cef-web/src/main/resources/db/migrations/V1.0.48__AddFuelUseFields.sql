ALTER TABLE reporting_period ADD COLUMN calculation_parameter_value numeric,
ALTER TABLE reporting_period ADD COLUMN calculation_parameter_uom character varying(20),
ALTER TABLE reporting_period ADD COLUMN calculation_material_code character varying(20);
