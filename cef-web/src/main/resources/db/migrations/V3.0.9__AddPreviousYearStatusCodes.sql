
ALTER TABLE public.release_point
ADD COLUMN previous_year_status_code varchar(20); 

ALTER TABLE public.emissions_unit
ADD COLUMN previous_year_status_code varchar(20); 

ALTER TABLE public.emissions_process
ADD COLUMN previous_year_status_code varchar(20); 

ALTER TABLE public.control
ADD COLUMN previous_year_status_code varchar(20); 