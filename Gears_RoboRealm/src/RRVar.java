import java.util.Arrays;

////////////////////////////////////////////////////////////////////////////////
// Class RRVar
////////////////////////////////////////////////////////////////////////////////

// <response>
// <BLOB_COUNT>2</BLOB_COUNT>
// <HEIGHT>46,44</HEIGHT>
// <WIDTH>21,20</WIDTH>
// <AREA>966,880</AREA>
// <BOTTOM_LEFT_X>312,377</BOTTOM_LEFT_X>
// <BOTTOM_LEFT_Y>342,342</BOTTOM_LEFT_Y>
// <BOTTOM_RIGHT_X>332,396</BOTTOM_RIGHT_X>
// <BOTTOM_RIGHT_Y>342,342</BOTTOM_RIGHT_Y>
// <TOP_LEFT_X>312,377</TOP_LEFT_X>
// <TOP_LEFT_Y>387,385</TOP_LEFT_Y>
// <TOP_RIGHT_X>332,396</TOP_RIGHT_X>
// <TOP_RIGHT_Y>387,385</TOP_RIGHT_Y>
// </response>

enum VarType { IntType, DoubleType, CornersType}

enum RRVar {
	BLOB_COUNT,
	HEIGHT, WIDTH, AREA,
	// BOTTOM_LEFT_X, BOTTOM_LEFT_Y, 
	// BOTTOM_RIGHT_X, BOTTOM_RIGHT_Y,
	// TOP_LEFT_X, TOP_LEFT_Y, 
	// TOP_RIGHT_X, TOP_RIGHT_Y,
	HARRIS_CORNERS(VarType.CornersType),
	RRVarCount;

	private VarType myVarType;
	private int myCount;
	private int myIntVal[];
	private double myDoubleVal[];
	private Corner[] myCorner;
	public static final RRVar myValues[] = values();

	private void createVal() {
		if (myVarType == VarType.DoubleType) {
			myCount = Consts.gearImageBlobCount;
			myDoubleVal = new double[myCount];
		} else if (myVarType == VarType.IntType) {
			myCount = Consts.gearImageBlobCount;
			myIntVal = new int[myCount];
		} else if (myVarType == VarType.CornersType) {
			myCount = Consts.cornersCount;
			myCorner = new Corner[myCount];
			for (int i=0; i < myCount; i++) {
				myCorner[i] = new Corner();
			}
		}
	}
	public void clearVal() {
		if (myVarType == VarType.DoubleType) {
			for (int i=0; i < myCount; i++)
				myDoubleVal[i] = 0.0;
		} else if (myVarType == VarType.IntType) {
			for (int i=0; i < myCount; i++)
				myIntVal[i] = 0;
		} else if (myVarType == VarType.CornersType) {
			for (int i=0; i < myCount; i++) {
				myCorner[i].setX(0);
				myCorner[i].setY(0);
			}
		}
	}
	private RRVar() {
		this.myVarType = VarType.IntType;
		createVal();
		clearVal();
	}

	private RRVar(VarType varType) {
		this.myVarType = varType;
		createVal();
		clearVal();
	}

	public static RRVar value(int ord) {
		return myValues[ord];
	}

	public static String name(int ord) { // Map from ordinals to enum names.
		return myValues[ord].toString();
	}

	public boolean isDouble() {
		return myVarType == VarType.DoubleType;
	}
	public boolean isInt() {
		return myVarType == VarType.IntType;
	}
	public boolean isCorners() {
		return myVarType == VarType.CornersType;
	}

	public int getInt(int index) {
		if (index >= myCount) {
			return 0;
		} else if (myVarType == VarType.DoubleType) {
			return (int) myDoubleVal[index];
		} else if (myVarType == VarType.IntType) {
			return myIntVal[index];
		} else if (myVarType == VarType.CornersType) {
			return 0;
		} else {
			return 0;
		}
	}
	public void setInt(int val, int index) {
		if (index >= myCount) {
		} else if (myVarType == VarType.DoubleType) {
			myDoubleVal[index] = val;
		} else if (myVarType == VarType.IntType) {
			myIntVal[index] = val;
		} else if (myVarType == VarType.CornersType) {
		}
	}
	public void setCornerX(int val, int index) {
		if (index >= myCount) {
		} else if (myVarType == VarType.DoubleType) {
			myDoubleVal[index] = val;
		} else if (myVarType == VarType.IntType) {
			myIntVal[index] = val;
		} else if (myVarType == VarType.CornersType) {
			myCorner[index].setX(val);
		}
	}
	public void setCornerY(int val, int index) {
		if (index >= myCount) {
		} else if (myVarType == VarType.DoubleType) {
			myDoubleVal[index] = val;
		} else if (myVarType == VarType.IntType) {
			myIntVal[index] = val;
		} else if (myVarType == VarType.CornersType) {
			myCorner[index].setY(val);
		}
	}

	public double getDouble(int index) {
		if (index >= myCount) {
			return 0.0;
		} else if (myVarType == VarType.DoubleType) {
			return myDoubleVal[index];
		} else if (myVarType == VarType.IntType) {
			return (double) myIntVal[index];
		} else if (myVarType == VarType.CornersType) {
			return 0.0;
		} else {
			return 0.0;
		}
	}
	public void setDouble(double val, int index) {
		if (index >= myCount) {
		} else if (myVarType == VarType.DoubleType) {
			myDoubleVal[index] = val;
		} else if (myVarType == VarType.IntType) {
			myIntVal[index] = (int)val;
		} else if (myVarType == VarType.CornersType) {
		}
	}
	
	public Corner[] getCorners() {
		if (myVarType == VarType.DoubleType) {
			return null;
		} else if (myVarType == VarType.IntType) {
			return null;
		} else if (myVarType == VarType.CornersType) {
			return myCorner;
		} else {
			return null;
		}
	}
	
	public void sortByX() {
		if (myVarType == VarType.CornersType) {
			// Put corners in increasing X order
			Arrays.sort(myCorner, Corner.CornerXComparator);
		}
	}
	
	public void print() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.toString());
		sb.append(" == ");
		for (int i=0; i < myCount - 1; i++) {
			String aValue;
			if (myVarType == VarType.DoubleType) {
				aValue = Double.toString(myDoubleVal[i]);
			} else if (myVarType == VarType.IntType) {
				aValue = Integer.toString(myIntVal[i]);
			} else if (myVarType == VarType.CornersType) {
				aValue = myCorner[i].toString();
			} else {
				aValue = "";
			}
			sb.append(aValue);
			sb.append(",");
		}

		if (myVarType == VarType.DoubleType) {
			sb.append(Double.toString(myDoubleVal[myCount - 1]));
		} else if (myVarType == VarType.IntType) {
			sb.append(Integer.toString(myIntVal[myCount - 1]));
		} else if (myVarType == VarType.CornersType) {
			sb.append(myCorner[myCount - 1].toString());
		}
		System.out.println(new String(sb));
	}
	
	public static void  printAll() {
		for (RRVar var : RRVar.values()) {
			var.print();
		}
	}
	
	public static void clear() {
		for (RRVar var : RRVar.values()) {
			var.clearVal();
		}
	}
}

