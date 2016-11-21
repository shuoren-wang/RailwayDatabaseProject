DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyTrain$$
CREATE PROCEDURE spModifyTrain
(IN
in_trainNo INT,
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


UPDATE trains
SET
	`Runs_M` = in_runsM,
	`Runs_T` = in_runsT,
	`Runs_W` = in_runsW,
	`Runs_TH` = in_runsTH,
	`Runs_F` = in_runsF,
	`Runs_S` = in_runsS,
	`Runs_SU` = in_runsSU,
	`TrainType_ID` = in_traintypeid,
	`RunsLine_ID` = in_lineid,
	`UpdatedBy_EmployeeID` = in_employeeId

WHERE
	`TrainNumber` = in_trainno;

SELECT 1;

END$$

DELIMITER ;
