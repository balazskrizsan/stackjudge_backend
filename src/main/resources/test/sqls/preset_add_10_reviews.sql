-- requires: preset_add_10_groups.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
VALUES (1654653, 1, 0, 2, 'long review test 1', '2021-01-16 01:50:01', 123),
       (6452316, 1, 0, 2, 'long review test 2', '2021-01-17 01:50:01', 123),
       (5564132, 4, 0, 2, 'long review test 3', '2021-01-18 01:50:01', 123),
       (6321654, 4, 0, 2, 'long review test 4', '2021-01-19 01:50:01', 123),
       (3456232, 5, 0, 2, 'long review test 5', '2021-01-20 01:50:01', 123),
       (4567865, 6, 0, 2, 'long review test 6', '2021-01-21 01:50:01', 123),
       (3564987, 7, 0, 2, 'long review test 7', '2021-01-22 01:50:01', 123),
       (9620320, 8, 0, 2, 'long review test 8', '2021-01-23 01:50:01', 123),
       (7565030, 9, 0, 2, 'long review test 9', '2021-01-24 01:50:01', 123),
       (3465132, 10, 0, 2, 'long review test106', '2021-01-25 01:50:01', 123);
