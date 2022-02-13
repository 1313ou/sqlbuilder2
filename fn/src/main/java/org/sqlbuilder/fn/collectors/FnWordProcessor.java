package org.sqlbuilder.fn.collectors;

import org.apache.xmlbeans.XmlException;
import org.sqlbuilder.common.Logger;
import org.sqlbuilder.common.Progress;
import org.sqlbuilder.fn.FnModule;
import org.sqlbuilder.fn.objects.Word;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import edu.berkeley.icsi.framenet.LexUnitDocument;
import edu.berkeley.icsi.framenet.ValencesType;

public class FnWordProcessor extends FnProcessor
{
	public FnWordProcessor(final Properties props)
	{
		super("lu", props, "w");
	}

	@Override
	protected void processFrameNetFile(final String fileName, final String name)
	{
		if (Logger.verbose)
		{
			Progress.traceHeader("framenet (lu)", name);
		}

		final int count = 0;
		final File file = new File(fileName);
		try
		{
			final LexUnitDocument _document = LexUnitDocument.Factory.parse(file);
			final LexUnitDocument.LexUnit _lexunit = _document.getLexUnit();

			// L E X E M E S

			for (var _lexeme : _lexunit.getLexemeArray())
			{
				Word.make(_lexeme.getName());
			}

			// V A L E N C E S
			// g o v e r n o r s

			final ValencesType _valences = _lexunit.getValences();
			for (var _governor : _valences.getGovernorArray())
			{
				Word.make(_governor.getLemma());
			}
		}
		catch (XmlException | IOException e)
		{
			Logger.instance.logXmlException(FnModule.MODULE_ID, this.tag, "xml-document", fileName, -1, null, "document=[" + fileName + "]", e);
		}
		if (Logger.verbose)
		{
			Progress.traceTailer("framenet (lu) " + name, Integer.toString(count));
		}
	}
}
