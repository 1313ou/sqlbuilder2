package org.sqlbuilder.fn.objects;

import org.sqlbuilder.common.Insertable;
import org.sqlbuilder.fn.Collector;
import org.sqlbuilder.fn.HasId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.berkeley.icsi.framenet.AnnoSetType;
import edu.berkeley.icsi.framenet.FEGroupRealizationType;

/*
patterns.table=fnpatterns
patterns.create=CREATE TABLE IF NOT EXISTS %Fn_patterns.table% ( patternid INTEGER NOT NULL,fegrid INTEGER,total INTEGER,PRIMARY KEY (patternid) );
patterns.fk1=ALTER TABLE %Fn_patterns.table% ADD CONSTRAINT fk_%Fn_patterns.table%_fegrid FOREIGN KEY (fegrid) REFERENCES %Fn_fegrouprealizations.table% (fegrid);
patterns.no-fk1=ALTER TABLE %Fn_patterns.table% DROP CONSTRAINT fk_%Fn_patterns.table%_fegrid CASCADE;
patterns.insert=INSERT INTO %Fn_patterns.table% (patternid,fegrid,total) VALUES(?,?,?);
 */
public class Pattern implements HasId, Insertable<Pattern>
{
	public static final Comparator<Pattern> COMPARATOR = Comparator.comparing(Pattern::getAnnosetIDs).thenComparing(Pattern::getFegr, FEGroupRealization.COMPARATOR);

	public static final Collector<Pattern> COLLECTOR = new Collector<>(COMPARATOR);

	public final String annosetIDs;

	public final FEGroupRealization fegr;

	public final int total;

	public static Pattern make(final FEGroupRealizationType.Pattern pattern, final FEGroupRealization fegr)
	{
		var p = new Pattern(pattern, fegr);
		COLLECTOR.add(p);
		return p;
	}

	private Pattern(final FEGroupRealizationType.Pattern pattern, final FEGroupRealization fegr)
	{
		this.annosetIDs = Arrays.stream(pattern.getAnnoSetArray()).mapToInt(AnnoSetType::getID).mapToObj(Integer::toString).collect(Collectors.joining(","));
		this.fegr = fegr;
		this.total = pattern.getTotal();
	}

	// A C C E S S

	public String getAnnosetIDs()
	{
		return annosetIDs;
	}

	public FEGroupRealization getFegr()
	{
		return fegr;
	}

	@Override
	public Object getId()
	{
		Integer id = COLLECTOR.get(this);
		if (id != null)
		{
			return id;
		}
		return "NULL";
	}

	// I D E N T I T Y

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Pattern pattern = (Pattern) o;
		return annosetIDs.equals(pattern.annosetIDs) && fegr.equals(pattern.fegr);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(annosetIDs, fegr);
	}

	// I N S E R T

	@Override
	public String dataRow()
	{
		return String.format("%s,%d,%s", getId(), //
				total, //
				fegr.getId());
	}

	@Override
	public String comment()
	{
		return String.format("{%s},{%s}}", annosetIDs, fegr.getFENames());
	}

	@Override
	public String toString()
	{
		return String.format("[GPAT pattern=%s fegr=%s]", annosetIDs, fegr.getFENames());
	}
}
