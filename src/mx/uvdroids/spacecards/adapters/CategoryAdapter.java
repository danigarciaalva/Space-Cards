package mx.uvdroids.spacecards.adapters;

import java.util.ArrayList;

import mx.uvdroids.spacecards.R;
import mx.uvdroids.spacecards.model.Category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<Category>{

	private ArrayList<Category> categories;
	
	public CategoryAdapter(Context context, int resource, ArrayList<Category> objects) {
		super(context, resource, objects);
		this.categories = objects;
	}
	
	@Override
	public int getCount() {
		return this.categories.size();
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_category, null);
		}
		Category c = getItem(position);
		TextView title = (TextView)view.findViewById(R.id.title_category);
		TextView description = (TextView)view.findViewById(R.id.description_category);
		ImageView image = (ImageView)view.findViewById(R.id.image_category);
		title.setText(c.title);
		description.setText(c.description);
		image.setImageDrawable(getContext().getResources().getDrawable(c.image_resource));
		return view;
	}

}
