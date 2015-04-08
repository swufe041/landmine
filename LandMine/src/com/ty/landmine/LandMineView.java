package com.ty.landmine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.TextView;
 
/**
 * @author  swufe041
 * @date    2015-4-8
 * @QQ	    84689229
 * @QQGroup 134339070
 */
public class LandMineView extends GridLayout {

	private int lenSide = 6;
	private int landMineCnt = 1;
	private int landMineLeft = 0;
	private int landMineViewWidth, landMineViewHeight;
	public static LandMineView landMineView = null;
	private LandMine[][] landMine = null;
	private int textSize = 28;
	private TextView leftLm, useTime;
	private int timerCnt = 0;
	private Thread timerThread = new Thread(new MyThread());
	private boolean isover = true, startTimer = false;// 计时器开关
	private int invisable = 0;

	public LandMineView(Context context) {
		super(context);
		timerThread.start();
		initGameView();
	}

	public LandMineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		timerThread.start();
		initGameView();
	}

	public LandMineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		timerThread.start();
		initGameView();
	}

	public void initGameView() {
		removeAllViews();
		landMine = null;
		landMineView = this;
		setColumnCount(lenSide);
		setBackgroundColor(0xffbbaaee);
		landMine = new LandMine[lenSide][lenSide];
		int lmWidth = (Math.min(landMineViewWidth, landMineViewHeight) - 5) / lenSide;
		addLandMine(lmWidth, lmWidth);
		// startGame();
	}

	public void addLandMine(int lmWidth, int lmHeight) {
		LandMine lm;
		for (int y = 0; y < lenSide; y++) {
			for (int x = 0; x < lenSide; x++) {
				lm = new LandMine(getContext());
				landMine[x][y] = lm;
				landMine[x][y].setTvWidth(lmWidth);
				addView(lm, lmWidth, lmHeight);

			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		landMineViewWidth = w;
		landMineViewHeight = h;
	}

	/***
	 * 开始游戏,先清空所有表格上的数字
	 */
	public void startGame() {
		startTimer = true;
		landMineLeft = landMineCnt;
		isover = true;
		timerCnt = 0;
		for (int y = 0; y < lenSide; y++) {
			for (int x = 0; x < lenSide; x++) {
				landMine[x][y].setNum(0);
				landMine[x][y].setViewAble(false);
				landMine[x][y].setLandMine(false);
				landMine[x][y].setTextSize(textSize);

				landMine[x][y].set_locationType(getLocationType(x, y));
				landMine[x][y].setLocation(new Point(x, y));
			}
		}
		for (int lm = 0; lm < landMineCnt; lm++) {

			initLandMine();
		}
		if (leftLm != null)
			leftLm.setText(landMineLeft + "");
	}

	/****
	 * 初始化地雷列表
	 */
	public void initLandMine() {
		int offsetX = getRadomNum(), offsetY = getRadomNum();
		while (landMine[offsetX][offsetY].getShowNum() == 99) {// 如果已经有爆炸就不能再添加
			offsetX = getRadomNum();
			offsetY = getRadomNum();
		}
		landMine[offsetX][offsetY].setNum(99);
		if (getLocationType(offsetX, offsetY) == 1) {// 左上角
			setNumPlus(offsetX + 1, offsetY);
			setNumPlus(offsetX + 1, offsetY + 1);
			setNumPlus(offsetX, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 2) {// 右上角
			setNumPlus(offsetX - 1, offsetY);
			setNumPlus(offsetX - 1, offsetY + 1);
			setNumPlus(offsetX, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 3) {// 左下角
			setNumPlus(offsetX, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY);
		} else if (getLocationType(offsetX, offsetY) == 4) {// 右下角
			setNumPlus(offsetX - 1, offsetY);
			setNumPlus(offsetX - 1, offsetY - 1);
			setNumPlus(offsetX, offsetY - 1);
		} else if (getLocationType(offsetX, offsetY) == 5) {// 左边第一列
			setNumPlus(offsetX, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY);
			setNumPlus(offsetX, offsetY + 1);
			setNumPlus(offsetX + 1, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 6) {// 最后一列
			setNumPlus(offsetX - 1, offsetY - 1);
			setNumPlus(offsetX, offsetY - 1);
			setNumPlus(offsetX - 1, offsetY);
			setNumPlus(offsetX - 1, offsetY + 1);
			setNumPlus(offsetX, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 7) {// 第一行
			setNumPlus(offsetX - 1, offsetY);
			setNumPlus(offsetX - 1, offsetY + 1);
			setNumPlus(offsetX, offsetY + 1);
			setNumPlus(offsetX + 1, offsetY + 1);
			setNumPlus(offsetX + 1, offsetY);
		} else if (getLocationType(offsetX, offsetY) == 8) {// 最后一行
			setNumPlus(offsetX - 1, offsetY - 1);
			setNumPlus(offsetX - 1, offsetY);
			setNumPlus(offsetX, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY - 1);
			setNumPlus(offsetX + 1, offsetY);
		} else {
			// 左上
			setNumPlus(offsetX - 1, offsetY - 1);
			// 上
			setNumPlus(offsetX, offsetY - 1);
			// 右上
			setNumPlus(offsetX + 1, offsetY - 1);
			// 左
			setNumPlus(offsetX - 1, offsetY);
			// 右
			setNumPlus(offsetX + 1, offsetY);
			// 左下
			setNumPlus(offsetX - 1, offsetY + 1);
			// 下
			setNumPlus(offsetX, offsetY + 1);
			// 右下
			setNumPlus(offsetX + 1, offsetY + 1);
		}

	}

	/***
	 * 判断是否需要将TextView上的值加一
	 * 
	 * @param offsetX
	 * @param offsetY
	 */
	public void setNumPlus(int offsetX, int offsetY) {
		if (landMine[offsetX][offsetY].getShowNum() != 99) {
			landMine[offsetX][offsetY].setNum(landMine[offsetX][offsetY].getShowNum() + 1);
		}
	}

	private int getRadomNum() {
		return (int) (Math.random() * (lenSide - 1));
	}

	/***
	 * 方便LandMine.java调用
	 * 
	 * @return
	 */
	public static LandMineView getLandMineView() {
		return landMineView;
	}

	/***
	 * 判断是否是需要递归
	 * 
	 * @param offsetX
	 * @param offsetY
	 */
	public void checkBlank(int offsetX, int offsetY) {
		if (!landMine[offsetX][offsetY].getViewAble()) {
			if (landMine[offsetX][offsetY].getShowNum() == 0) {
				landMine[offsetX][offsetY].touchView();
				openBlankByPoit(new Point(offsetX, offsetY));
			} else {
				landMine[offsetX][offsetY].touchView();
			}
		}
	}

	/***
	 * 如果点中方块没有数字也不是地雷则将其附近的所有空白块显示出来
	 * 
	 * @param _location
	 */
	public void openBlankByPoit(Point _location) {
		int offsetX = _location.x;
		int offsetY = _location.y;
		if (getLocationType(offsetX, offsetY) == 1) {// 左上角
			checkBlank(offsetX + 1, offsetY);
			checkBlank(offsetX + 1, offsetY + 1);
			checkBlank(offsetX, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 2) {// 右上角
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX - 1, offsetY + 1);
			checkBlank(offsetX, offsetY + 1);

		} else if (getLocationType(offsetX, offsetY) == 3) {// 左下角
			checkBlank(offsetX, offsetY - 1);
			checkBlank(offsetX + 1, offsetY - 1);
			checkBlank(offsetX + 1, offsetY);
		} else if (getLocationType(offsetX, offsetY) == 4) {// 右下角
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX - 1, offsetY - 1);
		} else if (getLocationType(offsetX, offsetY) == 5) {// 左边第一列
			checkBlank(offsetX, offsetY - 1);
			checkBlank(offsetX + 1, offsetY - 1);
			checkBlank(offsetX + 1, offsetY);
			checkBlank(offsetX, offsetY + 1);
			checkBlank(offsetX + 1, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 6) {// 最后一列
			checkBlank(offsetX - 1, offsetY - 1);
			checkBlank(offsetX, offsetY - 1);
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX - 1, offsetY + 1);
			checkBlank(offsetX, offsetY + 1);
		} else if (getLocationType(offsetX, offsetY) == 7) {// 第一行
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX - 1, offsetY + 1);
			checkBlank(offsetX, offsetY + 1);
			checkBlank(offsetX + 1, offsetY + 1);
			checkBlank(offsetX + 1, offsetY);
		} else if (getLocationType(offsetX, offsetY) == 8) {// 最后一行
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX - 1, offsetY - 1);
			checkBlank(offsetX, offsetY - 1);
			checkBlank(offsetX + 1, offsetY - 1);
			checkBlank(offsetX + 1, offsetY);
		} else {

			checkBlank(offsetX - 1, offsetY - 1);
			checkBlank(offsetX, offsetY - 1);
			checkBlank(offsetX + 1, offsetY - 1);
			checkBlank(offsetX - 1, offsetY);
			checkBlank(offsetX + 1, offsetY);

			checkBlank(offsetX - 1, offsetY + 1);
			checkBlank(offsetX, offsetY + 1);
			checkBlank(offsetX + 1, offsetY + 1);
		}

	}

	/****
	 * 判断方块的位置
	 * 
	 * @param offsetX
	 *            x坐标
	 * @param offsetY
	 *            y坐标
	 * @return
	 */
	public int getLocationType(int offsetX, int offsetY) {

		if ((offsetX - 1) < 0 && (offsetY - 1) < 0) {// 左上角

			return 1;
		} else if ((offsetX + 1) >= lenSide && (offsetY - 1) < 0) {// 右上角

			return 2;
		} else if ((offsetX - 1) < 0 && (offsetY + 1) >= lenSide) {// 左下角

			return 3;
		} else if ((offsetX + 1) >= lenSide && (offsetY + 1) >= lenSide) {// 右下角

			return 4;
		} else if ((offsetX - 1) < 0 && (offsetY - 1) >= 0 && (offsetY + 1) < lenSide) {// 左边第一列

			return 5;
		} else if ((offsetX + 1) >= lenSide && (offsetY - 1) >= 0 && (offsetY + 1) < lenSide) {// 最后一列

			return 6;
		} else if ((offsetY - 1) < 0 && (offsetX - 1) >= 0 && (offsetX + 1) < lenSide) {// 第一行

			return 7;
		} else if ((offsetY + 1) >= lenSide && (offsetX - 1) >= 0 && (offsetX + 1) < lenSide) {// 最后一行
			return 8;
		}

		return 9;
	}

	/****
	 * 点中地雷
	 * 
	 * @param _location
	 *            地雷坐标
	 */
	public void landMainFire(Point _location) {
		isover = false;

		new AlertDialog.Builder(getContext()).setMessage("你好踩中地雷游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startGame();
			}
		}).setCancelable(false).show();
	}

	public void landMainSuccess() {

		isover = false;
		new AlertDialog.Builder(getContext()).setMessage("你好,耗时间  " + timerCnt + "  秒找出全部地雷游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startGame();
			}
		}).setCancelable(false).show();
	}

	/***
	 * 游戏结束显示所有地雷
	 */
	public void gameOver() {
		for (int y = 0; y < lenSide; y++) {
			for (int x = 0; x < lenSide; x++) {
				if (landMine[x][y].getShowNum() == 99) {
					landMine[x][y].getAllLandMine();
				}
			}
		}
	}

	public void setLenSide(int lenSide) {
		this.lenSide = lenSide;
	}

	public void setLandMineCnt(int landMineCnt) {
		this.landMineCnt = landMineCnt;
		landMineLeft = landMineCnt;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public void setLeftLm(TextView leftLm) {
		this.leftLm = leftLm;
	}

	public void setUseTime(TextView useTime) {
		this.useTime = useTime;
	}

	public void setLandMineLeft(int landMineLeft) {
		this.landMineLeft = this.landMineLeft + landMineLeft;
		leftLm.setText(this.landMineLeft + "");

	}

	public void setInvisable(int cnt) {
		this.invisable = this.invisable + cnt;
		if (invisable == lenSide * lenSide) {
			landMainSuccess();
		}
	}

	final Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (startTimer) {
					timerCnt++;
					useTime.setText("" + timerCnt);
				}
			}
			super.handleMessage(msg);
		}
	};

	public class MyThread implements Runnable { // thread
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000); // sleep 1000ms
					if (isover) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				} catch (Exception e) {
				}
			}
		}
	}

}
