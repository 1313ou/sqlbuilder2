CREATE TABLE IF NOT EXISTS ${lexunits.table} (
${lexunits.luid} INTEGER NOT NULL,
${lexunits.frameid} INTEGER,
${lexunits.lexunit} VARCHAR(64),
${lexunits.posid} INTEGER,
${lexunits.ludefinition} TEXT,
${lexunits.ludict} CHARACTER,
${lexunits.statusid} INTEGER DEFAULT NULL,
${lexunits.totalannotated} INTEGER,
${lexunits.incorporatedfetypeid} INTEGER DEFAULT NULL
);
