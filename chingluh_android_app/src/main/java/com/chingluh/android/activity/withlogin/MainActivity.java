/**
 *
 */
package com.chingluh.android.activity.withlogin;

import android.os.Bundle;

import com.chingluh.android.R;
import com.chingluh.android.base.BaseActivityWithLogin;

/**
 * @author Ray
 */
public class MainActivity extends BaseActivityWithLogin{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
