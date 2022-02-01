ALTER TABLE ${grouppatterns_patterns.table} ADD CONSTRAINT `fk_@{grouppatterns_patterns.table}_@{grouppatterns_patterns.patternid}` FOREIGN KEY (${grouppatterns_patterns.patternid}) REFERENCES ${grouppatterns.table} (${grouppatterns.patternid});
ALTER TABLE ${grouppatterns_patterns.table} ADD CONSTRAINT `fk_@{grouppatterns_patterns.table}_@{grouppatterns_patterns.vuid}` FOREIGN KEY (${grouppatterns_patterns.vuid}) REFERENCES ${valenceunits.table} (${valenceunits.vuid});
ALTER TABLE ${grouppatterns_patterns.table} ADD CONSTRAINT `fk_@{grouppatterns_patterns.table}_@{grouppatterns_patterns.ferid}` FOREIGN KEY (${grouppatterns_patterns.ferid}) REFERENCES ${ferealizations.table} (${ferealizations.ferid});
