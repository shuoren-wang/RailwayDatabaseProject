DELIMITER $$

DROP PROCEDURE IF EXISTS spChangePassword$$
CREATE PROCEDURE spChangePassword
(IN
in_userid VARCHAR(50),
in_password VARCHAR(50),
in_newpassword VARCHAR(50)
)

BEGIN

IF EXISTS (SELECT NULL FROM users WHERE userid = in_userid AND PASSWORD = in_password) THEN
	-- userid and password match
	UPDATE users SET PASSWORD = in_newpassword WHERE userid = in_userid;
	SELECT 1;
ELSE
	-- no such match
	SELECT 0;
END IF;

END$$

DELIMITER ;
