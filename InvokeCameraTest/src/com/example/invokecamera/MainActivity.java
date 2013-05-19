package com.example.invokecamera;

import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int CAMERA_PIC_REQUEST = 1337;  
	private Button determineTest;
	private Button malariaTest;
	private Button multiplexTest;
	private Button customTest;
	private CheckBox retakeOption;
	private CheckBox enableCustInput;
	private EditText userInputBox;
	private String userInput;
	private boolean retake;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		retake = false;
		userInputBox = (EditText) findViewById(R.id.userInputBox);
		userInputBox.setEnabled(false);
		
		determineTest = (Button) findViewById(R.id.determine_test);
		determineTest.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 
            	 // Test dimensions for Determine Test
            	 int dim[] = {2214, 426, 738, 142, 1107, 213};
            	 createIntent(dim);
             }
         });
		
		malariaTest = (Button) findViewById(R.id.malaria_test);
		malariaTest.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 
            	 // Test dimensions for Malaria Test
            	 int dim[] = {1647, 463, 549, 154, 824, 232};
            	 createIntent(dim);
             }
         });
		
		multiplexTest = (Button) findViewById(R.id.multiplex_test);
		multiplexTest.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 
            	 // Test dimensions for Multiplex Test
            	 int dim[] = {1300, 1245, 325, 311, 650, 623};
            	 createIntent(dim);
             }
         });

		customTest = (Button) findViewById(R.id.customShape);
		customTest.setEnabled(false);
		customTest.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// convert the user input to an integer array
				userInput = ((TextView)userInputBox).getText().toString();
				String[] inputArray = userInput.trim().split("[,\\s]+");
				if (inputArray.length == 6) {
					int dim[] = new int[inputArray.length];
					boolean error = false;
					
					for (int i = 0; i < inputArray.length; i++) {
						try {
							dim[i] = Integer.parseInt(inputArray[i]);
						} catch(NumberFormatException e) {
							error = true;
							break;
						}
					}
					if (!error)
						createIntent(dim);
				}
			}
		});
		
		retakeOption = (CheckBox) findViewById(R.id.retakeBox);
		
		enableCustInput = (CheckBox) findViewById(R.id.enableCustInput);
		enableCustInput.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (enableCustInput.isChecked()) {
					userInputBox.setEnabled(true);
					customTest.setEnabled(true);
				} else {
					userInputBox.setEnabled(false);
					customTest.setEnabled(false);
				}
			}
		});
	}
	
	public void createIntent(int array[]) {
	     // Perform action on click
		 if (retakeOption.isChecked())
			 retake = true;
		 else 
			 retake = false;
	   	 
		 try {
		   	 Intent cameraIntent = new Intent(); 
		   	 cameraIntent.setComponent(new ComponentName("com.example.odkcamera","com.example.odkcamera.TakePicture"));
		   	 // pass the array of test dimensions, the saved photo directory, and a boolean for the retake button
		   	 cameraIntent.putExtra("retakeOption", retake);
		   	 cameraIntent.putExtra("dimensions", array);
		   	 cameraIntent.putExtra("filePath", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "ODKPictures");
		   	 startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		 } catch (ActivityNotFoundException e) {
			 // launching the TestShapeGenerator app failed
			 e.printStackTrace();
		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (requestCode == CAMERA_PIC_REQUEST) {  
	    	if (resultCode == RESULT_OK && data != null) {
	    		System.out.println("The picture was saved at: " + data.getStringExtra(android.provider.MediaStore.EXTRA_OUTPUT));
	    	}
	    }  
	}  
}
