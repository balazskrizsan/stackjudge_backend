/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables;


import com.kbalazsworks.stackjudge.db.Keys;
import com.kbalazsworks.stackjudge.db.Public;
import com.kbalazsworks.stackjudge.db.tables.records.NotificationRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
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
public class Notification extends TableImpl<NotificationRecord> {

    private static final long serialVersionUID = 154441633;

    /**
     * The reference instance of <code>public.notification</code>
     */
    public static final Notification NOTIFICATION = new Notification();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NotificationRecord> getRecordType() {
        return NotificationRecord.class;
    }

    /**
     * The column <code>public.notification.id</code>.
     */
    public final TableField<NotificationRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.notification.user_id</code>.
     */
    public final TableField<NotificationRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.notification.type</code>.
     */
    public final TableField<NotificationRecord, Short> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.notification.data</code>.
     */
    public final TableField<NotificationRecord, JSONB> DATA = createField(DSL.name("data"), org.jooq.impl.SQLDataType.JSONB.nullable(false), this, "");

    /**
     * The column <code>public.notification.created_at</code>.
     */
    public final TableField<NotificationRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.notification.viewed_at</code>.
     */
    public final TableField<NotificationRecord, LocalDateTime> VIEWED_AT = createField(DSL.name("viewed_at"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * Create a <code>public.notification</code> table reference
     */
    public Notification() {
        this(DSL.name("notification"), null);
    }

    /**
     * Create an aliased <code>public.notification</code> table reference
     */
    public Notification(String alias) {
        this(DSL.name(alias), NOTIFICATION);
    }

    /**
     * Create an aliased <code>public.notification</code> table reference
     */
    public Notification(Name alias) {
        this(alias, NOTIFICATION);
    }

    private Notification(Name alias, Table<NotificationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Notification(Name alias, Table<NotificationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Notification(Table<O> child, ForeignKey<O, NotificationRecord> key) {
        super(child, key, NOTIFICATION);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<NotificationRecord, Long> getIdentity() {
        return Keys.IDENTITY_NOTIFICATION;
    }

    @Override
    public UniqueKey<NotificationRecord> getPrimaryKey() {
        return Keys.NOTIFICATION_PK;
    }

    @Override
    public List<UniqueKey<NotificationRecord>> getKeys() {
        return Arrays.<UniqueKey<NotificationRecord>>asList(Keys.NOTIFICATION_PK);
    }

    @Override
    public List<ForeignKey<NotificationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<NotificationRecord, ?>>asList(Keys.NOTIFICATION__FK__NOTIFICATION_ID__USERS_ID__ON_DELETE_CASCADE);
    }

    public Users users() {
        return new Users(this, Keys.NOTIFICATION__FK__NOTIFICATION_ID__USERS_ID__ON_DELETE_CASCADE);
    }

    @Override
    public Notification as(String alias) {
        return new Notification(DSL.name(alias), this);
    }

    @Override
    public Notification as(Name alias) {
        return new Notification(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Notification rename(String name) {
        return new Notification(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Notification rename(Name name) {
        return new Notification(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, Short, JSONB, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}