
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyLine$$
CREATE PROCEDURE spModifyLine
(IN
in_employeeId INT,
in_lineid INT,
in_lineName VARCHAR(50),
in_isActive BOOL
)


BEGIN


UPDATE line
SET
	`LineName` = in_linename,
	`UpdatedBy_EmployeeID` = in_employeeId,
	`Active` = in_isActive
WHERE
	`ID` = in_lineid;

SELECT 1;

END$$

DELIMITER ;
