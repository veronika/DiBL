//  Copyright 2014, J. Pol
//
// This file is part of free software: you can redistribute it and/or modify it under the terms of the
// GNU General Public License as published by the Free Software Foundation.
// 
// This package is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
// the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// 
// See the GNU General Public License for more details. A copy of the GNU General Public License is
// available at <http://www.gnu.org/licenses/>.

function setRangeMax(max) {

	f = document.diagramConfig.pattern;
	f.value = 1;
	f.max = max;
	document.getElementById('nr').innerHTML = 1;
	document.getElementById('max').innerHTML = max;
}

function showRangeValue() {

	document.getElementById('nr').innerHTML = document.diagramConfig.pattern.value;
}

function showFlanders() {

	// export PATH=$PATH:/C/Program\ Files/Java/jre6/bin

	document.getElementById('sh').innerHTML
	= "java -jar dibl-cmd-0.1.2.jar -ext "
	+ document.diagramConfig.ext.value + " '3;2\n"
	+ document.diagramConfig.A1.value + ";"
	+ document.diagramConfig.B1.value + "\n"
	+ document.diagramConfig.A2.value + ";"
	+ document.diagramConfig.B2.value + "\n"
	+ document.diagramConfig.A3.value + ";"
	+ document.diagramConfig.B3.value + "'"
	+ " < input/flanders.svg"
	+ " > output." + document.diagramConfig.ext.value
	;

	document.getElementById('bat').innerHTML
	= "java -jar dibl-cmd-0.1.2.jar -ext "
	+ document.diagramConfig.ext.value + " 3;2^\r\n\r\n"
	+ document.diagramConfig.A1.value + ";"
	+ document.diagramConfig.B1.value + "^\r\n\r\n"
	+ document.diagramConfig.A2.value + ";"
	+ document.diagramConfig.B2.value + "^\r\n\r\n"
	+ document.diagramConfig.A3.value + ";"
	+ document.diagramConfig.B3.value
	+ " < input/flanders.svg"
	+ " > diagram." + document.diagramConfig.ext.value
	;
}

function show() {

	// for bash on Windows:
	// export PATH=$PATH:/C/Program\ Files/Java/jre6/bin

	cmd = "java -jar dibl-cmd-0.1.2.jar "
	+ document.diagramConfig.options.value
	+ " -ext " + document.diagramConfig.ext.value
	;
	io = " < input/PairTraversal/" 
	+ document.diagramConfig.diagramType.value + "/" 
	+ document.diagramConfig.dimensions.value
	+ document.diagramConfig.diagram.value + ".svg > diagram."
	+ document.diagramConfig.ext.value
	;
	pattern = " input/PairTraversal/"
	+ document.diagramConfig.diagramType.value + "/"
	+ document.diagramConfig.dimensions.value + "/"
	+ document.diagramConfig.dimensions.value + "_"
	+ document.diagramConfig.pattern.value + ".txt"
	;
	r1 
	= document.diagramConfig.A1.value + ";"
	+ document.diagramConfig.B1.value + ";"
	+ document.diagramConfig.C1.value + ";"
	+ document.diagramConfig.D1.value
	;
	r2
	= document.diagramConfig.A2.value + ";"
	+ document.diagramConfig.B2.value + ";"
	+ document.diagramConfig.C2.value + ";"
	+ document.diagramConfig.D2.value
	;
	r3
	= document.diagramConfig.A3.value + ";"
	+ document.diagramConfig.B3.value + ";"
	+ document.diagramConfig.C3.value + ";"
	+ document.diagramConfig.D3.value
	;
	r4
	= document.diagramConfig.A4.value + ";"
	+ document.diagramConfig.B4.value + ";"
	+ document.diagramConfig.C4.value + ";"
	+ document.diagramConfig.D4.value
	;
	document.getElementById('sh').innerHTML
	= cmd + " '4;4\n"
	+ r1 + "\n"
	+ r2 + "\n"
	+ r3 + "\n"
	+ r4 + "'" + pattern + io
	;
	document.getElementById('bat').innerHTML
	= cmd + " 4;4^\r\n\r\n"
	+ r1 + "^\r\n\r\n"
	+ r2 + "^\r\n\r\n"
	+ r3 + "^\r\n\r\n"
	+ r4 + pattern + io
	;
}