/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db;


import org.jooq.Sequence;
import org.jooq.impl.Internal;


/**
 * Convenience access to all sequences in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.address_id_seq</code>
     */
    public static final Sequence<Long> ADDRESS_ID_SEQ = Internal.createSequence("address_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.company_id_seq</code>
     */
    public static final Sequence<Long> COMPANY_ID_SEQ = Internal.createSequence("company_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.group_id_seq</code>
     */
    public static final Sequence<Long> GROUP_ID_SEQ = Internal.createSequence("group_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.review_id_seq</code>
     */
    public static final Sequence<Long> REVIEW_ID_SEQ = Internal.createSequence("review_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
