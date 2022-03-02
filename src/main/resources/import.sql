insert into `user` (name) values ('test_user');
insert into `product` (product_id, user_no) values ('testId1', 1), ('testId2', 1)
insert into `detail` (prod_title, prod_desc, product_id) values ('test title1', 'test desc1', 'testId1'), ('test title2', 'test desc2', 'testId2');
insert into `tag` (tag_value, product_id) values ('tag1', 'testId1'), ('tag2', 'testId1'), ('tag2', 'testId1'),('tag22', 'testId2'), ('tag23', 'testId2');
insert into `category`(cat_value) values ('TEST1'), ('TEST2'), ('TEST3'), ('TEST4');
insert into `mapper_prod_cat` (product_no, category_no) values (1, 1),(1, 2),(2, 3),(1, 4);