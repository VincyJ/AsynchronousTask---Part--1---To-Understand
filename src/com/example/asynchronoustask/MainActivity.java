package com.example.asynchronoustask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b=(Button) findViewById(R.id.button1);
		tv=(TextView) findViewById(R.id.textView1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyTask mytask=new MyTask(MainActivity.this, tv, b);
				mytask.execute();
				b.setEnabled(false);			}
		});
	}

	class MyTask extends AsyncTask<Void, Integer, String>
	{
		Context context;
		TextView tv;
		Button b;
		ProgressDialog pd;
		
		public MyTask(Context context,TextView tv,Button b) {
			this.context=context;
			this.tv=tv;
			this.b=b;
		}
		
		@Override
		protected void onPreExecute() {
			
			pd=new ProgressDialog(context);
			pd.setTitle("Download in progress");
			pd.setMax(10);
			pd.setProgress(0);
			pd.setProgressStyle(pd.STYLE_HORIZONTAL);
			pd.show();
			}
		
		@Override
		protected String doInBackground(Void... arg0) {
			int i=0;
			synchronized (this) 
			{
				while(i<10){
				try {
					wait(1500);
					i++;
					publishProgress(i);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				}
				
			}
			
			return "Download Complete";
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			int progress=values[0];
			pd.setProgress(progress);
			tv.setText("Download in progress");
			
		}
		@Override
		protected void onPostExecute(String result) {
			tv.setText(result);
			b.setEnabled(true);
			pd.hide();
		}
		
	}

}
