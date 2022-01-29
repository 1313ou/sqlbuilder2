package org.sqlbuilder.fn.objects;

import org.sqlbuilder.common.Insertable;
import org.sqlbuilder.common.Utils;
import org.sqlbuilder.fn.Collector;
import org.sqlbuilder.fn.HasId;
import org.sqlbuilder.fn.RequiresIdFrom;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Values
{
	/*
	poses.table=fnposes
	poses.create=CREATE TABLE IF NOT EXISTS %Fn_poses.table% ( posid INTEGER NOT NULL AUTO_INCREMENT,pos VARCHAR(8),PRIMARY KEY (posid) );
	poses.unq1=CREATE UNIQUE INDEX IF NOT EXISTS unq_%Fn_poses.table%_pos ON %Fn_poses.table% (pos);
	poses.no-unq1=DROP INDEX IF EXISTS unq_%Fn_poses.table%_pos;
	poses.insert=INSERT INTO %Fn_poses.table% (pos) VALUES(?);
	 */
	public static class Pos implements HasId, Insertable<Pos>
	{
		public static final Comparator<Pos> COMPARATOR = Comparator.comparing(t -> t.pos);

		public static final Map<Pos, Integer> MAP = new TreeMap<>(COMPARATOR);

		private final String pos;

		public static Pos make(final String pos, final int idx)
		{
			var p = new Pos(pos);
			MAP.put(p, idx);
			return p;
		}

		private Pos(final String pos)
		{
			this.pos = pos;
		}

		@RequiresIdFrom(type = Pos.class)
		@Override
		public Integer getIntId()
		{
			return MAP.get(this);
		}

		@Override
		public String dataRow()
		{
			return String.format("'%s'", pos);
		}
	}

	/*
	coretypes.table=fncoretypes
	coretypes.create=CREATE TABLE IF NOT EXISTS %Fn_coretypes.table% ( coretypeid INTEGER NOT NULL AUTO_INCREMENT,coretype VARCHAR(16),PRIMARY KEY (coretypeid) );
	coretypes.unq1=CREATE UNIQUE INDEX IF NOT EXISTS unq_%Fn_coretypes.table%_coretype ON %Fn_coretypes.table% (coretype);
	coretypes.no-unq1=DROP INDEX IF EXISTS unq_%Fn_coretypes.table%_coretype;
	coretypes.insert=INSERT INTO %Fn_coretypes.table% (coretype) VALUES(?);
	 */
	public static class CoreType implements HasId, Insertable<CoreType>
	{
		public static final Comparator<CoreType> COMPARATOR = Comparator.comparing(t -> t.coretype);

		public static final Map<CoreType, Integer> MAP = new TreeMap<>(COMPARATOR);

		private final String coretype;

		public static CoreType make(final String coretype, final int idx)
		{
			var t = new CoreType(coretype);
			MAP.put(t, idx);
			return t;
		}

		private CoreType(final String coretype)
		{
			this.coretype = coretype;
		}

		@RequiresIdFrom(type = CoreType.class)
		@Override
		public Integer getIntId()
		{
			return MAP.get(this);
		}

		@Override
		public String dataRow()
		{
			return String.format("'%s'", coretype);
		}
	}

	/*
	labelitypes.table=fnlabelitypes
	labelitypes.create=CREATE TABLE IF NOT EXISTS %Fn_labelitypes.table% ( labelitypeid INTEGER NOT NULL AUTO_INCREMENT,labelitype VARCHAR(4),labelitypedescr VARCHAR(16) DEFAULT NULL,PRIMARY KEY (labelitypeid) );
	labelitypes.insert=INSERT INTO %Fn_labelitypes.table% (labelitype) VALUES(?);
	 */
	public static class LabelIType implements HasId, Insertable<LabelIType>
	{
		public static final Comparator<LabelIType> COMPARATOR = Comparator.comparing(t -> t.labelitype);

		public static final Map<LabelIType, Integer> MAP = new TreeMap<>(COMPARATOR);

		private final String labelitype;

		public static LabelIType make(final String labelitype, final int idx)
		{
			var l = new LabelIType(labelitype);
			MAP.put(l, idx);
			return l;
		}

		private LabelIType(final String labelitype)
		{
			this.labelitype = labelitype;
		}

		@RequiresIdFrom(type = LabelIType.class)
		@Override
		public Integer getIntId()
		{
			return MAP.get(this);
		}

		@Override
		public String dataRow()
		{
			return String.format("'%s'", labelitype);
		}
	}
}
