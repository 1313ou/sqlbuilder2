package org.sqlbuilder.pb;

import java.util.*;

public class PbTheta implements Comparable<PbTheta>
{
	protected static final Set<PbTheta> SET = new HashSet<>();

	public static Map<PbTheta,Integer> MAP;

	private static String normalize(final String thetaName)
	{
		return thetaName.substring(0, 1).toUpperCase() + thetaName.substring(1).toLowerCase();
	}

	private final String pbtheta;

	// C O N S T R U C T O R

	public PbTheta(final String thetaName)
	{
		this.pbtheta = normalize(thetaName);
		SET.add(this);
	}

	// O R D E R

	@Override
	public int compareTo(final PbTheta that)
	{
		return pbtheta.compareTo(that.pbtheta);
	}
}
