ALTER TABLE ${roles.table} ADD CONSTRAINT `fk_@{roles.table}_@{roles.classid}` FOREIGN KEY (${roles.classid}) REFERENCES ${classes.table} (${classes.classid});
ALTER TABLE ${roles.table} ADD CONSTRAINT `fk_@{roles.table}_@{roles.roletypeid}` FOREIGN KEY (${roles.roletypeid}) REFERENCES ${roletypes.table} (${roletypes.roletypeid});
ALTER TABLE ${roles.table} ADD CONSTRAINT `fk_@{roles.table}_@{roles.restrsid}` FOREIGN KEY (${roles.restrsid}) REFERENCES ${restrs.table} (${restrs.restrsid});
