
insert into _USER(`id`,`login_id`, `login_pw`, `name`, `team`, `role`,`age`) values(1,'asdf','{bcrypt}$2a$10$d3QK7jlUd8NI6EdHmILzfejSoF.XSlmT/eRdb9zaXuM8YuWmTdzSO','test','서울FC','PLAYER',25);
insert into Player(`position`,`user_id`) values('FW',1);


insert into Test(`test_id`, `user_id`, `date`) values(0, 'asdf' , '20.09.01');

insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(0, '경기력', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(1, '기능자신감', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(2, '부상', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(3, '슬럼프', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(4, '진로미래', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(5, '관계', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(6, '훈련', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(7, '학업', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(8, '주전경쟁', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(9, '부담감', 'admin',0);
insert into Diagnose(`diagnose_id`, `diagnoseTitle`, `userId`, `test_id`) values(10, '사생활규제', 'admin',0);



insert into Question(`question_id`, `questionContext`, `weight`,`keyword` ,`diagnose_id`) values(0, '경기 전 스트레스 원인 체크 해주세요', 0, '경기전-스트레스' ,1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` ,`diagnose_id`) values(1, '경기 전 불안감에 대해 체크해주세요', 1,'경기전-불안감' ,1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(2, '어웨이 경기장 환경에 대해 스트레스 체크해주세요', 4,'어웨이 경기장 환경', 1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(3, '경기 전 훈련 집중도에 대해 체크해주세요', 2, '경기전-집중도',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(4, '경기 전 자신감에 대한 스트레스 체크해주세요', 3, '경기전-자신감',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(5, '경기 전 경기 부담감에 대해 체크해주세요', 0, '경기전-부담감',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(6, '경기 중 스트레스 원인 체크 해주세요', 0, '경기중-스트레스',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(7, '경기 중 실수에 대한 두려움에 대해 체크해주세요', 1, '경기중-실수',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(8, '경기 중 부상에 대한 걱정에 대해 체크해주세요', 4,'경기중-부상', 1);
insert into Question(`question_id`, `questionContext`, `weight`, `keyword` ,`diagnose_id`) values(9, '경기 중 자신감 하락에 대해 체크해주세요', 2, '경기중-자신감',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(10, '경기 중 심판에 의한 스트레스에 대해 체크해주세요', 3, '경기중-심판',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(11, '경기 중 감독/코치 지시에 의한 스트레스에 대해 체크', 0,'경기중-감독/코치', 1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(12, '경기 중 실력발휘에 대한 스트레스에 대해 체크해주세요', 0, '경기중-실력발휘',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(13, '경기 후 스트레스 원인 체크 해주세요.', 1, '경기후-스트레스',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(14, '경기 패배에 대한 스트레스 체크해주세요.', 4, '경기 패배',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(15, '경기 패배 원인제공에 대한 스트레스 체크해주세요.', 2, '패배 원인제공',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(16, '다른 선수로 인한 경기 패배 스트레스 체크해주세요.', 3, '다른 선수로 인한 패배',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(17, '경기력 불만족에 대한 스트레스 체크해주세요.', 0, '경기력 불만족',1);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(18, '실력 부족에 대한 스트레스 체크해주세요. ', 0, '실력 부족',1);


insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(20, '기능 자신감 스트레스 원인 체크 해주세요', 1, '기능 자신감',2);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(21, '기술에 대한 스트레스 체크해주세요', 1, '기술',2);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(22, '체력에 대한 스트레스 체크해주세요', 1, '체력',2);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(23, '피지컬(키)에 대한 스트레스 체크해주세요', 2, '피지컬(키)',2);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(24, '피지컬(체형)에 대한 스트레스 체크해주세요', 2, '피지컬(체형)',2);


insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(25, '부상 스트레스 원인 체크해주세요.', 1,'부상-스트레스', 3);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(26, '부상에 대한 스트레스 체크해주세요.', 2, '부상',3);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(27, '부상으로 인한 경기불참에 대한 스트레스 체크해주세요.', 2, '경기 불참',3);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(28, '부상 후 재활치료에 대한 스트레스 체크해주세요.', 0, '부상 후 재활',3);


insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(29, '슬럼프 스트레스 원인 체크해주세요', 0, '슬럼프-스트레스',4);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(30, '슬럼프 극복 어려움에 대해 체크해주세요.', 0, '슬럼프 극복 어려움',4);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(31, '어려움을 이야기 할 사람에 대해 체크해주세요.', 0,'슬럼프-사람', 4);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(32, '진로 스트레스 원인 체크 해주세요.', 0, '진로',5);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(33, '진학에 대한 스트레스 체크해주세요.', 0, '진학',5);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(34, '미래에 대한 불안 스트레스 체크해주세요.', 0, '미래 불안',5);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(35, '선수로서 실패에 대한 불안 스트레스 체크해주세요.', 0, '선수로서 실패 불안', 5);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(36, '관계 스트레스 원인 체크 해주세요.', 0, '관계-스트레스',6);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(37, '부모님과의 관계 스트레스 체크해주세요', 0, '부모님',6);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(38, '동료와의 관계 스트레스 체크해주세요', 0, '동료',6);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(39, '선배와의 관계 스트레스 체크해주세요', 0, '선배',6);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(40, '지도자와의 관계 스트레스 체크해주세요', 0, '지도자',6);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(41, '이성과의 관계 스트레스 체크해주세요.', 0, '이성',6);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(42, '훈련 스트레스 원인 체크 해주세요.', 0, '훈련-스트레스',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(43, '흥미에 대한 스트레스 체크해주세요.', 0, '흥미',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(44, '규칙적인 훈련 대한 불안 스트레스 체크해주세요.', 0, '규칙적 훈련',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(45, '훈련 중 부상에 스트레스 체크해주세요.', 0, '훈련 중 부상',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(46, '훈련에서 오는 경쟁에 대한 스트레스 체크해주세요.', 0, '훈련 중 경쟁',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(47, '훈련에서 실수에 대한 스트레스 체크해주세요.', 0, '훈련 중 실수',7);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(48, '훈련량에 대한 스트레스 체크해주세요.', 0, '훈련량',7);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(49, '학업 스트레스 원인(중분류) 체크 해주세요.', 0, '학업-스트레스',8);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(50, '수업 흥미 스트레스 강도 체크', 0, '수업 흥미',8);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(51, '공부 방법 스트레스 강도 체크', 0, '공부 방법',8);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(52, '학업 성적 스트레스 강도 체크', 0, '학업 성적',8);


insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(53, '경쟁 스트레스 원인 체크해주세요', 0, '경쟁-스트레스',9);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(54, '실력 한계에 대한 스트레스 체크해주세요', 0, '실력 한계',9);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(55, '주전 경쟁에 대한 스트레스 체크해주세요', 0, '주전 경쟁',9);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(56, '가족 기대감에 대한 스트레스 체크해주세요.', 0, '가족 기대감',10);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(57, '지도자의 기대감에 대한 스트레스 체크해주세요.', 0,'지도자 기대감', 10);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(58, '주위 기대감(동료, 친척 등)에 대한 스트레스 체크', 0, '주위 기대감',10);

insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(59, '사생활 규제에 대한 스트레스 원인 체크해주세요.', 0, '사생활 규제-스트레스',11);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(60, '자유시간 규제에 대한 스트레스 체크해주세요', 0, '자유시간 규제',11);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(61, '단체생활 스트레스에 대해 체크해주세요', 0, '단체 생활',11);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(62, '사생활 구속에 대한 스트레스 체크해주세요', 0, '사생활 구속',11);
insert into Question(`question_id`, `questionContext`, `weight`,`keyword` , `diagnose_id`) values(63, '똑같은 패턴 생활에 대한 스트레스 체크해주세요.', 0, '반복된 생활 패턴',11);




