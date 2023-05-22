package ir.alidev.fasttype;

import android.app.*;
import android.content.*;
import android.os.*;

public class InfoActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}

	@Override
	public void onBackPressed()
	{
		Intent i = new Intent(InfoActivity.this, StartActivity.class);
		startActivity(i);
		InfoActivity.this.finish();
	}
}
