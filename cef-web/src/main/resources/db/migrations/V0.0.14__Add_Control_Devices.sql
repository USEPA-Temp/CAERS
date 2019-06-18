CREATE TABLE control_type_code
(
    code character varying(20) NOT NULL,
    description character varying(200) NOT NULL,
    CONSTRAINT control_type_code_pkey PRIMARY KEY (code)
);

CREATE TABLE control_path
(
    id bigserial NOT NULL,
    description character varying(200) NOT NULL,
    control_type character varying(20) NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT NOW(),
    last_modified_by character varying(255) NOT NULL,
    last_modified_date timestamp without time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT control_path_pkey PRIMARY KEY (id),
    CONSTRAINT control_path_type_key FOREIGN KEY (control_type)
        REFERENCES control_type_code (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE control_assignment
(
    id bigserial NOT NULL,
    description character varying(200),
    release_point_id bigint,
    emissions_unit_id bigint,
    emissions_process_id bigint,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT NOW(),
    last_modified_by character varying(255) NOT NULL,
    last_modified_date timestamp without time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT control_assignment_pkey PRIMARY KEY (id),
    CONSTRAINT control_assignment_rp_fkey FOREIGN KEY (release_point_id)
        REFERENCES release_point (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT control_assignment_ep_fkey FOREIGN KEY (emissions_process_id)
        REFERENCES emissions_process (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT control_assignment_eu_fkey FOREIGN KEY (emissions_unit_id)
        REFERENCES emissions_unit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE control
(
    id bigserial NOT NULL,
    description character varying(200),
    percent_capture numeric(5, 1),
    percent_control numeric(5, 1),
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT NOW(),
    last_modified_by character varying(255) NOT NULL,
    last_modified_date timestamp without time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT control_pkey PRIMARY KEY (id)
);

CREATE TABLE public.control_pollutant
(
    id bigserial NOT NULL,
    control_id bigint NOT NULL,
    pollutant_code character varying(20) NOT NULL,
    pollutant_name character varying(200) NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT NOW(),
    last_modified_by character varying(255) NOT NULL,
    last_modified_date timestamp without time zone NOT NULL DEFAULT NOW(),
    CONSTRAINT control_pollutant_pkey PRIMARY KEY (id),
    CONSTRAINT pollutant_control_fkey FOREIGN KEY (control_id)
        REFERENCES public.control (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)



