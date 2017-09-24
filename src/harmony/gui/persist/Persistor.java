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

package harmony.gui.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import harmony.gui.graph.Space;

public abstract class Persistor<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public Class<?> sourceClass;
	
	public static Persistor<?> load(File inputFile) throws IOException, ClassNotFoundException {
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		streamIn = new FileInputStream(inputFile);
		objectinputstream = new ObjectInputStream(streamIn);
		@SuppressWarnings("unchecked")
		Persistor<?> prs = (Persistor<Space>) objectinputstream.readObject();
		objectinputstream.close();
		return prs;
	}
	
	public void persist(File outputFile) throws IOException {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		fout = new FileOutputStream(outputFile);
		oos = new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
	}

	public Persistor(T source) {
		sourceClass = source.getClass();
	}

	public abstract T recreate();

	public abstract void update(T source);
}
