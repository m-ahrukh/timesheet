CREATE TABLE IF NOT EXISTS employee (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS timesheet_template (
    id SERIAL NOT NULL PRIMARY KEY,
    template_day VARCHAR(10) NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    check_in TIME NOT NULL,
    check_out TIME NOT NULL,
    lunch_break_start TIME NOT NULL,
    lunch_break_end TIME NOT NULL,
    employee_id int,
    CONSTRAINT fk_employee_id
          FOREIGN KEY(employee_id)
    	  REFERENCES employee(id)
);

