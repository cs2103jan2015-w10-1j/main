//@author A0111830X
package com.done.task;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.done.model.Done;
import com.done.model.DoneTimedTask;

public class TaskReminder {
	Timer timer;


	public TaskReminder(DoneTimedTask done) {

		long timeToRemind = done.getEndTimeLong() - DateTime.now().getMillis();

		timer = new Timer();
		timer.schedule(new DoneReminderTimedTask(done),
				(timeToRemind));
	}

	public TaskReminder(Done done, String remindDate, String remindTime) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMyyyy HHmm");
		DateTime dateTime = dtf.parseDateTime(remindDate + " " + remindTime);
		long currentTime = LocalDateTime.now().getMillisOfDay();
		long endTimeValue = dateTime.getMillisOfDay();

		timer = new Timer();
		timer.schedule(new DoneReminder(done), (endTimeValue - currentTime));
	}

	public void stopTimer() {
		timer.cancel();
	}

	class DoneReminder extends TimerTask {

		private final Done done;

		public DoneReminder(Done done) {
			this.done = done;
		}

		@Override
		public void run() {
			Platform.runLater(new Runnable() {
				public void run() {
					Notifications.create().title("Done! Reminder")
							.text("Reminder for: " + done.getTitle())
							.hideAfter(Duration.INDEFINITE).darkStyle()
							.showWarning();
					timer.cancel();
				}
			});
		}

	}

	class DoneReminderTimedTask extends TimerTask {

		private final DoneTimedTask done;

		public DoneReminderTimedTask(DoneTimedTask done) {
			this.done = done;
		}

		@Override
		public void run() {
			Platform.runLater(new Runnable() {
				public void run() {
					Notifications.create().title("Done! Timed Task")
							.text("Time is up for: " + done.getTitle())
							.hideAfter(Duration.INDEFINITE).darkStyle()
							.showWarning();
					timer.cancel();
				}
			});
		}

	}

}
