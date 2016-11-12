DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTickets$$
CREATE PROCEDURE spViewTickets
(IN
in_forUserId INT
)


BEGIN


SELECT
t.owner_Id AS forUserId,
t.ID AS ticketId,
fls.locatedStation_id AS fromStationId,
tls.locatedStation_id AS toStationId,
t.departuredate AS travelDate,
s.Class,
fls.forline_id AS lineID,
t.fortrain_number AS TrainNumber

FROM
	tickets t
	INNER JOIN linestops fls ON fls.id = t.fromlinestop_id
	INNER JOIN linestops tls ON tls.id = t.toLineStop_id
	INNER JOIN seats s ON s.seatnumber = t.forseat_number AND s.trainnumber = t.fortrain_number
WHERE owner_id = in_forUserId;



END$$

DELIMITER ;
