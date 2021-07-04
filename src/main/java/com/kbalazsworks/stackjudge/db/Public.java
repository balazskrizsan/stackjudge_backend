/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db;


import com.kbalazsworks.stackjudge.db.tables.Address;
import com.kbalazsworks.stackjudge.db.tables.Company;
import com.kbalazsworks.stackjudge.db.tables.FlywaySchemaHistory;
import com.kbalazsworks.stackjudge.db.tables.Group;
import com.kbalazsworks.stackjudge.db.tables.Notification;
import com.kbalazsworks.stackjudge.db.tables.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.db.tables.Review;
import com.kbalazsworks.stackjudge.db.tables.Users;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -343284811;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.address</code>.
     */
    public final Address ADDRESS = Address.ADDRESS;

    /**
     * The table <code>public.company</code>.
     */
    public final Company COMPANY = Company.COMPANY;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.group</code>.
     */
    public final Group GROUP = Group.GROUP;

    /**
     * The table <code>public.notification</code>.
     */
    public final Notification NOTIFICATION = Notification.NOTIFICATION;

    /**
     * The table <code>public.protected_review_log</code>.
     */
    public final ProtectedReviewLog PROTECTED_REVIEW_LOG = ProtectedReviewLog.PROTECTED_REVIEW_LOG;

    /**
     * The table <code>public.review</code>.
     */
    public final Review REVIEW = Review.REVIEW;

    /**
     * The table <code>public.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Address.ADDRESS,
            Company.COMPANY,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Group.GROUP,
            Notification.NOTIFICATION,
            ProtectedReviewLog.PROTECTED_REVIEW_LOG,
            Review.REVIEW,
            Users.USERS);
    }
}
