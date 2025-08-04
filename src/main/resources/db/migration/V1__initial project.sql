CREATE TABLE `industry_categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `industry_category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `industry_categorycol_UNIQUE` (`industry_category`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `departments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `department` varchar(45) NOT NULL,
  `abbreviation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `department_UNIQUE` (`department`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;







CREATE TABLE `job_level` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `job_level` varchar(45) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `job_level_UNIQUE` (`job_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `teams` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `department_id` BIGINT NOT NULL,
  `team` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`) /*!80000 INVISIBLE */,
  UNIQUE KEY `depart_team` (`department_id`,`team`),
  CONSTRAINT `fk_department of team` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `sex` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `id_number` varchar(45) NOT NULL,
  `birthday` date DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email_private` varchar(100) DEFAULT NULL,
  `email_company` varchar(100) DEFAULT NULL,
  `employee_number` varchar(45) DEFAULT NULL,
  `job_level_id` BIGINT DEFAULT NULL,
  `team_id` BIGINT DEFAULT NULL,
  `system_role` varchar(45) NOT NULL,
  `hired_at` date DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by_id` BIGINT DEFAULT NULL,
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `id_number_UNIQUE` (`id_number`),
  UNIQUE KEY `employee_number_UNIQUE` (`employee_number`),
  KEY `job_level_idx` (`job_level_id`),
  KEY `team_id_idx` (`team_id`),
  KEY `last_modified_by_id_idx` (`last_modified_by_id`),
  CONSTRAINT `fk_job_level of users` FOREIGN KEY (`job_level_id`) REFERENCES `job_level` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_last_modified_by_id of users` FOREIGN KEY (`last_modified_by_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_team_id of users` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `is_company` tinyint(1) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `industry_category_id` BIGINT DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `industry_category_idx` (`industry_category_id`),
  KEY `last_modified_by_idx` (`last_modified_by_id`),
  CONSTRAINT `fk_industry_category of custom` FOREIGN KEY (`industry_category_id`) REFERENCES `industry_categories` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_last_modified_by of custom` FOREIGN KEY (`last_modified_by_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `point_of_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `contact_person` varchar(45) NOT NULL,
  `contact_phone` varchar(45) DEFAULT NULL,
  `contact_email` varchar(45) DEFAULT NULL,
  `contact_address` varchar(45) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`) /*!80000 INVISIBLE */,
  KEY `custom_id_idx` (`customer_id`) /*!80000 INVISIBLE */,
  KEY `last_modified_by_idx` (`last_modified_by_id`),
  KEY `poc_customerId_contactPerson` (`customer_id`,`contact_person`),
  CONSTRAINT `fk_customer_id of POC` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `test_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `team_id` BIGINT NOT NULL,
  `testing_days` INTEGER NOT NULL,
  `testing_price` decimal(10,2) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `team_id of testitem_idx` (`team_id`),
  KEY `last_modified_time of testitem_idx` (`last_modified_by_id`),
  CONSTRAINT `fk_last_modified_time of testitem` FOREIGN KEY (`last_modified_by_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_team_id of testitem` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `test_cases` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `test_case_number` varchar(45) DEFAULT NULL,
  `customer_id` BIGINT NOT NULL,
  `test_item_id` BIGINT NOT NULL,
  `testing_days` INTEGER NOT NULL,
  `testing_price` decimal(10,2) NOT NULL,
  `case_start_time` timestamp NULL DEFAULT NULL,
  `experiment_end_time` timestamp NULL DEFAULT NULL,
  `experiment_review_time` timestamp NULL DEFAULT NULL,
  `report_close_time` timestamp NULL DEFAULT NULL,
  `lab_deadline` timestamp NULL DEFAULT NULL,
  `report_deadline` timestamp NULL DEFAULT NULL,
  `experiment_conductor_id` BIGINT DEFAULT NULL,
  `experiment_reviewer_id` BIGINT DEFAULT NULL,
  `report_conductor_id` BIGINT DEFAULT NULL,
  `test_result` varchar(45) DEFAULT NULL,
  `case_status` varchar(45) DEFAULT NULL,
  `sample_status` varchar(45) DEFAULT NULL,
  `sample_name` varchar(45) DEFAULT NULL,
  `point_of_contact_id` BIGINT DEFAULT NULL,
  `sample_original_weight` double DEFAULT NULL,
  `sample_remaining_weight` double DEFAULT NULL,
  `case_handler_id` BIGINT DEFAULT NULL,
  `note` text,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `test_item_id of case_idx` (`test_item_id`),
  KEY `custom_id of case_idx` (`customer_id`),
  KEY `experiment_conductor_id of case_idx` (`experiment_conductor_id`),
  KEY `experiment_reviewer_id of case_idx` (`experiment_reviewer_id`),
  KEY `report_conductor_id of case_idx` (`report_conductor_id`),
  KEY `fk_case_handler of case_idx` (`case_handler_id`),
  KEY `fk_case_point_of_contact` (`point_of_contact_id`),
  KEY `fk_last_modified_by_id of case_idx` (`last_modified_by_id`),
  CONSTRAINT `fk_case_handler_id of case` FOREIGN KEY (`case_handler_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_case_point_of_contact` FOREIGN KEY (`point_of_contact_id`) REFERENCES `point_of_contact` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_id of case` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_conductor_id of case` FOREIGN KEY (`experiment_conductor_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_reviewer_id of case` FOREIGN KEY (`experiment_reviewer_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_report_conductor_id of case` FOREIGN KEY (`report_conductor_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_last_modified_by_id of case` FOREIGN KEY (`last_modified_by_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_test_item_id of case` FOREIGN KEY (`test_item_id`) REFERENCES `test_items` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;