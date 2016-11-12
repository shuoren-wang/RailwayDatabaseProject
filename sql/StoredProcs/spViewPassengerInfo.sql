DELIMITER $$

DROP PROCEDURE IF EXISTS spViewPassengerInfo$$
CREATE PROCEDURE spViewPassengerInfo(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
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

ORDER BY(
	CASE
		WHEN in_orderby = 'uid' THEN u.userid
		WHEN in_orderby = 'pid' THEN p.passengerid
		WHEN in_orderby = 'uname' THEN u.username
		WHEN in_orderby = 'name' THEN u.name
		WHEN in_orderby = 'phone' THEN p.phonenumber
		ELSE u.userid
	END)

LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
