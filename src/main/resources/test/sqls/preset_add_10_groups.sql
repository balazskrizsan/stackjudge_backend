-- requires: preset_add_3_companies
INSERT INTO "group" (id, company_id, parent_id, address_id, type_id, name, members_on_group_id, created_at, created_by)
VALUES (101001, 100001, null,   102001, 2, 'QQQ', 1, '2020-01-01 01:01:01', 1),
       (101002, 100001, 101001, 102001, 3, 'WWW', 1, '2020-01-02 01:01:01', 1),
       (101003, 100001, 101001, 102001, 3, 'EEE', 1, '2020-01-03 01:01:01', 1),
       (101004, 100002, null,   102002, 1, 'RRR', 1, '2020-01-04 01:01:01', 1),
       (101005, 100002, 101004, 102002, 1, 'TTT', 1, '2020-01-05 01:01:01', 1),
       (101006, 100003, null,   102002, 3, 'YYY', 1, '2020-01-06 01:01:01', 1),
       (101007, 100003, 101006, 102002, 3, 'UUU', 1, '2020-01-07 01:01:01', 1),
       (101008, 100003, 101006, 102003, 3, 'AAA', 1, '2020-01-08 01:01:01', 1),
       (101009, 100003, 101008, 102003, 2, 'SSS', 1, '2020-01-09 01:01:01', 1),
       (101010, 100003, 101009, 102003, 2, 'DDD', 1, '2020-01-10 01:01:01', 1);
