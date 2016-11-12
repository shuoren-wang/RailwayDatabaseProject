DELIMITER $$

DROP PROCEDURE IF EXISTS spPasswordRecovery$$
CREATE PROCEDURE spPasswordRecovery(
IN
in_uname VARCHAR(50),
in_name VARCHAR(50),
in_phone VARCHAR(10)
)

BEGIN

SELECT
	u.password
FROM users u
	INNER JOIN passengers p ON u.userid = p.userid

WHERE
	u.username = in_uname
	AND u.name = in_name
	AND p.phonenumber = in_phone;

END$$

DELIMITER ;
