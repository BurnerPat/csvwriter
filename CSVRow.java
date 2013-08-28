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

public class CSVRow {
	private Object[] data;
	private boolean[] isSet;
	
	CSVRow(CSVHeader header) {
		data = new Object[header.getColumnCount()];
		isSet = new boolean[header.getColumnCount()];
	}
	
	public void set(int index, Object value) throws CSVException {
		if (index < 0 || index >= data.length) {
			throw new CSVException("index out of range: " + index);
		}
		
		data[index] = value;
		isSet[index] = true;
	}
	
	public Object get(int index) throws CSVException {
		if (index < 0 || index >= data.length) {
			throw new CSVException("index out of range: " + index);
		}
		
		if (isSet[index]) {
			return data[index];
		}
		else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				builder.append(',');
			}
			
			if (isSet[i]) {
				boolean isString = (data[i] instanceof String);
				if (isString) {
					builder.append('"');
				}
				
				builder.append(data[i]);
				
				if (isString) {
					builder.append('"');
				}
			}
		}
		return builder.toString();
	}
	
	public void toWriter(Writer writer) throws IOException {
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				writer.write(',');
			}
			
			if (isSet[i]) {
				boolean isString = (data[i] instanceof String);
				if (isString) {
					writer.write('"');
				}
				
				writer.write(String.valueOf(data[i]));
				
				if (isString) {
					writer.write('"');
				}
			}
		}
	}
}
