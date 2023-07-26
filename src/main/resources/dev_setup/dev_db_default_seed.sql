TRUNCATE TABLE
    "users",
    "address",
    "group",
    "review",
    "protected_review_log",
    "notification",
    "persistence_log",
    "company_own_request",
    "company_owner",
    "company" RESTART IDENTITY;

INSERT INTO company(id, name, domain, company_size_id, it_size_id, logo_path, created_at, created_by)
VALUES (default, 'Google', 'http://gmail.com', 1, 5, 'company-logos/1.png', '2021-01-01 00:00:00', '00000000-0000-0000-0000-000000000001'),
       (default, 'Samsung Electronics', 'http://samsung.com', 3, 4, 'company-logos/2.png', '2021-01-02 00:00:00', '00000000-0000-0000-0000-000000000002'),
       (default, 'Samsung Electronics with a very very very very very long name', 'http://samsung.com', 4, 3, 'company-logos/3.png', '2021-01-03 00:00:00', '00000000-0000-0000-0000-000000000003'),
       (default, 'Foxconn', 'http://foxconn.com', 5, 2, 'company-logos/4.png', '2021-01-04 00:00:00', '00000000-0000-0000-0000-000000000004'),
       (default, 'Apple', 'http://apple.com', 4, 1, 'company-logos/5.png', '2021-01-05 00:00:00', '00000000-0000-0000-0000-000000000005'),
       (default, 'Microsoft', 'http://microsoft.com', 3, 2, 'company-logos/6.png', '2021-01-06 00:00:00', '00000000-0000-0000-0000-000000000006'),
       (default, 'Huawei', 'http://huawei', 2, 3, 'company-logos/7.png', '2021-01-07 00:00:00', '00000000-0000-0000-0000-000000000007'),
       (default, 'Dell Technologies', 'http://dell.com', 1, 4, 'company-logos/8.png', '2021-01-08 00:00:00', '00000000-0000-0000-0000-000000000008'),
       (default, 'Hitachi', 'http://hitachi.com', 2, 5, 'company-logos/9.png', '2021-01-09 00:00:00', '00000000-0000-0000-0000-000000000009'),
       (default, 'IBM', 'http://ibm.com', 3, 4, 'company-logos/10.png', '2021-01-10 00:00:00', '00000000-0000-0000-0000-000000000010'),
       (default, 'Sony', 'http://sony.com', 4, 3, 'company-logos/11.png', '2021-01-11 00:00:00', '00000000-0000-0000-0000-000000000011'),
       (default, 'Intel', 'http://intel.com', 5, 2, 'company-logos/12.png', '2021-01-12 00:00:00', '00000000-0000-0000-0000-000000000012'),
       (default, 'Facebook', 'http://facebook.com', 4, 1, 'company-logos/13.png', '2021-01-13 00:00:00', '00000000-0000-0000-0000-000000000013'),
       (default, 'Panasonic', 'http://panasonic.com', 3, 2, 'company-logos/14.png', '2021-01-14 00:00:00', '00000000-0000-0000-0000-000000000014'),
       (default, 'HP Inc.', 'http://hp.com', 2, 3, 'company-logos/15.png', '2021-01-15 00:00:00', '00000000-0000-0000-0000-000000000015'),
       (default, 'Tencent', 'http://tencent.com', 1, 4, 'company-logos/16.png', '2021-01-16 00:00:00', '00000000-0000-0000-0000-000000000016'),
       (default, 'LG Electronics', 'http://lg.com', 2, 5, 'company-logos/17.png', '2021-01-17 00:00:00', '00000000-0000-0000-0000-000000000017'),
       (default, 'Cisco', 'http://ciscno.com', 3, 4, 'company-logos/18.png', '2021-01-18 00:00:00', '00000000-0000-0000-0000-000000000018'),
       (default, 'Lenovo', 'http://lenovo.com', 4, 3, 'company-logos/19.png', '2021-01-19 00:00:00', '00000000-0000-0000-0000-000000000019'),
       (default, 'JD.com', 'http://jd.com', 5, 2, 'company-logos/20.png', '2021-01-20 00:00:00', '00000000-0000-0000-0000-000000000020')

