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

package com.burnerpat.csvwriter.test;

import java.io.IOException;
import java.io.OutputStreamWriter;

import com.burnerpat.csvwriter.CSVException;
import com.burnerpat.csvwriter.CSVWriter;

public class CSVTest {

	public static void main(String[] args) {
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		CSVWriter csv = new CSVWriter(writer);
		
		try {
			csv.setHeader("col1", "col2", "col3", "col4");
			for (int i = 0; i < 100; i++) {
				csv.set(0, 100);
				csv.set(1, 3.14159);
				csv.set(2, "TestString");
				
				if (Math.random() > 0.5) {
					csv.set(3, null);
				}
				csv.next();
			}
			csv.flush();
			writer.flush();
		}
		catch (CSVException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
