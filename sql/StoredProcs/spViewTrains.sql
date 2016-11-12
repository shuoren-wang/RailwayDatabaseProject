DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTrains$$
CREATE PROCEDURE spViewTrains(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	t.trainnumber,
	t.runs_m,
	t.runs_t,
	t.runs_w,
	t.runs_th,
	t.runs_f,
	t.runs_s,
	t.runs_su,
	t.traintype_id,
	tt.color,
	t.runsline_id,
	l.linename,
	t.createdby_employeeid,
	cru.name AS CreatedByEmployee_Name,
	t.updatedby_employeeid,
	t.active,
	upu.name AS UpdatedByEmployee_Name,
	COUNT(DISTINCT s.seatnumber) AS seatNumberCount,
	COUNT(DISTINCT acs.seatnumber) AS seatNumberCountActive
FROM
	trains t
	INNER JOIN traintypes tt ON tt.id = t.traintype_id
	INNER JOIN line l ON l.id = t.runsline_id
	INNER JOIN clerks crc ON crc.employeeid = t.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = t.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN seats s ON s.trainnumber = t.trainnumber
	LEFT JOIN seats acs ON acs.trainnumber = t.trainnumber AND acs.active
GROUP BY
	t.trainnumber,
	t.runs_m,
	t.runs_t,
	t.runs_w,
	t.runs_th,
	t.runs_f,
	t.runs_s,
	t.runs_su,
	t.traintype_id,
	tt.color,
	t.runsline_id,
	l.linename,
	t.createdby_employeeid,
	cru.name,
	t.updatedby_employeeid,
	t.active,
	upu.name

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN t.trainnumber
		WHEN in_orderby = 'color' THEN tt.color
		WHEN in_orderby = 'lid' THEN l.id
		WHEN in_orderby = 'ttid' THEN tt.id
		WHEN in_orderby = 'seatcount' THEN COUNT(DISTINCT s.seatnumber)
		WHEN in_orderby = 'seatcountactive' THEN COUNT(DISTINCT acs.seatnumber)
		ELSE t.trainnumber
	END)

LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
