package com.cdtu.util;

import java.util.Timer;
import java.util.TimerTask;

import com.cdtu.service.PublishSignInService;

public class MyTimerTask {
	private static TimerTask timerTask = null;

	/**
	 * 开始定时任务
	 *
	 * @author 李红兵
	 */
	public static void start(String psId, PublishSignInService psService) {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				psService.stopSignIn(psId);
				cancel();
			}
		};
		new Timer().schedule(timerTask, 5 * 60 * 1000);
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
