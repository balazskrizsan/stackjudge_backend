/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables;


import com.kbalazsworks.stackjudge.db.Keys;
import com.kbalazsworks.stackjudge.db.Public;
import com.kbalazsworks.stackjudge.db.tables.records.PersistenceLogRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
public class PersistenceLog extends TableImpl<PersistenceLogRecord> {

    private static final long serialVersionUID = -1159961504;

    /**
     * The reference instance of <code>public.persistence_log</code>
     */
    public static final PersistenceLog PERSISTENCE_LOG = new PersistenceLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PersistenceLogRecord> getRecordType() {
        return PersistenceLogRecord.class;
    }

    /**
     * The column <code>public.persistence_log.id</code>.
     */
    public final TableField<PersistenceLogRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.persistence_log.type</code>.
     */
    public final TableField<PersistenceLogRecord, Short> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.persistence_log.data</code>.
     */
    public final TableField<PersistenceLogRecord, JSONB> DATA = createField(DSL.name("data"), org.jooq.impl.SQLDataType.JSONB.nullable(false), this, "");

    /**
     * The column <code>public.persistence_log.created_at</code>.
     */
    public final TableField<PersistenceLogRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * Create a <code>public.persistence_log</code> table reference
     */
    public PersistenceLog() {
        this(DSL.name("persistence_log"), null);
    }

    /**
     * Create an aliased <code>public.persistence_log</code> table reference
     */
    public PersistenceLog(String alias) {
        this(DSL.name(alias), PERSISTENCE_LOG);
    }

    /**
     * Create an aliased <code>public.persistence_log</code> table reference
     */
    public PersistenceLog(Name alias) {
        this(alias, PERSISTENCE_LOG);
    }

    private PersistenceLog(Name alias, Table<PersistenceLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private PersistenceLog(Name alias, Table<PersistenceLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> PersistenceLog(Table<O> child, ForeignKey<O, PersistenceLogRecord> key) {
        super(child, key, PERSISTENCE_LOG);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PersistenceLogRecord, Long> getIdentity() {
        return Keys.IDENTITY_PERSISTENCE_LOG;
    }

    @Override
    public UniqueKey<PersistenceLogRecord> getPrimaryKey() {
        return Keys.PERSISTENCE_LOG___PK;
    }

    @Override
    public List<UniqueKey<PersistenceLogRecord>> getKeys() {
        return Arrays.<UniqueKey<PersistenceLogRecord>>asList(Keys.PERSISTENCE_LOG___PK);
    }

    @Override
    public PersistenceLog as(String alias) {
        return new PersistenceLog(DSL.name(alias), this);
    }

    @Override
    public PersistenceLog as(Name alias) {
        return new PersistenceLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PersistenceLog rename(String name) {
        return new PersistenceLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PersistenceLog rename(Name name) {
        return new PersistenceLog(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Short, JSONB, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
