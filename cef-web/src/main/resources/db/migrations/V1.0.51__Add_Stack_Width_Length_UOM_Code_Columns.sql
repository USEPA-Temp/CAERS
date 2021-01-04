ALTER TABLE release_point
    ADD COLUMN stack_width numeric(6),
    ADD COLUMN stack_length numeric(6),
    ADD COLUMN stack_width_uom_code varchar(20),
    ADD COLUMN stack_length_uom_code varchar(20);
