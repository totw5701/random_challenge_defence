insert into S3FILE (id, create_dtm, key, url) values (99999L, '15920101000000', 'key', 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp');

insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title, image_id) values (33L, 30, 'desc', 2, 'P', '사진 인증', 'tid3tle', 99999L);
insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title, image_id) values (34L, 30, 'desc', 2, 'P', '사진 인증', 'titleb', 99999L);
insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title, image_id) values (35L, 30, 'desc', 2, 'P', '사진 인증', 'titlse', 99999L);

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (1L, 33L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (2L, 33L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (3L, 33L, '중간 목표3');

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (4L, 34L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (5L, 34L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (6L, 34L, '중간 목표3');

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (7L, 35L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (8L, 35L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (9L, 35L, '중간 목표3');
