package ir.alidev.fasttype;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ir.alidev.fasttype.*;
import ir.tapsell.sdk.*;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity 
{
	RelativeLayout layout;
	Handler handler;
	String[] Fawords_easy, Enwords, colors;
	int screen_width, screen_height;
	List<TextView> allwords;
	int id = 0;
	EditText et;
	Button bt;
	TextView line, yourlevel, best;
	int level = 0;
	int kills = 0;
	int BEST = 0;
	int stop = 1, ad = 0;
	boolean reward_ready;
	MediaPlayer main, send, levelup, gameover;
	int sum;
	NumberFormat nf;
	String path;
	TapsellAd tapAd = null;
	final String appKey = "fsckpohbnsidhjpaghischmcieotgrdkbjsbbpjcrldrtndctdbmetmhhhrnfqifpdcaro";
	final String adKey = "60575a3441cb510001356e3d";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		start();
		ui();
		loadAd();
		loadGame();
		
		bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					if (stop == 1 && et.getText().toString().length() > 0) {
						
					TextView test = allwords.get(0);
					
					if (test.getText().toString().contains("+")) {
						String numb = test.getText().toString();
						String[] numbers = numb.split("\\+");
						int s1 = Integer.parseInt(numbers[0]);
						int s2 = Integer.parseInt(numbers[1]);
						sum = s1 + s2;
						
					}
					
						if ((et.getText().toString().equals(test.getText().toString()) || et.getText().toString().equals(String.valueOf(sum)) || et.getText().toString().equals(String.valueOf(nf.format(sum)))) && test.getY() < (line.getY())) {
							send.start();
							allwords.remove(0);
							layout.removeView(test);
							et.setText("");
							kills++;
							if (kills == 3) {
								levelup.start();
								level++;
								kills = 0;
								saveGame();
								yourlevel.setText("مرحله:" + level);
								best.setText("بهترین:" + BEST);
							}
						}
					}
				}
			
		});
		
		new Thread(new Runnable() {

				@Override
				public void run()
				{
					CreateWord();
					CreateLine();
					while(stop == 1) {
						try
						{
							
							if (level % 7 == 0 && level != 0) {
								Thread.sleep(8000 + (level * 50));
								if (stop == 1) {
								levelup.start();
								level++;
								saveGame();
								}
							}else{
								
								Thread.sleep(5000 + (level * 10));
							}
							handler.post(new Runnable() {

									@Override
									public void run()
									{
										CreateWord();
									}
								});
						}
						catch (InterruptedException e) {}
				}
			}
				
		}).start();
    }

	private void loadAd()
	{
		//request ad
		Tapsell.initialize(this, appKey);
		TapsellAdRequestOptions ropt = new TapsellAdRequestOptions();
		ropt.setCacheType(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);
		Tapsell.requestAd(this, adKey, ropt, new TapsellAdRequestListener() {

				@Override
				public void onError(String p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAdAvailable(TapsellAd p1)
				{
					tapAd = p1;
				}

				@Override
				public void onNoAdAvailable()
				{
					// TODO: Implement this method
				}

				@Override
				public void onNoNetwork()
				{
					// TODO: Implement this method
				}

				@Override
				public void onExpiring(TapsellAd p1)
				{
					// TODO: Implement this method
				}

			});
	}

	private void ui()
	{ 
		setContentView(R.layout.main);
		layout = findViewById(R.id.layout);
		et = findViewById(R.id.et);
		bt = findViewById(R.id.bt);
		if (path.equals("empty")) {
			AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
			animationDrawable.setEnterFadeDuration(2000); 
			animationDrawable.setExitFadeDuration(4000);
			animationDrawable.start(); 
		}else{
			layout.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeFile(path))); 
		}
		
	}

	private void start()
	{
		path = getIntent().getExtras().getString("path");
		screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
		screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;
		handler = new Handler();
		
		Fawords_easy = new String[] {"اوقات فراغت","حاصلضرب","مستضعف","بادیگارد","اردیبهشت","فروردین","شهریور","اسفند","هوش مصنوعی","مادون قرمز","آرایشگاه","انسولین","دیابت","خروس جنگی","بلیط","المپیک","پرسپولیس","شنل قرمزی","گربه چکمه پوش","چرخ و فلک","ستون فقرات","کالبد شکافی","مثنوی معنوی","سعدی شیرازی","تبادل نظر","کلانتری","خوابگاه","چراغ مطالعه","شلغم","مخاطبین","استدلال","آموزش و پرورش","مداد رنگی","گلبول سفید","گذرنامه","ایاب و ذهاب","خواستگاری","سگ","گردو","تهران","قانون","زندگی","خانه","شجاع","زخم","مغز","فرار","کندو","سیر","حصار","چمن","زنبور","قرآن","جسم","انجماد","شوهر","سلام","کامپیوتر","یخچال","سازمان بهداشت جهانی","امپراطور","وصیت نامه","کتاب","دست","میز","اتومبیل","درخت","کتابخانه","سبز","آبی","رنگین کمان","ستاره","خورشید","لیوان","نوشابه","چیپس","دندان","جاروبرقی","ساعت","شیرکاکائو","لبخند","مردم","استعمال","مذاکره","متقابل","خراب","جوراب","جواب","سنگ","کوه","فیزیک","ریاضی","متواضع","متعارف","داوطلب","چراغ قوه","دستمال کاغذی","تفاوت","فندق","سیب زمینی","گلدان","کمربند","شکارگاه","منظره","بیابان","مادر","سپهسالار","دانشمندان","کارگاه","زرد","قرمز","گرگ","روباه","بنفش","قورمه سبزی","مگس کش","فوتبالیست","سرطان","کفش","فرشته","منطقه","گورخر","کشور","خراسان رضوی","برج میلاد","لباس","جمشید","نمایش","حلقه","عروس","یادداشت","پرنده","کمانداران","استراق سمع","کیمیاگر","تحصیلات","اکوسیستم","حیات وحش","پستانداران","مخابرات","تلفن همراه","روشنفکران","جمهوری اسلامی","آذربایجان غربی","نیسان آبی","سرمربی","جام جهانی","قاضی","مضایقه","فدراسیون","فاضلاب","بادمجان","آش رشته","دمنوش گیاهی","جراح قلب","متخصص","مهندس عمران","خواجه شیرازی","سیب سلامت","سهام عدالت","شوالیه","یوزپلنگ","رمان","مسافرت","مهاجرت","رودخانه","کوهستان","اتوبوس","کره جنوبی","ماه رمضان","تفریق","گلابی","آلبالو","سونوگرافی","دستگاه گوارش","زردآلو","جدول تناوبی","انیشتین","شرلوک هلمز","آناکوندا","ماهیگیری","اقیانوس اطلس","نماز جماعت","سیستان و بلوچستان","نادرشاه افشار"};
		Enwords = new String[] {"god","man","woman","milk","example","free","position","transform","public","framework","computer","artificial intelligence","teacher","student","classmate","travel","air plane","sea","green","blue","red","cyan","magenta","dark","university","tower","glass","ball","convetionalization","combination","calculator","instance","play","start","activity","book","popular","famous","kill","dead","understand","random","landscape","portrait","clear","warrior","water","yoghurt","definition","wonderful","amazing","immediately","unsystematicly","success","preposition","intermediate","advance","horizontal","vertical","orientation"};
		
		allwords = new ArrayList<TextView>();
		
		main = MediaPlayer.create(this, R.raw.mainmusic);
		send = MediaPlayer.create(this, R.raw.send);
		levelup = MediaPlayer.create(this, R.raw.levelup);
		gameover = MediaPlayer.create(this, R.raw.gameover);
		
		main.setLooping(true);
		main.start();
		
		nf = NumberFormat.getInstance(new Locale("fa", "IR"));
		
	}
	
	private void CreateWord() {
		
		if (stop == 1) {
		
		final TextView word = new TextView(this);
		if (level % 5 == 0 && level > 1) {
			word.setText(String.valueOf(new Random().nextInt(level) + 1) + "+" + String.valueOf(new Random().nextInt(level) + 1));
		}else{
		word.setText(Fawords_easy[new Random().nextInt(Fawords_easy.length)]);
		}
		if (level % 5 == 2 && level > 20) {
			word.setText(Enwords[new Random().nextInt(Enwords.length)]);
		}
		word.setTextColor(setColor(new Random().nextInt(8)));
		word.setTextSize(15);
		if (word.getText().length() > 8) {
			word.setX(new Random().nextInt(screen_width - (screen_width/3)));
		}else{
			word.setX(new Random().nextInt(screen_width - (screen_width/5)));
		}
		word.setY(-30);
		word.setTypeface(word.getTypeface(), Typeface.BOLD); 
		word.setId(id);
		allwords.add(word);
		layout.addView(word);
		id++;
	
		
		new Thread(new Runnable() {

				@Override
				public void run()
				{
					while(allwords.contains(word) && stop == 1) {
						try
						{
							Thread.sleep(100);
							handler.post(new Runnable() {

									@Override
									public void run()
									{
										word.setY(word.getY() + 1 + (level/10));
										if (word.getY() > (line.getY() - 10)) {
											allwords.remove(word);
											layout.removeView(word);
											gameOver();
										}
									
								}
								
							});
							
						}
						catch (InterruptedException e)
						{}
					}
				}
				
			
		}).start();
		}
	}
	
	public int setColor(int color) {
		
		int col = 0;
		switch (color) {
			case 0: col = Color.WHITE;
				break;
			case 1: col = Color.GREEN;
			    break;
			case 2: col = Color.BLUE;
			    break;
			case 3: col = Color.CYAN;
			    break;
			case 4: col = Color.DKGRAY;
			    break;
			case 5: col = Color.MAGENTA;
			    break;
			case 6: col = Color.RED;
			    break;
			case 7: col = Color.YELLOW;
			    break;
		}
		
		return col;
	}
	
	private void CreateLine() {
		
		line = new TextView(this);
		line.setText("");
		line.setBackground(new ColorDrawable(Color.parseColor("#fcff00")));
		line.setWidth(screen_width);
		line.setHeight(3);
		line.setTextSize(10);
		line.setX(0);
		line.setY(screen_height/2.5f);
		layout.addView(line);
		
		yourlevel = new TextView(this);
		yourlevel.setText("مرحله:" + level);
		yourlevel.setTextColor(Color.YELLOW);
		yourlevel.setTextSize(10);
		yourlevel.setTypeface(yourlevel.getTypeface(), Typeface.BOLD);
		yourlevel.setX(screen_width - (screen_width/6));
		yourlevel.setY(line.getY() + 5);
		layout.addView(yourlevel);
		
		best = new TextView(this);
		best.setText("بهترین:" + BEST);
		best.setTextColor(Color.LTGRAY);
		best.setTextSize(10);
		best.setTypeface(best.getTypeface(), Typeface.BOLD);
		best.setX(screen_width/30);
		best.setY(line.getY() + 5);
		layout.addView(best);
		
	}
	
	private void saveGame() {
		SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
		SharedPreferences.Editor editor = shared.edit();
		editor.putInt("level", level);
		if (level > BEST) {
			editor.putInt("best", level);
		}
		editor.apply();
	}
	
	private void loadGame() {
		SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
		if (shared.contains("level")) {
			level = shared.getInt("level", 0);
		}
		if (shared.contains("best")) {
			BEST = shared.getInt("best", 0);
		}
	}
	
	private void gameOver()
	{
		main.stop();
		gameover.start();
		stop = 0;
		int temp = level;
		level = 0;
		saveGame();
		level = temp;
		Button watch = new Button(MainActivity.this);
		watch.setX(0);
		watch.setY(screen_height/4);
		watch.setWidth(screen_width);
		watch.setText("دیدن تبلیغ و ادامه بازی");
		watch.setTextSize(20);
		watch.setBackgroundResource(R.drawable.selector);
		if (tapAd != null) {
			layout.addView(watch);
			watch.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View p1)
					{
						ad = 1;
						showrewardad();
					}
		});
	}
		
		Button reset = new Button(MainActivity.this);
		reset.setX(0);
		reset.setY(watch.getY() + 200);
		reset.setWidth(screen_width);
		reset.setText("شروع دوباره");
		reset.setTextSize(20);
		reset.setBackgroundResource(R.drawable.selector);
		layout.addView(reset);
		
		reset.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
					SharedPreferences.Editor editor = shared.edit();
					editor.putInt("level", 0);
					editor.apply();
					
					Intent i = new Intent(MainActivity.this, MainActivity.class);
					i.putExtra("path", path);
					startActivity(i);
					MainActivity.this.finish();
				}
		});
	}
	
	private void showrewardad() {
		TapsellShowOptions options = new TapsellShowOptions();
		options.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_PORTRAIT);
		tapAd.show(MainActivity.this, options, new TapsellAdShowListener() {

				@Override
				public void onOpened(TapsellAd p1)
				{
					Tapsell.setRewardListener(new TapsellRewardListener() {

							@Override
							public void onAdShowFinished(TapsellAd p1, boolean p2)
							{
								if (p2 && p1.isRewardedAd()) {
									//reward
									SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
									SharedPreferences.Editor editor = shared.edit();
									editor.putInt("level", level);
									editor.apply();

									Intent i = new Intent(MainActivity.this, MainActivity.class);
									i.putExtra("path", path);
									startActivity(i);
									MainActivity.this.finish();
								}else{
									level = 0;
									SharedPreferences shared = getSharedPreferences("wordSpeed", MODE_PRIVATE);
									SharedPreferences.Editor editor = shared.edit();
									editor.putInt("level", 0);
									editor.apply();
									main.stop();
									Intent i = new Intent(MainActivity.this, StartActivity.class);
									startActivity(i);
									MainActivity.this.finish();
								}	
							}
						});
				}

				@Override
				public void onClosed(TapsellAd p1)
				{
					// TODO: Implement this method
				}

			});
	}

	@Override
	public void onBackPressed()
	{
		main.stop();
		if (stop == 0) {
			level = 0;
		}
		saveGame();
		Intent i = new Intent(MainActivity.this, StartActivity.class);
		startActivity(i);
		MainActivity.this.finish();
	}

	@Override
	protected void onStop()
	{
		// TODO: Implement this method
		super.onStop();
		main.stop();
		stop = 0;
		MainActivity.this.finish();
	}

}
