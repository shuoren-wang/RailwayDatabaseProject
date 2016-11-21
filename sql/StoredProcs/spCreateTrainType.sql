DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateTrainType$$
CREATE PROCEDURE spCreateTrainType
(IN
in_color VARCHAR(30)
)


BEGIN

INSERT INTO `traintypes`
	(
	`Color`
	)

SELECT
	in_color;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
