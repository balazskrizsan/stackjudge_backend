CREATE TABLE "company"
(
    "id"              BIGSERIAL    NOT NULL,
    "name"            VARCHAR(255) NOT NULL,
    "company_size_id" SMALLINT     NOT NULL,
    "it_size_id"      SMALLINT     NOT NULL,
    "logo_path"       VARCHAR(255),
    "created_at"      TIMESTAMP    NOT NULL,
    "created_by"      BIGINT       NULL,
    CONSTRAINT "company_pk" PRIMARY KEY ("id")
) WITH (OIDS = FALSE);

CREATE TABLE "address"
(
    "id"                BIGSERIAL    NOT NULL,
    "company_id"        BIGINT       NOT NULL,
    "full_address"      VARCHAR(255) NOT NULL,
    "marker_lat"        FLOAT        NOT NULL,
    "marker_lng"        FLOAT        NOT NULL,
    "manual_marker_lat" FLOAT,
    "manual_marker_lng" FLOAT,
    "created_at"        TIMESTAMP    NOT NULL,
    "created_by"        BIGINT       NULL,
    CONSTRAINT "address_pk" PRIMARY KEY ("id")
) WITH (OIDS = FALSE);

ALTER TABLE "address"
    ADD CONSTRAINT "fk__address_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

CREATE TABLE "group"
(
    "id"                  BIGSERIAL    NOT NULL,
    "company_id"          BIGINT       NOT NULL,
    "parent_id"           BIGINT,
    "type_id"             SMALLINT     NOT NULL,
    "name"                VARCHAR(255) NOT NULL,
    "members_on_group_id" SMALLINT     NOT NULL,
    "created_at"          TIMESTAMP    NOT NULL,
    "created_by"          BIGINT       NULL,
    CONSTRAINT "group_pk" PRIMARY KEY ("id")
) WITH (OIDS = FALSE);

ALTER TABLE "group"
    ADD CONSTRAINT "fk__group_company_id__company_id__on_delete_cascade"
        FOREIGN KEY ("company_id")
            REFERENCES "company" ("id") ON DELETE CASCADE;

ALTER TABLE "group"
    ADD CONSTRAINT "fk__group_parent_id__group_id__on_delete_cascade"
        FOREIGN KEY ("parent_id")
            REFERENCES "group" ("id") ON DELETE CASCADE;

CREATE TABLE "review"
(
    "id"         BIGSERIAL NOT NULL,
    "group_id"   BIGINT    NOT NULL,
    "visibility" SMALLINT  NOT NULL,
    "rate"       SMALLINT  NOT NULL,
    "review"     TEXT      NOT NULL,
    "created_at" timestamp NOT NULL,
    "created_by" BIGINT    NULL,
    CONSTRAINT "review_pk" PRIMARY KEY ("id")
) WITH (OIDS = FALSE);

ALTER TABLE "review"
    ADD CONSTRAINT "fk__review_id__group_id__on_delete_cascade"
        FOREIGN KEY ("group_id")
            REFERENCES "group" ("id") ON DELETE CASCADE;

CREATE TABLE users
(
  id       BIGSERIAL NOT NULL,
  username VARCHAR,
  password VARCHAR
);
