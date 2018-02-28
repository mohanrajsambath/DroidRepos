package org.next.equmed.uil;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import org.next.equmed.bal.BusinessAccessLayer;

public class ExceptionHandler implements
		Thread.UncaughtExceptionHandler {
	private final Activity myContext;
	private final String LINE_SEPARATOR = "\n";

	public ExceptionHandler(Activity context) {
		myContext = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		
		
		StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		StringBuilder errorReport = new StringBuilder();
		errorReport.append("************ CAUSE OF ERROR ************\n\n");
		errorReport.append(stackTrace.toString());

		errorReport.append("\n************ DEVICE INFORMATION ***********\n");
		errorReport.append("Brand: ");
		errorReport.append(Build.BRAND);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Device: ");
		errorReport.append(Build.DEVICE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Model: ");
		errorReport.append(Build.MODEL);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Id: ");
		errorReport.append(Build.ID);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Product: ");
		errorReport.append(Build.PRODUCT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("\n************ FIRMWARE ************\n");
		errorReport.append("SDK: ");
		errorReport.append(Build.VERSION.SDK_INT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Release: ");
		errorReport.append(Build.VERSION.RELEASE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Incremental: ");
		errorReport.append(Build.VERSION.INCREMENTAL);
		errorReport.append(LINE_SEPARATOR);

		String bug_class = BusinessAccessLayer.bug_class_name;
		if(bug_class.equals("Login")) {
			Intent intent = new Intent(myContext, LoginActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("Dashboard"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("InstallationDetails"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("EquipmentEnrollFragment"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("HospitalEnrollFragment"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("MasterActivity"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("MedicalEquipmentFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("MedicalEquipmentActivity"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("UserMasterActivity"))
		{
			Intent intent = new Intent(myContext, UserMasterActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("ViewMedicalEquipment"))
		{
			Intent intent = new Intent(myContext, DashBoardActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("ServiceFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("TrainingFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("WarrantyFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("ConsumablesFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}
		else if(bug_class.equals("VoiceOfCustomerFragment"))
		{
			Intent intent = new Intent(myContext, MedicalEquipmentActivity.class);
			myContext.startActivity(intent);
		}


		System.out.println("Error Log: "+errorReport.toString());

		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}

}