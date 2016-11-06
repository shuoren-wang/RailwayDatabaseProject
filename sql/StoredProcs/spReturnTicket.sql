DELIMITER $$

DROP PROCEDURE IF EXISTS spReturnTicket$$
CREATE PROCEDURE spReturnTicket
(IN
in_requestingUserId INT,
in_ticketID INT
)

BEGIN

IF EXISTS
	(
	SELECT NULL FROM tickets WHERE owner_id = in_requestingUserId AND id = in_ticketID
	)OR EXISTS(
	SELECT NULL FROM clerks WHERE userId = in_requestingUserId
	)
THEN
	-- return ticket
	UPDATE tickets SET returned = 1 WHERE id = in_ticketID;
	SELECT 1;
ELSE
	-- no such match
	SELECT 0;
END IF;

END$$

DELIMITER ;
