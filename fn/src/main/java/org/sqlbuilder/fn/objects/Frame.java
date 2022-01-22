package org.sqlbuilder.fn.objects;

import org.sqlbuilder.common.Insertable;
import org.sqlbuilder.common.Utils;
import org.sqlbuilder.fn.HasID;
import org.sqlbuilder.fn.collectors.FnFrameXmlProcessor;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import edu.berkeley.icsi.framenet.FrameDocument;

/*
frames.table=fnframes
frames.create=CREATE TABLE IF NOT EXISTS %Fn_frames.table% ( frameid INTEGER NOT NULL,frame VARCHAR(40),framedefinition TEXT,cdate VARCHAR(27),cby VARCHAR(5),PRIMARY KEY (frameid) );
frames.insert=INSERT INTO %Fn_frames.table% (frameid,frame,framedefinition,cdate,cby) VALUES(?,?,?,?,?);
 */
public class Frame implements HasID, Insertable<Frame>
{
	public static final Set<Frame> SET = new HashSet<>();

	private static final FnFrameXmlProcessor definitionProcessor = new FnFrameXmlProcessor();

	public final FrameDocument.Frame frame;

	public final String definition;

	public Frame(final FrameDocument.Frame frame) throws IOException, SAXException, ParserConfigurationException
	{
		super();
		this.frame = frame;
		try
		{
			this.definition = Frame.definitionProcessor.process(frame.getDefinition());
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			System.err.println(frame.getDefinition());
			throw e;
		}
	}

	public long getID()
	{
		return frame.getID();
	}

	@Override
	public String dataRow()
	{
		return String.format("%d,'%s','%s'", //
				frame.getID(), //
				Utils.escape(frame.getName()), //
				Utils.escape(definition));
		// String(4, this.frame.getCDate());
		// String(5, this.frame.getCBy());
	}

	@Override
	public String toString()
	{
		return String.format("[FRAME id=%s name=%s]", frame.getID(), frame.getName());
	}
}
