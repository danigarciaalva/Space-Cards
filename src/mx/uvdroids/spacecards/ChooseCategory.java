package mx.uvdroids.spacecards;

import java.util.ArrayList;

import mx.uvdroids.spacecards.adapters.CategoryAdapter;
import mx.uvdroids.spacecards.model.Category;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChooseCategory extends Activity implements OnItemClickListener{
	public static final String LEVEL = "level";
	public static final int LEVEL_EASY = 0;
	public static final int LEVEL_MEDIUM = 1;
	public static final int LEVEL_HARD = 2;
	public static final int LEVEL_EXPERT = 3;
	
	private ListView categories_list;
	private ArrayList<Category> categories;
	private Context context = this;
	
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		getActionBar().hide();
		categories = new ArrayList<Category>();
		String[] titles = getResources().getStringArray(R.array.titles_categories);
		String[] descriptions = getResources().getStringArray(R.array.descriptions_categories);
		TypedArray images = getResources().obtainTypedArray(R.array.images_categories);
		Category c = null;
		for(int i = 0; i < titles.length; i++){
			c = new Category();
			c.title = titles[i];
			c.description = descriptions[i];
			c.image_resource = images.getResourceId(i, -1);
			categories.add(c);
		}
		System.out.println(titles.length);
		categories_list = (ListView)findViewById(R.id.categories_list);
		categories_list.setAdapter(new CategoryAdapter(this, R.layout.item_category, categories));
		categories_list.setOnItemClickListener(this);
	}

	int level = 0;
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		String[] levels = getResources().getStringArray(R.array.level_options);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.choose_level));
		builder.setItems(levels, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				level = which;
				Intent i = new Intent(context, PlayActivity.class);
				i.putExtra(LEVEL, level);
				startActivity(i);
				finish();
			}
		});
		builder.show();
	}
	
}
