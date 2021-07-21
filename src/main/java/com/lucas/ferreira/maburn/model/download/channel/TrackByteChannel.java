package com.lucas.ferreira.maburn.model.download.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.IntConsumer;

public class TrackByteChannel implements ReadableByteChannel {

	private final ReadableByteChannel rbc;
	private IntConsumer onRead;
	private Boolean running =true;

	private int totalByteRead;

	public TrackByteChannel(ReadableByteChannel rbc) {
		this.rbc = rbc;
	}

	public IntConsumer getOnRead() {
		return onRead;
	}

	public void setOnRead(IntConsumer onRead) {
		this.onRead = onRead;
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		if (!running)
			waitUntilResume();

		int nRead = rbc.read(dst);
		notifyBytesRead(nRead);
		return nRead;

	}

	private void waitUntilResume() {
		while (!running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
		}
	}

	protected void notifyBytesRead(int nRead) {
		if (nRead <= 0) {
			return;
		}
		totalByteRead += nRead;
		if (onRead != null)
			onRead.accept(totalByteRead);

	}

	@Override
	public boolean isOpen() {
		return rbc.isOpen();
	}

	@Override
	public void close() throws IOException {
		rbc.close();
	}

	public Boolean getRunning() {
		return running;
	}
public void setRunning(Boolean running) {
	this.running = running;
}
}
