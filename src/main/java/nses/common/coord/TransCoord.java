package nses.common.coord;

public class TransCoord {
	public static final int COORD_TYPE_TM = 1;
	public static final int COORD_TYPE_KTM = 2;
	public static final int COORD_TYPE_UTM = 3;
	public static final int COORD_TYPE_CONGNAMUL = 4;
	public static final int COORD_TYPE_WGS84 = 5;
	public static final int COORD_TYPE_BESSEL = 6;
	public static final int COORD_TYPE_WTM = 7;
	public static final int COORD_TYPE_WKTM = 8;
	public static final int COORD_TYPE_WCONGNAMUL = 10;
	public static final int COORD_TYPE_TM_WEST = 11;
	public static final int COORD_TYPE_TM_EAST = 12;
	public static final int COORD_TYPE_TM_CENTER = 13;
	public static final double BASE_TM_LON = 129.0D;
	public static final double BASE_TM_LAT = 38.0D;
	public static final double BASE_KTM_LON = 128.0D;
	public static final double BASE_KTM_LAT = 38.0D;
	public static final double BASE_UTM_LON = 129.0D;
	public static final double BASE_UTM_LAT = 0.0D;
	private static final int[][] COORD_BASE = {
		new int[2], 
		{ 129, 38 },
		{ -1, -1 }, 
		{ 129 }, 
		{ -1, -1 }, 
		{ -1, -1 }, 
		{ -1, -1 },
		{ 127, 38 }, 
		{ -1, -1 }, 
		new int[2], 
		{ -1, -1 }, 
		{ 125, 38 }, 
		{ 129, 38 }, 
		{ 127, 38 }, 
		};

	public static CoordPoint getTransCoord(
			CoordPoint inPoint, 
			int fromType,
			int toType) {
		return convertCoord(
				inPoint, 
				fromType, 
				toType, 
				COORD_BASE[fromType][0],
				COORD_BASE[fromType][1], 
				COORD_BASE[toType][0],
				COORD_BASE[toType][1]);
	}

	private static CoordPoint convertCoord(
			CoordPoint point, 
			int fromType,
			int toType, 
			double frombx, 
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = null;
		double bx = frombx;

		switch (fromType) {
		case COORD_TYPE_TM:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 38.0D;
			}
			transPt = convertTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_KTM:
			if (frombx <= 0.0D) {
				bx = 128.0D;
				fromby = 38.0D;
			}
			transPt = convertKTM2(point, toType, tobx, toby);
			break;
		case COORD_TYPE_UTM:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 0.0D;
			}
			transPt = convertUTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertCONGNAMUL2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_WGS84:
			transPt = convertWGS2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_BESSEL:
			transPt = convertBESSEL2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_WTM:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertWTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			if (frombx <= 0.0D) {
				bx = 128.0D;
				fromby = 38.0D;
			}
			transPt = convertWKTM2(point, toType, bx, frombx, tobx, toby);
			break;
		case COORD_TYPE_WCONGNAMUL:
			if (frombx <= 0.0D) {
				bx = 127.0D;
				fromby = 38.0D;
			}
			transPt = convertWCONGNAMUL2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_TM_WEST:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 38.0D;
			}
			transPt = convertTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_TM_EAST:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 38.0D;
			}
			transPt = convertTM2(point, toType, bx, fromby, tobx, toby);
			break;
		case COORD_TYPE_TM_CENTER:
			if (frombx <= 0.0D) {
				bx = 129.0D;
				fromby = 38.0D;
			}
			transPt = convertTM2(point, toType, bx, fromby, tobx, toby);
			break;
		}

		return transPt;
	}

	private static CoordPoint convertTM2(
			CoordPoint point, 
			int toType,
			double frombx, 
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertTM2BESSEL(frombx, fromby);
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertTM2BESSEL(frombx, fromby);
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
			break;
		}

		return transPt;
	}

	private static CoordPoint convertKTM2(
			CoordPoint point, 
			int toType,
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();

		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertKTM2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertKTM2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
			break;
		}
		return transPt;
	}

	private static CoordPoint convertUTM2(
			CoordPoint point, 
			int toType, 
			double frombx,
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();

		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertUTM2WGS(frombx, fromby);
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertUTM2WGS(frombx, fromby);
			transPt.convertWGS2WCONG();
			break;
		}
		return transPt;
	}

	private static CoordPoint convertCONGNAMUL2(
			CoordPoint point, 
			int toType,
			double frombx, 
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();

		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			break;
		case COORD_TYPE_WGS84:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertCONG2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertCONG2BESSEL();
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
			break;
		}

		return transPt;
	}

	private static CoordPoint convertWGS2(
			CoordPoint point, 
			int toType, 
			double frombx,
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertWGS2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertWGS2WCONG();
			break;
		case COORD_TYPE_TM_WEST:
			if (frombx <= 0.0D) {
				frombx = 125.0D;
				fromby = 38.0D;
			}
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_TM_EAST:
			if (frombx <= 0.0D) {
				frombx = 129.0D;
				fromby = 38.0D;
			}
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_TM_CENTER:
			if (frombx <= 0.0D) {
				frombx = 127.0D;
				fromby = 38.0D;
			}
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		}

		return transPt;
	}

	private static CoordPoint convertBESSEL2(
			CoordPoint point, 
			int toType, 
			double frombx,
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertBESSEL2WGS();
			transPt.convertWGS2WCONG();
			break;
		}

		return transPt;
	}

	private static CoordPoint convertWTM2(
			CoordPoint point, 
			int toType, 
			double frombx,
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertWTM2WGS(frombx, fromby);
			transPt.convertWGS2WCONG();
			break;
		}

		return transPt;
	}

	private static CoordPoint convertWKTM2(
			CoordPoint point, 
			int toType, 
			double frombx,
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			transPt.convertWKTM2WGS();
			transPt.convertWGS2WCONG();
			break;
		}

		return transPt;
	}

	private static CoordPoint convertWCONGNAMUL2(
			CoordPoint point, 
			int toType,
			double frombx, 
			double fromby, 
			double tobx, 
			double toby) {
		CoordPoint transPt = point.clone();
		switch (toType) {
		case COORD_TYPE_TM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2TM(tobx, toby);
			break;
		case COORD_TYPE_KTM:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2KTM();
			break;
		case COORD_TYPE_UTM:
			if (tobx <= 0.0D) {
				tobx = 129.0D;
				toby = 0.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2UTM(tobx, toby);
			break;
		case COORD_TYPE_CONGNAMUL:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2CONG();
			break;
		case COORD_TYPE_WGS84:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			transPt.convertBESSEL2WGS();
			break;
		case COORD_TYPE_BESSEL:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2BESSEL();
			break;
		case COORD_TYPE_WTM:
			if (tobx <= 0.0D) {
				tobx = 127.0D;
				toby = 38.0D;
			}
			transPt.convertWCONG2WGS();
			transPt.convertWGS2WTM(tobx, toby);
			break;
		case COORD_TYPE_WKTM:
			transPt.convertWCONG2WGS();
			transPt.convertWGS2WKTM();
			break;
		case COORD_TYPE_WCONGNAMUL:
			break;
		}
		return transPt;
	}
}