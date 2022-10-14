insert into Test(`test_id`, `user_id`, `date`) values(0, 'asdf' , '20.09.01');

insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(0, 'playSkill', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(1, 'Stress', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(2, 'third', 'admin',0);


insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(0, 'firstP', 0, 1);
insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(1, 'secondP', 1, 1);
insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(2, 'thirdP', 4, 2);
insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(3, 'firstS', 2, 2);
insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(4, 'secondS', 3, 2);
insert into Question(`question_id`, `questionContext`, `weight`, `diagnose_id`) values(5, 'firstT', 0, 1);


