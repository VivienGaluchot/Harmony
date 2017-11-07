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

package harmony.dataProcess2.process;

import harmony.dataProcess2.data.DataType;

public class ProcessOutput {
	private Process process;
	private int id;

	public ProcessOutput(Process process, int outputID) {
		if (process == null)
			throw new NullPointerException("computeUnit can't be null");
		if (outputID < 0 || outputID > process.getOutputPattern().size())
			throw new IllegalArgumentException("id out of bounds : " + outputID);
		this.process = process;
		this.id = outputID;
	}

	public Process getProcess() {
		return process;
	}

	public int getId() {
		return id;
	}

	public Object getValue() {
		return process.getValue(id);
	}
	
	public DataType getOutputType() {
		return process.getOutputPattern().getType(id);
	}

	@Override
	public String toString() {
		if (process.getOutputPattern().size() > 1)
			return process.getName() + "[" + id + "]";
		else
			return process.getName();
	}
}