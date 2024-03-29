/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables;


import com.kbalazsworks.stackjudge.db.Keys;
import com.kbalazsworks.stackjudge.db.Public;
import com.kbalazsworks.stackjudge.db.tables.records.GroupRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
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
public class Group extends TableImpl<GroupRecord> {

    private static final long serialVersionUID = 2144330420;

    /**
     * The reference instance of <code>public.group</code>
     */
    public static final Group GROUP = new Group();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GroupRecord> getRecordType() {
        return GroupRecord.class;
    }

    /**
     * The column <code>public.group.id</code>.
     */
    public final TableField<GroupRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.group.parent_id</code>.
     */
    public final TableField<GroupRecord, Long> PARENT_ID = createField(DSL.name("parent_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.group.company_id</code>.
     */
    public final TableField<GroupRecord, Long> COMPANY_ID = createField(DSL.name("company_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.group.address_id</code>.
     */
    public final TableField<GroupRecord, Long> ADDRESS_ID = createField(DSL.name("address_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.group.name</code>.
     */
    public final TableField<GroupRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.group.type_id</code>.
     */
    public final TableField<GroupRecord, Short> TYPE_ID = createField(DSL.name("type_id"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.group.members_on_group_id</code>.
     */
    public final TableField<GroupRecord, Short> MEMBERS_ON_GROUP_ID = createField(DSL.name("members_on_group_id"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.group.created_at</code>.
     */
    public final TableField<GroupRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.group.created_by</code>.
     */
    public final TableField<GroupRecord, String> CREATED_BY = createField(DSL.name("created_by"), org.jooq.impl.SQLDataType.CHAR(36), this, "");

    /**
     * Create a <code>public.group</code> table reference
     */
    public Group() {
        this(DSL.name("group"), null);
    }

    /**
     * Create an aliased <code>public.group</code> table reference
     */
    public Group(String alias) {
        this(DSL.name(alias), GROUP);
    }

    /**
     * Create an aliased <code>public.group</code> table reference
     */
    public Group(Name alias) {
        this(alias, GROUP);
    }

    private Group(Name alias, Table<GroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private Group(Name alias, Table<GroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Group(Table<O> child, ForeignKey<O, GroupRecord> key) {
        super(child, key, GROUP);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<GroupRecord, Long> getIdentity() {
        return Keys.IDENTITY_GROUP;
    }

    @Override
    public UniqueKey<GroupRecord> getPrimaryKey() {
        return Keys.GROUP_PK;
    }

    @Override
    public List<UniqueKey<GroupRecord>> getKeys() {
        return Arrays.<UniqueKey<GroupRecord>>asList(Keys.GROUP_PK);
    }

    @Override
    public List<ForeignKey<GroupRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GroupRecord, ?>>asList(Keys.GROUP__FK__GROUP_PARENT_ID__GROUP_ID__ON_DELETE_CASCADE, Keys.GROUP__FK__GROUP_COMPANY_ID__COMPANY_ID__ON_DELETE_CASCADE, Keys.GROUP__FK__GROUP_ADDRESS_ID__ADDRESS_ID__ON_DELETE_CASCADE);
    }

    public Group group() {
        return new Group(this, Keys.GROUP__FK__GROUP_PARENT_ID__GROUP_ID__ON_DELETE_CASCADE);
    }

    public Company company() {
        return new Company(this, Keys.GROUP__FK__GROUP_COMPANY_ID__COMPANY_ID__ON_DELETE_CASCADE);
    }

    public Address address() {
        return new Address(this, Keys.GROUP__FK__GROUP_ADDRESS_ID__ADDRESS_ID__ON_DELETE_CASCADE);
    }

    @Override
    public Group as(String alias) {
        return new Group(DSL.name(alias), this);
    }

    @Override
    public Group as(Name alias) {
        return new Group(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Group rename(String name) {
        return new Group(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Group rename(Name name) {
        return new Group(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, Long, Long, Long, String, Short, Short, LocalDateTime, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
