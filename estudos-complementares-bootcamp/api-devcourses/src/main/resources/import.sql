--Users
INSERT INTO tb_user (name, email, birth_date, registration_date, password) VALUES ('Alex Brown', 'alex@gmail.com', TIMESTAMP WITH TIME ZONE '1990-12-12T03:00:00Z', NOW() , '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, birth_date, registration_date, password) VALUES ('Maria Green', 'maria@gmail.com', TIMESTAMP WITH TIME ZONE '1993-07-14T03:00:00Z', NOW(), '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, birth_date, registration_date, password) VALUES ('Bob Brown', 'bob@gmail.com', TIMESTAMP WITH TIME ZONE '1985-02-09T03:00:00Z', NOW(), '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
--Phones
INSERT INTO tb_phone (user_id, name, phone) VALUES (1, '11999123456', 'cellphone');
INSERT INTO tb_phone (user_id, name, phone) VALUES (1, '1177123456', 'homephone');
INSERT INTO tb_phone (user_id, name, phone) VALUES (2, '1177123456', 'homephone');
INSERT INTO tb_phone (user_id, name, phone) VALUES (3, '11999123456', 'cellphone');
--Roles
INSERT INTO tb_role (authority) VALUES ('ROLE_STUDENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_INSTRUCTOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
--User authorizations
INSERT INTO tb_user_role (user_id, role_id) VALUES(1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES(2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES(2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES(3, 3);
--Notifications
INSERT INTO tb_notification (text, moment, read, route, user_id) VALUES ('Task 1 feedback : please review', TIMESTAMP WITH TIME ZONE '2020-12-10T13:00:00Z', false, '/offers/1/resource/1/sections/1', 1);
INSERT INTO tb_notification (text, moment, read, route, user_id) VALUES ('Task 1 feedback : please review', TIMESTAMP WITH TIME ZONE '2020-12-12T13:00:00Z', true, '/offers/1/resource/1/sections/1', 1);
INSERT INTO tb_notification (text, moment, read, route, user_id) VALUES ('Task 1 feedback : task accepted', TIMESTAMP WITH TIME ZONE '2020-12-14T13:00:00Z', false, '/offers/1/resource/1/sections/1', 1);
--Courses
INSERT INTO tb_course (name, img_uri) VALUES ('Course Java', 'https://img.freepik.com/premium-vector/special-programming-language-computing-platform-ad_81534-2981.jpg?w=2000');
INSERT INTO tb_course (name, img_uri) VALUES ('Course HTML', 'https://cdn-icons-png.flaticon.com/512/919/919827.png?w=360');
INSERT INTO tb_course (name, img_uri) VALUES ('Course CSS', 'https://www.jonathanmoreira.com.br/imgs/CSS.jpg');
--Offers
INSERT INTO tb_offer (edition, creation_date, course_id) VALUES ('1.0', TIMESTAMP WITH TIME ZONE '2020-07-13T03:00:01Z', 1);
INSERT INTO tb_offer (edition, creation_date, course_id) VALUES ('2.0', TIMESTAMP WITH TIME ZONE '2020-12-13T03:00:01Z', 1);
INSERT INTO tb_offer (edition, creation_date, course_id) VALUES ('1.0', TIMESTAMP WITH TIME ZONE '2020-12-13T03:00:01Z', 2);
INSERT INTO tb_offer (edition, creation_date, course_id) VALUES ('1.0', TIMESTAMP WITH TIME ZONE '2020-12-13T03:00:01Z', 3);
--Enrollments
INSERT INTO tb_enrollment (user_id, offer_id, enroll_Moment, available) VALUES (1, 1, TIMESTAMP WITH TIME ZONE '2020-12-12T13:00:00Z',  true);
INSERT INTO tb_enrollment (user_id, offer_id, enroll_Moment, available) VALUES (2, 1, TIMESTAMP WITH TIME ZONE '2020-12-12T13:00:00Z',  true);
--Resources
INSERT INTO tb_resource (title, description, position, img_uri, type, external_link, offer_id) VALUES ('Java', 'Learn all about java', 1, 'https://c8.alamy.com/comp/G2ABE1/learn-learning-java-programming-with-laptop-and-code-on-the-computer-G2ABE1.jpg', 1, null, 1);
INSERT INTO tb_resource (title, description, position, img_uri, type, external_link, offer_id) VALUES ('Support Material', 'Study support material', 2, 'https://thumbs.dreamstime.com/b/online-electronically-support-learning-materials-books-digital-educational-course-training-tutorials-246213772.jpg', 2, null, 1);
INSERT INTO tb_resource (title, description, position, img_uri, type, external_link, offer_id) VALUES ('Extra Content', 'Extra content to help', 3, 'https://static.vecteezy.com/system/resources/previews/007/692/127/non_2x/people-concept-illustration-of-customer-support-online-assistance-consulting-crm-for-graphic-and-web-design-business-presentation-and-marketing-material-vector.jpg', 0, 'https://www.w3schools.com/java/default.asp', 1);
--Sections
INSERT INTO tb_section (title, description, position, img_uri, resource_id) VALUES ('Chapter 1', 'Introduction', 1, 'https://cdn-icons-png.flaticon.com/512/2535/2535523.png', 1);
INSERT INTO tb_section (title, description, position, img_uri, resource_id) VALUES ('Chapter 2', 'Creating your first java program', 2, 'https://cdn-icons-png.flaticon.com/512/2535/2535523.png', 1);
INSERT INTO tb_section (title, description, position, img_uri, resource_id) VALUES ('Chapter 3', 'We will create a cool calculator', 3, 'https://cdn-icons-png.flaticon.com/512/2535/2535523.png', 1);
--Lessons and Contents
INSERT INTO tb_lesson (title, position, section_id) VALUES ('Lesson 1-What is java', 1, 1);
INSERT INTO tb_content (id, text_Content, video_Uri) VALUES (1, 'Support material: link', 'https://youtu.be/sZAxLRMxEUo');

INSERT INTO tb_lesson (title, position, section_id) VALUES ('Lesson 2-What is JVM?', 2, 1);
INSERT INTO tb_content (id, text_Content, video_Uri) VALUES (2, 'Support material: link', 'https://youtu.be/sZAxLRMxEUo');

INSERT INTO tb_lesson (title, position, section_id) VALUES ('Lesson 3-What is bytecode', 3, 1);
INSERT INTO tb_content (id, text_Content, video_Uri) VALUES (3, 'Support material: link', 'https://youtu.be/sZAxLRMxEUo');
--Lessons and Tasks
INSERT INTO tb_lesson (title, position, section_id) VALUES ('Task 1-How does java work?', 4, 1);
INSERT INTO tb_task (id, description, question_Count, approval_Count) VALUES (4, 'Test about java', 5, 4);
--Lessons Done
INSERT INTO tb_lessons_done (user_id, offer_id, lesson_id) VALUES (1, 1, 1);
INSERT INTO tb_lessons_done (user_id, offer_id, lesson_id) VALUES (1, 1, 2);
--Deliveries
INSERT INTO tb_deliver (uri, moment, status, feedback, correct_Count, lesson_id, user_id, offer_id) VALUES ('https://github.com/jose-meurer/api-devcourses', TIMESTAMP WITH TIME ZONE '2020-12-10T10:00:00Z', 0, null, null, 4, 1, 1);
--UserReviews
INSERT INTO tb_user_review (rating, content, created, author_id, offer_id) VALUES (5, 'Good job!', NOW(), 1, 1);
INSERT INTO tb_user_review (rating, content, created, author_id, offer_id) VALUES (5, 'Very nice!', NOW(), 2, 1);








