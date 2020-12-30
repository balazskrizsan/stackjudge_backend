CREATE TABLE "company"
(
    "id"              bigserial    NOT NULL,
    "name"            varchar(255) NOT NULL,
    "company_size_id" smallint     NOT NULL,
    "it_size_id"      smallint     NOT NULL,
    "logo_path"       varchar(255),
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
    "manual_marker_lat" float,
    "manual_marker_lng" float,
    "created_at"        TIMESTAMP    NOT NULL,
    "created_by"        bigint
) WITH (OIDS= FALSE);

ALTER TABLE "address"
    ADD CONSTRAINT "fk__address_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

CREATE TABLE "group"
(
    "id"                  bigserial    NOT NULL,
    "company_id"          bigint       NOT NULL,
    "parent_id"           bigint,
    "type_id"             smallint     NOT NULL,
    "name"                varchar(255) NOT NULL,
    "members_on_group_id" smallint     NOT NULL,
    "created_at"          TIMESTAMP    NOT NULL,
    "created_by"          bigint,
    CONSTRAINT "group_pk" PRIMARY KEY ("id")
) WITH (OIDS= FALSE);

ALTER TABLE "group"
    ADD CONSTRAINT "fk__group_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

ALTER TABLE "group"
    ADD CONSTRAINT "fk__group_parent_id__group_id__on_delete_cascade"
        FOREIGN KEY ("parent_id")
            REFERENCES "group" ("id") ON DELETE CASCADE;
