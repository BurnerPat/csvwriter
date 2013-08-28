/* Copyright 2013 BurnerPat (https://github.com/BurnerPat)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.burnerpat.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class CSVWriter {
	private final Writer writer;
	private char[] lineDelim = new char[] {'\n'};
	
	private CSVHeader header;
	private List<CSVRow> rows;
	private CSVRow pointer;
	
	private boolean closed;
	
	public CSVWriter(Writer writer) {
		closed = false;
		rows = new LinkedList<CSVRow>();
		this.writer = writer;
	}
	
	public CSVWriter(Writer writer, char... lineDelim) {
		this(writer);
		this.lineDelim = lineDelim;
	}
	
	public char[] getLineDelim() {
		return lineDelim;
	}
	
	public void setLineDelim(char... lineDelim) {
		this.lineDelim = lineDelim;
	}
	
	public void setHeader(String... columns) throws CSVException {
		if (closed) {
			throw new CSVException("cannot set header: CSVWriter is already closed");
		}
		else {
			header = new CSVHeader(columns);
		}
	}
	
	public int addColumn(String column) throws CSVException {
		if (closed) {
			throw new CSVException("cannot add column: CSVWriter is already closed");
		}
		else {
			if (header == null) {
				header = new CSVHeader();
			}
			
			return header.addColumn(column);
		}
	}
	
	public void set(int index, Object value) throws CSVException {
		if (pointer == null) {
			pointer = new CSVRow(header);
			rows.add(pointer);
			closed = true;
		}
		
		pointer.set(index, value);
	}
	
	public void set(String column, Object value) throws CSVException {
		int index = header.getColumnIndex(column);
		
		if (index < 0) {
			throw new CSVException("unknown column: " + column);
		}
		else {
			set(index, value);
		}
	}
	
	public void next() {
		if (pointer == null) {
			pointer = new CSVRow(header);
			rows.add(pointer);
			closed = true;
		}
		
		pointer = new CSVRow(header);
		rows.add(pointer);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(header.toString());
		builder.append(lineDelim);
		
		boolean first = true;
		for (CSVRow row : rows) {
			if (first) {
				first = false;
			}
			else {
				builder.append(lineDelim);		
			}
			
			builder.append(row.toString());
		}
		
		return builder.toString();
	}
	
	public void flush() throws IOException {
		header.toWriter(writer);
		writer.write(lineDelim);
		
		boolean first = true;
		for (CSVRow row : rows) {
			if (first) {
				first = false;
			}
			else {
				writer.write(lineDelim);		
			}
			
			row.toWriter(writer);
		}
	}
}
