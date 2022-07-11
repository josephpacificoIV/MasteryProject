# MasteryProject


## Project Outline

### View Existing Reservations by Host

- Prompt Admin for to search by Host
- Validate entry of 'Host'
- Error messages for unknown host, host not found, host has no reservations
- If found a Host, display all current reservations for that Host. 
- Display information for each reservation, sorted by date.

### Create A Reservation for a Guest with a Host
- Admin enters GUEST email (required)
- Admin enters HOST email (required)
- Both Guest and Host must already exist in the data, cannot create. 
- Display all future reservations for the host
- Enter available start date (must be in the future)/ end date range (range must not overlap with existing ranges). 
- Calculate totals, with different weekend(friday or saturday)/weekday rates. 
- Do not charge for the end date. Allow booking a new reservation's start date on an existing reservation's end date.  
- Prompt Admin to confirm y/n
- Save reservation

### Edit Reservation
- Prompt Admin to search for a reservation by Host. 
- Can only edit the start date(must be in the future) and end date.
- Recalculates the total, displays summary.
- Prompt the Admin to confirm y/n

### Cancel A Future Reservation
- Prompt Admin to search for a reservation by Host.
- Display only future reservations.
- Cancel reservation by ID. 
- Show success message.
