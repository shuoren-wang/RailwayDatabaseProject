DELIMITER $$

DROP PROCEDURE IF EXISTS spPassengerInfo$$
CREATE PROCEDURE spPassengerInfo
(IN
in_userid INT
)

SELECT u.username, u.name, p.phonenumber
FROM users u INNER JOIN passengers p ON u.userid = p.userid
WHERE u.userid = in_userid$$

DELIMITER ;
