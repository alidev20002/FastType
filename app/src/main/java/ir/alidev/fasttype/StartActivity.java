package ir.alidev.fasttype;

import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.view.*;
import android.widget.*;
import android.*;
import android.content.pm.*;

public class StartActivity extends Activity
{
	
	Button play, info, rate, other, setph, delph;
	String path = "empty";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		ui();
		
		play.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					Intent intent = new Intent(StartActivity.this, MainActivity.class);
					intent.putExtra("path", path);
					startActivity(intent);
					StartActivity.this.finish();
				}
		});
		
		setph.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 				
					int RESULT_LOAD_IMAGE = 0;
					startActivityForResult(intent, RESULT_LOAD_IMAGE); 	
					getPermission();
				}

				private void getPermission()
				{
					// TODO: Implement this method
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
							requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
						}
					}
				}
		});
		
		delph.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
					SharedPreferences.Editor editor = shared.edit();
					editor.putString("path", "empty");
					path = "empty";
					editor.apply();
				}
		});
		
		info.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					Intent i = new Intent(StartActivity.this, InfoActivity.class);
					startActivity(i);
					StartActivity.this.finish();
				}
		});
		
		rate.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					String url= "myket://comment?id=ir.alidev.fasttype";
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(url));
					startActivity(intent);
				}
		});
		
		other.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					String url= "myket://developer/ir.alidev.fasttype";
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(url));
					startActivity(intent);
				}
				
		});
}

	private void ui()
	{
		play = findViewById(R.id.play);
		info = findViewById(R.id.info);
		rate = findViewById(R.id.rate);
		other = findViewById(R.id.other);
		setph = findViewById(R.id.setph);
		delph = findViewById(R.id.delph);
		
		getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		ActionBar ac = getActionBar();
		ac.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#888888")));
		ac.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
		if (shared.contains("path")) {
			path = shared.getString("path", null);
		}
	}

	@Override 	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 		
		super.onActivityResult(requestCode, resultCode, data); 		 		 		
		int RESULT_LOAD_IMAGE = 0;
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{ 			
			Uri selectedImage = data.getData(); 			
			String[] filePathColumn = { MediaStore.Images.Media.DATA }; 			
			Cursor cursor = getContentResolver().query(selectedImage, 					
													   filePathColumn, null, null, null); 			
			cursor.moveToFirst(); 			
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]); 			
			String picturePath = cursor.getString(columnIndex); 			
			cursor.close(); 
			path = picturePath;
			SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("path", path);
			editor.apply();
		} 		 		 
	}
}
