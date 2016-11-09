DELIMITER $$

DROP PROCEDURE IF EXISTS spClerkInfo$$
CREATE PROCEDURE spClerkInfo(
IN
in_uid INT
)

BEGIN

SELECT
	u.userid,
	c.employeeid,
	u.username,
	u.name,
	c.empposition
FROM users u
	INNER JOIN clerks c ON u.userid = c.userid

WHERE u.userid = in_uid;

END$$

DELIMITER ;
