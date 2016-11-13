DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyTrain$$
CREATE PROCEDURE spModifyTrain
(IN
in_trainNo INT,
in_employeeId INT,
in_Active BOOL
)


BEGIN


UPDATE trains
SET
	`Active` = in_Active,
	`UpdatedBy_EmployeeID` = in_employeeId

WHERE
	`TrainNumber` = in_trainno;

SELECT 1;

END$$

DELIMITER ;
