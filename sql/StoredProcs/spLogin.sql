DELIMITER $$

DROP PROCEDURE IF EXISTS spLogin$$
CREATE PROCEDURE spLogin
(IN
in_username VARCHAR(50),
in_password VARCHAR(50)
)


SELECT u.userid,
	CASE WHEN EXISTS(SELECT NULL FROM clerks c WHERE c.userid = u.userid) THEN 1

	ELSE 0
	END AS UserType
FROM users u
WHERE
	`username` = in_username AND `password` = in_password$$

DELIMITER ;
