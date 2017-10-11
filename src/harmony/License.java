//    Harmony : procedural sound waves generator
//    Copyright (C) 2017  Vivien Galuchot
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package harmony;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class License {
	public static String license = readLicenceFile();

	public static String readLicenceFile() {
		StringBuffer buff = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Ressources.licenseUrl.openStream(), "UTF-8"));
			String currentLine = null;
			if ((currentLine = br.readLine()) != null) {
				buff.append(currentLine);
			}
			while ((currentLine = br.readLine()) != null) {
				buff.append('\n');
				buff.append(currentLine);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}
}
