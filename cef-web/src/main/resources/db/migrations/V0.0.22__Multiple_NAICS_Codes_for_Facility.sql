CREATE TABLE facility_naics_xref
(
    id bigserial NOT NULL,
    facility_site_id bigint NOT NULL,
    naics_code numeric(6) NOT NULL,
    primary_flag boolean NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT NOW(),
    last_modified_by character varying(255) NOT NULL,
    last_modified_date timestamp without time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT facility_naics_xref_pkey PRIMARY KEY (id),
    CONSTRAINT facility_naics_facility_site_fkey FOREIGN KEY (facility_site_id)
        REFERENCES facility_site (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT facility_naics_code_fkey FOREIGN KEY (naics_code)
        REFERENCES naics_code (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DROP VIEW IF EXISTS vw_submissions_review_dashboard;

ALTER TABLE facility_site DROP COLUMN naics_code;