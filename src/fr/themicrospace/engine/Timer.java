package fr.themicrospace.engine;

public class Timer extends Attribute<Integer> {

	private int t0 = 0;
	private boolean ready = false;
	private boolean started = false;

	public Timer(Integer attribute) {
		super(attribute, "Timer");
	}

	public void start() {
		started = true;
	}

	public void update() {
		if (!ready && started)
			t0++;
		if (t0 >= attribute) {
			ready = true;
		}
	}

	public boolean isReady() {
		return ready;
	}

	public void reset() {
		t0 = 0;
		ready = false;
		started = false;
	}
	
	public boolean isStarted() {
		return started;
	}

}
