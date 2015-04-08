package com.ty.landmine;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
 
/**
 * @author  swufe041
 * @date    2015-4-8
 * @QQ	    84689229
 * @QQGroup 134339070
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "初级").setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(Menu.NONE, Menu.FIRST + 2, 1, "中级").setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(Menu.NONE, Menu.FIRST + 3, 1, "高级").setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Toast.makeText(this, "初级", Toast.LENGTH_LONG).show();
		LandMineView.getLandMineView().setLenSide(9);
		LandMineView.getLandMineView().setLandMineCnt(10);
			break;
		case Menu.FIRST + 2:
			Toast.makeText(this, "中级", Toast.LENGTH_LONG).show();
		LandMineView.getLandMineView().setLenSide(16);
		LandMineView.getLandMineView().setLandMineCnt(40);
		LandMineView.getLandMineView().setTextSize(8);
			break;
		case Menu.FIRST + 3:
			Toast.makeText(this, "高级", Toast.LENGTH_LONG).show();
		LandMineView.getLandMineView().setLenSide(20);
		LandMineView.getLandMineView().setLandMineCnt(69);
		LandMineView.getLandMineView().setTextSize(7);
			break;
		} 
		LandMineView.getLandMineView().setLeftLm((TextView)findViewById(R.id.tv_landmin_left));
		LandMineView.getLandMineView().setUseTime((TextView)findViewById(R.id.tv_timer));
		LandMineView.getLandMineView().initGameView();
		LandMineView.getLandMineView().startGame();
		return false;
	}
	
}
