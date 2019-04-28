package com.cdtu.util;

import java.util.Timer;
import java.util.TimerTask;

import com.cdtu.service.SignInService;

public class MyTimerTask {
	private static TimerTask timerTask = null;

	/**
	 * 开始定时任务
	 *
	 * @author 李红兵
	 */
	public static void start(String psId, int signInTime, SignInService signInService) {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				signInService.stopSignIn(psId);
				cancel();
			}
		};
		new Timer().schedule(timerTask, signInTime * 60 * 1000);
	}

	/**
	 * 取消定时任务
	 *
	 * @author 李红兵
	 */
	public static void cancel() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}
}
