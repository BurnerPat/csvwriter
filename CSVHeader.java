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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CSVHeader {
	private List<String> columns;
	private HashMap<String, Integer> columnIndexes;
	
	public CSVHeader() {
		columns = new LinkedList<String>();
		columnIndexes = new HashMap<String, Integer>();
	}
	
	public CSVHeader(String... columns) {
		this();
		for (int i = 0; i < columns.length; i++) {
			this.columns.add(columns[i]);
			columnIndexes.put(columns[i], Integer.valueOf(i));
		}
	}
	
	public String[] getColumns() {
		return columns.toArray(new String[columns.size()]);
	}
	
	public int getColumnIndex(String column) {
		Integer index = columnIndexes.get(column);
		if (index != null) {
			return index.intValue();
		}
		else {
			return -1;
		}
	}
	
	public String getColumn(int index) {
		if (index >= 0 && index < columns.size()) {
			return columns.get(index);
		}
		else {
			return null;
		}
	}
	
	public int addColumn(String column) {
		if (columnIndexes.containsKey(column)) {
			return -1;
		}
		else {
			columns.add(column);
			int index = columns.size() - 1;
			columnIndexes.put(column, Integer.valueOf(index));
			return index;
		}
	}
	
	public int getColumnCount() {
		return columns.size();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (String column : columns) {
			if (first) {
				first = false;
			}
			else {
				builder.append(',');
			}
			
			builder.append('"');
			builder.append(column);
			builder.append('"');
		}
		return builder.toString();
	}
	
	public void toWriter(Writer writer) throws IOException {
		boolean first = true;
		for (String column : columns) {
			if (first) {
				first = false;
			}
			else {
				writer.write(',');
			}
			
			writer.write('"');
			writer.write(column);
			writer.write('"');
		}
	}
}
