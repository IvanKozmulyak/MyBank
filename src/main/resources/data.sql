CREATE TABLE deposit_types (
    id serial not null,
    name varchar(255),
    percentage integer,
    triggers_count integer,
    primary key (id)
);

INSERT INTO deposit_types (name, percentage, triggers_count) VALUES
    ('3 month', '12', '3'),
    ('6 month', '15', '6'),
    ('12 month', '18', '12');