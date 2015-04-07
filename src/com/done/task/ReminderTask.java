package com.done.task;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import org.controlsfx.control.Notifications;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.done.model.Done;
import com.done.model.DoneTimedTask;

public class ReminderTask {
	Timer timer;

	//@author A0111830X
	public ReminderTask(DoneTimedTask done) {

		long startTimeValue = done.getStartTimeLong();
		long endTimeValue = done.getEndTimeLong();

		timer = new Timer();
		timer.schedule(new DoneReminderTimedTask(done),
				(endTimeValue - startTimeValue));

	}

	public ReminderTask(Done done, String remindDate, String remindTime) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMyyyy HH:mm");
		DateTime dateTime = dtf.parseDateTime(remindDate + " " + remindTime);
		long endTimeValue = dateTime.getMillis();

		timer = new Timer();
		timer.schedule(new DoneReminder(done), endTimeValue);
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
					Notifications.create().title("Done! Timed Task")
							.text("Reminder for: " + done.getTitle())
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
							.showWarning();
					timer.cancel();
				}
			});
		}

	}

}
