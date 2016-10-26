DELIMITER $$

DROP PROCEDURE IF EXISTS spRegister$$
CREATE PROCEDURE spRegister
(IN
in_username VARCHAR(50),
in_password VARCHAR(50),
in_name VARCHAR(50),
in_phoneno VARCHAR(10),
in_empposition VARCHAR(20),
in_usertype INT
)

proc_label:BEGIN

    DECLARE `_rollback` BOOL DEFAULT 0;
    DECLARE `uid` INT;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;

-- wrap in transaction to prevent partial error
START TRANSACTION;

IF EXISTS (SELECT username FROM users WHERE username = in_username) THEN
	LEAVE proc_label;
END IF;

INSERT INTO `users`
	(
	`NAME`,
	`UserName`,
	`PASSWORD`
	)
	VALUES
	(
	in_name,
	in_username,
	in_password
	);

SELECT userId INTO uid FROM users WHERE username = in_username;

IF in_usertype = 0 THEN
	INSERT INTO `passengers`
		(
			`PhoneNumber`,
			`UserID`
		)
		SELECT
			in_phoneno,
			uid;
	SELECT uid;
ELSEIF in_usertype = 1 THEN

	INSERT INTO `clerks`
		(
		`EmpPosition`,
		`UserID`
		)
		SELECT
			in_empposition,
			uid;
	SELECT uid;
ELSE
	SELECT 0 AS error;
END IF;


IF `_rollback` THEN
SELECT 123;
	ROLLBACK;
ELSE
	COMMIT;
END IF;

END$$

DELIMITER ;
