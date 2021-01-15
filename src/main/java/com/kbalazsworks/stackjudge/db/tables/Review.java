/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables;


import com.kbalazsworks.stackjudge.db.Keys;
import com.kbalazsworks.stackjudge.db.Public;
import com.kbalazsworks.stackjudge.db.tables.records.ReviewRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class Review extends TableImpl<ReviewRecord> {

    private static final long serialVersionUID = -1836686390;

    /**
     * The reference instance of <code>public.review</code>
     */
    public static final Review REVIEW = new Review();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReviewRecord> getRecordType() {
        return ReviewRecord.class;
    }

    /**
     * The column <code>public.review.id</code>.
     */
    public final TableField<ReviewRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('review_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.review.group_id</code>.
     */
    public final TableField<ReviewRecord, Long> GROUP_ID = createField(DSL.name("group_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.review.visibility</code>.
     */
    public final TableField<ReviewRecord, Short> VISIBILITY = createField(DSL.name("visibility"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.review.rate</code>.
     */
    public final TableField<ReviewRecord, Short> RATE = createField(DSL.name("rate"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.review.review</code>.
     */
    public final TableField<ReviewRecord, String> REVIEW_ = createField(DSL.name("review"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.review.created_at</code>.
     */
    public final TableField<ReviewRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.review.created_by</code>.
     */
    public final TableField<ReviewRecord, Long> CREATED_BY = createField(DSL.name("created_by"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.review</code> table reference
     */
    public Review() {
        this(DSL.name("review"), null);
    }

    /**
     * Create an aliased <code>public.review</code> table reference
     */
    public Review(String alias) {
        this(DSL.name(alias), REVIEW);
    }

    /**
     * Create an aliased <code>public.review</code> table reference
     */
    public Review(Name alias) {
        this(alias, REVIEW);
    }

    private Review(Name alias, Table<ReviewRecord> aliased) {
        this(alias, aliased, null);
    }

    private Review(Name alias, Table<ReviewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Review(Table<O> child, ForeignKey<O, ReviewRecord> key) {
        super(child, key, REVIEW);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<ReviewRecord, Long> getIdentity() {
        return Keys.IDENTITY_REVIEW;
    }

    @Override
    public UniqueKey<ReviewRecord> getPrimaryKey() {
        return Keys.REVIEW_PK;
    }

    @Override
    public List<UniqueKey<ReviewRecord>> getKeys() {
        return Arrays.<UniqueKey<ReviewRecord>>asList(Keys.REVIEW_PK);
    }

    @Override
    public List<ForeignKey<ReviewRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ReviewRecord, ?>>asList(Keys.REVIEW__FK__REVIEW_ID__GROUP_ID__ON_DELETE_CASCADE);
    }

    public Group group() {
        return new Group(this, Keys.REVIEW__FK__REVIEW_ID__GROUP_ID__ON_DELETE_CASCADE);
    }

    @Override
    public Review as(String alias) {
        return new Review(DSL.name(alias), this);
    }

    @Override
    public Review as(Name alias) {
        return new Review(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Review rename(String name) {
        return new Review(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Review rename(Name name) {
        return new Review(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, Short, Short, String, LocalDateTime, Long> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
