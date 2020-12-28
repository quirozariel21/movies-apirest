## Movie Rental ##

#### Movie rental project explaining the backend authentication using JWT (Json Web Token) authentication using Spring Security &amp; MySQL JPA.
There's support for the following requirements:

* Authentication login with JWT token
* Create a user
* CRUD movies with audit table
* Filter, pagination and sorting movies

---

## Swagger Docs ##
The project has been configured with a basic Swagger docket that exposes the commonly used API's along with the expected params.

![Selection_583](/uploads/49d524900aa8424c0d86f252af29b946/Selection_583.png)

---

## Postman ##
The postman collection is in the following route 

+ open `src/main/resources/MovieRental.postman_collection.json` file.

---

## Steps to Setup the Spring Boot Back end app

1. **Clone the application**

	```bash
	git clone https://gitlab.com/applaudostudios/java-challenges/ariel-quiroz-14-dec-2020.git
	cd ariel-quiroz-14-dec-2020
	```

2. **Create a MySQL database**

	```bash
	create database movies
	```

3. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.properties` file.

	+ change `spring.datasource.username`, `spring.datasource.url` and `spring.datasource.password` properties as per your mysql installation
	

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	The server will start on port 8080. Token default expiration is 600000ms i.e 10 minutes.
	```
5. **Run data.sql file**
	
	The spring boot app uses role based authorization powered by spring security. Please execute the following sql queries in the database to insert the `USER` and `ADMIN` roles.

    + open `src/main/resources/data.sql` file and execute in your database or copy the following sentences to execute.

	```sql
    -- Truncate tables
    TRUNCATE TABLE user_roles;
    TRUNCATE TABLE movie_images;
    TRUNCATE TABLE popularity;
    TRUNCATE TABLE purchase_rental;
    TRUNCATE TABLE movies cascade;
    TRUNCATE TABLE users cascade;
    
    -- CREATE USERS AND ROLES
    -- user: ariel, password: ariel12345, role: USER
    INSERT INTO users(id, username, password, full_name, email, created_at, last_password_change_at)
    VALUES(1, 'ariel', '$2a$10$myt98iwCM/FCEK09OGliDeLgi7wP8JDKUIlS7wdEwq3GcyQYz.a1K', 'Ariel Quiroz', 'quirozariel21@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    
    INSERT INTO user_roles (user_id, roles) VALUES(1, 'USER');
    
    -- user: admin, password: admin12345, role: ADMIN
    INSERT INTO users(id, username, password, full_name, email, created_at, last_password_change_at)
    VALUES(2, 'admin', '$2a$10$8mPEYBUH4KcB7J/SSz3H3O7XW/5k1eoI8t.t0/tkXY4e7mNBH0BMu', 'Admin', 'admin@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    
    INSERT INTO user_roles (user_id, roles) VALUES(2, 'ADMIN');
    
    -- user: alina, password: alina12345, roles: USER, ADMIN
    INSERT INTO users(id, username, password, full_name, email, created_at, last_password_change_at)
    VALUES(3, 'alina', '$2a$10$hlXrWNYncOAfMiLaZ9aDqet2aV7IKfLeUM3VGe3.lifDv4Uhfv1a2', 'alina', 'alina@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    
    INSERT INTO user_roles (user_id, roles) VALUES(3, 'USER');
    INSERT INTO user_roles (user_id, roles) VALUES(3, 'ADMIN');
    
    -- create movies --
    INSERT INTO movies (id, title, description, rental_price, sale_price, stock, availability, no_likes, created_at, created_by, modified_by)
    VALUES(1, 'A Christmas Story', 'In this holiday classic, Ralphie, a young boy growing up in the ''40''s, dreams of owning a Red Rider BB gun. He sets out to convince the world this is the perfect gift.', 3.99, 9.99, 10, true,10, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (2, 'Elf', 'One Christmas Eve a long time ago, a baby crawled into Santa''s'' bag of toys Raised as an elf. Buddy goes looking for his true place in the world--in New York City.', 3.99, 12.99, 10, true, 20, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (3, 'National Lampoon''s'' Christmas Vacation', 'The comic misadventures of the beleaguered Griswold family continue in this latest "Vacation" outing, the third and most successful of the series.', 3.99, 11.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (4, 'Polar Express', 'A doubting young boy takes an extraordinary train ride to the North Pole that shows him that the wonder of life never fades for those who believe.', 1.99, 2.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (5, 'How the Grinch Stole Christmas: The Ultimate Edition', 'Dr. Seuss’ timeless classic comes to life in an all-new Ultimate Edition, now with 2 newly remastered specials: The Grinch Grinches the Cat in the Hat and Halloween is Grinch Night!', 3.99, 16.99, 10, true, 543, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (6, 'IT', 'When children in town begin to disappear, a group of young kids is faced with their biggest fears as they square off against evil clown, Pennywise. Based on the Stephen King novel.', 5.99, 7.99, 10, true, 435, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (7, 'IT Chapter Two', 'Because every 27 years evil revisits the town of Derry, Maine, “IT Chapter Two” brings the characters back together as adults, nearly three decades after the events of the first film.', 3.99, 8.99, 10, true, 456, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (8, 'Les Misérables', 'Stéphane, a recent transplant to the impoverished suburb of Montfermeil, joins the local anti-crime squad where he works alongside two unscrupulous colleagues. When an arrest turns unexpectedly violent, the three officers must reckon with the aftermath and keep the neighborhood from spiraling out of control. Inspired by the 2005 Parisian riots, LES MISÉRABLES, is from breakout director Ladj Ly.', 5.99, 12.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (9, 'Hoffa', 'This comprehensive documentary chronicles the life and disappearance of Jimmy Hoffa, the mob-connected leader of the Teamsters union.', 1.99, 4.99, 10, true, 870, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (10, 'The Lighthouse', 'Two lighthouse keepers (Robert Pattinson and Willem Dafoe) fight each other for survival and sanity on a remote and mysterious New England island in the 1890s. From Robert Eggers, the visionary filmmaker behind horror masterpiece ''The Witch''.', 1.99, 4.99, 10, true, 765, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (11, 'Judy', 'Golden Globe® winner Renée Zellweger is "remarkable" (USA TODAY) in this unforgettable portrait of showbiz legend Judy Garland.', 2.99, 7.99, 10, true, 654, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (12, 'American Experience: The Swamp', 'The Swamp explores the repeated efforts to reclaim, control and transform what was seen as a vast wasteland into an agricultural and urban paradise, and, ultimately, the drive to preserve America''s'' greatest wetland.', 4.99, 5.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (13, 'Rocketman', 'This one-of-a-kind musical biopic embarks on the spectacular journey of Elton John in his rise to fame. With incredible performances of Elton''s'' most beloved songs, discover how a small-town boy became one of the most iconic figures in rock & roll.', 1.99, 4.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (14, 'Guns Akimbo', 'A guy relies on his newly acquired gladiator skills to save his ex-girlfriend from kidnappers.', 2.99, 6.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (15, 'Megamind', 'Packed with high-flying action and non-stop laughs, MEGAMIND puts a whole new hilarious twist on the superhero movie.', 3.99, 13.99, 10, true, 50, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (16, 'Street Fighter II: The Animated Movie', 'Based on the legendary video game series. This anime classic follows Ryu, a talented fighter who may be the greatest in all of the world, and Bison, the evil mastermind searching for Ryu''s'' talents.', 2.99, 9.99, 10, false, 0, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (17, 'Dragon Ball Super: Broly', 'Goku is back to training hard so he can face the most powerful foes the universes have to offer, and Vegeta is keeping up right beside him. But when they suddenly find themselves against an unknown Saiyan, they discover a terrible, destructive force.', 2.99, 6.99, 10, true, 330, CURRENT_TIMESTAMP, 'admin', 'admin'),
          (18, 'Digimon Adventure tri: Reunion', 'After years of inactivity, the DigiDestined regroup with their Digimon to save their world, but have the years changed their characters too much?', 3.99, 12.99, 10, true, 18, CURRENT_TIMESTAMP, 'admin', 'admin');
    
    
    INSERT INTO movie_images (movie_id, image) VALUES(1, 'http://localhost:8080/files/image1.png'), (1, 'http://localhost:8080/files/image1a.png'), (1, 'http://localhost:8080/files/image1b.png');
    INSERT INTO movie_images (movie_id, image) VALUES(2, 'http://localhost:8080/files/image2.png'), (2, 'http://localhost:8080/files/image2a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(3, 'http://localhost:8080/files/image3.png'), (3, 'http://localhost:8080/files/image3a.png'), (3, 'http://localhost:8080/files/image3b.png');
    INSERT INTO movie_images (movie_id, image) VALUES(4, 'http://localhost:8080/files/image4.png'), (4, 'http://localhost:8080/files/image4a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(5, 'http://localhost:8080/files/image5.png'), (5, 'http://localhost:8080/files/image5a.png'), (5, 'http://localhost:8080/files/image5b.png');
    INSERT INTO movie_images (movie_id, image) VALUES(6, 'http://localhost:8080/files/image6.png'), (6, 'http://localhost:8080/files/image6a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(7, 'http://localhost:8080/files/image7.png'), (7, 'http://localhost:8080/files/image7a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(8, 'http://localhost:8080/files/image8.png'), (8, 'http://localhost:8080/files/image8a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(9, 'http://localhost:8080/files/image9.png'), (9, 'http://localhost:8080/files/image9a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(10, 'http://localhost:8080/files/image10.png'), (10, 'http://localhost:8080/files/image10a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(11, 'http://localhost:8080/files/image11.png'), (11, 'http://localhost:8080/files/image11a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(12, 'http://localhost:8080/files/image12.png'), (12, 'http://localhost:8080/files/image12a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(13, 'http://localhost:8080/files/image13.png'), (13, 'http://localhost:8080/files/image13a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(14, 'http://localhost:8080/files/image14.png'), (14, 'http://localhost:8080/files/image14a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(15, 'http://localhost:8080/files/image15.png'), (15, 'http://localhost:8080/files/image15a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(16, 'http://localhost:8080/files/image16.png'), (16, 'http://localhost:8080/files/image16a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(17, 'http://localhost:8080/files/image17.png'), (17, 'http://localhost:8080/files/image17a.png');
    INSERT INTO movie_images (movie_id, image) VALUES(18, 'http://localhost:8080/files/image18.png'), (18, 'http://localhost:8080/files/image18a.png');
             
         

	```

	Any new user who signs up to the app is assigned the `ROLE USER` or `ROLE ADMIN` by default.

---


---
## Demo Screens ##

1. **Logging with a valid username**
---
![Selection_584](/uploads/6f0aad407701e6e5171a142c55c6c797/Selection_584.png)


2. **Logging with a invalid username**
---
![Selection_586](/uploads/c6dea22b84613953cee78abbf872ae51/Selection_586.png)
