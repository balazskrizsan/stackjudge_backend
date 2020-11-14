CREATE TABLE "company"
(
    "id"              bigserial    NOT NULL,
    "name"            varchar(255) NOT NULL,
    "company_size_id" smallint     NOT NULL,
    "it_size_id"      smallint     NOT NULL,
    "created_at"      TIMESTAMP    NOT NULL,
    "created_by"      bigint,
    CONSTRAINT "company_pk" PRIMARY KEY ("id")
) WITH (OIDS= FALSE);

CREATE TABLE "address"
(
    "id"                bigserial    NOT NULL,
    "company_id"        bigint       NOT NULL,
    "full_address"      varchar(255) NOT NULL,
    "marker_lat"        float        NOT NULL,
    "marker_lng"        float        NOT NULL,
    "manual_marker_lat" float        NOT NULL,
    "manual_marker_lng" float        NOT NULL,
    "created_at"        TIMESTAMP    NOT NULL,
    "created_by"        bigint
) WITH (OIDS= FALSE);

ALTER TABLE "address"
    ADD CONSTRAINT "fk__address_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

CREATE TABLE "stack"
(
    "id"                  bigserial    NOT NULL,
    "company_id"          bigint       NOT NULL,
    "parent_id"           bigint,
    "type_id"             smallint     NOT NULL,
    "name"                varchar(255) NOT NULL,
    "members_on_stack_id" smallint     NOT NULL,
    "created_at"          TIMESTAMP    NOT NULL,
    "created_by"          bigint,
    CONSTRAINT "stack_pk" PRIMARY KEY ("id")
) WITH (OIDS= FALSE);

ALTER TABLE "stack"
    ADD CONSTRAINT "fk__stack_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

ALTER TABLE "stack"
    ADD CONSTRAINT "fk__stack_parent_id__stack_id__on_delete_cascade"
        FOREIGN KEY ("parent_id")
            REFERENCES "stack" ("id") ON DELETE CASCADE;
