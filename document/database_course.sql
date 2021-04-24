CREATE TABLE `spring`.`users` (
  `phone_number` VARCHAR(10) NOT NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`phone_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `spring`.`users` (`phone_number`, `password`, `email`) VALUES ('0333666666', '33336666', 'gyuk@gmail.com');
