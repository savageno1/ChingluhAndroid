package com.chingluh.android.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.chingluh.android.R;
import com.chingluh.android.data.holder.CompanyViewHolder;
import com.chingluh.android.model.PubCompany;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CompanyAdapter extends BaseAdapter implements OnScrollListener {
	private LayoutInflater layoutInflater;
	private List<PubCompany> alPubCompany;
	private int scrollState = OnScrollListener.SCROLL_STATE_IDLE;
	private List<Runnable> alRunnable = new ArrayList<Runnable>();
	private ListView listView;

	public CompanyAdapter(Context context, ListView listView, List<PubCompany> alPubCompany) {
		this.layoutInflater = LayoutInflater.from(context);
		this.alPubCompany = alPubCompany;
		this.listView = listView;
		this.listView.setOnScrollListener(this);
	}

	@Override
	public int getCount() {
		return this.alPubCompany.size();
	}

	@Override
	public Object getItem(int position) {
		return this.alPubCompany.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.spinner_item_company, parent, false);
			TextView textViewCompanyId = (TextView) convertView.findViewById(R.id.textViewCompanyId);
			TextView textViewCompanySplit = (TextView) convertView.findViewById(R.id.textViewCompanySplit);
			TextView textViewCompanyName = (TextView) convertView.findViewById(R.id.textViewCompanyName);
			PubCompany pubCompany = this.alPubCompany.get(position);
			textViewCompanyId.setText(pubCompany.getCompanyId());
			textViewCompanySplit.setText(":");
			textViewCompanyName.setText(pubCompany.getCompanyName());
		}
		if ((convertView != null) && (convertView.getTag() instanceof CompanyViewHolder)) {
			updateView((CompanyViewHolder) convertView.getTag(), (PubCompany) getItem(position));
		}
		return convertView;
	}

	public void updateView(CompanyViewHolder viewHolder, PubCompany pubCompany) {
		if ((viewHolder != null) & (pubCompany != null)) {
			TextView textViewCompanyId = viewHolder.getTextViewCompanyId();
			TextView textViewCompanySplit = viewHolder.getTextViewCompanySplit();
			TextView textViewCompanyName = viewHolder.getTextViewCompanyName();
			textViewCompanyId.setVisibility(View.VISIBLE);
			textViewCompanyId.setText(pubCompany.getCompanyId());
			textViewCompanySplit.setVisibility(View.VISIBLE);
			textViewCompanySplit.setText(":");
			textViewCompanyName.setVisibility(View.VISIBLE);
			textViewCompanyName.setText(pubCompany.getCompanyName());
		}
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	public void notifyDataSetChanged(final int position) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				final int firstVisiablePosition = CompanyAdapter.this.listView.getFirstVisiblePosition();
				final int lastVisiablePosition = CompanyAdapter.this.listView.getLastVisiblePosition();
				final int relativePosition = position - firstVisiablePosition;
				if ((position >= firstVisiablePosition) && (position <= lastVisiablePosition)) {
					if (CompanyAdapter.this.scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						//當前不在滾動，立刻刷新
						Log.d("Snser", "notifyDataSetChanged position=" + position + " update now");
						updateView((CompanyViewHolder) CompanyAdapter.this.listView.getChildAt(relativePosition).getTag(), (PubCompany) getItem(position));
					} else {
						synchronized (CompanyAdapter.this.alRunnable) {
							//當前正在滾動，等滾動停止再刷新
							Log.d("Snser", "notifyDataSetChanged position=" + position + " update pending");
							CompanyAdapter.this.alRunnable.add(this);
						}
					}
				} else {
					//不在可視範圍內的listitem不需要手動刷新，等其可見時會通過getView自動刷新
					Log.d("Snser", "notifyDataSetChanged position=" + position + " update skip");
				}
			}
		};
		runnable.run();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			//滚动停止
			synchronized (this.alRunnable) {
				final Iterator<Runnable> iterator = this.alRunnable.iterator();
				while (iterator.hasNext()) {
					iterator.next().run();
					iterator.remove();
				}
			}
		}
	}
}
