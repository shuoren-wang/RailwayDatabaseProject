
DELIMITER $$

DROP PROCEDURE IF EXISTS spSearchPassengerInfo$$
CREATE PROCEDURE spSearchPassengerInfo(
IN
in_uid INT
)

BEGIN

SELECT
	u.userid,
	p.passengerid,
	u.username,
	u.name,
	p.phonenumber
FROM users u
	INNER JOIN passengers p ON u.userid = p.userid

WHERE u.userid = in_uid;

END$$

DELIMITER ;
