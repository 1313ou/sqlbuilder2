package org.sqlbuilder.fn;

import org.sqlbuilder.common.Insertable;

import java.util.HashSet;
import java.util.Set;

import edu.berkeley.icsi.framenet.SemTypeType;

public class FnSemType implements Insertable<FnSemType>
{
	public static final Set<FnSemType> SET = new HashSet<>();

	public final SemTypeType type;

	public FnSemType(final SemTypeType type)
	{
		this.type = type;
	}

	@Override
	public String dataRow()
	{
		// Long(1, this.semtype.getID());
		// String(2, this.semtype.getName());
		// String(3, this.semtype.getAbbrev());
		// String(4, this.semtype.getDefinition());
		return null;
	}

	@Override
	public String toString()
	{
		return String.format("[SEM semtypeid=%s name=%s]", this.type.getID(), this.type.getName());
	}
}
