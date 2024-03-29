/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables.records;


import com.kbalazsworks.stackjudge.db.tables.CompanyOwner;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CompanyOwnerRecord extends UpdatableRecordImpl<CompanyOwnerRecord> implements Record3<Long, String, LocalDateTime> {

    private static final long serialVersionUID = 1222839362;

    /**
     * Setter for <code>public.company_owner.company_id</code>.
     */
    public void setCompanyId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.company_owner.company_id</code>.
     */
    public Long getCompanyId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.company_owner.user_ids_user_id</code>.
     */
    public void setUserIdsUserId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.company_owner.user_ids_user_id</code>.
     */
    public String getUserIdsUserId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.company_owner.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.company_owner.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, String, LocalDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return CompanyOwner.COMPANY_OWNER.COMPANY_ID;
    }

    @Override
    public Field<String> field2() {
        return CompanyOwner.COMPANY_OWNER.USER_IDS_USER_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return CompanyOwner.COMPANY_OWNER.CREATED_AT;
    }

    @Override
    public Long component1() {
        return getCompanyId();
    }

    @Override
    public String component2() {
        return getUserIdsUserId();
    }

    @Override
    public LocalDateTime component3() {
        return getCreatedAt();
    }

    @Override
    public Long value1() {
        return getCompanyId();
    }

    @Override
    public String value2() {
        return getUserIdsUserId();
    }

    @Override
    public LocalDateTime value3() {
        return getCreatedAt();
    }

    @Override
    public CompanyOwnerRecord value1(Long value) {
        setCompanyId(value);
        return this;
    }

    @Override
    public CompanyOwnerRecord value2(String value) {
        setUserIdsUserId(value);
        return this;
    }

    @Override
    public CompanyOwnerRecord value3(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public CompanyOwnerRecord values(Long value1, String value2, LocalDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CompanyOwnerRecord
     */
    public CompanyOwnerRecord() {
        super(CompanyOwner.COMPANY_OWNER);
    }

    /**
     * Create a detached, initialised CompanyOwnerRecord
     */
    public CompanyOwnerRecord(Long companyId, String userIdsUserId, LocalDateTime createdAt) {
        super(CompanyOwner.COMPANY_OWNER);

        set(0, companyId);
        set(1, userIdsUserId);
        set(2, createdAt);
    }
}
