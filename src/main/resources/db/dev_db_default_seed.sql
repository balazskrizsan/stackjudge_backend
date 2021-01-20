TRUNCATE TABLE
    "address",
    "group",
    "review",
    "company" RESTART IDENTITY;

INSERT INTO company(id, name, company_size_id, it_size_id, logo_path, created_at, created_by)
VALUES (1, 'Test company 1', 1, 5, 'folder/1.jpg', '2021-01-01 00:00:00', 1),
       (2, 'Test company with long name 2', 3, 4, 'folder/2.jpg', '2021-01-02 00:00:00', 2),
       (3, 'Test company 3', 4, 3, 'folder/3.jpg', '2021-01-03 00:00:00', 3),
       (4, 'Test company 4', 5, 2, 'folder/4.jpg', '2021-01-04 00:00:00', 4),
       (5, 'Test company 5', 4, 1, 'folder/5.jpg', '2021-01-05 00:00:00', 5),
       (6, 'Test company 6', 3, 2, 'folder/6.jpg', '2021-01-06 00:00:00', 6),
       (7, 'Test company 7', 2, 3, 'folder/7.jpg', '2021-01-07 00:00:00', 7),
       (8, 'Test company 8', 1, 4, 'folder/8.jpg', '2021-01-08 00:00:00', 8),
       (9, 'Test company 9', 2, 5, 'folder/9.jpg', '2021-01-09 00:00:00', 9),
       (10, 'Test company 10', 3, 4, 'folder/10.jpg', '2021-01-10 00:00:00', 1),
       (11, 'Test company 11', 4, 3, 'folder/11.jpg', '2021-01-11 00:00:00', 11),
       (12, 'Test company 12', 5, 2, 'folder/12.jpg', '2021-01-12 00:00:00', 12),
       (13, 'Test company 13', 4, 1, 'folder/13.jpg', '2021-01-13 00:00:00', 13),
       (14, 'Test company 14', 3, 2, 'folder/14.jpg', '2021-01-14 00:00:00', 14),
       (15, 'Test company 15', 2, 3, 'folder/15.jpg', '2021-01-15 00:00:00', 15),
       (16, 'Test company 16', 1, 4, 'folder/16.jpg', '2021-01-16 00:00:00', 16),
       (17, 'Test company 17', 2, 5, 'folder/17.jpg', '2021-01-17 00:00:00', 17),
       (18, 'Test company 18', 3, 4, 'folder/18.jpg', '2021-01-18 00:00:00', 18),
       (19, 'Test company 19', 4, 3, 'folder/19.jpg', '2021-01-19 00:00:00', 19),
       (20, 'Test company 20', 5, 2, 'folder/20.jpg', '2021-01-20 00:00:00', 20);

INSERT INTO address(id, company_id, full_address, marker_lat, marker_lng, manual_marker_lat, manual_marker_lng,
                    created_at, created_by)
-- company: 1
VALUES (1, 1, 'Deák Ferenc tér 1, Budapest, 1052', 47.5167111, 19.0521111, 47.5167111, 19.0528111, '2020-11-01 11:22:33', 1),
       (2, 1, 'Deák Ferenc tér 2, Budapest, 1052', 47.5167222, 19.0528222, 47.5167112, 19.0528112, '2020-11-02 11:22:33', 2),
       (3, 1, 'Deák Ferenc tér 3, Budapest, 1052', 47.5167333, 19.0528333, 47.5167113, 19.0528113, '2020-11-03 11:22:33', 3),
       (4, 1, 'Deák Ferenc tér 4, Budapest, 1052', 47.5167444, 19.0528444, NULL, NULL, '2020-11-04 11:22:33', 4),
-- company: 2
       (5, 2, 'Deák Ferenc tér 5, Budapest, 1052', 47.5167555, 19.0528555, 47.5167115, 19.0528115, '2020-11-05 11:22:33', 5),
       (6, 2, 'Deák Ferenc tér 6, Budapest, 1052', 47.5167666, 19.0528666, 47.5167116, 19.0528116, '2020-11-06 11:22:33', 6),
       (7, 2, 'Deák Ferenc tér 7, Budapest, 1052', 47.5167777, 19.0528777, NULL, NULL, '2020-11-07 11:22:33', 7),
-- company: 3
       (8, 3, 'Deák Ferenc tér 8, Budapest, 1052', 47.5167888, 19.0528888, NULL, NULL, '2020-11-08 11:22:33', 8),
       (9, 3, 'Deák Ferenc tér 9, Budapest, 1052', 47.5167989, 19.0528999, NULL, NULL, '2020-11-09 11:22:33', 9),
       (10, 4, 'Deák Ferenc tér 10, Budapest, 1052', 47.5167999, 19.0528989, NULL, NULL, '2020-11-10 11:22:33', 10);

INSERT INTO "group" (id, company_id, parent_id, type_id, name, members_on_group_id, created_at, created_by)
-- company: 1
VALUES (1, 1, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', 1),
       (2, 1, 1, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (3, 1, 2, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (4, 1, 3, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', 1),
       (5, 1, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', 1),
       (6, 1, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (7, 1, 6, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (8, 1, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (9, 1, 8, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (10, 1, 9, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', 1);

-- requires: preset_add_10_groups.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
-- group_id: 1
VALUES (1, 1, 1, 1, 'Review 1, Group 1, Review text test, short 111', '2021-01-16 01:50:01', 1),
       (2, 1, 1, 2, 'Review 2, Group 1, Review text test, short 222', '2021-01-16 01:50:01', 2),
-- group_id: 2
       (3, 2, 1, 3, 'Review 3, Group 2, Review text test, short 333', '2021-01-16 01:50:01', 3),
       (4, 2, 1, 4, 'Review 4, Group 2, Review text test, short 444', '2021-01-16 01:50:01', 4),
-- group_id: 3
       (5, 3, 1, 5, 'Review 5, Group 3, Review text test, long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long 555', '2021-01-16 01:50:01', 5),
       (6, 3, 1, 6, 'Review 6, Group 3, Review text test, short 666', '2021-01-16 01:50:01', 6),
-- group_id: 4
       (7, 4, 1, 7, 'Review 7, Group 4, Review text test, short 777', '2021-01-16 01:50:01', 7),
       (8, 4, 1, 8, 'Review 8, Group 4, Review text test, 888 \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line 888', '2021-01-16 01:50:01', 8);
