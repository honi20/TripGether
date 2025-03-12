DROP DATABASE IF EXISTS `wooyeah`;
CREATE DATABASE `wooyeah`;
USE `wooyeah`;


-- -----------------------------------------------------
-- Table `wooyeah`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `wooyeah`.`user` (
  `uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  -- `nickname` VARCHAR(45) NOT NULL,
  `gender` VARCHAR(10) NULL, -- 0이면 여성, 1이면 남성
  `birthday` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `wooyeah`.`profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(45) NOT NULL,
  `save_folder` VARCHAR(100) NULL,
  `original_file` VARCHAR(100) NULL,
  `save_file` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `wooyeah`.`user` (`id`)
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `wooyeah`.`plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `wooyeah`.`plan` (
  `uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `writer` VARCHAR(45) NULL,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `created_at` DATETIME DEFAULT NOW(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`writer`) REFERENCES `wooyeah`.`user`(`id`)
  )
ENGINE = InnoDB;

DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `wooyeah`.`schedule` (
	`uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `day` INT NULL,
  `idx` INT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`plan_id`) REFERENCES `wooyeah`.`plan`(`id`) ON DELETE CASCADE
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `wooyeah`.`place`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `place`;
CREATE TABLE `wooyeah`.`place` (
  `uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id` INT NOT NULL AUTO_INCREMENT,
  `schedule_id` INT NULL,
  `place_order` INT NULL,
  `name` VARCHAR(100) NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  `description` VARCHAR(300) DEFAULT NULL,
  `budget` INT DEFAULT 0,
  `time` TIME NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`schedule_id`) REFERENCES `wooyeah`.`schedule` (`id`) ON DELETE CASCADE
  )
ENGINE = InnoDB;

DROP TABLE IF EXISTS `plan_img`;
CREATE TABLE `wooyeah`.`plan_img` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_id` INT NOT NULL,
  `save_folder` VARCHAR(100) NULL,
  `original_file` VARCHAR(100) NULL,
  `save_file` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`plan_id`) REFERENCES `wooyeah`.`plan` (`id`) ON DELETE CASCADE
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `wooyeah`.`matching`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `theme`;
CREATE TABLE `wooyeah`.`theme` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `for_energetic`	BOOLEAN DEFAULT 0,
    `for_leisurely`	BOOLEAN DEFAULT 0,
    `for_natural`	BOOLEAN DEFAULT 0,
    `for_urban`		BOOLEAN DEFAULT 0,
    `for_healing`	BOOLEAN DEFAULT 0,
    `for_shopping`	BOOLEAN DEFAULT 0,
    `for_morning`	BOOLEAN DEFAULT 0,
    `for_night_owl`	BOOLEAN DEFAULT 0,
    PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `matching`;
CREATE TABLE `wooyeah`.`matching` (
  `uuid`	VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id`		INT NOT NULL AUTO_INCREMENT,
  `title`	VARCHAR(100) NULL,
  `region`	VARCHAR(60),
  `recruit_person_cnt`	INT DEFAULT 0,
  `current_person_cnt`	INT DEFAULT 1,
  `description`	VARCHAR(300),
  `view_cnt`	INT DEFAULT 0,
  `created_at`	DATETIME DEFAULT NOW(),
  `writer_id`	VARCHAR(45),
  `plan_id`		INT,
  `theme_id` 	INT,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`writer_id`) REFERENCES `wooyeah`.`user` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`plan_id`) REFERENCES `wooyeah`.`plan` (`id`),
  FOREIGN KEY (`theme_id`) REFERENCES `wooyeah`.`theme` (`id`)
  )
  ENGINE = InnoDB;
  
  DROP TABLE IF EXISTS `trip_group`;
  CREATE TABLE `wooyeah`.`trip_group` (
	`user_id`		VARCHAR(45),
    `matching_id`	INT,
    PRIMARY KEY (`user_id`, `matching_id`),
    FOREIGN KEY (`user_id`) REFERENCES `wooyeah`.`user` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`matching_id`) REFERENCES `wooyeah`.`matching` (`id`) ON DELETE CASCADE
  )
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `wooyeah`.`notice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `wooyeah`.`notice` (
  `uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NULL,
  `title` VARCHAR(45) NULL, -- title을 변수+템플릿으로 조합할거면 굳이...
  `msg` VARCHAR(100) NULL,
  `created_at` DATETIME NULL DEFAULT NOW(),
  `read_status` TINYINT NULL DEFAULT 0,
  `processed_status` TINYINT NULL DEFAULT 0,
  `sender_id` VARCHAR(45) NULL,
  `receiver_id` VARCHAR(45) NULL,
  `matching_id` INT, -- 추후 matching table 생성시 연결
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sender_id`) REFERENCES `wooyeah`.`user` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`receiver_id`) REFERENCES `wooyeah`.`user` (`id`) ON DELETE CASCADE
  -- FOREIGN KEY (`matching_id`) REFERENCES `wooyeah`.`matching` (`id`) ON DELETE CASCADE
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `wooyeah`.`diary`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `wooyeah`.`diary` (
	`uuid` VARCHAR(36) NOT NULL DEFAULT (UUID()),
	`id` INT NOT NULL AUTO_INCREMENT,
    `writer_id` VARCHAR(45) NOT NULL,
    `matching_id` INT,
    `content` VARCHAR(100),
    `location` VARCHAR(60),
    `public_status` VARCHAR(30),
    `travel_date` VARCHAR(30),
    `created_at` DATETIME DEFAULT NOW(),
	PRIMARY KEY (`id`),
    FOREIGN KEY (`writer_id`) REFERENCES `wooyeah`.`user` (`id`) ON DELETE CASCADE
    -- FOREIGN KEY (`matching_id`) REFERENCES `wooyeah`.`matching` (`id`) ON DELETE SET NULL
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `diary_img`;
CREATE TABLE `wooyeah`.`diary_img` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `save_folder` VARCHAR(100) NULL,
  `original_file` VARCHAR(100) NULL,
  `save_file` VARCHAR(100) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `img_group`;
CREATE TABLE `wooyeah`.`img_group` (
	`diary_id`		INT,
    `diary_img_id`	INT,
	PRIMARY KEY (`diary_id`, `diary_img_id`),
	FOREIGN KEY (`diary_id`) REFERENCES `wooyeah`.`diary` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`diary_img_id`) REFERENCES `wooyeah`.`diary_img` (`id`) ON DELETE CASCADE
)
ENGINE = InnoDB;

CREATE TABLE token_status (
	uuid	varchar(100),
    hashed_token	char(128),
    expiration	timestamp,
    valid	boolean,
    constraint token_pk primary key(`uuid`, `hashed_token`)
);

SET SQL_SAFE_UPDATES = 0;