
DROP VIEW vw_report_download;

-- update view
CREATE OR REPLACE VIEW vw_report_download
AS
SELECT row_number() OVER (ORDER BY p.pollutant_cas_id) AS id,
    er.id AS report_id,
    replace(fs.alt_site_identifier::text, ','::text, ''::text) AS facility_site_id,
    er.year AS inventory_year,
    replace(eu.unit_identifier::text, ','::text, ''::text) AS emissions_unit_id,
    replace(eu.description::text, ','::text, ''::text) AS emission_unit_description,
    replace(ep.emissions_process_identifier::text, ','::text, ''::text) AS process_id,
    replace(ep.description::text, ','::text, ''::text) AS process_description,
    replace(p.pollutant_name::text, ','::text, ''::text) AS pollutant_name,
    replace(repcode.short_name::text, ','::text, ''::text) AS reporting_period_type,
    replace(calc_mat.description::text, ','::text, ''::text) AS throughput_material,
    replace(repper.calculation_parameter_value::text, ','::text, ''::text) AS throughput_amount,
    repper.calculation_parameter_uom AS throughput_uom,
    repper.fuel_use_value as fuel_value,
    repper.fuel_use_uom as fuel_uom,
    replace(fuel_calc_mat.description::text, ','::text, ''::text) AS fuel_material,
    repper.heat_content_value as heat_content_value,
    repper.heat_content_uom as heat_content_uom,
    cmc.description AS emission_calc_method,
    e.emissions_uom_code,
    e.emissions_numerator_uom,
    e.emissions_denominator_uom,
    e.emissions_factor,
    e.overall_control_percent,
    e.last_modified_by,
    e.last_modified_date,
    replace(replace(replace(e.emissions_factor_text::text, ','::text, ''::text), chr(13), ' '::text), chr(10), ' '::text) AS emissions_factor_text,
    replace(replace(replace(e.comments::text, ','::text, ''::text), chr(13), ' '::text), chr(10), ' '::text) AS emissions_comment,
    replace(replace(replace(e.calculation_comment::text, ','::text, ''::text), chr(13), ' '::text), chr(10), ' '::text) AS calculation_comment,
    e.total_emissions
   FROM emission e
     JOIN calculation_method_code cmc ON cmc.code::text = e.emissions_calc_method_code::text
     JOIN reporting_period repper ON repper.id = e.reporting_period_id
     JOIN reporting_period_code repcode ON repper.reporting_period_type_code::text = repcode.code::text
     LEFT JOIN calculation_material_code calc_mat ON calc_mat.code::text = repper.calculation_material_code::text
     LEFT JOIN calculation_material_code fuel_calc_mat ON fuel_calc_mat.code::text = repper.fuel_use_material_code::text
     JOIN emissions_process ep ON ep.id = repper.emissions_process_id
     JOIN emissions_unit eu ON ep.emissions_unit_id = eu.id
     JOIN facility_site fs ON fs.id = eu.facility_site_id
     JOIN emissions_report er ON er.id = fs.report_id
     JOIN pollutant p ON p.pollutant_code::text = e.pollutant_code::text
  WHERE ep.status_code::text = 'OP'::text
  ORDER BY er.id, eu.unit_identifier, ep.emissions_process_identifier, p.pollutant_code;
