CREATE UNIQUE INDEX `pk_@{pms.table}` ON ${pms.table} (${pms.pmid});
CREATE INDEX `k_@{pms.table}_@{pms.pmroleid}` ON ${pms.table} (${pms.pmroleid});
CREATE INDEX `k_@{pms.table}_@{pms.synsetid}` ON ${pms.table} (${pms.synsetid});
CREATE INDEX `k_@{pms.table}_@{pms.wordid}` ON ${pms.table} (${pms.wordid});
CREATE INDEX `k_@{pms.table}_@{pms.word}` ON ${pms.table} (${pms.word});
CREATE INDEX `k_@{pms.table}_@{pms.sensekey}` ON ${pms.table} (${pms.sensekey});
CREATE INDEX `k_@{pms.table}_@{pms.vnclass}` ON ${pms.table} (${pms.vnclass});
CREATE INDEX `k_@{pms.table}_@{pms.vnrole}` ON ${pms.table} (${pms.vnrole});
CREATE INDEX `k_@{pms.table}_@{pms.pbroleset}` ON ${pms.table} (${pms.pbroleset});
CREATE INDEX `k_@{pms.table}_@{pms.pbrole}` ON ${pms.table} (${pms.pbrole});
CREATE INDEX `k_@{pms.table}_@{pms.fnframe}` ON ${pms.table} (${pms.fnframe});
CREATE INDEX `k_@{pms.table}_@{pms.fnfe}` ON ${pms.table} (${pms.fnfe});
