--emissions_report
CREATE TABLE emissions_report
(
  id                    SERIAL PRIMARY KEY,
  facility_id           VARCHAR(255),
  status                VARCHAR(20) NOT NULL,
  year                  SMALLINT NOT NULL,
  created_date          timestamp NOT NULL,
  last_modified_date    timestamp NOT NULL
);