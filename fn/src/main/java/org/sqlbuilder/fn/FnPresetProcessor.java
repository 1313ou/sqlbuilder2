package org.sqlbuilder.fn;

import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.StringEnumAbstractBase.Table;
import org.sqlbuilder.common.Processor;
import org.sqlbuilder.common.Progress;

import edu.berkeley.icsi.framenet.CoreType;
import edu.berkeley.icsi.framenet.LabelType;
import edu.berkeley.icsi.framenet.POSType;

public class FnPresetProcessor extends Processor
{
	public FnPresetProcessor()
	{
		super("preset");
	}

	@Override
	protected void run()
	{
		Progress.traceHeader("preset framenet tables", "poses coretypes");

		// pos
		for (final String pos : FnPresetProcessor.getPoses())
		{
			final FnPos fnPos = new FnPos(pos);
			FnPos.SET.add(fnPos);
		}
		Progress.trace(1);

		// coretypes
		for (final String coretype : FnPresetProcessor.getCoreTypes())
		{
			final FnCoreType fnCoretype = new FnCoreType(coretype);
			FnCoreType.SET.add(fnCoretype);
		}
		Progress.trace(1);

		// labelitypes
		for (final String labelitype : FnPresetProcessor.getLabelITypes())
		{
			final FnLabelIType labelIType = new FnLabelIType(labelitype);
			FnLabelIType.SET.add(labelIType);
		}
		Progress.trace(1);

		Progress.traceTailer("preset framenet tables", "done");
	}

	public static String[] getValues(final Table types)
	{
		final String[] values = new String[types.lastInt()];
		for (int i = 1; i <= types.lastInt(); i++)
		{
			final StringEnumAbstractBase e = types.forInt(i);
			values[i - 1] = e.toString();
		}
		return values;
	}

	public static String[] getPoses()
	{
		return FnPresetProcessor.getValues(POSType.Enum.table);
	}

	public static String[] getCoreTypes()
	{
		return FnPresetProcessor.getValues(CoreType.Enum.table);
	}

	public static String[] getLabelITypes()
	{
		return FnPresetProcessor.getValues(LabelType.Itype.Enum.table);
	}

	public static void main(final String[] args)
	{
		System.out.println("\nPOSes:");
		for (final String s : FnPresetProcessor.getPoses())
		{
			System.out.println(s);
		}

		System.out.println("\nCORETYPEs:");
		for (final String s : FnPresetProcessor.getCoreTypes())
		{
			System.out.println(s);
		}

		System.out.println("\nLABELITYPEs:");
		for (final String s : FnPresetProcessor.getLabelITypes())
		{
			System.out.println(s);
		}
	}
}
