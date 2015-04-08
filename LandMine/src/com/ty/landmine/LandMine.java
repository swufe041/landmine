package com.ty.landmine;

import android.R;
import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author  swufe041
 * @date    2015-4-8
 * @QQ	    84689229
 * @QQGroup 134339070
 */
public class LandMine extends FrameLayout {
	private TextView label;//
	private int showNum;//坐标数字
	private Point _location = null;//坐标
	private int _locationType;//坐标类型,(左上角/右上角等)
	private boolean viewAble;//是否可见
	private boolean isLandMine = false;//是否确认为地雷
	private int textSize=28,tvWidth=220;
	public LandMine(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(textSize);
		label.setWidth(tvWidth);
		label.setGravity(Gravity.CENTER); 
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(3, 3, 0, 0);
		addView(label, lp);
		label.performClick();
	}

	public void setNum(int num) {
		showNum = num;
		//label.setTextColor(getResources().getColor(R.color.holo_orange_dark));
		label.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
		if (showNum == 99) {
			label.setText("");
		} else if (showNum == 0) {
			label.setText("");
		} else {
			label.setText("");
		}

		label.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewAble = true;
				
				LandMineView.getLandMineView().setInvisable(1);
				if (!isLandMine) {
					v.setBackgroundColor(getResources().getColor(R.color.holo_blue_dark));
					label.setTextColor(getResources().getColor(R.color.black));
					if (showNum == 0) {
						LandMineView.getLandMineView().openBlankByPoit(_location);
					} else if (showNum == 99) {
						v.setBackgroundColor(getResources().getColor(R.color.holo_red_light));
						LandMineView.getLandMineView().gameOver();
						LandMineView.getLandMineView().landMainFire(_location);
					} else {
						  label.setText(showNum+"");
					}
				}
			}
		});
		label.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (!viewAble) {
					viewAble = true;
					isLandMine = true;
					v.setBackgroundColor(getResources().getColor(R.color.holo_red_light));
					LandMineView.getLandMineView().setLandMineLeft(-1);
					LandMineView.getLandMineView().setInvisable(1);
				} else {
					if (isLandMine) {
						viewAble = false;
						isLandMine = false;
						v.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
						LandMineView.getLandMineView().setLandMineLeft(1);
						LandMineView.getLandMineView().setInvisable(-1);
					}
				}
				return true;
			}
		});
	}

	public int getShowNum() {
		return showNum;
	}

	public void setLocation(Point p) {
		this._location = p;
	}

	public TextView getLabel() {
		return label;
	}

	public void touchView() {
		viewAble = true;
		label.setBackgroundColor(getResources().getColor(R.color.holo_blue_dark));
		if (showNum != 99) {
			if(showNum !=0)
			label.setText(showNum+"");
			label.setTextColor(getResources().getColor(R.color.black));
		}

		LandMineView.getLandMineView().setInvisable(1);
	}

	public void getAllLandMine() {
		viewAble = true;
		isLandMine = true;
		label.setBackgroundColor(getResources().getColor(R.color.holo_red_light));
	}

	public int get_locationType() {
		return _locationType;
	}

	public boolean getViewAble() {
		return viewAble;
	}

	public void set_locationType(int _locationType) {
		this._locationType = _locationType;
	}

	public void setViewAble(boolean viewAble) {
		this.viewAble = viewAble;
	}

	public void setLandMine(boolean isLandMine) {
		this.isLandMine = isLandMine;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
		label.setTextSize(textSize);
	}
	public void setTvWidth(int tvWidth) {
		this.tvWidth = tvWidth;
		label.setWidth(tvWidth);
	}

	public boolean isLandMine() {
		return isLandMine;
	}
	
}
