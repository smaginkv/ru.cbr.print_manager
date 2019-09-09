package ru.cbr.service;

import java.util.concurrent.locks.ReentrantLock;

public class AverageCount {

	private long totalDuration;
	private long documentsCount;
	private ReentrantLock countLock;

	public AverageCount() {
		countLock = new ReentrantLock();
	}
	public void putPrintingDuration(long duration) {
		countLock.lock();
		try {
			totalDuration = totalDuration + duration;
			documentsCount++;
		} finally {
			countLock.unlock();
		}
	}

	public float getAveragePrintingDurationInSeconds() {
		countLock.lock();
		try {
			float result = (float)totalDuration / documentsCount/1000;
			return result;
		} finally {
			countLock.unlock();
		}
	}

}
