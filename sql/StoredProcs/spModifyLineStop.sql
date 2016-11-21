
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyLineStop$$
CREATE PROCEDURE spModifyLineStop
(IN
in_employeeid INT,
in_linestopid INT,
in_stopsforduration TIME,
in_status BOOL
)


BEGIN


UPDATE linestops
SET
	`StopsForDuration` = in_stopsforduration,
	`STATUS` = in_status,
	`UpdatedBy_EmployeeID` = in_employeeid

WHERE
	`ID` = in_linestopid ;


SELECT 1;

END$$

DELIMITER ;
