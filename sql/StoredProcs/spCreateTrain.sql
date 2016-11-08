DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateTrain$$


CREATE PROCEDURE spCreateTrain
(IN
in_employeeId INT,
in_runsM BOOL,
in_runsT BOOL,
in_runsW BOOL,
in_runsTH BOOL,
in_runsF BOOL,
in_runsS BOOL,
in_runsSU BOOL,
in_TrainTypeId INT,
in_LineId INT
)


BEGIN


INSERT INTO trains
(
	`Runs_M`,
	`Runs_T`,
	`Runs_W`,
	`Runs_TH`,
	`Runs_F`,
	`Runs_S`,
	`Runs_SU`,
	`TrainType_ID`,
	`RunsLine_ID`,
	`CreatedBy_EmployeeID`
)

SELECT
	in_runsM,
	in_runsT,
	in_runsW,
	in_runsTH,
	in_runsF,
	in_runsS,
	in_runsSU,
	in_trainTypeId,
	in_lineId,
	in_employeeid;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
