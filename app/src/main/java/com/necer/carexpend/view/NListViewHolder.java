package com.necer.carexpend.view;

import android.util.SparseArray;
import android.view.View;

/**
 * @Description:万能的viewHolder
 */
public class NListViewHolder {

	    public static <T extends View> T getView(View view, int id) {
	        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
	        if (viewHolder == null) {
	            viewHolder = new SparseArray<View>();
	            view.setTag(viewHolder);
	        }
	        View childView = viewHolder.get(id);
	        if (childView == null) {
	            childView = view.findViewById(id);
	            viewHolder.put(id, childView);
	        }
	        return (T) childView;
	    }

}
