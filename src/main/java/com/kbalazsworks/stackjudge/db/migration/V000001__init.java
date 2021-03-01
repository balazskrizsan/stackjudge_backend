package com.kbalazsworks.stackjudge.db.migration;

import lombok.val;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.SQLDataType.*;

@Component
public class V000001__init extends AbstractBaseJooqMigration
{
    @Override
    public void migrate(Context context) throws Exception
    {
        val qB = getQueryBuilder(context);

        //@todo: disable all OIDS
        //@todo: check available nullable BIGINTs
        qB.createTable("company")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("name", VARCHAR.nullable(false))
            .column("company_size_id", SMALLINTUNSIGNED.nullable(false))
            .column("it_size_id", SMALLINTUNSIGNED.nullable(false))
            .column("logo_path", VARCHAR.nullable(true).length(255))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", BIGINT.nullable(true))
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
            .column("created_by", BIGINT.nullable(true))
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
            .column("name", VARCHAR.nullable(true).length(255))
            .column("type_id", SMALLINTUNSIGNED.nullable(false))
            .column("members_on_group_id", SMALLINTUNSIGNED.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", BIGINT.nullable(true))
            .constraints(
                constraint("group_pk").primaryKey("id"),
                constraint("fk__group_company_id__company_id__on_delete_cascade")
                    .foreignKey("company_id")
                    .references("company", "id")
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
            .column("visibility", SMALLINTUNSIGNED.nullable(false))
            .column("rate", SMALLINTUNSIGNED.nullable(true).length(255))
            .column("review", LONGNVARCHAR.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", BIGINT.nullable(true))
            .constraints(
                constraint("review_pk").primaryKey("id"),
                constraint("fk__review_id__group_id__on_delete_cascade")
                    .foreignKey("group_id")
                    .references("group", "id")
                    .onDeleteCascade()
            )
            .execute();

        qB.createTable("users")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("provider_type", SMALLINTUNSIGNED.nullable(false))
            .column("username", VARCHAR.nullable(true).length(255))
            .column("password", VARCHAR.nullable(true).length(255))
            .column("display_name", VARCHAR.nullable(true).length(255))
            .column("external_id", BIGINTUNSIGNED.nullable(false))
            .execute();
    }
}
