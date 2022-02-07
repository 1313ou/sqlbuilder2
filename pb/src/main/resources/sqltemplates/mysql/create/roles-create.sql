CREATE TABLE IF NOT EXISTS ${roles.table} (
    ${roles.roleid} INTEGER NOT NULL,
    ${roles.rolesetid} INTEGER NOT NULL,
    ${roles.nargid} VARCHAR (1 ) NOT NULL,
    ${roles.funcid} INTEGER NULL,
    ${roles.thetaid} INTEGER NULL,
    ${roles.roledescr} VARCHAR (100) NOT NULL,
PRIMARY KEY (${roles.roleid}));