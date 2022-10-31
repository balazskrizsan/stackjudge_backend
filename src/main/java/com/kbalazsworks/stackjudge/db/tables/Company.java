/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables;


import com.kbalazsworks.stackjudge.db.Keys;
import com.kbalazsworks.stackjudge.db.Public;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Company extends TableImpl<CompanyRecord> {

    private static final long serialVersionUID = -737452323;

    /**
     * The reference instance of <code>public.company</code>
     */
    public static final Company COMPANY = new Company();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CompanyRecord> getRecordType() {
        return CompanyRecord.class;
    }

    /**
     * The column <code>public.company.id</code>.
     */
    public final TableField<CompanyRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.company.name</code>.
     */
    public final TableField<CompanyRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.company.domain</code>.
     */
    public final TableField<CompanyRecord, String> DOMAIN = createField(DSL.name("domain"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.company.company_size_id</code>.
     */
    public final TableField<CompanyRecord, Short> COMPANY_SIZE_ID = createField(DSL.name("company_size_id"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.company.it_size_id</code>.
     */
    public final TableField<CompanyRecord, Short> IT_SIZE_ID = createField(DSL.name("it_size_id"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.company.logo_path</code>.
     */
    public final TableField<CompanyRecord, String> LOGO_PATH = createField(DSL.name("logo_path"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.company.created_at</code>.
     */
    public final TableField<CompanyRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.company.created_by</code>.
     */
    public final TableField<CompanyRecord, String> CREATED_BY = createField(DSL.name("created_by"), org.jooq.impl.SQLDataType.CHAR(36), this, "");

    /**
     * Create a <code>public.company</code> table reference
     */
    public Company() {
        this(DSL.name("company"), null);
    }

    /**
     * Create an aliased <code>public.company</code> table reference
     */
    public Company(String alias) {
        this(DSL.name(alias), COMPANY);
    }

    /**
     * Create an aliased <code>public.company</code> table reference
     */
    public Company(Name alias) {
        this(alias, COMPANY);
    }

    private Company(Name alias, Table<CompanyRecord> aliased) {
        this(alias, aliased, null);
    }

    private Company(Name alias, Table<CompanyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Company(Table<O> child, ForeignKey<O, CompanyRecord> key) {
        super(child, key, COMPANY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<CompanyRecord, Long> getIdentity() {
        return Keys.IDENTITY_COMPANY;
    }

    @Override
    public UniqueKey<CompanyRecord> getPrimaryKey() {
        return Keys.COMPANY_PK;
    }

    @Override
    public List<UniqueKey<CompanyRecord>> getKeys() {
        return Arrays.<UniqueKey<CompanyRecord>>asList(Keys.COMPANY_PK);
    }

    @Override
    public Company as(String alias) {
        return new Company(DSL.name(alias), this);
    }

    @Override
    public Company as(Name alias) {
        return new Company(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Company rename(String name) {
        return new Company(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Company rename(Name name) {
        return new Company(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, Short, Short, String, LocalDateTime, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
