package com.kbalazsworks.stackjudge.db_migrations;

import lombok.val;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.SQLDataType.*;

@Component
public class V000001__init extends AbstractBaseJooqMigration
{
    @Override
    public void migrate(Context context)
    {
        val qB = getQueryBuilder(context);

        //@todo: disable all OIDS
        //@todo: check available nullable BIGINTs
        qB.createTable("company")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("name", VARCHAR.nullable(false))
            .column("domain", VARCHAR.nullable(false))
            .column("company_size_id", TINYINTUNSIGNED.nullable(false))
            .column("it_size_id", TINYINTUNSIGNED.nullable(false))
            .column("logo_path", VARCHAR.nullable(true).length(255))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", CHAR(36).nullable(true))
            .constraint(constraint("company_pk").primaryKey("id"))
            .execute();

        qB.createTable("address")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("company_id", BIGINT.nullable(false))
            .column("full_address", VARCHAR.nullable(true).length(255))
            .column("marker_lat", FLOAT.nullable(false))
            .column("marker_lng", FLOAT.nullable(false))
            .column("manual_marker_lat", FLOAT.nullable(true))
            .column("manual_marker_lng", FLOAT.nullable(true))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", CHAR(36).nullable(true))
            .constraints(
                constraint("address_pk").primaryKey("id"),
                constraint("fk__address_company_id__company_id__on_delete_cascade")
                    .foreignKey("company_id")
                    .references("company", "id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("group")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("parent_id", BIGINT.nullable(true))
            .column("company_id", BIGINT.nullable(false))
            .column("address_id", BIGINT.nullable(true))
            .column("name", VARCHAR.nullable(true).length(255))
            .column("type_id", TINYINTUNSIGNED.nullable(false))
            .column("members_on_group_id", TINYINTUNSIGNED.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", CHAR(36).nullable(true))
            .constraints(
                constraint("group_pk").primaryKey("id"),
                constraint("fk__group_company_id__company_id__on_delete_cascade")
                    .foreignKey("company_id")
                    .references("company", "id")
                    .onDeleteCascade(),
                constraint("fk__group_address_id__address_id__on_delete_cascade")
                    .foreignKey("address_id")
                    .references("address", "id")
                    .onDeleteCascade(),
                constraint("fk__group_parent_id__group_id__on_delete_cascade")
                    .foreignKey("parent_id")
                    .references("group", "id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("review")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("group_id", BIGINT.nullable(true))
            .column("visibility", TINYINTUNSIGNED.nullable(false))
            .column("rate", TINYINTUNSIGNED.nullable(true).length(255))
            .column("review", LONGNVARCHAR.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", CHAR(36).nullable(true))
            .constraints(
                constraint("review_pk").primaryKey("id"),
                constraint("fk__review_id__group_id__on_delete_cascade")
                    .foreignKey("group_id")
                    .references("group", "id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("protected_review_log")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("viewer_user_ids_user_id", CHAR(36).nullable(true))
            .column("review_id", BIGINT.nullable(false).identity(true))
            .column("created_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint("protected_review_log_pk").primaryKey("id"),
                constraint("fk__protected_review_log_review_id__review_id__on_delete_cascade")
                    .foreignKey("review_id")
                    .references("review", "id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("users")
            .column("ids_user_id", CHAR(36).nullable(false))
//            .column("is_email_user", BOOLEAN.nullable(false).defaultValue(false))
//            .column("is_facebook_user", BOOLEAN.nullable(false).defaultValue(false))
//            .column("profile_picture_url", VARCHAR(300).nullable(true))
//            .column("username", VARCHAR.nullable(true).length(255))
//            .column("password", VARCHAR.nullable(true).length(255))
//            .column("facebook_access_token", VARCHAR.nullable(true).length(255))
//            .column("facebook_id", BIGINT.nullable(true).length(255))
//            .column("pushover_user_token", VARCHAR(40).nullable(true))
            .constraints(constraint("users_pk").primaryKey("ids_user_id"))
            .execute();

        qB.createTable("notification")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("user_ids_user_id", CHAR(36).nullable(false))
            .column("type", TINYINTUNSIGNED.nullable(false))
            .column("data", JSONB.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("viewed_at", TIMESTAMP.nullable(true))
            .constraints(
                constraint("notification_pk").primaryKey("id"),
                constraint("fk__notification_id__users_ids_user_id__on_delete_cascade")
                    .foreignKey("user_ids_user_id")
                    .references("users", "ids_user_id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("persistence_log")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("type", TINYINTUNSIGNED.nullable(false))
            .column("data", JSONB.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint("persistence_log___pk").primaryKey("id")
            )
            .execute();

        qB.createTable("company_own_request")
            .column("requester_user_ids_user_id", CHAR(36).nullable(false))
            .column("requested_company_id", BIGINT.nullable(false))
            .column("secret", VARCHAR.nullable(true).length(60))
            .column("created_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint(DbConstants.COMPANY_OWN_REQUEST_PK)
                    .primaryKey("requester_user_ids_user_id", "requested_company_id"),
                constraint("fk___company_own_request__id___users__ids_user_id___on_delete_cascade")
                    .foreignKey("requester_user_ids_user_id")
                    .references("users", "ids_user_id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("google_static_maps_cache")
            .column("hash", VARCHAR.nullable(false).length(32))
            .column("file_name", VARCHAR.nullable(false).length(4096))
            .column("updated_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint("google_static_maps_cache___pk").primaryKey("hash")
            )
            .execute();

        qB.createTable("company_owner")
            .column("company_id", BIGINT.nullable(false).length(32))
            .column("user_ids_user_id", CHAR(36).nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint("user_ids_user_id___company_id___pk").primaryKey("company_id", "user_ids_user_id"),
                constraint("fk___company_owners__user_ids_user_id___users__ids_user_id___on_delete_cascade")
                    .foreignKey("user_ids_user_id")
                    .references("users", "ids_user_id")
                    .onDeleteCascade(),
                constraint("fk___company_owners__company_id___company__id___on_delete_cascade")
                    .foreignKey("company_id")
                    .references("company", "id")
                    .onDeleteCascade()
            )
            .execute();
    }
}
