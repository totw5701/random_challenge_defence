insert into S3FILE (id, create_dtm, key, url) values (1, '15920101000000', 'key', 'http://temp.com');

insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title) values (33L, 30, 'desc', 2, 'P', '사진 인증', 'tid3tle');
insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title) values (34L, 30, 'desc', 2, 'P', '사진 인증', 'titleb');
insert into challenge_card (id, assign_score, description, difficulty, evidence_type, final_goal, title, image_id) values (35L, 30, 'desc', 2, 'P', '사진 인증', 'titlse', 1);

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (1L, 35L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (2L, 35L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (3L, 35L, '중간 목표3');

