DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyTrainType$$
CREATE PROCEDURE spModifyTrainType
(IN
in_id INT,
in_color VARCHAR(30)
)


BEGIN


UPDATE traintypes
SET
	color = in_color

WHERE
	`ID` = in_id ;


SELECT 1;

END$$

DELIMITER ;
