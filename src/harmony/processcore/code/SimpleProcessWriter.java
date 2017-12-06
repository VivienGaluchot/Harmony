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

package harmony.processcore.code;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import harmony.processcore.process.HrmProcess;
import harmony.processcore.process.ProceduralUnit;
import harmony.processcore.process.ProcessOutput;

public class SimpleProcessWriter implements ProcessWritter {
	
	private final Writer out;
	
	public SimpleProcessWriter(Writer out) {
		this.out = out;
	}
	
	public void write(ProceduralUnit unit) throws IOException {
		out.write("# ProceduralUnit '");
		out.write(unit.getName());
		out.write("'\n");
		out.write(unit.toString());
		out.write(" = {\n");
		write(unit.getOutputProcess(), new HashSet<HrmProcess>());
		out.write("}\n");
		out.flush();
	}
	
	public void write(HrmProcess process) throws IOException {
		out.write("# Process '");
		out.write(process.getName());
		out.write("'\n");
		out.write(process.getComputeUnit().toString());
		out.write(" = {\n");
		write(process, new HashSet<HrmProcess>());
		out.write("}\n");
		out.flush();
	}
	
	public void close() throws IOException {
		out.close();
	}
	
	public void write(HrmProcess process, Set<HrmProcess> processWritten) throws IOException {
		assert process != null : "process can't be null";
		
		if (!processWritten.contains(process)) {
			processWritten.add(process);
			if (process.getInputPattern() != null) {
				for(int i =0; i < process.getInputPattern().size(); i++) {
					ProcessOutput dep = process.getDependencie(i);
					if (dep != null) {
						this.write(dep.getProcess(), processWritten);
					}
				}
			}
			out.write(process.toString());
			out.write(";\n");
		}
	}
	
}