INSERT INTO address(id, company_id, full_address, marker_lat, marker_lng, manual_marker_lat, manual_marker_lng,
                    created_at, created_by)
-- company: 1
VALUES (default, 1, '1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA', 37.4220452, -122.0846619, NULL, NULL, '2020-11-01 11:22:33', '00000000-0000-0000-0000-000000000001'),
       (default, 1, '6 Pancras Square, London N1C 4AG, UK', 51.5332642,-0.1281919, NULL, NULL, '2020-11-02 11:22:33', '00000000-0000-0000-0000-000000000002'),
       (default, 1, 'Tucholskystraße 2, 10117 Berlin, Germany', 52.5232521, 13.3901892, NULL, NULL, '2020-11-03 11:22:33', '00000000-0000-0000-0000-000000000003'),
-- company: 2
       (default, 2, '129 Samseong-ro, Taejang-dong, Yeongtong-gu, Suwon-si, Gyeonggi-do, South Korea', 37.2570082, 127.0501493, NULL, NULL, '2020-11-01 11:22:33', '00000000-0000-0000-0000-000000000001'),
-- company: 3
       (default, 3, '6 Pancras Square, London N1C 4AG, UK', 51.5332642,-0.1281919, NULL, NULL, '2020-11-02 11:22:33', '00000000-0000-0000-0000-000000000002'),
       (default, 3, 'Tucholskystraße 2, 10117 Berlin, Germany', 52.5232521, 13.3901892, NULL, NULL, '2020-11-03 11:22:33', '00000000-0000-0000-0000-000000000003');

INSERT INTO "group" (id, company_id, address_id, parent_id, type_id, name, members_on_group_id, created_at, created_by)
-- company: 1
VALUES (default, 1, 1, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 1, 1, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 1, 2, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 2, 3, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 2, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 2, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 3, 6, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 3, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 4, 8, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 4, 9, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', '00000000-0000-0000-0000-000000000001');

-- requires: preset_add_10_groups.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
-- group_id: 1
VALUES (default, 1, 1, 1, 'Review 1, Group 1, Review text test, short 111', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000001'),
       (default, 1, 2, 2, 'Review 2, Group 1, Review text test, short 222', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000002'),
-- group_id: 2
       (default, 2, 3, 3, 'Review 3, Group 2, Review text test, short 333', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000001'),
       (default, 2, 1, 4, 'Review 4, Group 2, Review text test, short 444', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000002'),
-- group_id: 3
       (default, 3, 2, 5, 'Review 5, Group 3, Review text test, long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long 555', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000001'),
       (default, 3, 3, 6, 'Review 6, Group 3, Review text test, short 666', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000002'),
-- group_id: 4
       (default, 4, 1, 7, 'Review 7, Group 4, Review text test, short 777', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000001'),
       (default, 4, 2, 8, 'Review 8, Group 4, Review text test, 888 \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line 888', '2021-01-16 01:50:01', '00000000-0000-0000-0000-000000000002');

INSERT INTO stackjudge.public."users" (id)
VALUES ('00000000-0000-0000-0000-000000000001'),
       ('00000000-0000-0000-0000-000000000002'),
       ('00000000-0000-0000-0000-000000000002');

INSERT INTO company_owner (company_id, user_id, created_at)
VALUES (1, 1, '2020-11-03 11:22:33'),
       (1, 2, '2020-11-03 11:22:33'),
       (2, 1, '2020-11-03 11:22:33'),
       (3, 2, '2020-11-03 11:22:33'),
       (4, 1, '2020-11-03 11:22:33'),
       (5, 1, '2020-11-03 11:22:33'),
       (5, 2, '2020-11-03 11:22:33');
