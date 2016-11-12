DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyUser$$

CREATE PROCEDURE spModifyUser
(IN
in_UserId INT,
in_UserName VARCHAR(50),
in_Name VARCHAR(50),
in_PhoneNumber VARCHAR(10),
in_Position VARCHAR(20)
)


BEGIN

DECLARE `_rollback` BOOL DEFAULT 0;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;

-- wrap in transaction to prevent partial error
START TRANSACTION;


UPDATE users SET username = in_username, `name` = in_name WHERE userid = in_userid;

IF EXISTS(SELECT NULL FROM clerks WHERE userid = in_userId) THEN
	UPDATE clerks SET empposition = in_position WHERE userid = in_userid;
ELSE
	UPDATE passengers SET phonenumber = in_phonenumber WHERE userid = in_userid;
END IF;


IF `_rollback` THEN
	ROLLBACK;
	SELECT 0;
ELSE
	COMMIT;

	SELECT
		u.userid,
		u.username,
		u.name,
		p.phonenumber,
		e.empposition
	FROM users u
	LEFT JOIN passengers p ON p.userid = u.userid
	LEFT JOIN clerks e ON e.userid = u.userid
	WHERE u.userid = in_userid;
END IF;


END$$

DELIMITER ;
