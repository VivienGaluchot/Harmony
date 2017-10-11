package harmony.gui;

import harmony.gui.graph.DrawPanel;

/* Inspired from https://stackoverflow.com/questions/6260744/how-do-i-set-up-a-jframe-with-a-refresh-rate */

class AnimationRunner extends Thread {
	private boolean shouldStop;
	private boolean pause;
	private DrawPanel panel;

	public AnimationRunner(DrawPanel panel) {
		this.panel = panel;
	}

	public void shouldStop() {
		this.shouldStop = true;
	}

	public void pauseAnimation() {
		pause = true;
	}

	public void resumeAnimation() {
		pause = false;
	}

	@Override
	public void run() {
		shouldStop = false;
		pause = false;
		while (!shouldStop) {
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!pause) {
				panel.repaint();
			}
		}
	}

	public boolean isPaused() {
		return pause;
	}
}