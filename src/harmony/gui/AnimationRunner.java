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

package harmony.gui;

import java.util.concurrent.Semaphore;

import harmony.gui.graph.DrawPanel;

/* Inspired from https://stackoverflow.com/questions/6260744/how-do-i-set-up-a-jframe-with-a-refresh-rate */

public class AnimationRunner extends Thread {
	private boolean shouldStop;

	private DrawPanel panel;

	private final Semaphore runSem = new Semaphore(1);

	private float desiredFrameRate;
	private float trueFrameRate;

	public AnimationRunner(DrawPanel panel) {
		this.panel = panel;
		desiredFrameRate = 60;
		trueFrameRate = 0;
	}

	public void shouldStop() {
		this.shouldStop = true;
	}

	public synchronized void pauseAnimation() {
		try {
			runSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void resumeAnimation() {
		runSem.release();
	}

	public synchronized boolean isPaused() {
		return runSem.availablePermits() == 0;
	}

	@Override
	public void run() {
		long repaintTime = 0;
		long cycleTime = 0;
		long lastRepaintStart = System.currentTimeMillis();
		long desiredRepaintPeriod = (long) (1000.0 / desiredFrameRate);
		shouldStop = false;
		try {
			while (!shouldStop) {
				runSem.acquire();

				lastRepaintStart = System.currentTimeMillis();
				panel.repaint();
				repaintTime = System.currentTimeMillis() - lastRepaintStart;
				if (desiredRepaintPeriod > repaintTime)
					Thread.sleep(desiredRepaintPeriod - repaintTime);
				cycleTime = System.currentTimeMillis() - lastRepaintStart;

				trueFrameRate = (float) (1000.0 / cycleTime) * 0.1f + trueFrameRate * 0.9f;

				runSem.release();
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public float getDesiredFrameRate() {
		return desiredFrameRate;
	}

	public void setDesiredFrameRate(float desiredFrameRate) {
		this.desiredFrameRate = desiredFrameRate;
	}

	public float getTrueFrameRate() {
		return trueFrameRate;
	}
}